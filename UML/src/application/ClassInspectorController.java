package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import model.DrawingPane;
import model.SelectableNode;
import model.UMLClass;

public class ClassInspectorController implements Initializable {
	
	private final ObjectProperty<UMLClass> inspectedClass = new SimpleObjectProperty<UMLClass>(new UMLClass("inital VAlue"));	
		
	@FXML
	private TextField Name;
	 
	public void initialize(URL location, ResourceBundle resources) {			
		System.out.println("init");				
		
		Name.textProperty().bindBidirectional(inspectedClass.get().classNameProperty());
		
		System.out.println(Name.getText());
		System.out.println(inspectedClass.get().classNameProperty());
		
	}

	public ObjectProperty<UMLClass> inspectedClassProperty() {
		return inspectedClass;
	}	
	
	public UMLClass getInspectedClass() {
		return inspectedClass.get();
	}	
	
	public void setInspectedClass(UMLClass inspectedClass) {		
		Name.textProperty().bindBidirectional(inspectedClass.classNameProperty());		
		this.inspectedClass.set(inspectedClass);		
	}
}
