package com.fuzzystream.fif_core;

public class Attribute {
	
	private String attribute;
	
	public Attribute(String attribute) {
		this.attribute = attribute.toLowerCase();
	}
	
	public String getAttributeName(){
		return this.attribute;
	}
	
	public void setAttributeName(String newAttribute){
		this.attribute = newAttribute;
	}
	
	public boolean equals(Object obj) {

		assert (obj!=null) : "Attribute : object parameter can't be null.";
		assert (obj instanceof Attribute) : "Attribute : can't match an attribute with an object that is not an attribute.";
		
		
		Attribute attr = (Attribute) obj;
		return (this.getAttributeName().compareToIgnoreCase(
				(attr.getAttributeName())) == 0);
	}

}
