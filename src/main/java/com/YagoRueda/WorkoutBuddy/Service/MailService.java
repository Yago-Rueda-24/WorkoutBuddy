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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

                String enlace = "http://localhost:8080/resetPassword.html"
                        + "?user=" + URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8)
                        + "&token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
                String texto = "Hola " + user.getUsername() + ",\n\n"
                        + "Para restablecer tu contraseña, haz clic en el siguiente enlace:\n"
                        + enlace + "\n\n"
                        + "Este enlace solo es válido una vez y caduca pronto. \n\n"+
                        "Si no has solicitado el cambio de contraseña ignora este mensaje";

                mensaje.setText(texto);
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
