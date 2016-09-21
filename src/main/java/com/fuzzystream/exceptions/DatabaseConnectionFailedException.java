package com.fuzzystream.exceptions;

public class DatabaseConnectionFailedException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseConnectionFailedException() {
		super("Error in connection to database. Please check connection.");
	}

}
