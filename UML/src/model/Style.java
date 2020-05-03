package model;

public enum Style {
	UML_CLASS("umlClass"), UML_CLASS_NAME("umlClassName"), ITALIC("italic"), CONNECTIONLINE("connectionLine");
	
	private final String cssValue;
	
	Style(String cssValue){
		this.cssValue = cssValue;
	}
	
	public String css() {
		return cssValue;
	}
}
