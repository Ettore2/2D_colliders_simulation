package engine2D_V1;


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
