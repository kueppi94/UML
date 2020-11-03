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
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ConnectionBox extends javafx.scene.shape.Rectangle {

	public static final String TOP = "top"; 
	public static final String BOTTOM = "bottom";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	
	private VBox parent;
	private String position;	
	
	public ConnectionBox(VBox parent, String position) {	
		this.parent = parent;
		this.position = position;
		
		//Gr��e bestimmen
		if(position.equals(TOP)|| position.equals(BOTTOM)) {
			widthProperty().bind(parent.widthProperty());
			setHeight(10);
		}
		else {
			setWidth(10);
			heightProperty().bind(parent.heightProperty());
		}
		
		
		//Koordinate des linken-oberen Eckpunkts (VBox)
		DoubleProperty x = parent.translateXProperty();
		DoubleProperty y = parent.translateYProperty();				
		
		//Koordinate des linken-oberen Eckpunkts des "Verbindungsrahmens"
		ObservableNumberValue ConnectionPointX;
		ObservableNumberValue ConnectionPointY;
		
		switch(position) {
		case TOP:
			ConnectionPointX = x;
			ConnectionPointY = y.subtract(heightProperty());
			break;
		case BOTTOM:
			ConnectionPointX = x;
			ConnectionPointY = y.add(parent.heightProperty());
			break;
		case LEFT:
			ConnectionPointX = x.subtract(widthProperty());
			ConnectionPointY = y;
			break;
		case RIGHT:
			ConnectionPointX = x.add(parent.widthProperty());
			ConnectionPointY = y;
			break;
		default:
			throw new UnsupportedOperationException();				
		}
		
		translateXProperty().bind(ConnectionPointX);
		translateYProperty().bind(ConnectionPointY);		
		
		setOnMouseClicked(connectionPointOnMousePressedEventHandler);
		setOnMouseDragged(connectionPointOnMouseDraggedEventHandler);
		
		getStyleClass().add("connectionBox");
		
	}	
	
	EventHandler<MouseEvent> connectionPointOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			System.out.println("Pressed");			
		}
	};
	
	EventHandler<MouseEvent> connectionPointOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			System.out.println("Dragged");
		}
	};

	public String getPosition() {
		return position;
	}
}