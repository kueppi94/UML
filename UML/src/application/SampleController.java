package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.UMLClass;

import java.awt.MouseInfo;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SampleController {
	@FXML
	private AnchorPane Content;		 
	
	public void initialize() {	        
		 UMLClass uml = new UMLClass("Testklasse");	
		 
		 Content.getChildren().add(uml);
		 uml.addProperty("private", "int", "test");			
		 uml.addProperty("Hallo", "doub", "test");		
	 }	 
}
