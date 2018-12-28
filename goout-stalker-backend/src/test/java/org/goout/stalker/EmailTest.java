package org.goout.stalker;

import org.goout.stalker.model.EmailConfig;
import org.goout.stalker.service.email.ConnectionError;
import org.goout.stalker.service.email.EmailService;
import org.junit.Test;

public class EmailTest {

	@Test(expected=ConnectionError.class)
	public void testWrongConnectionDetails() throws ConnectionError {

		EmailConfig config = EmailConfig.builder().withPassword("wrongPassword").withUsername("wrongEmail@gmail.com")
				.withSmtpPort("465").withSmtpServer("string").build();

		EmailService service = new EmailService();
		service.testConnection(config);

	}

}
