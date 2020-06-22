package model;

import javax.lang.model.SourceVersion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class Parameter extends javafx.scene.layout.HBox {
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	
	private ObjectProperty<DataType> dataTypeProperty = new SimpleObjectProperty<DataType>();		
	 
	public Parameter(String name, DataType dataType) {		
		Label nameLabel = new Label();
		Label dataTypeLabel = new Label();			
		
		setName(name);
		setDataType(dataType);				
			
		nameLabel.textProperty().bind(Bindings.concat(this.nameProperty, ": "));		
		dataTypeLabel.textProperty().bind(this.dataTypeProperty.get().umlNameProperty());
				
		this.dataTypeProperty.addListener(new ChangeListener<DataType>(){

			@Override
			public void changed(ObservableValue<? extends DataType> o, DataType oldVal, DataType newVal) {
				dataTypeLabel.textProperty().unbind();
				dataTypeLabel.textProperty().bind(Bindings.concat(dataTypeProperty.get().umlNameProperty(), " "));				
			}	        
	      });
		

		getChildren().addAll(nameLabel, dataTypeLabel);
	}
	
	public boolean setName(String name) {
		
		if(!SourceVersion.isName(name))
			return false;
		
		this.nameProperty.set(name);		
		return true;		
	}
	
	public String getName() {
		return this.nameProperty.get();
	}
	
	public StringProperty nameProperty() {
		return nameProperty;
	}

	public void setDataType(DataType dataType) {
		this.dataTypeProperty.set(dataType);
	}
	
	public ObjectProperty<DataType> dataTypeProperty(){
		return dataTypeProperty;
	}
	
	public DataType getDataType() {
		return dataTypeProperty.get();
	}
}
