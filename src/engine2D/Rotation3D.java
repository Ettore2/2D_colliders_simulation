package engine2D;


public class Rotation3D extends Size3D{

    //constructors
    public Rotation3D(double x, double y, double z){
        set(x%360, y%360, z%360);

    }
    public Rotation3D(){
        this(0, 0, 0);

    }
    public Rotation3D(double x, double y){
        this(x, y, 0);

    }
    public Rotation3D(Rotation3D r){
        if(r != null){
            set(r.x%360, r.y%360, r.z%360);
        }else {
            set(0,0,0);
        }

    }


    //other methods
    public void set(Rotation3D r){
        if(r != null){
            set(r.x%360, r.y%360, r.z%360);
        }else {
            set(0,0,0);
        }


    }
    public Rotation3D copy(){
        return super.copy().toRotation();

    }
    public void add(Rotation3D r){
        this.x = (this.x+r.x)%360;
        this.y = (this.y+r.y)%360;
        this.z = (this.z+r.z)%360;
    }
    public Rotation3D sum(double x, double y, double z){
        return super.sum(x,y,z).toRotation();

    }
    public Rotation3D sum(Vector3D v){
        if(v != null){
            return super.sum(v.x,v.y,v.z).toRotation();
        }
        return super.sum(0,0,0).toRotation();

    }
    public Rotation3D sum(Rotation3D r){
        if(r != null) {
            return super.sum(r.x,r.y,r.z).toRotation();
        }
        return super.sum(0,0,0).toRotation();

    }
    public Rotation3D diffAbs(double x, double y, double z){
        return super.diffAbs(x%360,y%360,z%360).toRotation();

    }
    public Rotation3D diffAbs(Rotation3D r){
        if( r != null){
            return super.diffAbs(r.x,r.y,r.z).toRotation();
        }
        return super.diffAbs(0,0,0).toRotation();

    }
    public Rotation3D negative(){
        return super.negative().toRotation();

    }
    public boolean equals(Rotation3D r){
        if(r == null){
            return false;
        }
        return equals(r.x,r.y,r.z);

    }
    public boolean equals(Rotation3D r, double toll){
        if(r == null){
            return false;
        }
        return equals(r.x%360,r.y%360,r.z%360,toll);

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















