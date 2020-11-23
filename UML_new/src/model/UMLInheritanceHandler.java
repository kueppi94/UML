package model;

import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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
		
		//Kindklasse ist start, Elternklasse ist end
		ConnectionLine line = new ConnectionLine(start, end);
		
		line.add(new Polygon(10.0, 20.0,
			    20.0, 0.0,
			    0.0, 0.0));
		
		return line;
	}
}


