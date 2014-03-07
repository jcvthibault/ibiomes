package edu.utah.bmi.ibiomes.web;

import java.io.Serializable;
import java.util.List;

/**
 * Service response (success/failure, data, and detailed message)
 * @author Julien Thibault
 *
 */
public class IBIOMESResponse {

	private String message;
	private boolean success;
	private Object data;
	
	/**
	 * Create new response
	 * @param success Success
	 * @param message Message
	 * @param data Data
	 */
	public IBIOMESResponse(boolean success, String message, Object data){
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
