package model;

/*
 * Sammlung von Css-Styles
 * Leichte �bersicht und �nderung von bestehenden Styles
 */

public enum Style {
	UML_ENTITY("umlEntity"), UML_ENTITY_NAME("umlEntityName"), ITALIC("italic"), CONNECTIONLINE("connectionLine"), H_BOX("h-box"),
	POSITIONCHANGER("positionChanger");
	
	private final String cssValue;
	
	Style(String cssValue){
		this.cssValue = cssValue;
	}
	
	public String css() {
		return cssValue;
	}
}
