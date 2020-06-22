package model;

import javafx.beans.property.SimpleStringProperty;

public enum Visibility {
	PUBLIC ("+", "public"), PROTECTED ("#", "protected"), NO_MODIFIER("~", ""), PRIVATE("-", "private");
	 
	public final String UML_SIGN;
	public final String ACCESS_MODIFIER_JAVA;
	
	private Visibility(String umlSign, String accessModifierJava) {
		this.UML_SIGN = umlSign;
		this.ACCESS_MODIFIER_JAVA = accessModifierJava;
	}	
	
	@Override
	public String toString() {		
		return UML_SIGN;	
	}	
}
