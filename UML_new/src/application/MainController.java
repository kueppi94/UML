

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

import java.awt.MouseInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainController {	
	@FXML
	private AnchorPane Content;		
	
	@FXML
	private AnchorPane InspectorPane;
	
	@FXML
	private Button NewUMLClassButton;
	
	@FXML
	private Button NewUMLInterfaceButton;
	
	@FXML
	private Button NewInheritanceButton;
	
	
	@FXML
	private MenuItem loadJavaProject;
	
	@FXML
	private MenuItem saveAsJavaProject;
	
	public void initialize() {	
		new SelectionHandler(Content, InspectorPane);
		
		 buttonCreationHelper(NewUMLClassButton, "/images/Class.PNG");
		 buttonCreationHelper(NewUMLInterfaceButton, "/images/Interface.PNG");		
		 buttonCreationHelper(NewInheritanceButton, "/images/Inheritance.png");
		
		
		NewUMLClassButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String name = textInputDialogHelper();
		    	
		    	if(name == null)
		    		return;		    	
		    			    	
		    	
		    	UMLClass umlClass = new UMLClass(name);
		    	
		    	Content.getChildren().add(umlClass);
		    }
		});
		
		
		NewUMLInterfaceButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String name = textInputDialogHelper();	
		    	
		    	if(name == null)
		    		return;
		    	
		    	UMLInterface umlInterface = new UMLInterface(name);
		    	
		    	Content.getChildren().add(umlInterface);		    	
		    }
		});
		
		
		NewInheritanceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Optional<Pair<UMLClass, UMLClass>> userInput =  showInheritanceDialog();			
				
				if(userInput == null)
					return;				
				
				UMLClass superclass = userInput.get().getKey();
				UMLClass subclass = userInput.get().getValue();
				
			 	UMLInheritanceHandler inheritance = new UMLInheritanceHandler(superclass, subclass);				
			 	
				subclass.setInheritance(inheritance);				
				
				subclass.setSuperclass(superclass);									
			}			
		});
		
		
		
		loadJavaProject.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	loadFromJavaProject();
		    }
		});
		
		saveAsJavaProject.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		saveAsJavaProject();
		    	} 
		    	catch(Exception ex) {
		    		Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Fehler beim Speichern");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Bei der Projekterstellung ist ein unerwarteter Fehler aufgetreten.");	    		
		    		
		    	}
		    	
		    	
		    }
		});		
	 }	 	
	
	private void saveAsJavaProject() throws FileNotFoundException {
		DirectoryChooser directoryChooser = new DirectoryChooser();		
		
		File selectedDirectory = directoryChooser.showDialog(null);	
		
		if(selectedDirectory == null)
			return;
		
		
		CodeGeneratorJava generator = new CodeGeneratorJava(Content, selectedDirectory.getAbsolutePath(), ".java");	
		
		generator.saveAllClasses();
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Projekt erfolgreich erstellt");
		alert.setHeaderText(null);
		alert.setContentText("Das UML-Diagramm wurde erfolgreich als Java-Projekt exportiert.");

		alert.showAndWait();
		
		
		//System.out.println(selectedDirectory);
	}
	
	
	private void loadFromJavaProject() {		
		DirectoryChooser directoryChooser = new DirectoryChooser();		
		
		File selectedDirectory = directoryChooser.showDialog(null);
		
		if(selectedDirectory == null)
			return;
		
		CodeLoaderJava loader = new CodeLoaderJava(Content, selectedDirectory.getAbsolutePath(), ".java");
		List<UMLClass> classes = loader.getAllClasses();
		List<UMLInterface> interfaces = loader.getAllInterfaces();		
		
		
		Content.getChildren().clear();
		
		for(UMLClass c : classes) {
			Content.getChildren().add(c);
			
			if(c.getSuperclassHelper() == null || c.getSuperclassHelper() == "")
				continue;
			
			for(UMLClass c2: classes) {
				if(c2.entityNameProperty().get().equals(c.getSuperclassHelper())) {
					UMLInheritanceHandler inheritance = new UMLInheritanceHandler(c2, c);				
				 	
					c.setInheritance(inheritance);				
					
					c.setSuperclass(c2);						
				}
			}
		}
			
		
		for(UMLInterface i : interfaces)
			Content.getChildren().add(i);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("UML-Diagramm erfolgreich erstellt");
		alert.setHeaderText(null);
		alert.setContentText("Das UML-Diagramm wurde erfolgreich erstellt.");

		alert.showAndWait();
	}	
	
	
	private String textInputDialogHelper() {
		TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Entität erstellen");	
    	dialog.setHeaderText(null);
    	dialog.setContentText("Name der Entität:");
    	
    	final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);	
    	btOk.setDisable(true);
    	
    	dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
    		btOk.setDisable(newValue.trim().isEmpty());
    	});	    	
    	
    	
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent())		
    		return result.get();    
    	
    	//Input Dialog geschlossen
    	return null;    	
	}
	
	private Optional<Pair<UMLClass, UMLClass>> showInheritanceDialog() {
		// TODO Auto-generated method stub
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Vererbung erstellen");
		//dialog.setHeaderText("Look, a Custom Login Dialog");

		// Set the icon (must be included in the project).
		//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType createButtonType = new ButtonType("Erstellen", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		ObservableList<String> classnames = FXCollections.observableArrayList();
		
		for(Node node : Content.getChildren())
		{
			if(node instanceof UMLClass)
				classnames.add(((UMLClass) node).entityNameProperty().get());
		}
		

		ComboBox<String> cmbSuperclass = new ComboBox<String>(classnames);		
		ComboBox<String> cmbSubclass = new ComboBox<String>(classnames);		

		grid.add(new Label("Elternklasse:"), 0, 0);
		grid.add(cmbSuperclass, 1, 0);
		grid.add(new Label("Kindklasse:"), 0, 1);
		grid.add(cmbSubclass, 1, 1);
		
		Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
		createButton.setDisable(true);			
		
		//Prüfe, ob beide Klassen ausgewählt		
		cmbSubclass.valueProperty().addListener((observable, oldValue, newValue) -> {			
			if(cmbSuperclass.getValue() != null && !cmbSuperclass.getValue().equals(newValue))
				createButton.setDisable(false);
			else
				createButton.setDisable(true);
			
		});
		
		cmbSuperclass.valueProperty().addListener((observable, oldValue, newValue) -> {			
			if(cmbSubclass.getValue() != null && !cmbSubclass.getValue().equals(newValue))
				createButton.setDisable(false);
			else
				createButton.setDisable(true);
		});
		

		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == createButtonType) {		    	
		        return new Pair<>(cmbSuperclass.getValue(), cmbSubclass.getValue());
		    }
		    return null;
		});		
		
		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		
		if (result.isPresent()){		
			
			UMLClass superclassObject = null;
			UMLClass subclassObject = null;
			
			for(Node node : Content.getChildren()) {
				if(node instanceof UMLClass) {
					String classname = ((UMLClass)node).entityNameProperty().get();
					
					if(classname.equals(result.get().getKey()))
						superclassObject = (UMLClass)node;
					else if(classname.equals(result.get().getValue()))
						subclassObject = (UMLClass)node;				
				}			
			}
			
			
			
			return Optional.of(new Pair<UMLClass, UMLClass>(superclassObject, subclassObject));			
		}
		
		return null;
		
	}
	
	
	
	
	
	
	private void buttonCreationHelper(Button btn, String imgPath) {
		Image img = new Image(imgPath);
		ImageView view = new ImageView(img);	
		
		view.setFitHeight(70);
		view.setFitWidth(100);		
		
		btn.setGraphic(view);		
	}
}
