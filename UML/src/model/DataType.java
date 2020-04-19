package model;

public enum DataType {
	BYTE("byte", "0"), SHORT("short", "0"), INT("int", "0"), 
	LONG("long", "0L"), FLOAT("float", "0.0f"), DOUBLE("double", "0.0d"), 
	BOOLEAN("boolean", "false"), CHAR("char", "'u0000'"), STRING("String", "null"),
	OTHER("", "null");
	
	private String name;
	private String defaultValue;
	
	private DataType(String name, String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
}
