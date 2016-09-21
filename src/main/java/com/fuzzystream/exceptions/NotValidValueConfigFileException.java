package com.fuzzystream.exceptions;

public class NotValidValueConfigFileException extends RuntimeException {

	public NotValidValueConfigFileException() {
		super("Error in reading info from configuration file. Please check DB.conf.");
	}
}
