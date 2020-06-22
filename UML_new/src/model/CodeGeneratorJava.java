package model;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class CodeGeneratorJava extends CodeGenerator {
	public CodeGeneratorJava(Node canvas, String outputDir, String ext) {
		super(canvas, outputDir, ext);		
	}
	
	@Override
	public String getPropertyCode(Property property) {		
		return String.format("vis=%s dataType=%s name=%s;",
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
		
		return String.format("%vis %ret %name (%para) \n"
				+ "return %retValue;\n"
				+ "}", method.getVisibility(), method.getReturnType(), method.getName(), paraCode, 
				method.getReturnType().DEFAULT_VALUE);
		
	}
	
	@Override
	public void saveClassToFile(UMLClass umlClass) {
		
	}
}
