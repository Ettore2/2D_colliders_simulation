package engine2D_V1;

import java.util.Vector;

public class Vector3D extends Size3D{
    public static final Vector3D V_UP = new Vector3D(1,0,0), V_DOWN = new Vector3D(-1,0,0),
            V_LEFT = new Vector3D(0,-1,0), V_RIGHT = new Vector3D(0,1,0);

    //constructors
    public Vector3D(double x, double y, double z){
        set(x, y, z);

    }
    public Vector3D(){
        this(0, 0, 0);

    }
    public Vector3D(double x, double y){
        this(x, y, 0);

    }
    public Vector3D(Vector3D v){
        this(v.x, v.y, v.z);

    }


    //other methods
    public void set(Vector3D v){
        set(v.x, v.y, v.z);

    }
    public Vector3D copy(){
        return super.copy().toVector();

    }
    public Vector3D sum(double x, double y, double z){
        return super.sum(x,y,z).toVector();

    }
    public Vector3D sum(Vector3D v){
        return super.sum(v.x,v.y,v.z).toVector();

    }
    public Vector3D diffAbs(double x, double y, double z){
        return super.diffAbs(x,y,z).toVector();

    }
    public Vector3D diffAbs(Vector3D v){
        return new Vector3D(Math.abs(this.x - v.x), Math.abs(this.y - v.y), Math.abs(this.z - v.z));

    }
    public Vector3D negative(){
        return super.negative().toVector();

    }
    public boolean equals(Vector3D v){
        return equals(v.x,v.y,v.z);

    }
    public boolean equals(Vector3D v, double toll){
        return equals(v.x,v.y,v.z,toll);

    }

    //specific methods
    public double getStrength(){
        return Math.sqrt(x*x + y*y + z*z);

    }
    public double getStrength2D(){
        return Math.sqrt(x*x + y*y);

    }

    @Override
    public String toString() {
        return "vector | "+super.toString();
    }
}
