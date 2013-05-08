package jp.takuro.utils;

import java.io.*;
import javax.vecmath.*;

public class ModelUtilsStl extends ModelUtils{
	
	private int HEADER_LENGTH = 80; // Length of the header of the stl *binary* file
	
	/**
	* Calculate volume of 3d data (.stl)
	* @author Takuro Wada
	* @param filename Path of the 3d data
	* @return Volume (cm3)
	*/
	public double calculateVolume(String filename){
		int triangle_count;
		double total_volume = 0;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "r");
			triangle_count = this.readHeader(raf);
			for(int i = 0; i < triangle_count; i++){
				total_volume += calculateSignedVolume(readTriangle(raf));
			}
			raf.close();
		} catch (Exception e) {}
		
		return total_volume;
	}
	
	/**
	* Calculate surface area of 3d data (.stl)
	* Not implemented
	* @author Takuro Wada
	* @param filename Path of the 3d data
	* @return Surface area (cm3)
	*/
	public double calculateSurface(String filename){
		return 1.0;
	}
	
	private int readHeader(RandomAccessFile raf){
		try{
			raf.seek(HEADER_LENGTH);
			int count = this.readInt(raf.readInt());
			//System.out.println(count + " triangles.");
			return count;
		} catch(Exception e){
			throw new Error(e);
		}
	}
	
	@SuppressWarnings("finally")
	private TriangleStl readTriangle(RandomAccessFile raf){
		TriangleStl t = new TriangleStl();
		
		try{		
			//50byte•ª‚ð“Ç‚Ýž‚Þ
			t.n = new Vector3d(
						this.readFloat(raf.readFloat()),
						this.readFloat(raf.readFloat()),
						this.readFloat(raf.readFloat())
					);
			
			t.p1 = new Vector3d(
					this.readFloat(raf.readFloat()),
					this.readFloat(raf.readFloat()),
					this.readFloat(raf.readFloat())
				);
			
			t.p2 = new Vector3d(
					this.readFloat(raf.readFloat()),
					this.readFloat(raf.readFloat()),
					this.readFloat(raf.readFloat())
				);
			
			t.p3 = new Vector3d(
					this.readFloat(raf.readFloat()),
					this.readFloat(raf.readFloat()),
					this.readFloat(raf.readFloat())
				);
			
			raf.skipBytes(2);
	
		} catch(EOFException e){
			
		} catch(Exception e){
			throw new Error(e);
		} finally {
			return t;
		}
	}
}

