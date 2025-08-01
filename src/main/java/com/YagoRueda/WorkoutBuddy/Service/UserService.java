package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.DTO.RecoverPasswordDTO;
import com.YagoRueda.WorkoutBuddy.DTO.SignupDTO;
import com.YagoRueda.WorkoutBuddy.DTO.UserInfoDTO;
import com.YagoRueda.WorkoutBuddy.entity.FollowEntity;
import com.YagoRueda.WorkoutBuddy.entity.PetPasswordEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.exception.InpuDataException;
import com.YagoRueda.WorkoutBuddy.exception.InvalidaPetitionException;
import com.YagoRueda.WorkoutBuddy.repository.FollowRepository;
import com.YagoRueda.WorkoutBuddy.repository.PetPasswordRepository;
import com.YagoRueda.WorkoutBuddy.repository.RoutineRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private MessageDigest hash;

    private final RoutineRepository routine_repository;

    private final PetPasswordRepository petPasswordRepository;

    private final FollowRepository follow_repository;

    private final int LIMITECONSULTA = 10;

    @Autowired
    private MailService mailService;

    public UserService(UserRepository repository, RoutineRepository routineRepository, PetPasswordRepository petPasswordRepository, FollowRepository followRepository) {
        this.repository = repository;
        this.routine_repository = routineRepository;
        this.petPasswordRepository = petPasswordRepository;
        follow_repository = followRepository;
        try {
            this.hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    /**
     * Realiza el proceso de login verificando credenciales del usuario.
     *
     * @param user el usuario con nombre y contraseña a validar
     * @return {@code true} si las credenciales son válidas, de lo contrario {@code false}
     */
    public boolean login(UserEntity user) {

        hash.reset();
        hash.update(user.getPassword().getBytes());
        byte[] hashed_password = this.hash.digest();

        UserEntity foundUser = repository.findByUsername(user.getUsername());
        if (foundUser == null) {
            return false;
        }
        return foundUser.getPassword().equals(new String(hashed_password, StandardCharsets.UTF_8));
    }

    /**
     * Registra un nuevo usuario si los datos son válidos.
     *
     * @param signup datos de registro del usuario
     * @return 0 si se registró correctamente, 1 si las contraseñas no coinciden, 2 si el usuario ya existe
     */
    public int signUp(SignupDTO signup) {
        boolean exists = repository.existsByUsername(signup.getUsername());
        boolean correct_password = signup.getPasswordrepeat().equals(signup.getPassword());
        if (!correct_password) {
            return 1;
        }
        if (exists) {
            return 2;
        }
        hash.reset();
        hash.update(signup.getPassword().getBytes());
        byte[] hashed_password = this.hash.digest();
        //Guardar la información del usuario en base de datos
        UserEntity user = new UserEntity();
        user.setUsername(signup.getUsername());
        user.setEmail(signup.getEmail());
        user.setPassword(new String(hashed_password, StandardCharsets.UTF_8));
        repository.save(user);
        return 0;
    }

    /**
     * Inicia el proceso de recuperación de contraseña enviando un token al correo del usuario.
     *
     * @param username nombre de usuario
     * @throws InpuDataException  si los datos de entrada no son válidos
     * @throws MessagingException si falla el envío del correo
     */
    public void lostPassword(String username) throws InpuDataException, MessagingException {
        if (username == null || username.trim().isEmpty()) {
            throw new InpuDataException("El nombre de usuario no puede ser vacio");
        }

        if (!repository.existsByUsername(username)) {
            throw new InpuDataException("El usuario que has intrducido no existe");
        }

        UserEntity user = repository.findByUsername(username);
        long id = user.getId();
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new InpuDataException("El usuario no tiene un correo asignado");
        }


        List<PetPasswordEntity> openPetitions = petPasswordRepository.findByUserIdAndUtilizadoFalseAndExpiradoFalse(id);
        if (!openPetitions.isEmpty()) {
            openPetitions.forEach(arrPet -> arrPet.setExpirado(true));
            petPasswordRepository.saveAll(openPetitions);
        }


        PetPasswordEntity pet = new PetPasswordEntity();
        pet.setUser(user);
        pet.setExpirado(false);
        pet.setUtilizado(false);
        String token = UUID.randomUUID().toString();
        pet.setToken(token);
        pet.setPetition_date(Instant.now());

        petPasswordRepository.save(pet);

        try {
            mailService.mailLostPassword(user, token);
        } catch (MailSendException | MailAuthenticationException e) {
            throw new MessagingException(e.getMessage());
        }


    }

    /**
     * Cambia la contraseña de un usuario mediante una petición válida de recuperación.
     *
     * @param dto DTO con los datos necesarios para el cambio de contraseña
     * @throws InpuDataException         si los datos no son válidos
     * @throws InvalidaPetitionException si la petición ha expirado o ya ha sido utilizada
     */
    public void changePassword(RecoverPasswordDTO dto) throws InpuDataException, InvalidaPetitionException {
        String username = dto.getUsername();
        String token = dto.getToken();
        String password = dto.getPassword();
        String repeatPassword = dto.getRepeatpassword();

        // Validación de entradas
        if (!password.equals(repeatPassword)) {
            throw new InpuDataException("Las contraseñas no son iguales");
        }

        if (!repository.existsByUsername(username)) {
            throw new InpuDataException("El usuario no existe en el sistema");
        }

        // Recuperación y comprobaciones de la petición
        PetPasswordEntity pet;
        Optional<PetPasswordEntity> optional = petPasswordRepository.findByUsernameAndToken(username, token);
        if (optional.isPresent()) {
            pet = optional.get();

            if (pet.getExpirado()) {
                throw new InvalidaPetitionException("La petición ha expirado");
            }
            if (pet.getUtilizado()) {
                throw new InvalidaPetitionException("La petición ya ha sido utilizada");
            }

            Duration interval = Duration.between(pet.getPetition_date(), Instant.now());
            if (interval.toMinutes() > 10) {
                pet.setExpirado(true);
                petPasswordRepository.save(pet);
                throw new InvalidaPetitionException("La petición ha expirado");

            }


            //Recuperación del usuario
            UserEntity user = repository.findByUsername(username);

            // hasheo de la password
            hash.reset();
            hash.update(password.getBytes());
            byte[] hashed_password = this.hash.digest();

            //Almacenmiento de la password
            user.setPassword(new String(hashed_password, StandardCharsets.UTF_8));
            repository.save(user);

            //Actualización de la petición
            pet.setUtilizado(true);
            petPasswordRepository.save(pet);
        } else {
            throw new InpuDataException("No existe una petición activa para el usuario");
        }

    }

    /**
     * Lista un número limitado de usuarios, excluyendo al que realiza la consulta.
     *
     * @param username nombre del usuario que realiza la petición
     * @return lista de {@link UserInfoDTO} de otros usuarios
     */
    public List<UserInfoDTO> listLimitedUsers(String username) {
        List<UserEntity> entities = repository.findLimitedUsers(LIMITECONSULTA);
        List<UserInfoDTO> users = entities.stream().filter(entity -> !entity.getUsername().equals(username))
                .map(entity -> {
                    UserInfoDTO dto = new UserInfoDTO();
                    dto.setId(entity.getId());
                    dto.setUsername(entity.getUsername());
                    return dto;
                })
                .toList();

        return users;
    }

    /**
     * Lista de usuarios cuyo nombre comienza con cierto filtro, excluyendo al usuario que realiza la búsqueda.
     *
     * @param filteredUsername prefijo del nombre de usuario a buscar
     * @param username         nombre del usuario solicitante para excluirlo del resultado
     * @return lista de {@link UserInfoDTO} que coinciden con el filtro
     */
    public List<UserInfoDTO> listFilteredLimitedUsers(String filteredUsername, String username) {
        PageRequest pageRequest = PageRequest.of(0, LIMITECONSULTA);
        List<UserEntity> entities = repository.findByUsernameStartingWith(filteredUsername, pageRequest);
        List<UserInfoDTO> users = entities.stream().filter(entity -> !entity.getUsername().equals(username))
                .map(entity -> {
                    UserInfoDTO dto = new UserInfoDTO();
                    dto.setId(entity.getId());
                    dto.setUsername(entity.getUsername());
                    return dto;
                })
                .toList();

        return users;
    }

    /**
     * Función de servicio para obtener los datos de un usuario
     * @param follower El username del usuario que hace la llamada para obtener información
     * @param followed El username del usuario del que se desea obtener la información
     * @return {@link UserInfoDTO} con la información de un usuario
     * @throws InpuDataException
     */
    public UserInfoDTO GetUserInfo( String follower,  String followed) throws InpuDataException {

        if (!repository.existsByUsername(followed)) {
            throw new InpuDataException("El seguido no existe en la base de datos");
        }
        if (!repository.existsByUsername(follower)) {
            throw new InpuDataException("El seguidor no existe en la base de datos");
        }




        UserEntity user_followed = repository.findByUsername(followed);
        UserEntity user_follower = repository.findByUsername(follower);
        boolean isfollowing =follow_repository.existsByFollowerAndFollowed(user_follower,user_followed);
        long num_follower = follow_repository.countByFollowed(user_followed);
        long num_followed = follow_repository.countByFollower(user_followed);
        UserInfoDTO dto = new UserInfoDTO();

        int num_routines = routine_repository.findByUserUsername(followed).size();
        dto.setId(user_followed.getId());
        dto.setUsername(user_followed.getUsername());
        dto.setRoutines(num_routines);
        dto.setFollowing(isfollowing);
        dto.setFollowed(num_followed);
        dto.setFollowers(num_follower);

        return dto;


    }

    /**
     * Función de servicio para el follow entre 2 usuarios
     * @param follower El username del usuario que sera el seguidor
     * @param followed El username del usuario que sera el seguido
     * @throws InpuDataException
     */
    public void follow(String follower, String followed) throws InpuDataException {
        if (!repository.existsByUsername(follower)) {
            throw new InpuDataException("No existe el seguidor");
        }
        if (!repository.existsByUsername(followed)) {
            throw new InpuDataException("No existe el seguido");
        }


        UserEntity user_follower = repository.findByUsername(follower);

        UserEntity user_followed = repository.findByUsername(followed);

        if (user_followed.getId() == user_follower.getId()) {
            throw new InpuDataException("Un usuario no se puede seguir a si mismo");
        }
        if (follow_repository.existsByFollowerAndFollowed(user_follower, user_followed)) {
            throw new InpuDataException("los usuarios ya se estan siguiendo");
        }

        FollowEntity follow = new FollowEntity();
        follow.setFollower(user_follower);
        follow.setFollowed(user_followed);
        follow_repository.save(follow);

    }

    /**
     * Función de servicio para el unfollow entre 2 usuarios
     * @param follower El username del usuario que sera el seguidor
     * @param followed El username del usuario que sera el seguido
     * @throws InpuDataException
     */
    public void unfollow(String follower, String followed) throws InpuDataException {
        if (!repository.existsByUsername(follower)) {
            throw new InpuDataException("No existe el seguidor");
        }
        if (!repository.existsByUsername(followed)) {
            throw new InpuDataException("No existe el seguido");
        }

        UserEntity user_follower = repository.findByUsername(follower);

        UserEntity user_followed = repository.findByUsername(followed);

        if (!follow_repository.existsByFollowerAndFollowed(user_follower, user_followed)) {
            throw new InpuDataException("no existe la relacion entre usuarios");
        }


        FollowEntity follow = follow_repository.findByFollowerAndFollowed(user_follower, user_followed);
        follow_repository.delete(follow);

    }

}
