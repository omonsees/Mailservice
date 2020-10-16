package rocks.monsees.mailservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rocks.monsees.mailservice.model.Message;
import rocks.monsees.mailservice.service.MailService;

@RestController
public class MailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    @CrossOrigin(origins = {"http://www.omonsees.de", "http://omonsees.de"})
    public void sendMail(@RequestBody Message message) {
        logger.info("JSON message: "+message.toString());
        try {
            mailService.sendMail(message);
        } catch (MailSendException exception) {
            logger.warn("MailSendException raised" + exception);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Sorry, but we couldn't send your message.", exception);
        }
    }
}
