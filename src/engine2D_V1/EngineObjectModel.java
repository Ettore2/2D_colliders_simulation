package engine2D_V1;

import java.util.Vector;

public abstract class EngineObjectModel implements HaveTag, HaveTransform{
    public static final String DEFAULT_TAG = "gameObject";
    public static final int DIR_STOP = 0, DIR_UP = 1, DIR_DOWN = -DIR_UP, DIR_RIGHT = 2, DIR_LEFT = -DIR_RIGHT;
    public static final int DIR_UP_LEFT = 2,DIR_UP_RIGHT = 3, DIR_DOWN_LEFT = -3,DIR_DOWN_RIGHT = -2;

    public boolean needGraphicUpdate;


    public String tag;
    public Vector<Collider2D> colliders;


    //constructors
    public EngineObjectModel(String tag){
        setTag(tag);

        colliders = new Vector<>();
        needGraphicUpdate = true;

    }


    //states methods
    public boolean collidingWith(EngineObjectModel obj){
        for(int i = 0; i < colliders.size(); i++){
            for(int j = 0; j < obj.colliders.size(); j++){
                if(colliders.get(i).isColliding(obj.colliders.get(j))){
                    return true;
                }
            }
        }

        return false;
    }
    public void logicUpdate(double deltaT){}
    public void collision(EngineObjectModel obj, double deltaT) {}
    public void postCollisionUpdate(double deltaT){}
    public void graphicUpdate(double deltaT){}
    public void postGraphicUpdate(double deltaT){}


    //otherMethods
    public boolean canCollide(){
        boolean canCollide = false;

        for(int i = 0; i < colliders.size(); i++){
            if(colliders.get(i).isActive){
                return true;
            }
        }
        return false;
    }
    public double distance2D(EngineObjectModel obj){
        return this.getPosition().distance2D(obj.getPosition());
    }
    public double distance(EngineObjectModel obj){
        return this.getPosition().distance(obj.getPosition());
    }
    public void setActiveOnColliders(boolean isActive){
        for(int i = 0; i < colliders.size(); i++){
            colliders.get(i).isActive = isActive;
        }
    }


    //HaveTag overrides
    @Override
    public String getTag() {
        return tag;

    }
    @Override
    public void setTag(String tag) {
        if(tag == null){
            this.tag = DEFAULT_TAG;
        }else {
            this.tag = tag;
        }

    }
}
