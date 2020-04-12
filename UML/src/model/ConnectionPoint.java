package model;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.css.StyleClass;
import javafx.scene.layout.VBox;

public class ConnectionPoint extends javafx.scene.shape.Circle {

	public static final String TOP = "top";
	public static final String BOTTOM = "bottom";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	
	public ConnectionPoint(VBox parent, String position) {
		setRadius(10.0);
		
		//Koordinate des linken-oberen Eckpunkts
		DoubleProperty x = parent.translateXProperty();
		DoubleProperty y = parent.translateYProperty();
		
		//Breite & Höhe der VBox
		ReadOnlyDoubleProperty width = parent.widthProperty();
		ReadOnlyDoubleProperty height = parent.heightProperty();
		
		//Berechnet die x&y-Koordinate des ConnectionPoints anhand der im Konstruktor übergebenen Position
		ObservableNumberValue ConnectionPointX;
		ObservableNumberValue ConnectionPointY;
		switch(position) {
		case TOP:
			ConnectionPointX = x.add(width.divide(2));
			ConnectionPointY = y;				
			break;
		case BOTTOM:
			ConnectionPointX = x.add(width.divide(2));
			ConnectionPointY = y.add(height);
			break;
		case LEFT:
			ConnectionPointX = x;	
			ConnectionPointY = y.add(height.divide(2));
			break;
		case RIGHT:
			ConnectionPointX = x.add(width);	
			ConnectionPointY = y.add(height.divide(2));
			break;
		default:
			throw new UnsupportedOperationException();			
		}		
		
		
		//Bindet den ConnectionPoint an die vorgesehene Stelle
		centerXProperty().bind(ConnectionPointX);
		centerYProperty().bind(ConnectionPointY);
		
		
		//getStyleClass().add("test");
		//setCenterY(100);		
	}
	
}
