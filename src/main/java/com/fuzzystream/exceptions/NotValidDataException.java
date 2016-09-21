package com.fuzzystream.exceptions;

public class NotValidDataException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotValidDataException() {
		super("Error in retrieving information from table in database.");
	}

}
