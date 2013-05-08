package jp.takuro.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ModelUtils {
	
	/**
	* Calculate volume of 3d data
	* @author Takuro Wada
	* @param filename Path of the 3d data
	* @return Volume (cm3)
	*/
	public abstract double calculateVolume(String filename);

	/**
	* Calculate surface area of 3d data
	* Not implemented
	* @author Takuro Wada
	* @param filename Path of the 3d data
	* @return Surface area (cm3)
	*/
	public abstract double calculateSurface(String filename);
	
	/**
	* Calculate signed volume with the given triangle
	* @author Takuro Wada
	* @param t Triangle for calculation
	* @return Signed volume (cm3)
	*/
	protected double calculateSignedVolume(Triangle t){
		double v321 = t.p3.x * t.p2.y * t.p1.z;
		double v231 = t.p2.x * t.p3.y * t.p1.z;
		double v312 = t.p3.x * t.p1.y * t.p2.z;
		double v132 = t.p1.x * t.p3.y * t.p2.z;
		double v213 = t.p2.x * t.p1.y * t.p3.z;
		double v123 = t.p1.x * t.p2.y * t.p3.z;
	
		double sined_volume = (-v321 + v231 + v312 - v132 - v213 + v123)/6.0f;
		return sined_volume;
	}
	
	/**
	* Translate int(BIG_ENDIAN) to int(LITTLE_ENDIAN)
	* @author Takuro Wada
	* @param int_num BIG_ENDIAN int
	* @return LITTLE_ENDIAN int
	*/
	protected int readInt(int in_num){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(in_num);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getInt(0);
	}
	
	/**
	* Translate float(BIG_ENDIAN) to float(LITTLE_ENDIAN)
	* @author Takuro Wada
	* @param int_num BIG_ENDIAN float
	* @return LITTLE_ENDIAN float
	*/
	protected float readFloat(float in_float){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putFloat(in_float);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getFloat(0);
	}
}
