package org.goout.stalker.service.email;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.goout.stalker.JSONUtil;
import org.goout.stalker.config.GlobalConfig;
import org.goout.stalker.model.EmailConfig;
import org.goout.stalker.model.EventsByArtists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EmailService {

	@Inject
	private GlobalConfig config;

	@EJB
	private JSONUtil jsonUtil;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void send(EventsByArtists events) {

		String json = jsonUtil.convertToJson(events);

		String username = config.MAIL_USERNAME();
		String password = config.MAIL_PASSWORD();

		Properties props = new Properties();
		props.put("mail.smtp.host", config.SMTP_SERVER());
		props.put("mail.smtp.socketFactory.port", config.SMTP_PORT());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", config.SMTP_PORT());

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			// message.set
			message.setFrom(new InternetAddress(config.MAIL_USERNAME()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.MAIL_USERNAME()));
			message.setSubject(config.MAIL_SUBJECT());
			message.setText(config.EMAIL_INTRO() + json);

			Transport.send(message);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	public void testConnection(EmailConfig emailConfig) throws ConnectionError {

		try {

			String username = emailConfig.getUsername();
			String password = emailConfig.getPassword();

			Properties props = new Properties();
			props.put("mail.smtp.host", emailConfig.getSmtpServer());
			props.put("mail.smtp.socketFactory.port", emailConfig.getSmtpPort());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", emailConfig.getSmtpPort());
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Transport transport = session.getTransport("smtp");
			transport.connect(emailConfig.getSmtpServer(), Integer.valueOf(emailConfig.getSmtpServer()), username,
					password);
			transport.close();
		} catch (Exception e) {

			throw new ConnectionError("Wasn't possible to establish connection due to:" + e.getMessage());

		}

	}

}
