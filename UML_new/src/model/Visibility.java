package model;

import javafx.beans.property.SimpleStringProperty;

/*
 * Sammlung aller in UML definierten Sichtbarkeiten und deren UML-Notation
 */

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
	
	public static Visibility find(String visibility) {		
		if( visibility == null || visibility.equals(""))
			return NO_MODIFIER;
		
		visibility = visibility.toUpperCase();		
		
		for(Visibility v : values()) {
			if(v.name().equals(visibility))
				return v;
		}
		
		return NO_MODIFIER;
	}
}
