package rocks.monsees.mailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import rocks.monsees.mailservice.model.Message;

@Component
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSender javaMailSender;
    private final Environment environment;

    public MailService(JavaMailSender javaMailSender, Environment environment) {
        this.javaMailSender = javaMailSender;
        this.environment = environment;
    }

    public void sendMail(Message message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(environment.getProperty("mail.from"));
        mail.setTo(environment.getProperty("mail.to"));
        mail.setReplyTo(message.getEmail());
        mail.setSubject(message.getSubject());
        mail.setText(message.getText());

        logger.info("SimpleMailMessage content: " + mail.toString());

        javaMailSender.send(mail);
    }


}
