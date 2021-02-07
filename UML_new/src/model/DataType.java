package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Beinhaltet die gängigen Datentypen.
 * Speichert zudem den Standardwert der verschiedenen Datentypen (wichtig für das Erstellen von Methoden mit einem Rückgabewert) * 
 */

public enum DataType { 
	BYTE("byte", "0", "byte"), SHORT("short", "0", "short"), INT("int", "0", "int"), 
	LONG("long", "0L", "long"), FLOAT("float", "0.0f", "float"), DOUBLE("double", "0.0d", "double"), 
	BOOLEAN("boolean", "false", "boolean"), CHAR("char", "'u0000'", "char"), STRING("String", "null", "String");
	
	public final String UML_NAME;
	public final String DEFAULT_VALUE;
	
	public final String JAVA_VALUE;
	
	private DataType(String name, String defaultValue, String javaValue) {
		this.UML_NAME = name;
		this.DEFAULT_VALUE = defaultValue;
		this.JAVA_VALUE = javaValue;
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
