package model;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;

public class ConnectionLine extends javafx.scene.Group {
	private ConnectionBox start; 
	private ConnectionBox end;
	
	private Path path = new Path();
	private MoveTo startpoint = new MoveTo();
	private HLineTo hLine = new HLineTo();	
	private VLineTo vLine = new VLineTo();
	
	private Circle positionChanger = new Circle(15);
	
	public ConnectionLine(ConnectionBox start, ConnectionBox end) {
		this.start = start;
		this.end = end;			
		
		startpoint.xProperty().bind(this.start.translateXProperty());
		startpoint.yProperty().bind(this.start.translateYProperty());				
		
		positionChanger.setOnMouseClicked(connectionLineOnMousePressedEventHandler);
		positionChanger.setOnMouseDragged(connectionLineOnMouseDraggedEventHandler);		
		
		path.getElements().addAll(startpoint, hLine, vLine);			
		
		positionChanger.setCenterX(end.getTranslateX());
		positionChanger.setCenterY(start.getTranslateY());
		
		hLine.xProperty().bind(this.positionChanger.centerXProperty());
		vLine.yProperty().bind(end.translateYProperty());			
		
		getChildren().addAll(path, positionChanger);
		
		
		path.getStyleClass().add(Style.CONNECTIONLINE.css());		
	}	
	
	
	/*
	 * Bindet am Ende der Verbindungslinie ein Node-Element ein.
	 */
	public void add(Node node) {			
		node.translateXProperty().bind(end.translateXProperty());
		node.translateYProperty().bind(end.translateYProperty());			
		
		getChildren().add(node);
	}
	
	
	final DragContext dragContext = new DragContext();
	
	EventHandler<MouseEvent> connectionLineOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			dragContext.mouseAnchorX = t.getX();
            dragContext.mouseAnchorY = t.getY();
            dragContext.initialTranslateX =
                positionChanger.getCenterX();
            dragContext.initialTranslateY =
            	positionChanger.getCenterY();	                		
		}
	};
	
	EventHandler<MouseEvent> connectionLineOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double newTranslateX = dragContext.initialTranslateX + t.getX() - dragContext.mouseAnchorX;
        	double newTranslateY = dragContext.initialTranslateY + t.getY() - dragContext.mouseAnchorY;
        	
        	positionChanger.setCenterX(newTranslateX);
        	positionChanger.setCenterY(newTranslateY);
		}
	};
	
	
	private static final class DragContext {		
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
