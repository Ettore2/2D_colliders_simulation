package engine2D;


public abstract class ParticleModel extends EngineEntityModel{
    public static final String DEFAULT_TAG = "particles";
    public static final double DO_NOT_FADE = -1;


    protected EngineManager engine;
    protected final Vector3D force, forceAcc;
    protected double angForce, angAcc;//force of rotation
    protected final Point3D pos;
    protected final Rotation3D rot;
    protected double lifeTime;
    protected double timerLife;
    protected double fadingStartTime, currVisibility, minVisibility, fadingSpeed;


    //constructors
    public ParticleModel(EngineManager manager, double lifeTime, Point3D pos, Rotation3D rot, Vector3D force, Vector3D acceleration, double angForce, double angAcc) {
        super(DEFAULT_TAG);
        this.timerLife = 0;
        this.lifeTime = lifeTime;
        this.engine = manager;

        this.pos = new Point3D();
        this.rot = new Rotation3D();
        this.force = new Vector3D();
        this.forceAcc = new Vector3D();
        this.angForce = angForce;
        this.angAcc = angAcc;

        this.pos.set(pos);
        this.rot.set(rot);
        this.force.set(force);
        this.forceAcc.set(acceleration);

        setFading(DO_NOT_FADE,1,0,0);

    }
    public ParticleModel(EngineManager manager, double lifeTime, Point3D pos) {
        this(manager,lifeTime,pos,null,null,null,0,0);

    }


    //interfaces
    public void setPosition(Point3D p) {
        this.pos.set(p);
    }
    public void translate(Vector3D v) {
        this.pos.add(v);
    }
    public void translate(double x, double y, double z) {
        this.pos.add(new Point3D(x,y,z));
    }
    public Point3D getPosition() {
        return pos;
    }
    public void setRotation(Rotation3D r) {
        rot.set(r);
    }
    public void rotate(Vector3D v) {
        rot.add(v);
    }
    public void rotate(double x, double y, double z) {
        rot.add(new Rotation3D(x,y,z));
    }
    public Rotation3D getRotation() {
        return rot;
    }


    //other methods
    public void logicUpdate(double deltaT) {
        if(timerLife < lifeTime && currVisibility > 0){
            timerLife += deltaT;
            updateState(deltaT);

        }else {
            deleteFromEngine();
        }

    }
    public void setFading(double fadingStartTime,double visibilityStartVal, double visibilityEndVal, double fadingSpeed){
        this.fadingStartTime = fadingStartTime;
        this.currVisibility = visibilityStartVal;
        this.minVisibility = visibilityEndVal;
        this.fadingSpeed = fadingSpeed;
    }
    public void deleteFromEngine(){
        if(engine != null && engine.manageEntity(this)){
            engine.removeEntity(this);
        }

    }
    public void updateState(double deltaT){
        translate(force.createScaledInstance(deltaT/1000));
        rotate(0,0,angForce*deltaT/1000);
        force.add(forceAcc.createScaledInstance(deltaT/1000));
        angAcc += angAcc;


        if(fadingStartTime >= 0 && currVisibility > minVisibility && timerLife >= fadingStartTime){
            currVisibility-=fadingSpeed*deltaT/1000;

            if(currVisibility < minVisibility){
                currVisibility = minVisibility;
            }
        }

    }
}











