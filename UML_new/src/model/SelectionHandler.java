package model;

import application.ClassInspectorController;
import application.InterfaceInspectorController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/*
 * Bindet einen "Inspector-Panel" dynamisch ein. Dieser ermöglicht es die Werte der UML-Klassen &
 * UML-Interfaces zu ändern.
 * Abhängig von der ausgewählten Node (zurzeit nur UMLClass oder UMLInterface) wird das dazugehörige "Inspector-Panel"
 * angezeigt.
 */


public class SelectionHandler {	
	//Node, dessen Werte geändert werden sollen
	private SimpleObjectProperty<SelectableNode> selectedNode = new SimpleObjectProperty<SelectableNode>();
	
	private Pane InspectorPane;
	private ClassInspectorController classInspectorController;
	private InterfaceInspectorController interfaceInspectorController;
	
	public SelectionHandler(Pane root, Pane inspectorPane) {		
		this.InspectorPane = inspectorPane;
		
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {					
				Node target = (Node) mouseEvent.getTarget();								
				
				while(!(target instanceof SelectableNode) && !target.equals(root)) 					
					target = target.getParent();
				
				if(target instanceof SelectableNode) {
					selectedNode.set(((SelectableNode)target));
					inspectorPane.setVisible(true);
				}					
				
				if(target.equals(root))
					inspectorPane.setVisible(false);
			}
		});		
		
		selectedNode.addListener(new ChangeListener<SelectableNode>() {
			@Override
			public void changed(ObservableValue<? extends SelectableNode> observable, SelectableNode oldValue, SelectableNode newValue) {
				InspectorPane.getChildren().clear();			
				
				
				String inspectorFxml = "";
				
				if(newValue instanceof UMLClass) {
					inspectorFxml = "/view/ClassInspector.fxml";					
					
					try {		
						FXMLLoader loader = new FXMLLoader(getClass().getResource(inspectorFxml));	
						InspectorPane.getChildren().add(loader.load());	
						classInspectorController = loader.getController();							
					}
					catch(Exception e) { System.out.println(e.getMessage()); }		
					
					classInspectorController.setInspectedClass(((UMLClass)newValue));
					
					Node content = ((UMLClass)selectedNode.get()).getParent();					
					
					classInspectorController.initData((AnchorPane)content);
					
				}	
				else if(newValue instanceof UMLInterface) {
					inspectorFxml = "/view/InterfaceInspector.fxml";					
					
					try {		
						FXMLLoader loader = new FXMLLoader(getClass().getResource(inspectorFxml));	
						InspectorPane.getChildren().add(loader.load());	
						interfaceInspectorController = loader.getController();					
					}
					catch(Exception e) { System.out.println(e.getMessage()); }		
					
					interfaceInspectorController.setInspectedInterface(((UMLInterface)newValue));
				}							
			}
			
		});
	}	
	
	public SelectableNode getSelectedNode() {
		return selectedNode.get();
	}
}
