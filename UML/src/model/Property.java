package model;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

/*
 * Aus UML-Sicht ist die "Eigenschaft" nur ein Parameter mit Sichtbarkeit.
 * Daher erbt Property von Parameter
 */
 
public class Property extends Parameter {

	private Visibility visibility;	
	
	public Property(Visibility visiblity, String name, DataType dataType) {
		super(name, dataType);
		
		Label visibilityLabel = new Label();			
		
		setVisiblity(visiblity);			
		
		visibilityLabel.textProperty().bind(Bindings.concat(this.visibility.umlSignProperty(), " "));				

		getChildren().add(0, visibilityLabel);
	}

	public void setVisiblity(Visibility visibility) {	
		this.visibility = visibility;
		/*
		if(this.visibility == null)
			this.visibility = visibility;
		else				
			this.visibility.update(visibility);	
			*/	
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
}
