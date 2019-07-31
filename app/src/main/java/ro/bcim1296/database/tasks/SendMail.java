package ro.bcim1296.database.tasks;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import ro.bcim1296.model.Note;

public class SendMail {

    private static void send(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

         try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText("hhhhhhh");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void initAndSend(Note note) {
        String from = "bcsongi19@gmail.com";
        String pass = "helloo19+++";
        String[] to = {"bcsongi19@gmail.com"}; // list of recipient email addresses
        String subject = /*note.getTitle();*/"Mail kuldes proba";
        String body = /*note.getDescription() + "\n" + note.getDueDate()*/"Welcome!";
        send(from, pass, to, subject, body);
    }
}
