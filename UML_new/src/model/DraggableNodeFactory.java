package model;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public abstract class DraggableNodeFactory {	
	 
	public static Node create(final Node node) {
	    final DragContext dragContext = new DragContext();
	    final Group wrapGroup = new Group(node);	    
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.MOUSE_PRESSED,
	        new EventHandler<MouseEvent>() {
	            public void handle(final MouseEvent mouseEvent) {	                
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getX();
                    dragContext.mouseAnchorY = mouseEvent.getY();
                    dragContext.initialTranslateX =
                        node.getTranslateX();
                    dragContext.initialTranslateY =
                        node.getTranslateY();	                
	            }
	        });
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.MOUSE_DRAGGED,
	        new EventHandler<MouseEvent>() {
	            public void handle(final MouseEvent mouseEvent) {	                
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
	            	double newTranslateX = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
	            	double newTranslateY = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
	            	
	            	Node pane = ((Node) (mouseEvent.getSource())).getParent();
	            	
	            	if(newTranslateX > pane.getTranslateX())
	            		node.setTranslateX(newTranslateX);
	            	else
	            		node.setTranslateX(pane.getTranslateX());
	            	
	            	if(newTranslateY > pane.getTranslateY())
	            		node.setTranslateY(newTranslateY);
	            	else
	            		node.setTranslateY(pane.getTranslateY());
	            }
	        });
	 
	    return wrapGroup;

	}	
	
	private static final class DragContext {		
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
