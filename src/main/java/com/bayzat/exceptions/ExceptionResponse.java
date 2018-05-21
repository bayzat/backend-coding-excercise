package com.bayzat.exceptions;

import java.util.Date;

public class ExceptionResponse {

	private Date time;
	private String message;
	private String details;

	public ExceptionResponse(Date time, String message, String details) {
		super();
		this.time = time;
		this.message = message;
		this.details = details;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
