package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.scene.layout.AnchorPane;

public class CodeLoaderJava extends CodeLoader {
	
	public CodeLoaderJava(AnchorPane canvas, String inputDir, String ext) {
		super(canvas, inputDir, ext);		
	}

	@Override
	public UMLClass getClassFromCode() {
		String code = "";
		
		try {
			code = Files.readString(Paths.get(files[0].getAbsolutePath()));
		}
		catch(IOException e) {
			return null;
		}
		
		
		code = code.replaceAll("[\n,\t]", "");
		
		
		System.out.println(code);
		
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected UMLInterface getInterfaceFromCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UMLClass> getAllClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UMLInterface> getAllInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}	
}
