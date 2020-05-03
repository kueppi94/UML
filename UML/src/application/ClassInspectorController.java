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
		inspectedClass.addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue<?> o,Object oldVal, Object newVal){	
	        	UMLClass inspectedClassNew = ((UMLClass)newVal);
	        	
	        	Name.textProperty().bindBidirectional(inspectedClassNew.classNameProperty());	
	    		IsAbstract.selectedProperty().bindBidirectional(inspectedClassNew.abstractProperty());
	    		    		
	    		
	    		//Testing
	    		ListProperty<Property> prop = inspectedClassNew.propertiesProperty();	
	    		for(Property p : prop.get()) {	    		
	    			HBox container = new HBox();	    		
	    			
	    			ComboBox<Visibility> cmbVisibility = new ComboBox<Visibility>();
	    			cmbVisibility.getItems().setAll(Visibility.values());
	    			
	    			cmbVisibility.valueProperty().addListener(new ChangeListener<Visibility>(){
						@Override
						public void changed(ObservableValue<? extends Visibility> o, Visibility oldVal, Visibility newVal) {												
							p.setVisiblity(newVal);
							
							System.out.println(p.getVisibility());
						}
	    		      });
	    			
	    			container.getChildren().addAll(cmbVisibility);
	    			PropertyBox.getChildren().add(container);
	    			 			
	    			
	    			
	    			//container.getChildren().add(cmbVisibility);
	    			//PropertyBox.getChildren().add(container);
	    		}
	    		//End Testing
	    		 
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
