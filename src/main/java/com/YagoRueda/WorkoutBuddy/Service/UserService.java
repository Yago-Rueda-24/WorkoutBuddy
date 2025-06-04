package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.DTO.SignupDTO;
import com.YagoRueda.WorkoutBuddy.entity.PetPasswordEntity;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.exception.InpuDataException;
import com.YagoRueda.WorkoutBuddy.repository.PetPasswordRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private MessageDigest hash;

    private final PetPasswordRepository petPasswordRepository;

    @Autowired
    private MailService mailService;

    public UserService(UserRepository repository, PetPasswordRepository petPasswordRepository) {
        this.repository = repository;
        this.petPasswordRepository = petPasswordRepository;
        try {
            this.hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


    public List<UserEntity> findAll() {
        return repository.findAll();
    }

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

        petPasswordRepository.save(pet);

        try {
            mailService.mailLostPassword(user, token);
        } catch (MailSendException | MailAuthenticationException e) {
            throw new MessagingException(e.getMessage());
        }


    }


}
