import engine2D.*;

public class Floor extends EngineObjectModel {
    Point3D pos;
    Rotation3D rot;
    Main.MyCanvas canvas;

    public Floor(Main.MyCanvas canvas){
        super("floor");
        this.canvas = canvas;
        this.pos = new Point3D(0,0,0);
        colliders.add(new BoxCollider(this,new Point3D(),new Vector3D(600,62)));
        rot = new Rotation3D();
        canvas.objs.add(this);
        canvas.needRedraw = true;
    }
    @Override
    public void setPosition(Point3D p) {
        this.pos.set(p);
    }

    @Override
    public void translate(Vector3D v) {
        this.pos.add(v);
    }

    @Override
    public void translate(double x, double y, double z) {
        this.pos.add(x,y,z);
    }

    @Override
    public Point3D getPosition() {
        return pos;
    }

    @Override
    public void setRotation(Rotation3D r) {
        this.rot = r;
    }

    @Override
    public void rotate(Vector3D v) {
        this.rot.add(v);
    }

    @Override
    public void rotate(double x, double y, double z) {
        this.rot.add(x,y,z);
    }

    @Override
    public Rotation3D getRotation() {
        return rot;
    }
}
