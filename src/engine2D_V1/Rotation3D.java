package engine2D_V1;

public class Rotation3D extends Size3D{

    //constructors
    public Rotation3D(double x, double y, double z){
        set(x, y, z);

    }
    public Rotation3D(){
        this(0, 0, 0);

    }
    public Rotation3D(double x, double y){
        this(x, y, 0);

    }
    public Rotation3D(Rotation3D r){
        this(r.x, r.y, r.z);

    }


    //other methods
    public void set(Rotation3D r){
        set(r.x, r.y, r.z);

    }
    public Rotation3D copy(){
        return super.copy().toRotation();

    }
    public void add(Rotation3D r){
        this.x += r.x;
        this.y += r.y;
        this.z += r.z;
    }
    public Rotation3D sum(double x, double y, double z){
        return super.sum(x,y,z).toRotation();

    }
    public Rotation3D sum(Vector3D v){
        return super.sum(v.x,v.y,v.z).toRotation();

    }
    public Rotation3D sum(Rotation3D r){
        return super.sum(r.x,r.y,r.z).toRotation();

    }
    public Rotation3D diffAbs(double x, double y, double z){
        return super.diffAbs(x,y,z).toRotation();

    }
    public Rotation3D diffAbs(Rotation3D r){
        return super.diffAbs(r.x,r.y,r.z).toRotation();

    }
    public Rotation3D negative(){
        return super.negative().toRotation();

    }
    public boolean equals(Rotation3D r){
        return equals(r.x,r.y,r.z);

    }
    public boolean equals(Rotation3D r, double toll){
        return equals(r.x,r.y,r.z,toll);

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
        return "rotation | "+super.toString();
    }
}















