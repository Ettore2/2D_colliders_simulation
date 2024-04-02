package engine2D_V1;

import java.util.Date;
import java.util.Vector;

public class EngineManager {
    private Vector<EngineObjectModel> managedObjects;
    private Vector<EngineEntityModel> managedEntities;
    private Vector<EngineObjectModel> toRemoveObjects, toAddObjects;
    private Vector<EngineEntityModel> toRemoveEntities, toAddEntities;
    private boolean toRemoveAllObjects, toRemoveAllEntities;
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
        this.managedEntities = new Vector<>();
        this.toRemoveEntities = new Vector<>();
        this.toAddObjects = new Vector<>();
        this.toRemoveObjects = new Vector<>();
        this.toAddEntities = new Vector<>();
        toRemoveAllObjects = false;
        toRemoveAllEntities = false;
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
        Date start = new Date();
        //debug("do updates");
        doLogicUpdates(framesDelay);
        //debug("doneLogicUpdates");
        doCollisions(framesDelay);
        //debug("doneCollisions");
        doFinalUpdates(framesDelay);
        Date end = new Date();

        updateManagedThings();
        //debug("doCle framesDelay: "+framesDelay+" doCle duration: "+(end.getTime() - start.getTime()));
        //debug("managed objects: "+managedObjects.size()+" managed entities: "+managedEntities.size());
        //debug("doCycle end##########################");
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
        if(!managedObjects.contains(obj) && !toAddObjects.contains(obj)){
            toAddObjects.add(obj);
            return true;
        }
        return false;

    }
    synchronized public boolean addObjectInstantly(EngineObjectModel obj){
        if(managedObjects.contains(obj)){
            return false;
        }
        managedObjects.add(obj);
        return true;
    }
    synchronized public boolean addEntity(EngineEntityModel ent){
        if(!managedEntities.contains(ent) && !toAddEntities.contains(ent)){
            toAddEntities.add(ent);
            return true;
        }
        return false;
    }
    synchronized public boolean addEntityInstantly(EngineEntityModel ent){
        if(managedEntities.contains(ent)){
            return false;
        }
        managedEntities.add(ent);
        return true;
    }
    /**
     * returns true if the object was managed by this manager
     */
    synchronized public boolean removeObject(EngineObjectModel obj){
        if(managedObjects.contains(obj) && !toRemoveObjects.contains(obj)){
            toRemoveObjects.add(obj);
            return true;
        }
        return false;
    }
    synchronized public boolean removeEntity(EngineEntityModel ent){
        if(managedEntities.contains(ent) && !toRemoveEntities.contains(ent)){
            toRemoveEntities.add(ent);
            return true;
        }
        return false;
    }
    synchronized public void removeAll(){
        toRemoveAllObjects = true;
        toRemoveAllEntities = true;

    }
    synchronized public void removeAllObjects(){
        toRemoveAllObjects = true;

    }
    synchronized public void removeAllEntities(){
        toRemoveAllEntities = true;

    }
    synchronized public Vector<EngineObjectModel> getManagedObjects(){
        return  managedObjects;

    }
    synchronized public Vector<EngineEntityModel> getManagedEntities(){
        return managedEntities;

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
    synchronized public boolean manageEntity(EngineEntityModel ent){
        if(managedEntities.contains(ent)){
            return true;
        }
        return false;
    }
    synchronized  public void updateManagedThings(){
        //add and remove objects
        if(toRemoveAllObjects){
            toRemoveObjects.clear();
            managedObjects.clear();
            toRemoveAllObjects = false;
        }else {
            while (!toRemoveObjects.isEmpty()){
                if(managedObjects.contains(toRemoveObjects.get(0))){
                    managedObjects.remove(toRemoveObjects.remove(0));
                }else {
                    toRemoveObjects.remove(0);
                }
            }
        }
        while (!toAddObjects.isEmpty()){
            if(!managedObjects.contains(toAddObjects.get(0))){
                managedObjects.add(toAddObjects.remove(0));
            }else {
                toAddObjects.remove(0);
            }
        }

        //add and remove entities
        if(toRemoveAllEntities){
            toRemoveEntities.clear();
            managedEntities.clear();
            toRemoveAllEntities = false;
        }else {
            while (!toRemoveEntities.isEmpty()) {
                if (managedEntities.contains(toRemoveEntities.get(0))) {
                    managedEntities.remove(toRemoveEntities.remove(0));
                } else {
                    toRemoveEntities.remove(0);
                }
            }
        }
        while (!toAddEntities.isEmpty()){
            if(!managedEntities.contains(toAddEntities.get(0))){
                managedEntities.add(toAddEntities.remove(0));
            }else {
                toAddEntities.remove(0);
            }
        }
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
        for (int i = 0; i < managedEntities.size(); i++){
            managedEntities.get(i).logicUpdate(deltaT);
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
    private void doFinalUpdates(double deltaT) {
        for (int i = 0; i < managedObjects.size(); i++){
            managedObjects.get(i).postCollisionUpdate(deltaT);
            managedObjects.get(i).graphicUpdate(deltaT);
            managedObjects.get(i).postGraphicUpdate(deltaT);
        }
    }

}
