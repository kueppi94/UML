package model;

import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class UMLInheritanceHandler {
	
	UMLClass superclass;
	UMLClass subclass;
	
	public UMLInheritanceHandler(UMLClass superclass, UMLClass subclass) {
		this.superclass = superclass;
		this.subclass = subclass;		
		
		this.subclass.setSuperclass(superclass);
	}
	
	
	/*
	 * Erstellt die notwendigen Komponenten für die Zeichnungsfläche und gibt diese zurück.	 
	 */
	public Node connect(){		
		Bounds superBounds = superclass.localToScene(superclass.getBoundsInLocal());
		Bounds subBounds = subclass.localToScene(subclass.getBoundsInLocal());
		
		ConnectionBox start;
		ConnectionBox end;		
		
		if(subBounds.getMinX() <= superBounds.getMinX())		
			start = subclass.rightConnection;					
		else
			start = subclass.leftConnection;			
		
		if(subBounds.getMinY() <= superBounds.getMinY())
			end = superclass.botConnection;			
		else
			end = superclass.topConnection;	
		
		return new ConnectionLine(start, end);			
	}
	
	
	//Hilfsklasse
	private static final class DragContext {		
	    public double mouseAnchorX;
	    public double mouseAnchorY;
	    public double initialTranslateX;
	    public double initialTranslateY;
	}

}


