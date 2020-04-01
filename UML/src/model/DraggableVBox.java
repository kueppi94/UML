package model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class DraggableVBox extends javafx.scene.layout.VBox {

	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	
	public DraggableVBox() {
		setOnMousePressed(vBoxOnMousePressedEventHandler);
		setOnMouseDragged(vBoxOnMouseDraggedEventHandler);
	}
	

	// https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
	EventHandler<MouseEvent> vBoxOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();
			orgTranslateX = ((Node) (t.getSource())).getTranslateX();
			orgTranslateY = ((Node) (t.getSource())).getTranslateY();

		}
	};

	EventHandler<MouseEvent> vBoxOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double offsetX = t.getSceneX() - orgSceneX;
			double offsetY = t.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			Node parent = getParent();

			// left border collision
			if (newTranslateX > parent.getTranslateX())
				((Node) (t.getSource())).setTranslateX(newTranslateX);
			else
				((Node) (t.getSource())).setTranslateX(parent.getTranslateX());

			// top border collision
			if (newTranslateY > parent.getTranslateY())
				((Node) (t.getSource())).setTranslateY(newTranslateY);
			else
				((Node) (t.getSource())).setTranslateY(parent.getTranslateY());
		}
	};
}
