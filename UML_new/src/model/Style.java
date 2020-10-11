package model;

public enum Style {
	UML_ENTITY("umlEntity"), UML_ENTITY_NAME("umlEntityName"), ITALIC("italic"), CONNECTIONLINE("connectionLine"), H_BOX("h-box");
	
	private final String cssValue;
	
	Style(String cssValue){
		this.cssValue = cssValue;
	}
	
	public String css() {
		return cssValue;
	}
}
