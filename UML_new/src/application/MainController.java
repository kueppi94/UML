

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

import java.awt.MouseInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
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

public class MainController {
	@FXML
	private AnchorPane Content;		
	
	@FXML
	private AnchorPane InspectorPane;
	
	private Node selectedNode;
	
	public void initialize() {	
		SelectionHandler handler = new SelectionHandler(Content, InspectorPane);
		/*
		try {
			InspectorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ClassInspector.fxml")));
		}
		catch (Exception e){
			System.out.println(e.getStackTrace());
		}
		*/
		
		
		//testing
		 UMLClass uml = new UMLClass("Testklasse1");
		 UMLClass uml2 = new UMLClass("Testklasse2");
		 UMLInterface i1 = new UMLInterface("Interface1");
		 
		  
		 Content.getChildren().add(uml);
		 Content.getChildren().add(uml2);	
		 Content.getChildren().add(i1);
		 
		 ArrayList<Parameter> a = new ArrayList<Parameter>();
		 a.add(new Parameter("parameter1", DataType.DOUBLE));
		 a.add(new Parameter("parameter2", DataType.FLOAT));
		 
		 ArrayList<Parameter> b = new ArrayList<Parameter>();
		 
		 ArrayList<Parameter> c = new ArrayList<Parameter>();
		 
		 ArrayList<Parameter> d = new ArrayList<Parameter>();
		 
		 c.add(new Parameter("ABC", DataType.BOOLEAN));
		 c.add(new Parameter("DEF", DataType.INT));
		 c.add(new Parameter("FFFF", DataType.CHAR));
		 c.add(new Parameter("TTT", DataType.SHORT));
		 
		 d.add(new Parameter("Para1", DataType.BOOLEAN));
		 d.add(new Parameter("Para2", DataType.BYTE));
		 
		 model.Method m = new model.Method(Visibility.PUBLIC, true, "test", a, DataType.INT);		 
		 model.Method m2 = new model.Method(Visibility.PROTECTED, false, "test1", b, DataType.DOUBLE);		
		 model.Method m3 = new model.Method(Visibility.NO_MODIFIER, true, "test3", c, DataType.CHAR);
		 model.Method m4 = new model.Method(Visibility.PRIVATE, true, "test1", d, DataType.CHAR);
		 
		 
		 
		 m2.setVisibility(Visibility.PUBLIC);
		 
		 uml.addMethod(m);
		 uml.addMethod(m2);
		 uml.addMethod(m3);
		 
		 i1.addMethod(m4);
		 
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
		 
		 
		 
		 Property test = new Property(Visibility.NO_MODIFIER, "_z1", DataType.BOOLEAN);		 
		 Property test2 = new Property(Visibility.PRIVATE, "_z2", DataType.BOOLEAN);
		 
		 Property test3 = new Property(Visibility.PRIVATE, "inter1", DataType.CHAR);
		 
		 i1.addProperty(test3);
		 
		 uml.addProperty(test);	
		 uml.addProperty(test2);	
		 //end testing		 
		 
		 //System.out.print(InspectorPane);
	 }	 
	
	
	public void test() {		
		
		UMLClass uml = ((UMLClass)Content.getChildren().get(0));
		UMLClass uml2 = ((UMLClass)Content.getChildren().get(1));
		
		model.Method m1 = new model.Method(Visibility.PRIVATE, false, "test123Gen", DataType.BOOLEAN);
		m1.addParameter(new Parameter("EinTest", DataType.BOOLEAN));
		
		uml2.addMethod(m1);
		
		
		
		CodeGeneratorJava a = new CodeGeneratorJava(Content, "C:\\Users\\Küppi\\Desktop\\bin", ".java");
		
		//System.out.println(uml2.methodsProperty().get().get(0).parameterProperty().get().get(0));
		
		//System.out.println(a.getParameterCode(uml2.methodsProperty().get().get(0).parameterProperty().get().get(0), ", "));
		
		//System.out.println(a.getPropertyCode(uml.propertiesProperty().get(0)));
		
		try {
			a.saveClassToFile();
		}catch(Exception e) {
			
		}	
		
		
		/*
		
		((UMLClass)Content.getChildren().get(0)).addProperty(new Property(Visibility.PUBLIC, "z3", DataType.STRING));
		if(((UMLClass)Content.getChildren().get(0)).isAbstract())		
			((UMLClass)Content.getChildren().get(0)).setAbstract(false);
		else
			((UMLClass)Content.getChildren().get(0)).setAbstract(true);
		//((UMLClass)Content.getChildren().get(0)).replaceProperty(0, new Property("-", "String", "doublekkkkkkkkkkkkkkkkkkkkkkkkkkkk"));	
		((UMLClass)Content.getChildren().get(0)).setClassName("abc");	
		
		
		ConnectionLine test1 = new ConnectionLine(uml.getTopConnection(), uml2.getBotConnection());
		 
		 Content.getChildren().add(test1);	
		 
		 System.out.println(Content.getChildren());
		 */
	}
}
