package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.DrawingPane;
import model.UMLClass;

public class ClassInspectorController {
	private DrawingPane pane;
	
	@FXML
	private TextField Name;
	
	 public void injectMainController(DrawingPane pane){
	        this.pane = pane;
    }
	 
	 public void initialize() {	 	
		 
	 }
}
