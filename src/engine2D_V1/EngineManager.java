package engine2D_V1;

import java.util.Date;
import java.util.Vector;

public class EngineManager {
    private Vector<EngineObjectModel> managedObjects;
    protected int fps;
    protected double framesDelay;
    protected Date lastExecution;
    protected double frameTimer;
    protected boolean deleteTimeExceed;


    //constructors

    /**
     *
     * @param fps frames executed every second
     */
    public EngineManager(int fps, boolean deleteTimeExceed){
        this.managedObjects = new Vector<>();
        this.setFps(fps);
        this.lastExecution = new Date();
        this.frameTimer = 0;
        this.deleteTimeExceed = deleteTimeExceed;

    }
    /**
     *
     * @param fps frames executed every second
     */
    public EngineManager(int fps){
        this(fps,false);

    }


    //setters
    synchronized public void setFps(int fps){
        this.fps = fps;
        framesDelay = 6000f / fps;

    }


    //getters
    synchronized public int getFps(){
        return fps;

    }
    synchronized public double getFramesDelay(){
        return framesDelay;

    }


    //other methods
    /**
     * needs to be called enough for the frameRate to be constant but not enough to saturate teh cpu
     */
    synchronized public void doCycle(){
        //debug("do updates");
        doLogicUpdates(framesDelay);
        //debug("doneLogicUpdates");
        doCollisions(framesDelay);
        //debug("doneCollisions");
        doPostCollisionUpdates(framesDelay);
        //debug("donePostCollisionUpdates");
        doGraphicUpdates(framesDelay);
        //debug("doneGraphicUpdates");
        doPostGraphicUpdates(framesDelay);
        //debug("doPostGraphicUpdates");

    }
    synchronized public boolean shouldCycle(){
        updateTimer();

        return frameTimer >= framesDelay;

    }
    synchronized public void subtractTimer(){
        if(deleteTimeExceed){
            frameTimer = 0;
        }else {
            frameTimer -= framesDelay;
        }
    }
    /**
     * returns true if the object was not already been added
     */
    synchronized public boolean addObject(EngineObjectModel obj){
        if(managedObjects.contains(obj)){
            return false;
        }
        managedObjects.add(obj);
        return true;
    }
    /**
     * returns true if the object was managed by this manager
     */
    synchronized public boolean removeObject(EngineObjectModel obj){
        if(managedObjects.contains(obj)){
            managedObjects.remove(obj);
            return true;
        }
        return false;
    }
    synchronized public void removeAllObjects(){
        managedObjects.clear();

    }
    synchronized public Vector<EngineObjectModel> getManagedObjects(){
        return  managedObjects;

    }
    /**
     * returns true if the object is managed by this manager
     */
    synchronized public boolean manageObject(EngineObjectModel obj){
        if(managedObjects.contains(obj)){
            return true;
        }
        return false;
    }


    //function-methods
    private void updateTimer(){
        Date currDate = new Date();
        frameTimer += currDate.getTime() - lastExecution.getTime();
        lastExecution = currDate;
    }
    protected void doLogicUpdates(double deltaT){
        for (int i = 0; i < managedObjects.size(); i++){
            managedObjects.get(i).logicUpdate(deltaT);
        }
    }

    /**
     * will both check and resolve the collisions
     */
    protected void doCollisions(double deltaT){
        EngineObjectModel obj1,obj2;
        for(int i = 0; i < managedObjects.size()-1; i++){
            obj1 = managedObjects.get(i);
            if(obj1.canCollide()){
                for(int j = i+1; j < managedObjects.size(); j++){
                    //debug("  "+i +" "+j);
                    obj2 = managedObjects.get(j);
                    if(obj1.collidingWith(managedObjects.get(j))){
                        obj1.collision(obj2, deltaT);
                        obj2.collision(obj1, deltaT);
                    }
                }

            }
        }
    }
    protected void doPostCollisionUpdates(double deltaT){
        for (int i = 0; i < managedObjects.size(); i++){
            managedObjects.get(i).postCollisionUpdate(deltaT);
        }
    }
    protected void doGraphicUpdates(double deltaT){
        for (int i = 0; i < managedObjects.size(); i++){
            if(managedObjects.get(i).needGraphicUpdate){
                managedObjects.get(i).graphicUpdate(deltaT);
            }

        }
    }
    protected void doPostGraphicUpdates(double deltaT){
        for (int i = 0; i < managedObjects.size(); i++){
            managedObjects.get(i).postGraphicUpdate(deltaT);
        }
    }

}
