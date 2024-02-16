package engine2D_V1;

public class Rotation3D{
    public double x, y, z;


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


    //rotation
    public void set(Rotation3D r){
        set(r.x, r.y, r.z);

    }
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }
    public Rotation3D copy(){
        return new Rotation3D(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));

    }
    public void add(Rotation3D r){
        this.x += r.x;
        this.y += r.y;
        this.z += r.z;
    }
    public Rotation3D sum(Rotation3D r){
        return new Rotation3D(this.x + r.x, this.y + r.y, this.z + r.z);

    }
    public Rotation3D diff(Rotation3D r){
        return new Rotation3D(Math.abs(this.x - r.x), Math.abs(this.y - r.y), Math.abs(this.z - r.z));

    }
    public Rotation3D negative(){
        return new Rotation3D(-x, -y, -z);

    }


    //translation methods
    public Vector3D getVector(){
        return new Vector3D(x,y,z);

    }

    @Override
    public String toString() {
        return "x: "+x+"\ty: "+y+"\tz: "+z;
    }
}














