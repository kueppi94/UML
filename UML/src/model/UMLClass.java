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
	
	private SimpleStringProperty classname;	  
	
	private VBox propertyBox = new VBox();
	
	
	public UMLClass(String classname) {		
		Label classNameLabel = new Label(classname);
		classNameLabel.getStyleClass().add("umlClassName");
		this.classname = new SimpleStringProperty(classname);
		classNameLabel.textProperty().bindBidirectional(this.classname);
		
		getChildren().add(classNameLabel);		
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
}
