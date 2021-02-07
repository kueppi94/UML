package model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;

/*
 * Basisklasse für Verbindungen zwischen Entitäten
 */

public class ConnectionLine extends javafx.scene.Group {
	protected Entity start; 
	protected Entity end;
	
	private ConnectionBox startBox;
	private ConnectionBox endBox;
	
	//Pfad zwischen Start und Endpunkt
	private Path path = new Path();
	private MoveTo startpoint = new MoveTo();
	private HLineTo hLine = new HLineTo();	
	private VLineTo vLine = new VLineTo();
	
	//Kreis zum Verschieben der Verbindungslinien in horizontaler und vertikaler Richtung
	private Circle positionChanger = new Circle(15);
	
	//Begrenzungspunkte für den "positionChanger"
	private SimpleDoubleProperty minXProperty = new SimpleDoubleProperty();
	private SimpleDoubleProperty maxXProperty = new SimpleDoubleProperty();
	private SimpleDoubleProperty minYProperty = new SimpleDoubleProperty();
	private SimpleDoubleProperty maxYProperty = new SimpleDoubleProperty();
	
	
	/**
	 * Erstellt eine Verbindungslinie zwischen Start und Endpunkt
	 * @param start Startpunkt der Verbindung
	 * @param end Endpunkt der Verbindung
	 */
	public ConnectionLine(Entity start, Entity end) {		
		if(start == null || end == null)
			return;
		
		this.start = start;
		this.end = end;				
		
		startBox = start.leftConnection;
		endBox = end.topConnection;
		
		
		//Setze Maximal- und Minimalwerte für das Verschieben der Verbindungslinie			
		minXProperty.bind(endBox.translateXProperty());		
		maxXProperty.bind(Bindings.add(endBox.translateXProperty(), endBox.widthProperty()));
		minYProperty.bind(startBox.translateYProperty());
		maxYProperty.bind(Bindings.add(startBox.translateYProperty(), startBox.heightProperty()));		
		
		startpoint.xProperty().bind(this.startBox.translateXProperty());
		startpoint.yProperty().bind(this.startBox.translateYProperty());				
		
		positionChanger.setOnMouseClicked(positionChangerOnMousePressedEventHandler);
		positionChanger.setOnMouseDragged(positionChangerOnMouseDraggedEventHandler);		
		
		path.getElements().addAll(startpoint, hLine, vLine);			
		
		positionChanger.setCenterX(endBox.getTranslateX());
		positionChanger.setCenterY(startBox.getTranslateY());
		
		
		startpoint.yProperty().bind(positionChanger.centerYProperty());		
		hLine.xProperty().bind(this.positionChanger.centerXProperty());
		vLine.yProperty().bind(endBox.translateYProperty());			
				
		
		start.setOnMousePressed(entityOnMousePressedEventHandler);
		start.setOnMouseDragged(entityOnMouseDraggedEventHandler);
		
		end.setOnMousePressed(entityOnMousePressedEventHandler);
		end.setOnMouseDragged(entityOnMouseDraggedEventHandler);		
		
		getChildren().addAll(path, positionChanger);			
	}	
	
	
	/*
	 * Bindet am Ende der Verbindungslinie ein Node-Element ein.
	 * Size wird genutzt, um die Linie mittig zu platzieren.
	 */
	public void add(Node node, double size) {			
		node.translateXProperty().bind(Bindings.subtract(hLine.xProperty(), size));
		node.translateYProperty().bind(vLine.yProperty());			
		
		getChildren().add(node);
	}
	
	
	
	
	
	//Verschiebt den Kreis für die Positionsänderung der Verbindungslinien
	final DragContext dragContextPC = new DragContext();
	
	EventHandler<MouseEvent> positionChangerOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			dragContextPC.mouseAnchorX = t.getX();
            dragContextPC.mouseAnchorY = t.getY();
            dragContextPC.initialTranslateX =
                positionChanger.getCenterX();
            dragContextPC.initialTranslateY =
            	positionChanger.getCenterY();	                		
		}
	};
	
	EventHandler<MouseEvent> positionChangerOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double newTranslateX = dragContextPC.initialTranslateX + t.getX() - dragContextPC.mouseAnchorX;
        	double newTranslateY = dragContextPC.initialTranslateY + t.getY() - dragContextPC.mouseAnchorY;
        	
        	if(newTranslateX >= minXProperty.get() && newTranslateX <= maxXProperty.get())
        		positionChanger.setCenterX(newTranslateX);
        	if(newTranslateY >= minYProperty.get() && newTranslateY <= maxYProperty.get())
        		positionChanger.setCenterY(newTranslateY);
		}
	};
	
	
	
	
	
	
	
	
	
	final DragContext dragContextEntity = new DragContext();
	private Entity clickedEntity;
	
	//sorgt dafür, dass die Verbindungslinien auch mit den Entitäten verschoben werden. 
	//Dafür muss aber der Kreis, an den die Linien gebunden sind verschoben werden.
	EventHandler<MouseEvent> entityOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			Node clickedNode = (Node)t.getTarget();
			
			while(!(clickedNode instanceof Entity)) {
				clickedNode = clickedNode.getParent();				
			}
			
			clickedEntity = (Entity)clickedNode;
			
			dragContextEntity.mouseAnchorX = t.getX();
			dragContextEntity.mouseAnchorY = t.getY();
			dragContextEntity.initialTranslateX =
                positionChanger.getCenterX();
			dragContextEntity.initialTranslateY =
            	positionChanger.getCenterY();	 
		}
	};
	
	EventHandler<MouseEvent> entityOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double newTranslateX = dragContextEntity.initialTranslateX + t.getX() - dragContextEntity.mouseAnchorX;
        	double newTranslateY = dragContextEntity.initialTranslateY + t.getY() - dragContextEntity.mouseAnchorY;
        	
        	if(clickedEntity.equals(start)) {
        		if(newTranslateX >= minXProperty.get() && newTranslateX <= maxXProperty.get())
            		positionChanger.setCenterX(newTranslateX);
        		
        		positionChanger.setCenterY(newTranslateY);
        	}
        	else {
        		if(newTranslateY >= minYProperty.get() && newTranslateY <= maxYProperty.get())
            		positionChanger.setCenterY(newTranslateY);
        		
        		positionChanger.setCenterX(newTranslateX);
        	}
        	
        	
        	
        	//positionChanger.setCenterX(newTranslateX);
        	//positionChanger.setCenterY(newTranslateY);       	
        	
		}
	};
	
	
	
	
	private static final class DragContext {		
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
