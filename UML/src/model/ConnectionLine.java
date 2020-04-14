package model;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

public class ConnectionLine extends javafx.scene.shape.Path {
	private ConnectionPoint start;
	private ConnectionPoint end;
	
	public ConnectionLine(ConnectionPoint start, ConnectionPoint end) {
		MoveTo startpoint = new MoveTo(start.getCenterX(), start.getCenterY());
		LineTo endpoint = new LineTo(end.getCenterX(), end.getCenterY());
	
		getElements().addAll(startpoint, endpoint);
	}
}
