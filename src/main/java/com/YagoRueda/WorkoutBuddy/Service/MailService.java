package com.YagoRueda.WorkoutBuddy.Service;

import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.exception.InpuDataException;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public MailService(UserRepository repository) {
    }

    public void mailLostPassword(UserEntity user, String token) throws MailSendException, MailAuthenticationException {
        try {
            new Thread(() -> {
                SimpleMailMessage mensaje = new SimpleMailMessage();
                mensaje.setTo(user.getEmail());
                mensaje.setSubject("Restablecimiento de contraseña");
                mensaje.setText("Prueba");
                mensaje.setFrom("Workoutbuddy@gmail.com");
                mailSender.send(mensaje);
            }).start();
        } catch (MailSendException e) {
            throw new MailSendException("Error en el envio del mail");
        } catch (MailAuthenticationException e) {
            throw new MailAuthenticationException("Error en la autenticación del mail");
        }

    }
}
