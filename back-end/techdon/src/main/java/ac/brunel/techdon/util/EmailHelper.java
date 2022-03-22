package ac.brunel.techdon.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailHelper {

    private static final JavaMailSender mailSender;

    public static void sendEmail(SimpleMailMessage message) {

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleMailMessage getNewOfferEmail(String email, String firstName, String type) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("TechDon: Congratulations, you've been offered a device");
        message.setText("Dear " + firstName + ",\n\n"
                + "Congratulations!\nYou have been offered a new " + type + ".\n"
                + "Follow the link below to view the details and accept or decline.\n\n"
                + "http://localhost:3000/offered/"
                + "\n\n"
                + "Regards,\n"
                + "TechDonate Team");
        message.setTo(email);
        message.setFrom(Preferences.getEmailUsername());
        return message;
    }

    public static SimpleMailMessage getContactDetailsEmail(String email, String firstName, String otherName, String otherPhone, String otherEmail, boolean isDonor) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("TechDon: Donation contact details");
        message.setText("Dear " + firstName + ",\n\n"
                + (isDonor ? "A student has accepted your device." : "You have accepted a device.")
                + "\nFollow the link below to view the details and accept or decline"
                + "\n\n"
                + "Here are their contact details to get in touch:\n"
                + "Name: " + otherName
                + "\nPhone: " + otherPhone
                + "\nEmail: " + otherEmail
                + "\n\n"
                + "Regards,"
                + "TechDonate Team");
        message.setTo(email);
        message.setFrom(Preferences.getEmailUsername());
        return message;
    }

    public static SimpleMailMessage getRegisterEmail(String email, String firstName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Welcome to TechDon ");
        message.setText("Dear " + firstName + ",\n\n"
                + "Thank you for joining Tech Donate."
                +"\n\n"
                + "Your registration has been successful!\n\n"
                + "Regards,\n"
                + "TechDonate Team");
        message.setTo(email);
        message.setFrom(Preferences.getEmailUsername());
        return message;
    }
    

    static {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(Preferences.getEmailHost());
        mailSenderImpl.setPort(Preferences.getEmailPort());
        mailSenderImpl.setUsername(Preferences.getEmailUsername());
        mailSenderImpl.setPassword(Preferences.getEmailPassword());

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("spring.mail.properties.mail.smtp.auth", "true");
        props.put("spring.mail.properties.mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        mailSender = mailSenderImpl;
    }
}