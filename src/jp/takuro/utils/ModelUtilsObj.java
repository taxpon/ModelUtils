package jp.takuro.utils;

import java.io.*;
import java.util.*;
import javax.vecmath.*;

public class ModelUtilsObj extends ModelUtils{
	
	private ArrayList<Vector3d> vertice_list = new ArrayList<Vector3d>();
	private ArrayList<FaceObj> face_list = new ArrayList<FaceObj>();
	
	/**
	* Calculate volume of 3d data (.obj)
	* @author Takuro Wada
	* @param filename Path of the 3d data
	* @return Volume (cm3)
	*/
	public double calculateVolume(String filename){
		
		double total_volume = 0;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "r");
			this.read(raf);
			total_volume = this.calculateTotalVolume();
			raf.close();
		} catch (IOException e) {
			System.out.println("Catch exception :" + e);
		}
		
		return total_volume;
	}
	
	/**
	* Calculate surface area of 3d data (.obj)
	* Not implemented
	* @author Takuro Wada
	* @param filename Path of the 3d data
	* @return Surface area (cm3)
	*/
	public double calculateSurface(String filename){
		return 1.0;
	}
	
	private void read(RandomAccessFile raf){
		int vertices = 0;
		int triangles = 0;
		
		try {
			while(true){
				
				String tmpline = raf.readLine();
				if(tmpline == null) break;
				
				String line_data[] = tmpline.split(" ");
				
				if(line_data[0].equals("v")){
					vertices++;
					vertice_list.add(new Vector3d(
							Double.parseDouble(line_data[1]),
							Double.parseDouble(line_data[2]), 
							Double.parseDouble(line_data[3])));
					
				} else if (line_data[0].equals("f")){
					triangles++;
					face_list.add(new FaceObj(
								Integer.parseInt(line_data[1].split("/")[0])-1,
								Integer.parseInt(line_data[2].split("/")[0])-1,
								Integer.parseInt(line_data[3].split("/")[0])-1
							));
					
				} else if (line_data[0].equals("vt")){
				} else if (line_data[0].equals("vn")){
				} else if (line_data[0].equals("g")){
				} else if (line_data[0].equals("mtllib")){
				} else if (line_data[0].equals("usemtl")){
				}
			}
		} catch (EOFException eofe) {
		} catch (IOException e) {
		} catch (NullPointerException e){
		} finally{
		}
	}
	
	private double calculateTotalVolume(){
		
		double total_volume = 0;
		for(FaceObj tmpfo: face_list){
			total_volume += calculateSignedVolume(new TriangleObj(
								vertice_list.get(tmpfo.p1), 
								vertice_list.get(tmpfo.p2), 
								vertice_list.get(tmpfo.p3)));
		}
		
		return total_volume;
	}
}

class FaceObj{
	int p1;
	int p2;
	int p3;
	
	public FaceObj(int p1, int p2, int p3){
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
}