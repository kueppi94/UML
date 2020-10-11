package model;

import application.ClassInspectorController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SelectionHandler {	
	private SimpleObjectProperty<SelectableNode> selectedNode = new SimpleObjectProperty<SelectableNode>();
	
	private Pane InspectorPane;
	private ClassInspectorController inspectorController;
	
	public SelectionHandler(Pane root, Pane inspectorPane) {
		this.InspectorPane = inspectorPane;
		
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {					
				Node target = (Node) mouseEvent.getTarget();
				
				System.out.println(target);
				
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
				String inspectorFxml = "";
				
				if(newValue instanceof UMLClass) {
					inspectorFxml = "/view/ClassInspector_new.fxml";											
				}									
					
				
				InspectorPane.getChildren().clear();
				 
				try {		
					FXMLLoader loader = new FXMLLoader(getClass().getResource(inspectorFxml));	
					InspectorPane.getChildren().add(loader.load());	
					inspectorController = loader.getController();					
				}
				catch(Exception e) { System.out.println(e.getMessage()); }					
				
				inspectorController.setInspectedClass(((UMLClass)newValue));					
			}
			
		});
	}		
}
