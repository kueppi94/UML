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
		return properties.add(new Property(Visibility.PUBLIC, property.getName(), property.getDataType()));	
	}

	@Override
	public void setProperty(int pid, Property property) {
		properties.set(pid, new Property(Visibility.PUBLIC, property.getName(), property.getDataType()));		
		
	}

	@Override
	public boolean addMethod(Method method) {
		return methods.add(new Method(Visibility.PUBLIC, true, method.getName(), method.parameterProperty(), method.getReturnType()));	
	}

	@Override
	public void setMethod(int mid, Method method) {
		methods.set(mid, new Method(Visibility.PUBLIC, true, method.getName(), method.parameterProperty(), method.getReturnType()));	
	}	
}
