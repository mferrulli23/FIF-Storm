package com.fuzzystream.fif_core;


import java.net.URI;

public class Resource {
	
	//da approfondire URI
	private URI identificator;
	
	public Resource(URI identificator) {
		this.identificator = identificator;
	}
	
	public void setURI(URI id){
		this.identificator = id;
	}
	
	public URI getURI(){
		return this.identificator;
	}

}
