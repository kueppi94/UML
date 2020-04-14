package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class UMLClass extends javafx.scene.Group {	
	
	//beinhaltet sämtliche Informationen (Klassenname, Eigenschaften & Methoden)	
	private DraggableVBox umlClassBox = new DraggableVBox();	
	
	private Label className;	
	
	//speichert alle Eigenschaften der Klasse
	private VBox propertyBox = new VBox();
	
	private boolean isAbstract;
	
	//Verbindungspunkte für Beziehungen zwischen Klassen, Interfaces usw.
	private ConnectionPoint topConnection;
	private ConnectionPoint botConnection;
	private ConnectionPoint leftConnection;
	private ConnectionPoint rightConnection;	
	
	
	public UMLClass(String classname) {		
		className = new Label(classname);
		className.getStyleClass().add("umlClassName");			
		
		umlClassBox.getChildren().add(className);		
		umlClassBox.getChildren().add(new Separator());
		umlClassBox.getChildren().add(propertyBox);
		umlClassBox.getChildren().add(new Separator());
		
		topConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.TOP);
		botConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.BOTTOM);
		leftConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.LEFT);
		rightConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.RIGHT);		
		
		umlClassBox.getStyleClass().add("umlClass");	
		
		getChildren().addAll(umlClassBox, topConnection, botConnection, leftConnection, rightConnection);
		
		//parent.getChildren().addAll(this, topConnection, botConnection, leftConnection, rightConnection);
				
		
		//this.getParent()
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

	public ConnectionPoint getTopConnection() {
		return topConnection;
	}

	public ConnectionPoint getBotConnection() {
		return botConnection;
	}

	public ConnectionPoint getLeftConnection() {
		return leftConnection;
	}

	public ConnectionPoint getRightConnection() {
		return rightConnection;
	}
}
