package com.fuzzystream.exceptions;

public class NotValidQueryException extends RuntimeException {
	
	public NotValidQueryException() {
		super("Query with error(s). Please check the query.");
	}

}
