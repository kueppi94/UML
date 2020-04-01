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
	
	private int propertyIndex = 2;
	private int methodIndex = 4;	
	private SimpleStringProperty classname;	  
	
	
	public UMLClass(String classname) {		
		Label classNameLabel = new Label(classname);
		classNameLabel.getStyleClass().add("umlClassName");
		this.classname = new SimpleStringProperty(classname);
		classNameLabel.textProperty().bindBidirectional(this.classname);
		
		getChildren().add(classNameLabel);		
		getChildren().add(new Separator());
		getChildren().add(new Separator());
		
		getStyleClass().add("umlClass");		
	}
	
	public void addProperty(String visiblity, String datatype, String name) {
		getChildren().add(propertyIndex, new Label(
			String.format("%s %s: %s", visiblity, name, datatype)));
		propertyIndex++;		
	}	
}
