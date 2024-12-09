package engine2D;

public class EngineObject extends EngineObjectModel {
    public final Point3D position;
    public final Rotation3D rotation;


    //constructors
    public EngineObject(Point3D p, Rotation3D rotation, String tag){
        super(tag);
        
        this.position = p;
        this.rotation = rotation;
    }
    public EngineObject(){
        this(new Point3D(), new Rotation3D(), DEFAULT_TAG);

    }
    public EngineObject(Point3D p){
        this(p, new Rotation3D(), DEFAULT_TAG);

    }


    //EngineObjectModel overrides
    @Override
    public void setPosition(Point3D p) {
        position.set(p);

    }
    @Override
    public void translate(Vector3D v) {
        position.add(v);

    }
    @Override
    public void translate(double x, double y, double z) {
        position.add(x, y, z);

    }
    @Override
    public Point3D getPosition() {
        return position;

    }
    @Override
    public void setRotation(Rotation3D r) {
        rotation.set(r);

    }
    @Override
    public void rotate(Vector3D v) {
        rotation.add(v);

    }
    @Override
    public void rotate(double x, double y, double z) {
        rotation.add(x, y, z);

    }
    @Override
    public Rotation3D getRotation() {
        return rotation;

    }
}




