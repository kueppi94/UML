package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum DataType { 
	BYTE("byte", "0"), SHORT("short", "0"), INT("int", "0"), 
	LONG("long", "0L"), FLOAT("float", "0.0f"), DOUBLE("double", "0.0d"), 
	BOOLEAN("boolean", "false"), CHAR("char", "'u0000'"), STRING("String", "null");
	
	public final String UML_NAME;
	public final String DEFAULT_VALUE;
	
	private DataType(String name, String defaultValue) {
		this.UML_NAME = name;
		this.DEFAULT_VALUE = defaultValue;
	}
	
	public StringProperty umlNameProperty() {
		return new SimpleStringProperty(UML_NAME);
	}
	
	public String getName() {
		return UML_NAME;
	}
	
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}
	
	@Override
	public String toString() {
		return UML_NAME;
	}
}
