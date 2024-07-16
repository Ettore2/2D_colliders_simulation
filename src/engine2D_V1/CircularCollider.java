package engine2D_V1;
import static engine2D_V1.EngineObjectModel.*;


public class CircularCollider extends Collider2D {
    public double radius;


    //constructors
    public CircularCollider(EngineObjectModel owner, Point3D center, String tag, double radius) {
        super(owner, center, tag);
        this.radius = radius;
    }
    public CircularCollider(EngineObjectModel owner, Point3D center, double radius) {
        this(owner, center, null, radius);

    }


    //other methods
    public void setDimensions(Point3D center, double radius){
        this.center = center;
        this.radius = radius;

    }

    @Override
    public double getArea() {
        return Math.PI*2*radius;

    }

    @Override
    public int getPositionRelativeToOther(Point3D pos) {
        Point3D myPos = getPositionAbs();

        if(myPos.x > pos.x){
            if(myPos.y > pos.y){
                if(INVERTED_Y){
                    return DIR_DOWN_RIGHT;
                }
                return DIR_UP_RIGHT;
            } else if(myPos.y < pos.y){
                if(INVERTED_Y){
                    return DIR_UP_RIGHT;
                }
                return DIR_DOWN_RIGHT;
            }else {
                return DIR_RIGHT;
            }
        } else if(myPos.x < pos.x){
            if(myPos.y > pos.y){
                if(INVERTED_Y){
                    return DIR_DOWN_LEFT;
                }
                return DIR_UP_LEFT;
            } else if(myPos.y < pos.y){
                if(INVERTED_Y){
                    return DIR_DOWN_LEFT;
                }
                return DIR_UP_LEFT;
            }else {
                return DIR_LEFT;
            }
        }else {
            if(myPos.y > pos.y){
                if(INVERTED_Y){
                    return DIR_DOWN;
                }
                return DIR_UP;
            } else if(myPos.y < pos.y){
                if(INVERTED_Y){
                    return DIR_UP;
                }
                return DIR_DOWN;
            }else {
                return DIR_STOP;
            }
        }

    }

    //Collider3D overrides
    public boolean isColliding(Collider2D col) {
        if(!this.isActive || !col.isActive){
            return false;
        }

        if(col.getClass() == BoxCollider.class){
            return col.isColliding(this);
        }

        if(col.getClass() == CircularCollider.class){
            return getPositionAbs().distance2D(col.getPositionAbs()) <= this.radius + ((CircularCollider)col).radius;
        }

        return false;
    }
}
