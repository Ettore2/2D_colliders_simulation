package engine2D_V1;


public abstract class Collider2D implements HaveTag {
    public static boolean DEFAULT_VISIBILITY = false;
    public static boolean INVERTED_Y = false;
    public static final int SQUARE = 0, CIRCLE = 1;


    public final int type;
    protected String tag;
    protected Point3D center;
    protected Rotation3D rotation;
    public boolean isActive,useRelativePosition,isVisible;
    protected EngineObjectModel owner;


    //constructors
    Collider2D(int type, EngineObjectModel owner, Point3D center, String tag){
        this.center = center;
        this.type = type;
        this.owner = owner;
        this.tag = tag;

        this.rotation = new Rotation3D(0,0,0);
        isActive = true;
        useRelativePosition = true;
        isVisible = DEFAULT_VISIBILITY;
    }
    Collider2D(int type, EngineObjectModel owner, Point3D center){
        this(type, owner, center,  "");

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
        if(owner == null){
            return new Point3D(center.x,center.y,center.z);
        }
        Rotation3D ownerRotation = owner.getRotation();
        double ownerDistance = owner.getPosition().distance2D(center);

        return new Point3D(ownerDistance*Math.cos(ownerRotation.z), ownerDistance*Math.sin(ownerRotation.z), this.center.z);

    }
    public Point3D getPositionAbs(){
        if(owner == null){
            return getPositionRel();
        }
        return getPositionRel().sum(owner.getPosition());

    }
    public double distanceXY(Point3D p){
        return getPositionAbs().distance2D(p);

    }
    public double distance(Point3D p){
        return getPositionAbs().distance(p);

    }


    //HaveTag overrides
    public String getTag(){
        return tag;

    }
    public void setTag(String tag){
        this.tag = tag;

    }


}
