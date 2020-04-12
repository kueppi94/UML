package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class UMLClass extends DraggableVBox {	
	
	private Label className; 
	
	private VBox propertyBox = new VBox();
	
	private boolean isAbstract;
	
	
	public UMLClass(String classname) {		
		className = new Label(classname);
		className.getStyleClass().add("umlClassName");			
		
		getChildren().add(className);		
		getChildren().add(new Separator());
		getChildren().add(propertyBox);
		getChildren().add(new Separator());
		
		getStyleClass().add("umlClass");		
	}
	
	public void addProperty(Property property) {
		propertyBox.getChildren().add(property);		
	}	
	
	public void replaceProperty(int pid, Property property) {
		propertyBox.getChildren().set(pid, property);
	}
	
	public void setClassName(String className) {
		this.className.setText(className);
	}
	
	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		
		if(isAbstract) {
			
		}
	}
	
	public boolean getIsAbstract() {
		return isAbstract;
	}
}
