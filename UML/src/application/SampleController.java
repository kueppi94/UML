

package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

import java.awt.MouseInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


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
		 UMLClass uml = new UMLClass("Testklasse1");
		 UMLClass uml2 = new UMLClass("Testklasse2");
		 
		 Content.getChildren().add(uml);
		 Content.getChildren().add(uml2);			 
		 
		 Map<String, DataType> a = new HashMap<String, DataType>();
		 a.put("abc", DataType.BOOLEAN);
		 a.put("abc", DataType.BOOLEAN);
		 
		 
		 model.Method m = new model.Method(Visibility.PUBLIC, true, "test", a, DataType.INT);
		 System.out.println(a.size());
		 
		 uml.addMethod(m);
		 
		 
		 //UMLClass uml2 = new UMLClass("Testklasse2");
		 
		 //TEsting
		 //ConnectionPoint pr = new ConnectionPoint(uml, ConnectionPoint.RIGHT);
		 //ConnectionPoint pl = new ConnectionPoint(uml, ConnectionPoint.LEFT);
		 //ConnectionPoint pt = new ConnectionPoint(uml, ConnectionPoint.TOP);
		 //ConnectionPoint pb = new ConnectionPoint(uml, ConnectionPoint.BOTTOM);
		 //Content.getChildren().addAll(pr, pl, pt, pb);
		 
		 
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
		 
		 
		 
		 Property test = new Property(Visibility.NO_MODIFIER, "_z", DataType.BOOLEAN);		 
		 
		 
		 uml.addProperty(test);	
		 //end testing		 
	 }	 
	
	@FXML
	public void test() {	
		UMLClass uml = ((UMLClass)Content.getChildren().get(0));
		UMLClass uml2 = ((UMLClass)Content.getChildren().get(1));
		
		((UMLClass)Content.getChildren().get(0)).addProperty(new Property(Visibility.PUBLIC, "z2kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", DataType.STRING));
		if(((UMLClass)Content.getChildren().get(0)).isAbstract())		
			((UMLClass)Content.getChildren().get(0)).setAbstract(false);
		else
			((UMLClass)Content.getChildren().get(0)).setAbstract(true);
		//((UMLClass)Content.getChildren().get(0)).replaceProperty(0, new Property("-", "String", "doublekkkkkkkkkkkkkkkkkkkkkkkkkkkk"));	
		((UMLClass)Content.getChildren().get(0)).setClassName("abc");	
		
		
		ConnectionLine test1 = new ConnectionLine(uml.getTopConnection(), uml2.getBotConnection());
		 
		 Content.getChildren().add(test1);	
		 
		 System.out.println(Content.getChildren());
	}
}
