package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ConnectionPoint;
import model.Property;
import model.UMLClass;

import java.awt.MouseInfo;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SampleController {
	@FXML
	private AnchorPane Content;		
	
	public void initialize() {	    
		//testing
		 UMLClass uml = new UMLClass("Testklasse");	
		 //UMLClass uml2 = new UMLClass("Testklasse2");
		 
		 //TEsting
		 ConnectionPoint pr = new ConnectionPoint(uml, ConnectionPoint.RIGHT);
		 ConnectionPoint pl = new ConnectionPoint(uml, ConnectionPoint.LEFT);
		 ConnectionPoint pt = new ConnectionPoint(uml, ConnectionPoint.TOP);
		 ConnectionPoint pb = new ConnectionPoint(uml, ConnectionPoint.BOTTOM);
		 Content.getChildren().addAll(pr, pl, pt, pb);
		 
		 Content.getChildren().add(uml);		 
		 //End testing
		 
		 
		 //Content.getChildren().add(uml2);
		 
		 /*
		 Path path = new Path();

		 MoveTo moveTo = new MoveTo();
		 moveTo.setX(5.0f);
		 moveTo.setY(5.0f);

		 HLineTo hLineTo = new HLineTo();
		 hLineTo.setX(70.0f);

		 QuadCurveTo quadCurveTo = new QuadCurveTo();
		 quadCurveTo.setX(120.0f);
		 quadCurveTo.setY(60.0f);
		 quadCurveTo.setControlX(100.0f);
		 quadCurveTo.setControlY(10.0f);

		 LineTo lineTo = new LineTo();
		 lineTo.setX(175.0f);
		 lineTo.setY(55.0f);

		 ArcTo arcTo = new ArcTo();
		 arcTo.setX(50.0f);
		 arcTo.setY(50.0f);
		 arcTo.setRadiusX(50.0f);
		 arcTo.setRadiusY(50.0f);

		 path.getElements().add(moveTo);
		 path.getElements().add(hLineTo);
		 path.getElements().add(quadCurveTo);
		 path.getElements().add(lineTo);
		 path.getElements().add(arcTo);
		 
		 
		 
		 Content.getChildren().add(path);
		 */		 
		 
		 
		 Property test = new Property("+", "zaehler", "int");
		 		 
		 uml.addProperty(test);	
		 //end testing		 
	 }	 
	
	@FXML
	public void test() {
		((UMLClass)Content.getChildren().get(4)).addProperty(new Property("+", "zaehler", "int"));
		((UMLClass)Content.getChildren().get(4)).replaceProperty(0, new Property("-", "test", "doublekkkkkkkkkkkkkkkkkkkkkkkkkkkk"));	
		((UMLClass)Content.getChildren().get(4)).setClassName("abc");	
	}
}
