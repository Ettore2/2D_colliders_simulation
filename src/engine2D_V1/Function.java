package engine2D_V1;

import java.util.Objects;

public class Function {
    public static final double TOLLERANCE = 0.001d;
    Double m;
    double q;


    //constructors
    public Function(Point3D rotCenter, double degrees, Point3D p1, Point3D p2){
        if(Math.abs(p1.x - p2.x) < TOLLERANCE){
            this.m = null;
            this.q = p1.x;
        }else if(Math.abs(p1.y - p2.y) < TOLLERANCE){
            this.m = 0d;
            this.q = p1.y;
        }else {
            this.m = (p2.y-p1.y)/(p2.x-p1.x);
            this.q = p1.y-m*p1.x;
        }

        rotate(rotCenter,degrees);

    }
    public Function(Point3D p1, Point3D p2){
        this(null,0,p1,p2);

    }


    //other methods
    public void rotate(Point3D rotCenter, double degrees){
        if(degrees != 0){
            if(m != null){
                setRotation(rotCenter, (double) (degrees+Math.toDegrees(Math.atan2(m,1))));
            }else {
                setRotation(rotCenter, degrees + 90);
            }


        }
    }
    public void setRotation(Point3D rotCenter, double degrees){
        degrees = degrees%360;
        if(degrees < 0){
            degrees = 360 + degrees;
        }

        if(rotCenter == null){
            rotCenter = new Point3D(0,0,0);
        }

        double distance = distance(rotCenter);
        Point3D newP = new Point3D((double) (distance*Math.cos(Math.toRadians(degrees))), (double) (distance*Math.sin(Math.toRadians(degrees))),0);

        if(Math.abs(degrees%180 - 90) < TOLLERANCE){
            m = null;
            q = newP.x;
        }else if(Math.abs(degrees%180) < TOLLERANCE){
            m = 0d;
            q = newP.y;
        }else {
            m = (double)Math.tan(Math.toRadians(degrees));
            q = newP.y - m*newP.x;
        }
    }
    public void setM(double m) {
        this.m = m;

    }
    public void setQ(double q) {
        this.q = q;

    }
    public double getImage(double x){
        return getImagePoint(x).y;

    }
    public Point3D getImagePoint(double x){
        if(m == null){
            return new Point3D(q,x);
        }else {
            return new Point3D(x,m*x+q);
        }

    }
    public double getCounterImage(double y){
        return getCounterImagePoint(y).x;

    }
    public Point3D getCounterImagePoint(double y){
        if(m == null){
            return new Point3D(q,y);
        }else if(m == 0){
            return new Point3D(y,q);
        }else {
            return new Point3D((y-q)/m,y);
        }

    }
    public Point3D getIntersection(Function f){
        if(Objects.equals(f.m, this.m)){
            if(this.q == f.q){
                return new Point3D(0,q);
            }
            return null;
        }

        if(this.m == null){
            return new Point3D(this.q,f.getImage(this.q));
        }else{
            if(f.m == null){
                return new Point3D(f.q,this.getImage(f.q));
            }else{
                double x = (f.q-this.q)/(this.m - f.m);
                return new Point3D(x,getImage(x));
            }
        }

    }
    public boolean isPointOwned(Point3D p){
        if(m == null){
            return q == p.x;
        }
        return getImage(p.x) == p.y;

    }
    public boolean isPointOver(Point3D p){
        if(m == null){
            return q < p.x;
        }
        return p.y > getImage(p.x);

    }
    public boolean isPointUnder(Point3D p){
        if(m == null){
            return q > p.x;
        }
        return p.y < getImage(p.x);

    }
    public Point3D distancePoint(Point3D p){
        if(m == null){
            return new Point3D(this.q,p.y);
        }else {
            if(m == 0){
                return new Point3D(p.x,this.q);
            }
            double m2 = -1f/m;
            double q2 = p.y -p.x*m2;
            double x = (q - q2)/(m2-m);

            return new Point3D(x, getImage(x),0);
        }
    }
    public double distance(Point3D p){
        return distancePoint(p).distance2D(p);

    }

    @Override
    public String toString() {
        return "m: "+m+" q: "+q;
    }
}
