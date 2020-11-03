package model;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class CodeGeneratorJava extends CodeGenerator {
	
	public CodeGeneratorJava(AnchorPane canvas, String outputDir, String ext) {
		super(canvas, outputDir, ext);		
	}	
	
	@Override
	public String getPropertyCode(Property property) {		
		if(property.getVisibility().equals(Visibility.NO_MODIFIER))
			return String.format("\t%s %s;\n", 
					property.getDataType(), property.getName());
		
		return String.format("\t%s %s %s;\n",
				property.getVisibility().ACCESS_MODIFIER_JAVA, property.getDataType(), property.getName());
	}
	
	@Override
	public String getParameterCode(Parameter parameter, String separator) {
		return String.format("%s %s%s",
				parameter.getDataType(), parameter.getName(), separator);		
	}
	
	@Override
	public String getMethodCode(Method method) {
		String paraCode = "";
		
		ObservableList<Parameter> para = method.parameterProperty().get();
		for(int i = 0; i< para.size(); i++) {			
			String sep = ", ";
			
			if(i == para.size() -1)
				sep = "";
				
			paraCode += getParameterCode(para.get(i), sep);			
		}		
		
		if(!method.isAbstract())
			return String.format("\t%s %s %s (%s) {\n"
				+ "\t\treturn %s;\n"
				+ "\t}\n\n", 
				
				method.getVisibility().ACCESS_MODIFIER_JAVA, method.getReturnType(), method.getName(), paraCode, 
				method.getReturnType().DEFAULT_VALUE);		
		else			
			return String.format("\t%s abstract %s %s (%s);\n\n", 
					method.getVisibility().ACCESS_MODIFIER_JAVA, method.getReturnType(), method.getName(), paraCode);		
	}
	
	
	@Override
	public String getEntityCode(Entity umlEntity) {		
		String entityCode;
		
		if(umlEntity instanceof UMLClass)
			entityCode = String.format("%s %s %s {\n", "public", "class", umlEntity.entityNameProperty().get());
		else
			entityCode = String.format("%s %s %s {\n", "public", "interface", umlEntity.entityNameProperty().get());
			
		
		for(Property p : umlEntity.propertiesProperty().get())
			entityCode += getPropertyCode(p);
		
		entityCode += "\n";
		
		for(Method m : umlEntity.methodsProperty().get()) 
			entityCode += getMethodCode(m);	
			
		
		
		entityCode += "}";		
		
		return entityCode;
	}	
	
}
