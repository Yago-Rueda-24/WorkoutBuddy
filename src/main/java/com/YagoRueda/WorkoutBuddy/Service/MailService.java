package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.exception.InpuDataException;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
public class MailService {
    private final UserRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public MailService(UserRepository repository) {
        this.repository = repository;
    }

    public void lostPassword(String username) throws InpuDataException, MessagingException {
        if (username == null || username.trim().isEmpty()) {
            throw new InpuDataException("El nombre de usuario no puede ser vacio");
        }

        if (!repository.existsByUsername(username)) {
            throw new InpuDataException("El usuario que has intrducido no existe");
        }

        UserEntity user = repository.findByUsername(username);

        if (user.getEmail().trim().isEmpty()) {
            throw new InpuDataException("El usuario no tiene un correo asignado");
        }

        new Thread(() -> {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(user.getEmail());
            mensaje.setSubject("Restablecimiento de contraseña");
            mensaje.setText("Prueba");
            mensaje.setFrom("Workoutbuddy@gmail.com");
            mailSender.send(mensaje);
        }).start();


    }
}
