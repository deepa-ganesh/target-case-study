package com.target.marketing.exception;

public class TargetServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3963357060193688608L;

	private int code;
	private String message;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 */
	public TargetServiceException() {
		super("Exception occured in Service Layer");
	}

	/**
	 * @param message
	 */
	public TargetServiceException(String message) {
		this.message = message;
	}

	/**
	 * @param code
	 * @param message
	 */
	public TargetServiceException(int code, String message) {
		this.message = message;
		this.code = code;
	}

}
