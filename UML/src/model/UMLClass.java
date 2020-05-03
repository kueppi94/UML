package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
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

public class UMLClass extends javafx.scene.Group implements SelectableNode {
	
	private StringProperty classNameProperty = new SimpleStringProperty();		
	 
	private BooleanProperty isAbstractProperty = new SimpleBooleanProperty();
	private static final int ABSTRACT_ID = 1;	
	private static final String ABSTRACT_TEXT = "{abstract}";
	
	private SimpleListProperty<Property> properties = new SimpleListProperty<Property>(FXCollections.observableArrayList());
	
	private SimpleListProperty<Method> methods = new SimpleListProperty<Method>(FXCollections.observableArrayList());
		
	//Verbindungspunkte für Beziehungen zwischen Klassen, Interfaces usw.
	private ConnectionPoint topConnection;
	private ConnectionPoint botConnection;
	private ConnectionPoint leftConnection;
	private ConnectionPoint rightConnection;		
	
	public UMLClass(String className) {	
		
		Node uml = createUmlNode(className);		
		
		getChildren().addAll(uml, topConnection, botConnection, leftConnection, rightConnection);
		
	}	
	
	public void select() {
		
	}
	
	public void unselect() {
		
	}
	
	public StringProperty classNameProperty() {
		return classNameProperty;
	}
	
	public void setClassName(String className) {
		classNameProperty.set(className);
	}
	
	public BooleanProperty abstractProperty() {
		return isAbstractProperty;
	}
	
	public void setAbstract(boolean isAbstract) {
		isAbstractProperty.set(isAbstract);
	}
	
	public boolean isAbstract() {
		return isAbstractProperty.get();
	}
	
	public ListProperty<Property> propertiesProperty() {
		return properties;
	}
	
	public boolean addProperty(Property property) {		
		return properties.add(property);	
		
	}	
	
	public void setProperty(int pid, Property property) {		
		properties.set(pid, property);		
	}	
	
	public boolean addMethod(Method method) {
		return methods.add(method);			
	}
	
	public void setMethod(int mid, Method method) {
		methods.set(mid, method);
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

	private Node createUmlNode(String className) {		
		VBox umlClassBox = new VBox();		
		umlClassBox.setAlignment(Pos.CENTER);
		
		Label classNameLabel = new Label(className);
		classNameLabel.getStyleClass().add(Style.UML_CLASS_NAME.css());
		
		VBox propertyBox = new VBox();		
		VBox methodBox = new VBox();
		
		umlClassBox.getChildren().addAll(classNameLabel, new Separator(), propertyBox, new Separator(), methodBox);			
		
		topConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.TOP);
		botConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.BOTTOM);
		leftConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.LEFT);
		rightConnection = new ConnectionPoint(umlClassBox, ConnectionPoint.RIGHT);	
		
		setClassName(className);		
		
		//Set Style
		umlClassBox.getStyleClass().add(Style.UML_CLASS.css());		
		
		//Define Bindings
		classNameLabel.textProperty().bind(classNameProperty);		
		
		isAbstractProperty.addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue<?> o,Object oldVal, Object newVal){	        	
	        	if((Boolean)newVal)
	        		umlClassBox.getChildren().add(ABSTRACT_ID, new Label(ABSTRACT_TEXT));
	             else
	            	umlClassBox.getChildren().remove(ABSTRACT_ID);	  	 
	        }
	      });
		
		Bindings.bindContent(propertyBox.getChildren(), properties);	
		Bindings.bindContent(methodBox.getChildren(), methods);
		
		//Make Box draggable
		return DraggableNodeFactory.create(umlClassBox);
	}	
}
