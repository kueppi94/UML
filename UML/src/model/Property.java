package model;

import javafx.scene.control.Label;

public class Property extends javafx.scene.layout.HBox {

	private Label visiblity = new Label();
	private Label name = new Label();
	private Label dataType = new Label();

	public Property(String visiblity, String name, String dataType) {

		setVisiblity(visiblity);;
		setName(name);
		setReturnedType(dataType);

		getChildren().addAll(this.visiblity, this.name, this.dataType);
	}

	public void setVisiblity(String visiblity) {
		this.visiblity.setText(visiblity + " ");
	}

	public void setName(String name) {
		this.name.setText(name + ": ");
	}

	public void setReturnedType(String returnedType) {
		this.dataType.setText(returnedType);
	}
}
