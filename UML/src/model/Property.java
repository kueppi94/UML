package model;

import javax.lang.model.SourceVersion;

import javafx.scene.control.Label;

public class Property extends javafx.scene.layout.HBox {

	private Label visiblity = new Label();
	private Label name = new Label();
	private Label dataType = new Label();
	
	public Property(Visibility visiblity, String name, DataType dataType) {

		setVisiblity(visiblity);;
		setName(name);
		setDataType(dataType);

		getChildren().addAll(this.visiblity, this.name, this.dataType);
	}

	public void setVisiblity(Visibility visiblity) {
		this.visiblity.setText(visiblity.toUML().getValue() + " ");
	}

	public void setName(String name) {
		
		if(SourceVersion.isName(name))
			this.name.setText(name + ": ");
		else
			throw new IllegalArgumentException("Name der Eigenschaft ist ungültig!");		
	}

	public void setDataType(DataType dataType) {
		this.dataType.setText(dataType.getName());
	}
}
