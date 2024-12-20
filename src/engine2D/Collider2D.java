package engine2D;



public abstract class Collider2D implements HaveTag {
    public static final String DEFAULT_TAG = "collider";
    public static boolean DEFAULT_VISIBILITY = false;
    public static boolean INVERTED_Y = false;
    protected String tag;
    protected Point3D center;
    protected Rotation3D rotation;
    public boolean isActive,useRelativePosition,isVisible;
    protected EngineObjectModel owner;


    //constructors
    Collider2D(EngineObjectModel owner, Point3D center, String tag){
        this.center = center;
        this.owner = owner;
        if(tag != null){
            this.tag = tag;
        }else {
            this.tag = DEFAULT_TAG;
        }


        this.rotation = new Rotation3D(0,0,0);
        isActive = true;
        useRelativePosition = true;
        isVisible = DEFAULT_VISIBILITY;
    }
    Collider2D(EngineObjectModel owner, Point3D center){
        this(owner, center,  null);

    }


    //setters
    public void setOwner(EngineObjectModel owner) {
        this.owner = owner;

    }
    public void setRotation(Rotation3D rotation) {
        if(rotation == null){
            this.rotation = new Rotation3D(0,0,0);
        }else {
            this.rotation = rotation;

            rotation.x = rotation.x%360;
            rotation.y = rotation.y%360;
            rotation.z = rotation.z%360;

            if(rotation.x < 0){
                rotation.x = 360 + rotation.x;
            }
            if(rotation.y < 0){
                rotation.y = 360 + rotation.y;
            }
            if(rotation.z < 0){
                rotation.z = 360 + rotation.z;
            }
        }
    }

    //getters
    public EngineObjectModel getOwner() {
        return owner;

    }
    public Rotation3D getRotationRel() {
        return rotation;

    }
    public Rotation3D getRotationAbs() {
        Rotation3D r = getRotationRel();
        if(owner == null){
            return r.copy();
        }

        return owner.getRotation().sum(r);

    }

    //other methods
    public abstract boolean isColliding(Collider2D col);
    public abstract double getArea();
    public void rotate(double degrees){
        Rotation3D currRotation = getRotationRel();

        setRotation(new Rotation3D(currRotation.x,currRotation.y,currRotation.z+degrees));

    }


    //HavePosition3D overrides
    public void setPositionRel(Point3D p){
        center.set(p);

    }
    public void setPositionRel(double x, double y, double z){
        center.set(x,y,z);

    }
    public void setPosX(double x){
        center.x = x;

    }
    public void setPosY(double y){
        center.y = y;

    }
    public void setPosZ(double z){
        center.z = z;

    }
    public Point3D getPositionRel(){
        return new Point3D(center.x,center.y,center.z);

    }
    public Point3D getPositionAbs(){
        if(owner == null){
            return getPositionRel();
        }

        Point3D posRel = getPositionRel();
        double rotation = Math.toRadians(owner.getRotation().z) + Math.atan2(posRel.y, posRel.x);
        Point3D ownerPos = owner.getPosition();
        double distance = getPositionRel().distance2D(new Point3D(0,0,0));

        return new Point3D(ownerPos.sum(new Point3D(distance*Math.cos(rotation),distance*Math.sin(rotation))));

    }
    public double distanceXY(Point3D p){
        return getPositionAbs().distance2D(p);

    }
    public double distance(Point3D p){
        return getPositionAbs().distance(p);

    }
    abstract public int getPositionRelativeToOther(Point3D pos);


    //HaveTag overrides
    public String getTag(){
        return tag;

    }
    public void setTag(String tag){
        this.tag = tag;

    }


}
