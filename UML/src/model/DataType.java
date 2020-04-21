package model;

import javafx.beans.property.SimpleStringProperty;

public enum DataType {
	BYTE("byte", "0"), SHORT("short", "0"), INT("int", "0"), 
	LONG("long", "0L"), FLOAT("float", "0.0f"), DOUBLE("double", "0.0d"), 
	BOOLEAN("boolean", "false"), CHAR("char", "'u0000'"), STRING("String", "null"),
	OTHER("", "null");
	
	private SimpleStringProperty umlName = new SimpleStringProperty();
	private String defaultValue;
	
	private DataType(String name, String defaultValue) {
		this.umlName.set(name);
		this.defaultValue = defaultValue;
	}
	
	public SimpleStringProperty umlNameProperty() {
		return umlName;
	}
	
	public String getName() {
		return umlName.get();
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
}
