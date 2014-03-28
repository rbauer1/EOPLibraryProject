package common;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

	private Properties emailProperties;
	private Session session;

	public Mailer() {
		emailProperties = new PropertyFile("emailConfig.ini");
		session = Session.getDefaultInstance(emailProperties);
	}

	public void send(String to, String subject, String html) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailProperties.getProperty("mail.smtp.user"),
					emailProperties.getProperty("fromAlias")));
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject(subject);
			message.setContent(html, "text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect(emailProperties.getProperty("mail.smtp.host"),
					emailProperties.getProperty("mail.smtp.user"),
					emailProperties.getProperty("mail.smtp.password"));
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
