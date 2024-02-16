package engine2D_V1;

import java.util.Vector;

public abstract class EngineObjectModel implements HaveTag{
    public static final String DEFAULT_TAG = "gameObject";
    public static final int DIR_STOP = 0, DIR_UP = 1, DIR_DOWN = -DIR_UP, DIR_RIGHT = 2, DIR_LEFT = -DIR_RIGHT;

    public boolean needGraphicUpdate;


    public String tag;
    public Vector<Collider2D> colliders;


    //constructors
    public EngineObjectModel(String tag){
        this.tag = tag;
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


    //position and rotation methods
    public abstract void setPosition(Point3D p);
    public abstract void translate(Vector3D v);
    public abstract void translate(double x, double y, double z);
    public abstract Point3D getPosition();
    public abstract void setRotation(Rotation3D r);
    public abstract void rotate(Vector3D v);
    public abstract void rotate(double x, double y, double z);
    public abstract Rotation3D getRotation();


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


    //HaveTag overrides
    @Override
    public String getTag() {
        return tag;

    }
    @Override
    public void setTag(String tag) {
        this.tag = tag;

    }
}
