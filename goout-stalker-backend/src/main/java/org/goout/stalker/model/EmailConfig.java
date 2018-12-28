package org.goout.stalker.model;

import javax.annotation.Generated;

public class EmailConfig {

	private String smtpServer;
	private String smtpPort;
	private String username;
	private String password;
	private Boolean enabled;
	private String interval;
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Generated("SparkTools")
	private EmailConfig(Builder builder) {
		this.smtpServer = builder.smtpServer;
		this.smtpPort = builder.smtpPort;
		this.username = builder.username;
		this.password = builder.password;
		this.enabled = builder.enabled;
		this.interval = builder.interval;
		this.city = builder.city;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public EmailConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Creates builder to build {@link EmailConfig}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link EmailConfig}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String smtpServer;
		private String smtpPort;
		private String username;
		private String password;
		private Boolean enabled;
		private String interval;
		private String city;

		private Builder() {
		}

		public Builder withSmtpServer(String smtpServer) {
			this.smtpServer = smtpServer;
			return this;
		}

		public Builder withSmtpPort(String smtpPort) {
			this.smtpPort = smtpPort;
			return this;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withEnabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Builder withInterval(String interval) {
			this.interval = interval;
			return this;
		}

		public Builder withCity(String city) {
			this.city = city;
			return this;
		}

		public EmailConfig build() {
			return new EmailConfig(this);
		}
	}

}
