package model;

import javafx.beans.property.SimpleStringProperty;

public enum Visibility {
	PUBLIC ("+", "public"), PROTECTED ("#", "protected"), NO_MODIFIER("~", ""), PRIVATE("-", "private");
	 
	private SimpleStringProperty umlSign = new SimpleStringProperty();
	private String accessModifierJava;
	
	private Visibility(String umlSign, String accessModifierJava) {
		this.umlSign.set(umlSign);
		this.accessModifierJava = accessModifierJava;
	}	
	
	public SimpleStringProperty umlSignProperty() {
		return umlSign;
	}
	
	public String toJava() {
		return accessModifierJava;
	}	
	
	//wird benötigt, um Objekte dieser Klasse zu aktualisiern. Bei einer einfachen Zuweisung gehen die Bindings verloren...
	public void update(Visibility newVisibility) {		
		umlSign.set(newVisibility.umlSign.get());
		accessModifierJava = newVisibility.accessModifierJava;
		
	}
}
