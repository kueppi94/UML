package model;

import javax.lang.model.SourceVersion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class Property extends javafx.scene.layout.HBox {

	private Visibility visibility;
	private SimpleStringProperty name = new SimpleStringProperty();
	private DataType dataType;
	
	public Property(Visibility visiblity, String name, DataType dataType) {

		Label visibilityLabel = new Label();
		Label nameLabel = new Label();
		Label dataTypeLabel = new Label();		
		
		setVisiblity(visiblity);
		setName(name);
		setDataType(dataType);		
		
		visibilityLabel.textProperty().bind(Bindings.concat(this.visibility.umlSignProperty(), " "));		
		nameLabel.textProperty().bind(Bindings.concat(this.name, ": "));		
		dataTypeLabel.textProperty().bind(this.dataType.umlNameProperty());
					

		getChildren().addAll(visibilityLabel, nameLabel, dataTypeLabel);
	}

	public void setVisiblity(Visibility visiblity) {
		this.visibility = visiblity;
	}

	public void setName(String name) {
		
		if(SourceVersion.isName(name))
			this.name.set(name);
		else
			throw new IllegalArgumentException("Name der Eigenschaft ist ungültig!");		
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}		
}
