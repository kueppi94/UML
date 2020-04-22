package model;

import java.awt.Font;
import java.util.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Method extends javafx.scene.layout.HBox implements Styles {
	
	private Visibility visibility;
	private SimpleBooleanProperty isAbstractProperty = new SimpleBooleanProperty();	
	//null bei void
	private DataType returnType;
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	
	private Map<String, DataType> parameters = new HashMap<String, DataType>();
	
	public Method(Visibility visibility, boolean isAbstract, String name, Map<String, DataType> parameters, DataType returnType) {
		Label visibilityLabel = new Label();		
		Label returnTypeLabel = new Label();
		Label nameLabel = new Label();
		HBox propertyBox = new HBox();
		
		setVisibility(visibility);
		setAbstract(isAbstract);
		setName(name);
		setReturnType(returnType);
		setParameters(parameters);		
				
		visibilityLabel.textProperty().bind(this.visibility.umlSignProperty());			
		returnTypeLabel.textProperty().bind(this.returnType.umlNameProperty());
		nameLabel.textProperty().bind(this.nameProperty);
		isAbstractProperty.addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue<?> o,Object oldVal, Object newVal){	        	
	        	if((Boolean)newVal)
	        		nameLabel.getStyleClass().add(ITALIC);
	             else
	            	 nameLabel.getStyleClass().remove(ITALIC);
	        }
	      });		
		
		getChildren().addAll(visibilityLabel, nameLabel, propertyBox, returnTypeLabel);
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isAbstract() {
		return isAbstractProperty.get();
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstractProperty.set(isAbstract);
	}

	public DataType getReturnType() {
		return returnType;
	}

	public void setReturnType(DataType returnType) {
		this.returnType = returnType;
	}

	public String getName() {
		return this.nameProperty.get();
	}

	public void setName(String name) {
		this.nameProperty.set(name);
	}

	public Map<String, DataType> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, DataType> parameters) {
		this.parameters = parameters;
	}
}
