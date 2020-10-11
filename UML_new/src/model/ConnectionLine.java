package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.VLineTo;

public class ConnectionLine extends javafx.scene.shape.Path {
	private ConnectionBox start; 
	private ConnectionBox end;
	
	private MoveTo startpoint = new MoveTo();
	private HLineTo hLine = new HLineTo();
	private VLineTo vLine = new VLineTo();
	
	public ConnectionLine(ConnectionBox start, ConnectionBox end) {
		this.start = start;
		this.end = end;			
		
		startpoint.xProperty().bind(this.start.translateXProperty());
		startpoint.yProperty().bind(this.start.translateYProperty());
		
		
		hLine.xProperty().bind(this.end.translateXProperty());
		
		vLine.yProperty().bind(this.end.translateYProperty());
		
		if(start.getPosition().equals(ConnectionBox.TOP) || start.getPosition().equals(ConnectionBox.BOTTOM)) {
			getElements().addAll(startpoint, vLine, hLine);	
		}
		else {
			getElements().addAll(startpoint, hLine, vLine);	
		}
		
		
		getStyleClass().add(Style.CONNECTIONLINE.css());
		}	
}
