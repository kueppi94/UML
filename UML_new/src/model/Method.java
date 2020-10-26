package model;

import java.awt.Font;
import java.util.*;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
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
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Method extends javafx.scene.Group {
	 
	private ObjectProperty<Visibility> visibilityProperty = new SimpleObjectProperty<Visibility>();	
	
	private SimpleBooleanProperty isAbstractProperty = new SimpleBooleanProperty();	
	//null bei void
	private ObjectProperty<DataType> returnTypeProperty = new SimpleObjectProperty<DataType>();
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	
	private SimpleListProperty<Parameter> parameters = new SimpleListProperty<Parameter>(FXCollections.observableArrayList());	

	public Method(Visibility visibility, boolean isAbstract, String name, List<Parameter> parameters, DataType returnType) {
		
		Node method = createMethodNode(visibility, isAbstract, name, parameters, returnType);		
		
		getChildren().add(method);		
	}	
	
	public Method(Visibility visibility, boolean isAbstract, String name, DataType returnType) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Node method = createMethodNode(visibility, isAbstract, name, parameters, returnType);	
		
		getChildren().add(method);
		
	}
	
	public Method(Visibility visibility, boolean isAbstract, String name, List<Parameter> parameters) {
		DataType returnType = null;
		
		Node method = createMethodNode(visibility, isAbstract, name, parameters, returnType);
		
		getChildren().add(method);
	}
	

	public Visibility getVisibility() {
		return visibilityProperty.get();
	}

	public void setVisibility(Visibility visibility) {
		this.visibilityProperty.set(visibility);	
	}
	
	public BooleanProperty abstractProperty() {
		return isAbstractProperty;
	}

	public boolean isAbstract() {
		return isAbstractProperty.get();
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstractProperty.set(isAbstract);
	}

	public DataType getReturnType() {
		return returnTypeProperty.get();
	}

	public void setReturnType(DataType returnType) {
		this.returnTypeProperty.set(returnType);
	}
	
	public StringProperty nameProperty() {
		return nameProperty;
	}

	public String getName() {
		return this.nameProperty.get();
	}

	public void setName(String name) {
		this.nameProperty.set(name);
	}	

	public void addAllParameters(List<Parameter> parameters) {			
		this.parameters.addAll(parameters);		
	}	
	
	public ListProperty<Parameter> parameterProperty() {
		return parameters;
	}
	
	public boolean addParameter(Parameter parameter) {		
		return parameters.add(parameter);	
		
	}	
	
	public void setParameter(int pid, Property parameter) {		
		parameters.set(pid, parameter);		
	}	
	
	private Node createMethodNode(Visibility visibility, boolean isAbstract, String name, List<Parameter> parameters, DataType returnType) {
		Label visibilityLabel = new Label();		
		Label returnTypeLabel = new Label();
		Label nameLabel = new Label();		
		HBox parameterBox = new HBox();				
	
		setVisibility(visibility);		
		setReturnType(returnType);
			
				
		visibilityLabel.setText(getVisibility().UML_SIGN + " ");		
		
		this.visibilityProperty.addListener(new ChangeListener<Visibility>(){

			@Override
			public void changed(ObservableValue<? extends Visibility> o, Visibility oldVal, Visibility newVal) {				
				visibilityLabel.setText(getVisibility().UML_SIGN + " ");						
			}	        
	      });
		
		
		nameLabel.textProperty().bind(Bindings.concat(this.nameProperty, " "));
		
		returnTypeLabel.setText(getReturnType().UML_NAME);		
		
		this.returnTypeProperty.addListener(new ChangeListener<DataType>() {
			@Override
			public void changed(ObservableValue<? extends DataType> o, DataType oldVal, DataType newVal) {								
				returnTypeLabel.setText(newVal.UML_NAME);			
			}	
			
		});
		
		
		isAbstractProperty.addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue<?> o,Object oldVal, Object newVal){	        	
	        	if((Boolean)newVal)	        	
	        		nameLabel.getStyleClass().add(Style.ITALIC.css());	 	        		       	
	             else
	            	 nameLabel.getStyleClass().remove(Style.ITALIC.css());
	        }
	      });			
		
		this.parameters.addListener((ListChangeListener<Parameter>) p -> {
			while(p.next()) {
				for(int i = 0; i < p.getAddedSize(); i++) {			
					//nachträgliches Hinzufügen von Parametern
					if(parameterBox.getChildren().size() > 0 && i == 0)
						parameterBox.getChildren().add(new Label(", "));
					
					
					//Initialisierung
					parameterBox.getChildren().add(p.getAddedSubList().get(i));				
					
					if(i < p.getAddedSize() - 1)
						parameterBox.getChildren().add(new Label(", "));						
				}	
				
				for(int i = 0; i < p.getRemovedSize(); i++) {
					System.out.println(p.getFrom());
					
					parameterBox.getChildren().remove(p.getFrom());
					
					//Löschen des Komma-Labels
					if(p.getFrom() < parameterBox.getChildren().size())						
						parameterBox.getChildren().remove(p.getFrom());
					
					
				}
			}			
		});		
		
		
		addAllParameters(parameters);	
		setAbstract(isAbstract);
		setName(name);
		
		
		return new HBox(visibilityLabel, nameLabel, new Label("( "), parameterBox, new Label(" ): "), returnTypeLabel);
		
	}
}
