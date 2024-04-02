package engine2D_V1;

class Size3D {
    public static final double DEFAULT_TOLLERANCE = 0.0001d;
    public double x, y, z;

    //constructors
    protected Size3D(double x, double y, double z){
        set(x, y, z);

    }
    protected Size3D(double x, double y){
        this(x, y, 0);

    }
    protected Size3D(){
        this(0, 0, 0);

    }


    //other methods
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }
    public Size3D copy(){
        return new Size3D(this.x, this.y, this.z);

    }
    public void add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    public void add(Vector3D v){
        if(v != null){
            add(v.x,v.y,v.z);
        }


    }
    public Size3D sum(double x, double y, double z){
        return new Size3D(this.x + x, this.y + y, this.z + z);

    }
    public Size3D sum(Vector3D v){
        if(v != null){
            return sum(v.x,v.y,v.z);
        }
        return sum(0,0,0);

    }
    public Size3D diffAbs(double x, double y, double z){
        return new Size3D(Math.abs(this.x - x), Math.abs(this.y - y), Math.abs(this.z - z));

    }
    public Size3D negative(){
        return new Size3D(-x, -y, -z);

    }
    public boolean equals(double x, double y, double z, double toll){
        if(Math.abs(this.x-x) > toll){
            return false;
        }
        if(Math.abs(this.y-y) > toll){
            return false;
        }
        if(Math.abs(this.z-z) > toll){
            return false;
        }
        return true;
    }
    public boolean equals(double x, double y, double z){
        return equals(x,y,z,DEFAULT_TOLLERANCE);

    }


    //translation methods
    public Point3D toPoint(){
        return new Point3D(x,y,z);

    }
    public Vector3D toVector(){
        return new Vector3D(x,y,z);

    }
    public Rotation3D toRotation(){
        return new Rotation3D(x,y,z);

    }

    @Override
    public String toString() {
        return "x: "+x+"\ty: "+y+"\tz: "+z;
    }
}
