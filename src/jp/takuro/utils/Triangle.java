package jp.takuro.utils;

import javax.vecmath.*;

public abstract class Triangle {
	Vector3d p1;
	Vector3d p2;
	Vector3d p3;
	
	Triangle(){}
	
	Triangle(Vector3d p1, Vector3d p2, Vector3d p3){
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
}

class TriangleObj extends Triangle{
	public TriangleObj(Vector3d p1, Vector3d p2, Vector3d p3){
		super(p1,p2,p3);
	}
}

class TriangleStl extends Triangle{
	Vector3d n;
}


