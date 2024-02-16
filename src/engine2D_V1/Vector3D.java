package engine2D_V1;

public class Vector3D {
    public static final Vector3D V_UP = new Vector3D(1,0,0), V_DOWN = new Vector3D(-1,0,0),
            V_LEFT = new Vector3D(0,-1,0), V_RIGHT = new Vector3D(0,1,0);

    public double x, y, z;

    //constructors
    public Vector3D(double x, double y, double z){
        set(x, y, z);

    }
    public Vector3D(){
        this(0, 0, 0);

    }
    public Vector3D(Point3D p){
        this(p.x, p.y, p.z);

    }
    public Vector3D(double x, double y){
        this(x, y, 0);

    }


    //other methods
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }
    public void set(Vector3D p){
        set(p.x, p.y, p.z);

    }
    public Vector3D copy(){
        return new Vector3D(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));

    }
    public void add(Vector3D v){
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }
    public Vector3D sum(Vector3D v){
        return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);

    }
    public Vector3D diff(Vector3D v){
        return new Vector3D(Math.abs(this.x - v.x), Math.abs(this.y - v.y), Math.abs(this.z - v.z));

    }
    public Vector3D negative(){
        return new Vector3D(-x, -y, -z);

    }
    public boolean equals(Vector3D v){
        return this.x == v.x && this.y == v.y && this.z == v.z;

    }
    public boolean equals(Vector3D v, double toll){
        if(!(this.x > v.x + toll && this.x < v.x-toll)){
            return false;
        }
        if(!(this.y > v.y + toll && this.y < v.y-toll)){
            return false;
        }
        if(!(this.z > v.z + toll && this.z < v.z-toll)){
            return false;
        }
        return true;
    }


    //translation methods
    public Point3D getPoint(){
        return new Point3D(x,y,z);

    }
    public Rotation3D getRotation(){
        return new Rotation3D(x,y,z);

    }

    @Override
    public String toString() {
        return "x: "+x+"\ty: "+y+"\tz: "+z;
    }
}
