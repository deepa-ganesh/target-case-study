package com.target.marketing.common;

public enum TargetResponse {

	SUCCESS(1000, "Success"),
	COMMENT_NOT_FOUND(1001, "Invalid payload, comment is not found"),
	BLANK_COMMENT(1002, "Invalid payload, comment is empty");

	private int responseCode;
	private String responseMessage;

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	private TargetResponse(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
}
