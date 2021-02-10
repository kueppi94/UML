package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/*
 * JavaFX-Node, die eine UML-Klasse representiert.
 * 
 * Derzeit werden folgende Funktionen unterst�tzt:
 * - Klassennamen �ndern
 * - Klassen als abstrakt kennzeichnen 
 * - Eigenschaften hinzuf�gen/�ndern/l�schen
 * - Methoden hinzuf�gen/�ndern/l�schen und als abstrakt kennzeichnen
 *   - Paramter f�r Methoden hinzuf�gen/�ndern/l�schen
 * - Superklassen hinzuf�gen und l�schen
 */

public class UMLClass extends Entity {	 
	
	private static final int ABSTRACT_ID = 1;	
	private static final String ABSTRACT_TEXT = "{abstract}";
	
	private ObjectProperty<UMLInheritanceHandler> inheritanceProperty = new SimpleObjectProperty<UMLInheritanceHandler>();		

	private ObjectProperty<UMLClass> superclassProperty = new SimpleObjectProperty<UMLClass>();
	
	//wird ausschlie�lich f�r das Laden von Dateien aus Quelltext ben�tigt.
	//Speichert zwischenzeitlich den Namen der Superklasse.
	private String superclassHelper;

	public UMLClass(String className) {
		super(className);				
	}	
	
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;		
	}
	
	public void setAbstract(boolean isAbstract) {
		isAbstractProperty.set(isAbstract);
	}
	
	@Override
	public boolean addProperty(Property property) {		
		return properties.add(property);	
		
	}	
	
	@Override
	public void setProperty(int pid, Property property) {		
		properties.set(pid, property);		
	}
	
	@Override
	public boolean addMethod(Method method) {
		return methods.add(method);			
	}
	
	@Override
	public void setMethod(int mid, Method method) {
		methods.set(mid, method);
	}
	
	
	public UMLClass getSuperclass() {
		return superclassProperty.get();
	}

	public void setSuperclass(UMLClass superclass) {
		this.superclassProperty.set(superclass);
		if(superclass != null)
			superclassHelper = superclass.entityNameProperty().get();
	}
	
	public ObjectProperty<UMLInheritanceHandler> getInheritanceProperty() {
		return inheritanceProperty;
	}
	
	public UMLInheritanceHandler getInheritance() {
		return inheritanceProperty.get();	
	}
	
	public String getSuperclassHelper() {
		return superclassHelper;
	}
	
	public void setSuperclassHelper(String classString) {
		superclassHelper = classString;
	}
	
	/**
	 * Erstellt eine Vererbung zwischen zwei Klassen 
	 */
	public void setInheritance(UMLInheritanceHandler inheritance) {
		Pane parent = (Pane)this.getParent();
		
		UMLInheritanceHandler inheritanceOld = inheritanceProperty.get();
		
		if(inheritanceOld != null)
			parent.getChildren().remove(inheritanceOld);
		
		inheritanceProperty.set(inheritance);
		
		if(inheritance.getSuperClass() != null)
			parent.getChildren().add(inheritance);
	}
}
