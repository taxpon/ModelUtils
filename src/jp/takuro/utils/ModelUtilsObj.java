package jp.takuro.utils;

import java.io.*;
import java.util.*;
import javax.vecmath.*;

public class ModelUtilsObj extends ModelUtils{
	
	private ArrayList<Vector3d> vertice_list = new ArrayList<Vector3d>();
	private ArrayList<FaceObj> face_list = new ArrayList<FaceObj>();
	
	public double calculateVolume(String filename){
		
		double total_volume = 0;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "r");
			this.read(raf);
			
			total_volume = this.calculateTotalVolume();
			
			raf.close();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Catch exception :" + e);
		}
		
		return total_volume;
	}
	
	public double calculateSurface(String filename){
		return 1.0;
	}
	
	private void read(RandomAccessFile raf){
		int vertices = 0;
		int triangles = 0;
		int count = 0;
		
		try {
			while(true){
				count++;
				
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
			// TODO: handle exception
			System.out.println("Vertices :" + vertices);
			System.out.println("Triagnles :" + triangles);
			
		} catch (IOException e) {
			// TODO: handle exception
		} catch (NullPointerException e){
			
		} finally{
		//	return;
		}
	}
	
	private double calculateTotalVolume(){
		
		double total_volume = 0;
		
		System.out.println("vertice_list length : "+ vertice_list.size());
		System.out.println("face_list length : "+ face_list.size());
		
		for(FaceObj tmpfo: face_list){
			total_volume += calculateSignedVolume(new TriangleObj(
								vertice_list.get(tmpfo.p1), 
								vertice_list.get(tmpfo.p2), 
								vertice_list.get(tmpfo.p3)));
		}
		
		return total_volume;
	}
	
	private double calculateSignedVolume(TriangleObj t){
		
		double v321 = t.p3.x * t.p2.y * t.p1.z;
		double v231 = t.p2.x * t.p3.y * t.p1.z;
		double v312 = t.p3.x * t.p1.y * t.p2.z;
		double v132 = t.p1.x * t.p3.y * t.p2.z;
		double v213 = t.p2.x * t.p1.y * t.p3.z;
		double v123 = t.p1.x * t.p2.y * t.p3.z;
	
		double sined_volume = (-v321 + v231 + v312 - v132 - v213 + v123)/6.0f;
		return sined_volume;
	}

}

class TriangleObj{
	Vector3d p1;
	Vector3d p2;
	Vector3d p3;
	
	public TriangleObj(Vector3d p1, Vector3d p2, Vector3d p3){
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
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