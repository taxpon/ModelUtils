package jp.takuro.utils;

import java.io.*;

public class ModelUtilsMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if( args.length !=  1){
			System.err.println("Invalid argument.");
			return;
		}
		
		String filename = args[0];
		String suffix = getSuffix(new File(filename));
		
		try{
			ModelUtils mu = createModelUtils(suffix);
			double vol = mu.calculateVolume(filename)/1000.0;
			System.out.printf("Total Volume : %.2f(cm3)\n", vol);
		} catch (Exception e){
			System.err.println("Invalid file type.");
		}
	}
	
	public static ModelUtils createModelUtils(String suffix) throws Exception{
		
		ModelUtils mu;
		
		if(suffix.equals("stl")){
			mu = new ModelUtilsStl();
		} else if(suffix.equals("obj")){
			mu = new ModelUtilsObj();
		} else {
			throw new Exception("Invalid file type.");
		}
		
		return mu;
	}
	
	public static String getSuffix(File path) {
	    if (path.isDirectory()) {
	        return null;
	    }
	 
	    String fileName = path.getName();
	 
	    int lastDotPosition = fileName.lastIndexOf(".");
	    if (lastDotPosition != -1) {
	        return fileName.substring(lastDotPosition + 1);
	    }
	    return null;
	}

}
