package model;

import javafx.beans.property.SimpleStringProperty;

public enum Visibility {
	PUBLIC ("+", "public"), PROTECTED ("#", "protected"), NO_MODIFIER("~", ""), PRIVATE("-", "private");
	
	private String umlSign;
	private String accessModifierJava;
	
	private Visibility(String umlSign, String accessModifierJava) {
		this.umlSign = umlSign;
		this.accessModifierJava = accessModifierJava;
	}	
	
	public SimpleStringProperty toUML() {
		return new SimpleStringProperty(umlSign);
	}
	
	public String toJava() {
		return accessModifierJava;
	}
}
