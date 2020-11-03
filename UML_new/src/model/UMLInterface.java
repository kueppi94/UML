package model;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class UMLInterface extends Entity {
	
	private static final int INTERFACE_ID = 0;	
	private static final String INTERFACE_TEXT = "<<Interface>>";
	
	
	public UMLInterface(String interfaceName) {	
		super(interfaceName);	
		
		isAbstractProperty.set(true);
		
		umlEntityBox.getChildren().add(INTERFACE_ID, new Label(INTERFACE_TEXT));		
	}

	@Override
	public boolean addProperty(Property property) {
		property.setVisibility(Visibility.PUBLIC);
		
		return properties.add(property);	
	}

	@Override
	public void setProperty(int pid, Property property) {
		property.setVisibility(Visibility.PUBLIC);
		
		properties.set(pid, property);		
		
	}

	@Override
	public boolean addMethod(Method method) {
		method.setVisibility(Visibility.PUBLIC);
		method.setAbstract(true);
		
		
		return methods.add(method);	
	}

	@Override
	public void setMethod(int mid, Method method) {
		method.setVisibility(Visibility.PUBLIC);
		method.setAbstract(true);
		
		methods.set(mid, method);	
	}	
}
