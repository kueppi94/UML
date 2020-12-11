package model;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import javafx.scene.layout.AnchorPane;

public abstract class CodeLoader {
	protected String inputDir;
	protected AnchorPane canvas;
	protected String ext;
	
	protected File[] files;
	
	
	public CodeLoader(AnchorPane canvas, String inputDir, String ext) {
		this.inputDir = inputDir;
		this.canvas = canvas;
		this.ext = ext;
		
		files = getFilesFromFolder();
	}
	
	
	public abstract UMLClass getClassFromCode();
	protected abstract UMLInterface getInterfaceFromCode();
	
	public abstract List<UMLClass> getAllClasses();
	public abstract List<UMLInterface> getAllInterfaces();
	
	
	
	private File[] getFilesFromFolder() {
		File folder = new File(inputDir);
		FileFilter filter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.getAbsolutePath().toLowerCase().endsWith(ext))
					return true;			
			return false;
			}
		};
		
		return folder.listFiles(filter);
		
	}
}
