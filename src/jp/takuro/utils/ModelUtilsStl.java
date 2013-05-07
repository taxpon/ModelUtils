package jp.takuro.utils;

import java.io.*;
import java.nio.*;
import javax.vecmath.*;

public class ModelUtilsStl extends ModelUtils{
	private int HEADER_LENGTH = 80;
	
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
			//50byte分を読み込む
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
	
	private double calculateSignedVolume(TriangleStl t){
		
		double v321 = t.p3.x * t.p2.y * t.p1.z;
		double v231 = t.p2.x * t.p3.y * t.p1.z;
		double v312 = t.p3.x * t.p1.y * t.p2.z;
		double v132 = t.p1.x * t.p3.y * t.p2.z;
		double v213 = t.p2.x * t.p1.y * t.p3.z;
		double v123 = t.p1.x * t.p2.y * t.p3.z;
	
		double sined_volume = (-v321 + v231 + v312 - v132 - v213 + v123)/6.0f;
		return sined_volume;
	}
	
	// ユーティリティ関数
	private int readInt(int in_num){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(in_num);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getInt(0);
	}
	
	private float readFloat(float in_float){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putFloat(in_float);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getFloat(0);
	}
}

class TriangleStl{
	Vector3d n;
	Vector3d p1;
	Vector3d p2;
	Vector3d p3;
}

