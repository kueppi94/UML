package model;

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
				String inspectorFxml = "";
				
				if(newValue instanceof UMLClass) 
					inspectorFxml = "/view/ClassInspector.fxml";					
					
				try {
					InspectorPane.getChildren().add(FXMLLoader.load(getClass().getResource(inspectorFxml)));
				}
				catch(Exception e) { }
				
					
			}
			
		});
	}		
}
