package model;

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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public abstract class Entity extends javafx.scene.Group implements SelectableNode {
	protected BooleanProperty isAbstractProperty = new SimpleBooleanProperty();	
	
	protected VBox umlEntityBox = new VBox();	
	
	protected Label entityNameLabel = new Label();
	
	
	protected StringProperty nameProperty = new SimpleStringProperty();	
	protected Visibility visibility;	
	
	protected SimpleListProperty<Property> properties = new SimpleListProperty<Property>(FXCollections.observableArrayList());
	
	protected SimpleListProperty<Method> methods = new SimpleListProperty<Method>(FXCollections.observableArrayList());
		
	//Verbindungspunkte für Beziehungen zwischen Entitäten
	protected ConnectionBox topConnection;
	protected ConnectionBox botConnection;
	protected ConnectionBox leftConnection;
	protected ConnectionBox rightConnection;
	

	public Entity(String entityName) {	
		
		isAbstractProperty.addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue<?> o,Object oldVal, Object newVal){	        	
	        	if((Boolean)newVal)
	        		entityNameLabel.getStyleClass().add(Style.ITALIC.css());	        		
	             else
	            	 entityNameLabel.getStyleClass().remove(Style.ITALIC.css());		            	
	        }
	      });
		
		
		Node uml = createUmlNode(entityName);	
		visibility = Visibility.PUBLIC;		
		
		getChildren().addAll(uml, topConnection, botConnection, leftConnection, rightConnection);	
		
		
	}		
	
	public void setEntityName(String className) {
		nameProperty.set(className);
	}
	
	public StringProperty entityNameProperty() {
		return nameProperty;
	}	
	
	public BooleanProperty abstractProperty() {
		return isAbstractProperty;
	}
	
	public boolean isAbstract() {
		return isAbstractProperty.get();
	}
	
	public ListProperty<Property> propertiesProperty() {
		return properties;
	}
	
	public ListProperty<Method> methodsProperty(){
		return methods;
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
	
	public abstract boolean addProperty(Property property);
	
	public abstract void setProperty(int pid, Property property);
	
	
	
	public abstract boolean addMethod(Method method);
	
	public abstract void setMethod(int mid, Method method);
	
	

			

	public ConnectionBox getTopConnection() {
		return topConnection;
	}

	public ConnectionBox getBotConnection() {
		return botConnection;
	}

	public ConnectionBox getLeftConnection() {
		return leftConnection;
	}

	public ConnectionBox getRightConnection() {
		return rightConnection;
	}		
	
	
	private Node createUmlNode(String entityName) {				
		umlEntityBox.setAlignment(Pos.CENTER);
		
		entityNameLabel = new Label(entityName);
		entityNameLabel.getStyleClass().add(Style.UML_ENTITY_NAME.css());
		
		VBox propertyBox = new VBox();		
		VBox methodBox = new VBox();
		
		umlEntityBox.getChildren().addAll(entityNameLabel, new Separator(), propertyBox, new Separator(), methodBox);			
		
		topConnection = new ConnectionBox(umlEntityBox, ConnectionBox.TOP);
		botConnection = new ConnectionBox(umlEntityBox, ConnectionBox.BOTTOM);
		leftConnection = new ConnectionBox(umlEntityBox, ConnectionBox.LEFT);
		rightConnection = new ConnectionBox(umlEntityBox, ConnectionBox.RIGHT);	
		
		setEntityName(entityName);		
		
		//Set Style
		umlEntityBox.getStyleClass().add(Style.UML_ENTITY.css());		
		
		//Define Bindings
		entityNameLabel.textProperty().bind(nameProperty);				
		
		Bindings.bindContent(propertyBox.getChildren(), properties);		
		Bindings.bindContent(methodBox.getChildren(), methods);
		
		//Make Box draggable
		return DraggableNodeFactory.create(umlEntityBox);
	}	
	
}
