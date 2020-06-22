package application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.DataType;
import model.DrawingPane;
import model.Parameter;
import model.Property;
import model.SelectableNode;
import model.Style;
import model.UMLClass;
import model.Visibility;

public class ClassInspectorController implements Initializable {
	@FXML
	private final ObjectProperty<UMLClass> inspectedClass = new SimpleObjectProperty<UMLClass>(new UMLClass(""));	
		
	@FXML
	private TextField Name;
	@FXML
	private CheckBox IsAbstract;
	@FXML
	private VBox PropertyBox;
	@FXML
	private Button NewProperty;
	@FXML
	private VBox MethodBox;
	
	 
	public void initialize(URL location, ResourceBundle resources) {
		NewProperty.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        inspectedClass.get().addProperty(new Property(Visibility.PRIVATE, "p1", DataType.INT));
		    }
		});
		
		
		inspectedClass.addListener(new ChangeListener<UMLClass>(){
			
			//Initialisiert und aktualisiert die Eigenschaften anhand der selektierten Klasse
			@Override
			public void changed(ObservableValue<? extends UMLClass> o, UMLClass oldVal, UMLClass newVal) {					
				//aktualisiere Name und Abstract-Wert
				Name.textProperty().bindBidirectional(newVal.classNameProperty());	
	    		IsAbstract.selectedProperty().bindBidirectional(newVal.abstractProperty());	    		
	    		
	    		//Erstelle für jede Eigenschaft einen HBox-Container
	    		ListProperty<Property> prop = newVal.propertiesProperty();	
	    		for(Property p : prop.get()) {	    		
	    			HBox container = new HBox();	    		
	    			
	    			ComboBox<Visibility> cmbVisibility = new ComboBox<Visibility>();
	    			cmbVisibility.getItems().setAll(Visibility.values());
	    			cmbVisibility.getSelectionModel().select(p.getVisibility());
	    			
	    			cmbVisibility.valueProperty().addListener(new ChangeListener<Visibility>(){
						@Override
						public void changed(ObservableValue<? extends Visibility> o, Visibility oldVal, Visibility newVal) {												
							p.setVisiblity(newVal);							
						}
	    		      });
	    			
	    			TextField tfPropName = new TextField();
	    			tfPropName.textProperty().bindBidirectional(p.nameProperty());
	    			
	    			ComboBox<DataType> cmbDataType = new ComboBox<DataType>();
	    			cmbDataType.getItems().setAll(DataType.values());
	    			cmbDataType.getSelectionModel().select(p.getDataType());
	    			
	    			cmbDataType.valueProperty().addListener(new ChangeListener<DataType>(){
						@Override
						public void changed(ObservableValue<? extends DataType> o, DataType oldVal, DataType newVal) {												
							p.setDataType(newVal);					
						}
	    		      });	
	    			
	    			Button deleteProp = new Button("-");
	    			deleteProp.setOnAction(new EventHandler<ActionEvent>() {
	    			    @Override public void handle(ActionEvent e) {
	    			        prop.remove(prop.indexOf(p));
	    			    }
	    			});
	    			
	    			//Binde den HBox-container an die PropertyBox (VBox) des FXML
	    			container.getStyleClass().add(Style.H_BOX.css());   			
	    			container.getChildren().addAll(cmbVisibility, tfPropName, cmbDataType, deleteProp);
	    			PropertyBox.getChildren().add(container);	    			
	    		}
	    		
	    		//Initialisiert und aktualisiert die Methoden anhand der selektierten Klasse
	    		ListProperty<model.Method> methods = newVal.methodsProperty();	
	    		for(model.Method m : methods.get()) {
	    			
	    			CheckBox isAbstractMethod = new CheckBox("Abstrakt?");
	    			isAbstractMethod.selectedProperty().bindBidirectional(m.abstractProperty());	    			
	    			
	    			ComboBox<Visibility> cmbVisibility = new ComboBox<Visibility>();
	    			cmbVisibility.getItems().setAll(Visibility.values());
	    			cmbVisibility.getSelectionModel().select(m.getVisibility());
	    			
	    			ComboBox<DataType> cmbReturnType = new ComboBox<DataType>();
	    			cmbReturnType.getItems().setAll(DataType.values());
	    			cmbReturnType.getSelectionModel().select(m.getReturnType());
	    			
	    			TextField tfName = new TextField();
	    			tfName.textProperty().bindBidirectional(m.nameProperty());
	    			
	    			
	    			VBox paraVBox = new VBox();	    			
	    			ListProperty<Parameter> para = m.parameterProperty();
	    				    			
	    			for(Parameter p : para) {
	    				HBox paraContainer = new HBox();	 
	    				ComboBox<DataType> cmbDataType = new ComboBox<DataType>();
		    			cmbDataType.getItems().setAll(DataType.values());
		    			cmbDataType.getSelectionModel().select(p.getDataType());
		    			
		    			TextField tfPropName = new TextField();
		    			tfPropName.textProperty().bindBidirectional(p.nameProperty());
	    				
	    				
	    				paraContainer.getChildren().addAll(cmbDataType, tfPropName);
	    				paraContainer.getStyleClass().add(Style.H_BOX.css());   	
	    				
	    				paraVBox.getChildren().add(paraContainer);
	    			}
	    			
	    			//, new Accordion(paraContainer)
	    			
	    			HBox abstractHBox = new HBox(isAbstractMethod);
	    			abstractHBox.getStyleClass().add(Style.H_BOX.css());   	
	    			HBox generalInfoHBox = new HBox(cmbVisibility, tfName, cmbReturnType);
	    			generalInfoHBox.getStyleClass().add(Style.H_BOX.css());   	
	    			HBox parameterHBox = new HBox(new TitledPane("Parameter", paraVBox));
	    			parameterHBox.getStyleClass().add(Style.H_BOX.css());
	    				    			
	    			TitledPane p = new TitledPane(m.getName(), new VBox(abstractHBox, generalInfoHBox, parameterHBox));
	    			p.setExpanded(false);
	    			
	    			MethodBox.getChildren().add(p);   	    			
	    		}				
			}	        	
	      });
		
		
		NewProperty.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        inspectedClass.get().addProperty(new Property(Visibility.PRIVATE, "p1", DataType.INT));
		    }
		});
	}

	public HBox propertyToHBox(Property property) {
		TextField name = new TextField();
		name.textProperty().bindBidirectional(property.nameProperty());
		
		return new HBox(name);
	}
	
	public ObjectProperty<UMLClass> inspectedClassProperty() {
		return inspectedClass;
	}	
	
	public UMLClass getInspectedClass() {
		return inspectedClass.get();
	}	
	
	public void setInspectedClass(UMLClass inspectedClass) {	
		this.inspectedClass.set(inspectedClass);			
	}
}
