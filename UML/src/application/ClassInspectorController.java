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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	
	 
	public void initialize(URL location, ResourceBundle resources) {		
		inspectedClass.addListener(new ChangeListener<UMLClass>(){
			
			//Initialisiert und aktualisiert die Daten anhand der selektierten Klasse
			@Override
			public void changed(ObservableValue<? extends UMLClass> o, UMLClass oldVal, UMLClass newVal) {				
				
				Name.textProperty().bindBidirectional(newVal.classNameProperty());	
	    		IsAbstract.selectedProperty().bindBidirectional(newVal.abstractProperty());	    		
	    		
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
	    			
	    			container.getStyleClass().add(Style.H_BOX.css());   			
	    			container.getChildren().addAll(cmbVisibility, tfPropName, cmbDataType);
	    			PropertyBox.getChildren().add(container);	    			
	    		}
	    		
				
				
				
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
