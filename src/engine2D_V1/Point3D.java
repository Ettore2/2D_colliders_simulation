package engine2D_V1;

public class Point3D extends Size3D{

    //constructors
    public Point3D(double x, double y, double z){
        set(x, y, z);

    }
    public Point3D(){
        this(0, 0, 0);

    }
    public Point3D(double x, double y){
        this(x, y, 0);

    }
    public Point3D(Point3D v){
        this(v.x, v.y, v.z);

    }


    //other methods
    public void set(Point3D v){
        set(v.x, v.y, v.z);

    }
    public Point3D copy(){
        return super.copy().toPoint();

    }
    public void add(Point3D v){
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }
    public Point3D sum(double x, double y, double z){
        return super.sum(x,y,z).toPoint();

    }
    public Point3D sum(Vector3D v){
        return super.sum(v.x,v.y,v.z).toPoint();

    }
    public Point3D sum(Point3D p){
        return super.sum(p.x,p.y,p.z).toPoint();

    }
    public Point3D diffAbs(double x, double y, double z){
        return super.diffAbs(x,y,z).toPoint();

    }
    public Point3D diffAbs(Point3D p){
        return super.diffAbs(p.x, p.y, p.z).toPoint();

    }
    public Point3D negative(){
        return super.negative().toPoint();

    }
    public boolean equals(Point3D p){
        return equals(p.x,p.y,p.z);

    }
    public boolean equals(Point3D p, double toll){
        return equals(p.x,p.y,p.z,toll);

    }


    //specific methods
    public double distance(double x, double y, double z){
        return Math.sqrt((this.x-x)*(this.x-x) + (this.y-y)*(this.y-y) + (this.z-z)*(this.z-z));

    }
    public double distance(Point3D p){
        return distance(p.x,p.y,p.z);

    }
    public double distance2D(double x, double y){
        return Math.sqrt((this.x-x)*(this.x-x) + (this.y-y)*(this.y-y));

    }
    public double distance2D(Point3D p){
        return distance2D(p.x,p.y);

    }



    @Override
    public String toString() {
        return "point | "+super.toString();
    }
}












