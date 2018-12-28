package org.goout.stalker.service.email;

public class ConnectionError extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg;

	public ConnectionError(String errorMessage) {
		super(errorMessage);
		this.msg = errorMessage;

	}

	public ConnectionError(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

	public String getMsg() {
		return msg;
	}

}
