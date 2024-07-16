package engine2D_V1;

import java.util.Random;

public abstract class ParticlesCreator extends EngineEntityModel{
    protected static final Random r = new Random();
    public static final String DEFAULT_TAG = "particles_creator";
    public static final double DO_NOT_FADE = ParticleModel.DO_NOT_FADE;
    public static final double INFINITE_DURATION = -1;


    protected EngineManager manager;
    protected boolean paused;
    protected final Point3D pos;
    protected final Rotation3D rot;
    protected double lifeTime, timerLife;
    protected double spawnFixDelay, spawnRandDelay, spawnCurrDelay, timerSpawn;
    protected double spawnFixLifeTime, spawnRandLifeTime;
    protected boolean applyAccelerationWithForceRotation;
    protected double spawnFixRot, spawnRandRot;
    protected double spawnFixForce, spawnRandForce;
    protected double spawnFixForceRot, spawnRandForceRot;
    protected double spawnFixAcc, spawnRandAcc;
    protected double spawnFixAccRot, spawnRandAccRot;
    protected double spawnFixAngForce, spawnRandAngForce;
    protected double spawnFixAngAcc, spawnRandAngAcc;
    protected double spawnFixDistance, spawnRandDistance;
    protected double spawnFixDistanceRot, spawnRandDistanceRot;
    protected double fadingStartTime, visibilityStartVal, visibilityEndVal, fadingSpeed;




    //constructors
    public ParticlesCreator(EngineManager manager, Point3D pos, double spawnFixDelay, double spawnRandDelay, double lifeTime){
        super(DEFAULT_TAG);

        this.manager = manager;
        this.pos = new Point3D();
        this.rot = new Rotation3D();
        setLifeTime(lifeTime);

        this.pos.set(pos);
        timerLife = 0;
        timerSpawn = 0;

        setSpawnDelay(spawnFixDelay,spawnRandDelay);
        setSpawnDistance(0,0);
        setSpawnDistanceRot(0,0);
        setSpawnParticleRotation(0,0);
        setSpawnForce(0,0);
        setSpawnForceRotation(0,0);
        setSpawnAcceleration(0,0);
        setSpawnAccelerationRot(0,0);
        setSpawnAngularForce(0,0);
        setSpawnAngularAcceleration(0,0);
        applyAccelerationWithForceRotation(false);
        setSpawnFading(DO_NOT_FADE,1,0,0);

        spawnCurrDelay = getNewSpawnDelay();

        start();
    }


    //interfaces
    @Override
    public void setPosition(Point3D p) {
        pos.set(p);

    }
    @Override
    public void translate(Vector3D v) {
        pos.add(v);

    }
    @Override
    public void translate(double x, double y, double z) {
        pos.add(x,y,z);

    }
    @Override
    public Point3D getPosition() {
        return pos;

    }
    @Override
    public void setRotation(Rotation3D r) {
        rot.set(r);

    }
    @Override
    public void rotate(Vector3D v) {
        rot.add(v);

    }
    @Override
    public void rotate(double x, double y, double z) {
        rot.add(x,y,z);

    }
    @Override
    public Rotation3D getRotation() {
        return rot;

    }
    @Override
    public void logicUpdate(double deltaT) {
        if(!paused){
            timerLife += deltaT;
            timerSpawn += deltaT;


            while (timerSpawn >= spawnCurrDelay){
                timerSpawn -= spawnCurrDelay;
                if(!applyAccelerationWithForceRotation){
                    spawnParticle(getSpawnLifeTime(),getNewSpawnPos(),getNewSpawnForce(),getNewSpawnAcceleration(),getNewSpawnRot(),getNewSpawnAngForce(),getNewSpawnAngAcc());
                }else {
                    Vector3D force = getNewSpawnForce();
                    spawnParticle(getSpawnLifeTime(),getNewSpawnPos(),force,getNewSpawnAcceleration(force.getAngleDeg()),getNewSpawnRot(),getNewSpawnAngForce(),getNewSpawnAngAcc());
                }

                spawnCurrDelay = getNewSpawnDelay();

            }

            if(timerLife >= lifeTime && lifeTime > 0){
                cancel();

            }

        }
    }


    //other methods
    public abstract ParticleModel spawnParticle(double lifeTime, Point3D pos, Vector3D force, Vector3D acceleration, Rotation3D rot, double angForce, double angAcc);
    public void spawn(){
        timerLife = 0;
        manager.addEntity(this);
    }
    public void cancel(){
        manager.removeEntity(this);

    }
    public void stop(){
        paused = true;
        timerSpawn = 0;
    }
    public void start(){
        paused = false;
    }
    public void reset(){
        timerLife = 0;

    }
    public void applyAccelerationWithForceRotation(boolean b) {
        this.applyAccelerationWithForceRotation = b;

    }
    private double getNewSpawnDelay(){
        if(spawnRandDelay == 0){
            return spawnFixDelay;
        }
        return spawnFixDelay + r.nextDouble()%spawnRandDelay;

    }
    public void setLifeTime(double time){
        this.lifeTime = time;

    }
    public void setSpawnDelay(double fixDelay, double randDelay){
        this.spawnFixDelay = fixDelay;
        this.spawnRandDelay = randDelay;
    }
    public void setSpawnLifeTime(double fixTime, double randTime){
        this.spawnFixLifeTime = fixTime;
        this.spawnRandLifeTime = randTime;
    }
    public void setSpawnParticleRotation(double fixRot, double randRot){
        this.spawnFixRot = fixRot;
        this.spawnRandRot = randRot;
    }
    public void setSpawnForce(double fixF, double randF){
        this.spawnFixForce = fixF;
        this.spawnRandForce = randF;
    }
    public void setSpawnForceRotation(double fixRot, double randRot){
        this.spawnFixForceRot = fixRot;
        this.spawnRandForceRot = randRot;
    }
    public void setSpawnAcceleration(double fixA, double randA){
        this.spawnFixAcc = fixA;
        this.spawnRandAcc = randA;
    }
    public void setSpawnAccelerationRot(double fixRot, double randRot){
        this.spawnFixAccRot = fixRot;
        this.spawnRandAccRot = randRot;
    }
    public void setSpawnAngularForce(double fixF, double randF){
        this.spawnFixAngForce = fixF;
        this.spawnRandAngForce = randF;
    }
    public void setSpawnAngularAcceleration(double fixA, double randA){
        this.spawnFixAngAcc = fixA;
        this.spawnRandAngAcc = randA;
    }
    public void setSpawnDistance(double fixD, double randD){
        this.spawnFixDistance = fixD;
        this.spawnRandDistance = randD;
    }
    public void setSpawnDistanceRot(double fixR, double randR){
        this.spawnFixDistanceRot = fixR;
        this.spawnRandDistanceRot = randR;
    }
    public void setSpawnFading(double fadingStartTime, double startVal, double endValue, double fadingSpeed){
        this.fadingStartTime = fadingStartTime;
        this.visibilityStartVal = startVal;
        this.visibilityEndVal = endValue;
        this.fadingSpeed = fadingSpeed;
    }

    private double getSpawnLifeTime(){
        if(spawnRandLifeTime == 0){
            return spawnFixLifeTime;
        }
        return spawnFixLifeTime + r.nextDouble()% spawnRandLifeTime;

    }
    private Point3D getNewSpawnPos(){
        double rot;
        double force;
        if(spawnRandDistanceRot == 0){
            rot = spawnFixDistanceRot;
        }else {
            rot = spawnFixDistanceRot + r.nextDouble()%spawnRandDistanceRot;
        }
        rot = Math.toRadians(rot);
        if(spawnRandDistance == 0){
            force = spawnFixDistance;
        }else {
            force = spawnFixDistance + r.nextDouble()%spawnRandDistance;
        }

        return getPosition().sum(new Vector3D(force*Math.cos(rot),force*Math.sin(rot),0));
    }
    private Vector3D getNewSpawnForce(){
        double rot;
        double force;
        if(spawnRandForceRot == 0){
            rot = spawnFixForceRot;
        }else {
            rot = spawnFixForceRot + (r.nextDouble()*1000)%spawnRandForceRot;
        }

        rot = Math.toRadians(rot);
        if(spawnRandForce == 0){
            force = spawnFixForce;
        }else {
            force = spawnFixForce + (r.nextDouble()*1000)%spawnRandForce;
        }
        return new Vector3D(force*Math.cos(rot),force*Math.sin(rot),0);
    }
    private Vector3D getNewSpawnAcceleration(double rot){
        rot = Math.toRadians(rot);
        double force;
        if(spawnRandAcc == 0){
            force = spawnFixAcc;
        }else {
            force = spawnFixAcc + (r.nextDouble()*1000)%spawnRandAcc;
        }
        return new Vector3D(force*Math.cos(rot),force*Math.sin(rot),0);
    }
    private Vector3D getNewSpawnAcceleration(){
        if(spawnRandAccRot == 0){
            return getNewSpawnAcceleration(spawnFixAccRot);
        }else {
            return getNewSpawnAcceleration(spawnFixAccRot + (r.nextDouble()*1000)%spawnRandAccRot);
        }
    }
    private Rotation3D getNewSpawnRot(){
        if(spawnRandRot == 0){
            return new Rotation3D(0,0,spawnFixRot);
        }else {
            return new Rotation3D(0,0,spawnFixRot + (r.nextDouble()*1000)% spawnRandRot);
        }


    }
    private double getNewSpawnAngForce(){
        if(spawnRandAngForce == 0){
            return spawnFixAngForce;
        }else {
            return spawnFixAngForce + (r.nextDouble()*1000)% spawnRandAngForce;
        }

    }
    private double getNewSpawnAngAcc(){
        if(spawnRandAngAcc == 0){
            return spawnFixAngAcc;
        }else {
            return spawnFixAngAcc + (r.nextDouble()*1000)% spawnRandAngAcc;
        }


    }
}
