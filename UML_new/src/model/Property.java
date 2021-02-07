package model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

/*
 * Node für Eigenschaften von Klassen und Interfaces
 * Aus UML-Sicht ist die "Eigenschaft" nur ein Parameter mit Sichtbarkeit.
 * Daher erbt Property von Parameter
 */
 
public class Property extends Parameter {

	private ObjectProperty<Visibility> visibilityProperty = new SimpleObjectProperty<Visibility>();	
	
	/**
	 * Erstellt eine HBox für eine Eigenschaft mit den übergebenen Parametern
	 * @param visibility Sichtbarkeit der Eigenschaft
	 * @param name Name der Eigenschaft
	 * @param dataType Datentyp der Eigenschaft
	 */
	public Property(Visibility visibility, String name, DataType dataType) {
		super(name, dataType);				
		
		Label visibilityLabel = new Label();	
		
		setVisibility(visibility);			
		
		visibilityLabel.textProperty().bind(Bindings.concat(this.visibilityProperty.get().UML_SIGN, " "));	
		
		this.visibilityProperty.addListener(new ChangeListener<Visibility>(){

			@Override
			public void changed(ObservableValue<? extends Visibility> o, Visibility oldVal, Visibility newVal) {
				visibilityLabel.textProperty().unbind();
				visibilityLabel.textProperty().bind(Bindings.concat(visibilityProperty.get().UML_SIGN, " "));				
			}	        
	      });

		getChildren().add(0, visibilityLabel);
	}

	public void setVisibility(Visibility visibility) {		
		this.visibilityProperty.set(visibility);		
	}
	
	public Visibility getVisibility() {
		return visibilityProperty.get();
	}
}
