package model;

import java.util.*;

import javafx.scene.control.Label;

public class Method extends javafx.scene.layout.HBox {
	private Visibility visibility;
	private boolean isAbstract;
	//null bei void
	private DataType returnType;
	private String name;
	
	private Map<String, DataType> parameters = new HashMap<String, DataType>();
	
	public Method(Visibility visibility, boolean isAbstract, DataType returnType, String name, Map<String, DataType> parameters) {
		setVisibility(visibility);
		setAbstract(isAbstract);
		setReturnType(returnType);
		setParameters(parameters);		
		
		Label a = new Label();
		a.textProperty().bind(visibility.toUML());
		
		getChildren().add(a);
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public DataType getReturnType() {
		return returnType;
	}

	public void setReturnType(DataType returnType) {
		this.returnType = returnType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, DataType> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, DataType> parameters) {
		this.parameters = parameters;
	}
}
