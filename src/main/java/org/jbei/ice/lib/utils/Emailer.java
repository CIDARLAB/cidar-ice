package org.jbei.ice.lib.utils;

import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dto.ConfigurationKey;

<<<<<<< HEAD
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
=======
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

/**
 * Utility methods for email.
 * <p>
 * The SMTP server is specified in the configuration file and the admin email is also used for all communications
 *
 * @author Hector Plahar
 */
public class Emailer {

    private static final String GMAIL_APPLICATION_PASSWORD = "iceicecidar209";

    /**
     * Sends an email to the specified recipient with a carbon copy send to the specified ccEmail.
     * The email contains the specified subject and body
     *
     * @param receiverEmail Address to send email to.
     * @param ccEmail       Address to send carbon copy to.
     * @param subject       Text of subject.
     * @param body          Text of body.
     */
    public static boolean send(String receiverEmail, String ccEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.getConfigValue(ConfigurationKey.ADMIN_EMAIL), GMAIL_APPLICATION_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Utils.getConfigValue(ConfigurationKey.ADMIN_EMAIL)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            //message.setRecipients(Message.RecipientType.CC,
            //        InternetAddress.parse(ccEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            Logger.error(e);
            return false;
        }
    }

    /**
     * Send an email, and cc to the administrator.
     *
     * @param receiverEmail Address to send email to.
     * @param subject       Subject text.
     * @param body          Body text.
     */
    public static boolean send(String receiverEmail, String subject, String body) {
        return send(receiverEmail, Utils.getConfigValue(ConfigurationKey.ADMIN_EMAIL), subject, body);
    }

    /**
     * Send an email to the administrator.
     *
     * @param subject Subject text.
     * @param body    Body text.
     */
    public static void error(String subject, String body) {
        send(Utils.getConfigValue(ConfigurationKey.ADMIN_EMAIL), subject, body);
    }
}
