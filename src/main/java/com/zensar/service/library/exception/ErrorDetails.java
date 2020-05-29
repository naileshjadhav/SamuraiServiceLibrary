package com.zensar.service.library.exception;

import java.util.Date;

public class ErrorDetails {

	private Date timestamp;
	private String message;
	private String details;
	private String Status;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public ErrorDetails() {
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
