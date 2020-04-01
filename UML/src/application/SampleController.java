package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Property;
import model.UMLClass;

import java.awt.MouseInfo;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SampleController {
	@FXML
	private AnchorPane Content;		
	
	public void initialize() {	    
		//testing
		 UMLClass uml = new UMLClass("Testklasse");	
		 
		 Content.getChildren().add(uml);		 
		 
		 Property test = new Property("+", "zaehler", "int");
		 		 
		 uml.addProperty(test);	
		 //end testing		 
	 }	 
	
	@FXML
	public void test() {
		((UMLClass)Content.getChildren().get(0)).addProperty(new Property("+", "zaehler", "int"));
		((UMLClass)Content.getChildren().get(0)).replaceProperty(0, new Property("-", "test", "double"));	
	}
}
