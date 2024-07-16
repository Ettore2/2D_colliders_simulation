import engine2D_V1.*;

public class Player extends EngineObjectModel {
    Point3D pos;
    Rotation3D rot;
    Main.MyCanvas canvas;

    boolean grounded;
    boolean inpL,inpR,inpU,inpD,inp1,inp2;
    public Player(Main.MyCanvas canvas){
        super("player");
        this.canvas = canvas;
        this.pos = new Point3D(0,350,0);
        colliders.add(new BoxCollider(this,new Point3D(),new Vector3D(60,62)));
        rot = new Rotation3D();
        grounded = false;
        inpL = false;
        inpR = false;
        inpU = false;
        inpD = false;
        inp1 = false;
        inp2 = false;
        canvas.objs.add(this);
        canvas.needRedraw = true;

    }

    @Override
    public void logicUpdate(double deltaT) {
        super.logicUpdate(deltaT);
        grounded = false;
    }
    @Override
    public void collision(EngineObjectModel obj, double deltaT) {
        super.collision(obj, deltaT);
        if(obj.tag == "floor"){
            grounded = true;
        }
    }

    @Override
    public void postCollisionUpdate(double deltaT) {
        /*
        if(!grounded){
            translate(new Vector3D(0,0.5));
            canvas.needRedraw = true;
        }
        if(grounded && inpU){
            translate(new Vector3D(0,-200));
            canvas.needRedraw = true;
        }
        */

        if(inpR && !inpL){
            translate(new Vector3D(3,0));
            canvas.needRedraw = true;
        }
        if(!inpR && inpL){
            translate(new Vector3D(-3,0));
            canvas.needRedraw = true;
        }
        if(inpU && !inpD){
            translate(new Vector3D(0,-3));
            canvas.needRedraw = true;
        }
        if(!inpU && inpD){
            translate(new Vector3D(0,3));
            canvas.needRedraw = true;
        }
        if(inp1 && !inp2){
            rotate(new Vector3D(0,0,-3));
            canvas.needRedraw = true;
        }
        if(!inp1 && inp2){
            rotate(new Vector3D(0,0,3));
            canvas.needRedraw = true;
        }


        inpL = false;
        inpR = false;
        inpU = false;
        inpD = false;
        inp1 = false;
        inp2 = false;
    }

    @Override
    public void graphicUpdate(double deltaT) {
        super.graphicUpdate(deltaT);
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
