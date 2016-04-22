package com.vesti.fonis.fonisvesti.email;

/**
 * Created by Sarma on 4/21/2016.
 */
import com.vesti.fonis.fonisvesti.email.JSSEProvider;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body,String[] fileNames, String sender, String recipients) throws Exception {
        try{
//            MimeMessage message = new MimeMessage(session);
//            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
//            message.setSender(new InternetAddress(sender));
//            message.setSubject(subject);
//            message.setDataHandler(handler);
//            if (recipients.indexOf(',') > 0)
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
//            else
//                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

            // Set Subject: header field
            message.setSubject(subject);

//            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
//
//            // Now set the actual message
            messageBodyPart.setText(body);
//
//            // Create a multipart message
            Multipart multipart = new MimeMultipart();
//
//            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(fileNames[0]);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Slika-1");

            BodyPart messageBodyPart1 = new MimeBodyPart();
            DataSource source1 = new FileDataSource(fileNames[1]);
            messageBodyPart1.setDataHandler(new DataHandler(source1));
            messageBodyPart1.setFileName("Slika-2");

            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(messageBodyPart1);

            // Send the complete message parts
            message.setContent(multipart);
            Transport.send(message);
        }catch(Exception e){
            throw new MessagingException("Message not sent.");
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}
