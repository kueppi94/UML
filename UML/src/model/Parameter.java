package model;

import javax.lang.model.SourceVersion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class Parameter extends javafx.scene.layout.HBox {
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	private DataType dataType;
	
	public Parameter(String name, DataType dataType) {		
		Label nameLabel = new Label();
		Label dataTypeLabel = new Label();			
		
		setName(name);
		setDataType(dataType);				
			
		nameLabel.textProperty().bind(Bindings.concat(this.nameProperty, ": "));		
		dataTypeLabel.textProperty().bind(this.dataType.umlNameProperty());
					

		getChildren().addAll(nameLabel, dataTypeLabel);
	}
	
	public boolean setName(String name) {
		
		if(!SourceVersion.isName(name))
			return false;
		
		this.nameProperty.set(name);		
		return true;
		
		
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}		
}
