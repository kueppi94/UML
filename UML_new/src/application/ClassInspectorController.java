package application;


import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.DataType;
import model.Method;
import model.Parameter;
import model.Property;
import model.SelectableNode;
import model.Style;
import model.UMLClass;
import model.UMLInheritanceHandler;
import model.Visibility;

/*
 * Diese Klasse k�mmert sich um die einzelnen Events des Klassen-Inspektors
 */

public class ClassInspectorController implements Initializable {
	@FXML
	private VBox ClassInspector;	
	
	private AnchorPane Content;
	
	@FXML
	private final ObjectProperty<UMLClass> inspectedClass = new SimpleObjectProperty<UMLClass>(null);	
	
	private ListProperty<Property> propertiesProperty = new SimpleListProperty<Property>();
	
	private ListProperty<Method> methodsProperty = new SimpleListProperty<Method>();
	
		
	@FXML
	private ComboBox<Visibility> CbVisibility;
	
	@FXML
	private ComboBox<String> CbSuperclass;
	
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
	@FXML
	private Button NewMethod;
	
	
	/**
	 * Initialisiert die Auswahlbox f�r die Superklasse 
	 * @param content Zeichenfl�che mit den UML-Nodes
	 */
	public void initData(AnchorPane content) {
		this.Content = content;
		
		CbSuperclass.getItems().add(null);
		for(Node node : Content.getChildren()) {
			if(node instanceof UMLClass && ! ((UMLClass) node).entityNameProperty().get().equals(inspectedClass.get().entityNameProperty().get())) {
				UMLClass umlClass = (UMLClass) node;
				CbSuperclass.getItems().add(umlClass.entityNameProperty().get());
			}	
			
		UMLClass superclass = inspectedClass.get().getSuperclass();		
			
		if(superclass != null)
			CbSuperclass.getSelectionModel().select(superclass.entityNameProperty().get());
		
		
		
		CbSuperclass.valueProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> o, String oldVal, String newVal) {		
				//Keine Elternklasse ausgew�hlt.
				if(newVal == null) {					
					inspectedClass.get().setInheritance(new UMLInheritanceHandler(null, inspectedClass.get()));
					
					
					
					return;
				}
				
				
				UMLClass newSuperClass = null;
				
				for(Node node : Content.getChildren()) {
					if(node instanceof UMLClass && ((UMLClass) node).entityNameProperty().get().equals(newVal))
						newSuperClass = (UMLClass)node;
				}
				
				inspectedClass.get().setInheritance(new UMLInheritanceHandler(newSuperClass, inspectedClass.get()));
			}
	      });
				
		}
	}
	 
	/**
	 * Definiert die Aktionen bei Button-Clicks
	 * Generiert au�erdem Eigenschaft-Inspektoren f�r Eigenschaften und Methoden
	 */
	public void initialize(URL location, ResourceBundle resources) {		
		newPropertyButtonAction();	
		newMethodButtonAction();
		
		inspectedClass.addListener(new ChangeListener<UMLClass>(){
			
			//Initialisiert und aktualisiert die Eigenschaften anhand der selektierten Klasse
			@Override
			public void changed(ObservableValue<? extends UMLClass> o, UMLClass oldVal, UMLClass newVal) {	
				//Bindings definieren				
				propertiesProperty.bindBidirectional(newVal.propertiesProperty());
				methodsProperty.bindBidirectional(newVal.methodsProperty());	
				
				
				populateGeneralData(newVal);  
				
	    		
	    		//Erstelle f�r jede Eigenschaft einen HBox-Container	    			    		
	    		for(Property p : propertiesProperty.get()) {	    		
	    			 addNewPropertyInspector(p);			
	    		}
	    		
	    		//Initialisiert und aktualisiert die Methoden anhand der selektierten Klasse	    		
	    		for(model.Method m : methodsProperty.get()) {
	    			addNewMethodInspector(m);	    			   	    			
	    		}				
			}	        	
	      });		
	}
	
	
	
	
	/**
	 * Erstellt eine neue Eigenschaft f�r die Klasse. Das UML-Node wird automatisch aktualisiert.
	 */
	public void newPropertyButtonAction() {
		NewProperty.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Property p = new Property(Visibility.PRIVATE, "p1", DataType.INT);
		    	
		        inspectedClass.get().addProperty(p);
		        
		        addNewPropertyInspector(p);
		    }
		});
	}
	
	/**
	 * Erstellt eine neue Methode f�r die Klasse. Das UML-Node wird automatisch aktualisiert.
	 */
	public void newMethodButtonAction() {
		NewMethod.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Method m = new Method(Visibility.PUBLIC, false, "Method1", DataType.BOOLEAN);
		    	
		        inspectedClass.get().addMethod(m);
		        
		        addNewMethodInspector(m);
		    }
		});
	}	
	
	/**
	 * Erstellt einen Eigenschaften-Inspektor f�r einen Methodenparameter	 
	 */
	public HBox getNewParameterInspector(ListProperty<Parameter> parametersProperty, Parameter p) {
		
		HBox paraContainer = new HBox();	 
		ComboBox<DataType> cmbDataType = new ComboBox<DataType>();
		cmbDataType.getItems().setAll(DataType.values());
		cmbDataType.getSelectionModel().select(p.getDataType());
		
		
		cmbDataType.valueProperty().addListener(new ChangeListener<DataType>(){
			@Override
			public void changed(ObservableValue<? extends DataType> o, DataType oldVal, DataType newVal) {					
				p.setDataType(newVal);										
			}
	      });
		
		TextField tfPropName = new TextField();
		tfPropName.textProperty().bindBidirectional(p.nameProperty());				
		
		Button deleteParameter = new Button("-");
		deleteParameter.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {		    	
		    	
		    	parametersProperty.remove(parametersProperty.indexOf(p));		        
		        
		        HBox container = (HBox)deleteParameter.getParent();
		        VBox help = (VBox)container.getParent();
		        
		        help.getChildren().remove(container);		        
		    }
		});
		
		
		paraContainer.getChildren().addAll(cmbDataType, tfPropName, deleteParameter);
		paraContainer.getStyleClass().add(Style.H_BOX.css()); 		
		
		return paraContainer;
	}
	
	
	/**
	 * Erstellt einen Eigenschaften-Inspektor f�r Eigenschaften einer Klasse	 
	 */
	public void addNewPropertyInspector(Property p) {
		HBox container = new HBox();	    		
		
		ComboBox<Visibility> cmbVisibility = new ComboBox<Visibility>();
		cmbVisibility.getItems().setAll(Visibility.values());
		cmbVisibility.getSelectionModel().select(p.getVisibility());
		
		cmbVisibility.valueProperty().addListener(new ChangeListener<Visibility>(){
			@Override
			public void changed(ObservableValue<? extends Visibility> o, Visibility oldVal, Visibility newVal) {												
				p.setVisibility(newVal);							
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
		    	propertiesProperty.remove(propertiesProperty.indexOf(p));		        
		        
		        HBox container = (HBox)deleteProp.getParent();
		        VBox help = (VBox)container.getParent();
		        
		        help.getChildren().remove(container);		        
		    }
		});
		
		
		//Binde den HBox-container an die PropertyBox (VBox) des FXML
		container.getStyleClass().add(Style.H_BOX.css());   			
		container.getChildren().addAll(cmbVisibility, tfPropName, cmbDataType, deleteProp);
		PropertyBox.getChildren().add(container);	   
	}
	
	/**
	 * Erstellt einen neuen Eigenschaften-Inspektor f�r die �bergebene Methode	 
	 */
	public void addNewMethodInspector(Method m) {
		CheckBox isAbstractMethod = new CheckBox("Abstrakt?");
		isAbstractMethod.selectedProperty().bindBidirectional(m.abstractProperty());	    			
		
		ComboBox<Visibility> cmbVisibility = new ComboBox<Visibility>();
		cmbVisibility.getItems().setAll(Visibility.values());
		cmbVisibility.getSelectionModel().select(m.getVisibility());
		
		cmbVisibility.valueProperty().addListener(new ChangeListener<Visibility>(){
			@Override
			public void changed(ObservableValue<? extends Visibility> o, Visibility oldVal, Visibility newVal) {												
				m.setVisibility(newVal);							
			}
	      });
		
		ComboBox<DataType> cmbReturnType = new ComboBox<DataType>();
		cmbReturnType.getItems().setAll(DataType.values());
		cmbReturnType.getSelectionModel().select(m.getReturnType());
		
		cmbReturnType.valueProperty().addListener(new ChangeListener<DataType>() {
			@Override
			public void changed(ObservableValue<? extends DataType> o, DataType oldVal, DataType newVal) {												
				m.setReturnType(newVal);							
			}
			
		});
		
		
		TextField tfName = new TextField();
		tfName.textProperty().bindBidirectional(m.nameProperty());
		
		
		VBox paraVBox = new VBox();	    			
		ListProperty<Parameter> para = m.parameterProperty();
			    			
		for(Parameter p : para) {
			HBox paraContainer = getNewParameterInspector(m.parameterProperty(),  p);			 			
			
			paraVBox.getChildren().add(paraContainer);
		}
		
		Button newParameterButton = new Button("Neu");
		newParameterButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	Parameter p = new Parameter("p1", DataType.INT);
		    	
		        m.addParameter(p);
		        
		        HBox paraContainer = getNewParameterInspector(m.parameterProperty(), p);	
		        
		        paraVBox.getChildren().add(paraContainer);
		    }		
		});
		
		
		
		
		paraVBox.getChildren().add(0, newParameterButton);		
		
		
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
	
	
	public void populateGeneralData(UMLClass newVal) {
		CbVisibility.getItems().setAll(Visibility.values());
		CbVisibility.getSelectionModel().select(newVal.getVisibility());		
		
		//aktualisiere Name und Abstract-Wert
		Name.textProperty().bindBidirectional(newVal.entityNameProperty());	
		IsAbstract.selectedProperty().bindBidirectional(newVal.abstractProperty());	  
	}
	
	
	public void delete() {
		Pane canvas = (Pane)inspectedClass.get().getParent();
		
		canvas.getChildren().remove(inspectedClass.get());
		
		ClassInspector.getChildren().clear();
		
		
	}
	
	
	
	
	//Getters & Setters	
	public ObjectProperty<UMLClass> inspectedClassProperty() {
		return inspectedClass;
	}	
	
	public UMLClass getInspectedClass() {
		return inspectedClass.get();
	}	
	
	public void setInspectedClass(UMLClass inspectedClass) {	
		this.inspectedClass.set(inspectedClass);			
	}
	
	public void setContent(AnchorPane content) {
		Content = content;
	}
}
