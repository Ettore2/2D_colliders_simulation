package engine2D;


import static engine2D.EngineObjectModel.*;

public class BoxCollider extends Collider2D {
    public static final int FUNCTION_UP = 0,FUNCTION_RIGHT = 1,FUNCTION_DOWN = 2,FUNCTION_LEFT = 3;
    public static final int POINT_UP_LEFT = 0,POINT_UP_RIGHT = 1,POINT_DOWN_RIGHT = 2,POINT_DOWN_LEFT = 3,POINT_CENTER = 4;
    public static final int DIAGONAL_UP_LEFT_TO_DOWN_RIGHT = 0,DIAGONAL_DOWN_LEFT_TO_UP_RIGHT = 1;


    public Vector3D size;


    //constructors
    public BoxCollider(EngineObjectModel owner, Point3D center, String tag, Vector3D size) {
        super(owner, center, tag);
        this.size = size;
    }
    public BoxCollider(EngineObjectModel owner, Point3D center, Vector3D size) {
        this(owner, center, null, size);

    }
    public BoxCollider(EngineObjectModel owner, Point3D upLeftP, Point3D downRightP) {
        this(owner, new Point3D(upLeftP.x + (downRightP.x-upLeftP.x)/2,upLeftP.y + (downRightP.y-upLeftP.y)/2), null, new Vector3D(Math.abs(downRightP.x-upLeftP.x),Math.abs(downRightP.y-upLeftP.y),0));
    }


    //other methods
    public double getHalfDiagonal(){
        return Math.sqrt(size.x*size.x + size.y*size.y)/2;

    }
    public double getLeftMostCoordinate(){
        Function[] functions = getFunctions();

        return Math.min(functions[FUNCTION_LEFT].getIntersection(functions[FUNCTION_UP]).x,functions[FUNCTION_LEFT].getIntersection(functions[FUNCTION_DOWN]).x);
    }
    public double getRightMostCoordinate(){
        Function[] functions = getFunctions();

        return Math.max(functions[FUNCTION_RIGHT].getIntersection(functions[FUNCTION_UP]).x,functions[FUNCTION_LEFT].getIntersection(functions[FUNCTION_DOWN]).x);

    }
    public double getUpMostCoordinate(){
        Function[] functions = getFunctions();

        if(!INVERTED_Y){
            return Math.max(functions[FUNCTION_UP].getIntersection(functions[FUNCTION_LEFT]).y,functions[FUNCTION_UP].getIntersection(functions[FUNCTION_RIGHT]).y);
        }else {
            return Math.min(functions[FUNCTION_UP].getIntersection(functions[FUNCTION_LEFT]).y,functions[FUNCTION_UP].getIntersection(functions[FUNCTION_RIGHT]).y);
        }
    }
    public double getDownMostCoordinate(){
        Function[] functions = getFunctions();

        if(!INVERTED_Y){
            return Math.min(functions[FUNCTION_DOWN].getIntersection(functions[FUNCTION_LEFT]).y,functions[FUNCTION_DOWN].getIntersection(functions[FUNCTION_RIGHT]).y);
        }else {
            return Math.max(functions[FUNCTION_DOWN].getIntersection(functions[FUNCTION_LEFT]).y,functions[FUNCTION_DOWN].getIntersection(functions[FUNCTION_RIGHT]).y);
        }
    }
    public Point3D getMostDownLeftPoint(){
        return getVertices()[POINT_DOWN_LEFT];

    }
    public Point3D getMostUpLeftPoint(){
        return getVertices()[POINT_UP_LEFT];


    }
    public Point3D getMostDownRightPoint(){
        return getVertices()[POINT_DOWN_RIGHT];

    }
    public Point3D getMostUpRightPoint(){
        return getVertices()[POINT_UP_RIGHT];

    }
    public void setDimensions(Point3D center, Vector3D size){
        this.center = center;
        this.size = size;

    }
    public void setSize(Vector3D size){
        this.size = size;

    }
    public Function[] getFunctions(){
        return getFunctions(getVertices());

    }
    public static Function[] getFunctions(Point3D[] vertices){
        Function[] result = new Function[4];
        result[FUNCTION_DOWN] = new Function(vertices[POINT_DOWN_LEFT], vertices[POINT_DOWN_RIGHT]);
        result[FUNCTION_UP] = new Function(vertices[POINT_UP_LEFT], vertices[POINT_UP_RIGHT]);
        result[FUNCTION_LEFT] = new Function(vertices[POINT_DOWN_LEFT], vertices[POINT_UP_LEFT]);
        result[FUNCTION_RIGHT] = new Function(vertices[POINT_DOWN_RIGHT], vertices[POINT_UP_RIGHT]);

        return result;
    }
    public Point3D[] getVertices(Function[] diagonals){
        Point3D pos = getPositionAbs();
        double distance = Math.sqrt(size.x* size.x+size.y* size.y)/2;
        Point3D[] result = new Point3D[4];
        double rotation;
        //System.out.println("diagonals: "+diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m + "   " + diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m);
        //System.out.println("rotations: "+Math.toDegrees(Math.atan2(diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m,1))+"    "+Math.toDegrees(Math.atan2(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m,1)));


        if(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m != null){
            rotation = Math.atan2(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m,1);
            result[POINT_UP_LEFT] = new Point3D(pos.x-distance*Math.cos(rotation), pos.y-distance*Math.sin(rotation));
            result[POINT_DOWN_RIGHT] = new Point3D(pos.x+distance*Math.cos(rotation), pos.y+distance*Math.sin(rotation));
        }else {
            if(INVERTED_Y){
                result[POINT_UP_LEFT] = new Point3D(pos.x, pos.y-distance);
                result[POINT_DOWN_RIGHT] = new Point3D(pos.x, pos.y+distance);
            }else {
                result[POINT_UP_LEFT] = new Point3D(pos.x, pos.y+distance);
                result[POINT_DOWN_RIGHT] = new Point3D(pos.x, pos.y-distance);
            }
        }

        if(diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m != null){
            rotation = Math.atan2(diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m,1);
            result[POINT_UP_RIGHT] = new Point3D(pos.x+distance*Math.cos(rotation), pos.y+distance*Math.sin(rotation));
            result[POINT_DOWN_LEFT] = new Point3D(pos.x-distance*Math.cos(rotation), pos.y-distance*Math.sin(rotation));
        }else {
            if(INVERTED_Y){
                result[POINT_UP_RIGHT] = new Point3D(pos.x, pos.y-distance);
                result[POINT_DOWN_LEFT] = new Point3D(pos.x, pos.y+distance);
            }else {
                result[POINT_UP_RIGHT] = new Point3D(pos.x, pos.y+distance);
                result[POINT_DOWN_LEFT] = new Point3D(pos.x, pos.y-distance);
            }
        }

        //for(int i = 0 ; i < result.length; i++){
        //    System.out.println(result[i].toString());
        //}
        return result;

    }
    public Point3D[] getVertices(){
        return getVertices(getDiagonals());

    }
    public Point3D[] getPoints(Function[] diagonals){
        Point3D[] vertices = getVertices(diagonals);
        Point3D[] points = new Point3D[vertices.length+1];
        for(int i = 0; i < vertices.length; i++){
            points[i] = vertices[i];
        }
        points[points.length-1] = getPositionAbs();
        return points;

    }
    public Point3D[] getPoints(){
        return getPoints(getDiagonals());

    }
    public Function[] getDiagonals(){
        Point3D pos = getPositionAbs();
        double rotation = getRotationAbs().z;
        Function fTmp;
        Function[] diagonals = new Function[2];
        diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT] = new Function(pos,rotation,pos,pos.sum(new Point3D(size.x, -size.y)));
        diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT] = new Function(pos,rotation,pos,pos.sum(new Point3D(size.x, size.y)));

        if(diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m == null){
            fTmp = diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT];
            diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT] = diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT];
            diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT] = fTmp;
        }else if (diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m != null){
            double result = diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m / diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m;
            if(result >= 0){
                fTmp = diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT];
                diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT] = diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT];
                diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT] = fTmp;
            }else {
                if(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m > diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m){
                    fTmp = diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT];
                    diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT] = diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT];
                    diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT] = fTmp;
                }
            }
        }

        if(INVERTED_Y){
            fTmp = diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT];
            diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT] = diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT];
            diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT] = fTmp;

        }



        return diagonals;
    }

    //function-methods
    public Point3D getSegmentsCollision(Function f, double fMin, double fMax, Function g, double gMin, double gMax){
        //System.out.println(f.toString()+"|"+fMin+"|"+fMax);
        //System.out.println(g.toString()+"|"+gMin+"|"+gMax);
        Point3D p = f.getIntersection(g);
        if(p != null){
            //System.out.println(p.toString());
            if(f.m == null){
                if(g.m == null){
                    return p;
                }else {
                    return p.y <= fMax && p.y >= fMin && p.x <= gMax && p.x >= gMin ? p : null;
                }
            }else {
                if(g.m == null){
                    return p.y <= gMax && p.y >= gMin && p.x <= fMax && p.x >= fMin ? p : null;
                }else {
                    return (p.x <= Math.min(fMax,gMax) && p.x >= Math.max(fMin,gMin)) ? p : null;
                }
            }
        }
        return null;
    }
    public Point3D getSegmentsCollision(Function f, double fMin, double fMax, Point3D b1, Point3D b2){
        Function g = new Function(b1, b2);
        if(g.m == null){
            b1 = new Point3D(b1.y,0);
            b2 = new Point3D(b2.y,0);
        }
        return getSegmentsCollision(f, fMin, fMax,g,Math.min(b1.x,b2.x),Math.max(b1.x,b2.x));
    }
    public Point3D getSegmentsCollision(Point3D a1, Point3D a2, Point3D b1, Point3D b2){
        Function f = new Function(a1, a2), g = new Function(b1, b2);
        if(f.m == null){
            a1 = new Point3D(a1.y,0);
            a2 = new Point3D(a2.y,0);
        }
        if(g.m == null){
            b1 = new Point3D(b1.y,0);
            b2 = new Point3D(b2.y,0);
        }

        return getSegmentsCollision(
                f,Math.min(a1.x,a2.x),Math.max(a1.x,a2.x),
                g,Math.min(b1.x,b2.x),Math.max(b1.x,b2.x)
        );
    }


    @Override
    public double getArea() {
        return size.x * size.y;

    }
    @Override
    public int getPositionRelativeToOther(Point3D pos) {
        Function[] f = getFunctions();
        boolean[] results = new boolean[4];

        for(int j = 0; j < f.length; j++){
            results[j] = f[j].isPointUnder(pos);

        }

        if(results[FUNCTION_DOWN] != results[FUNCTION_UP] || f[FUNCTION_DOWN].isPointOwned(pos) || f[FUNCTION_UP].isPointOwned(pos)){
            if(results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || f[FUNCTION_RIGHT].isPointOwned(pos) || f[FUNCTION_LEFT].isPointOwned(pos)){
                return DIR_STOP;
            }else if(results[FUNCTION_RIGHT]){
                return DIR_RIGHT;
            }else {
                return DIR_LEFT;
            }
        }else if(results[FUNCTION_UP]){
            if(results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || f[FUNCTION_RIGHT].isPointOwned(pos) || f[FUNCTION_LEFT].isPointOwned(pos)){
                if(INVERTED_Y){
                    return DIR_UP;
                }
                return DIR_DOWN;
            }else if(results[FUNCTION_RIGHT]){
                if(INVERTED_Y){
                    return DIR_UP_RIGHT;
                }
                return DIR_DOWN_RIGHT;
            }else {
                if(INVERTED_Y){
                    return DIR_UP_LEFT;
                }
                return DIR_DOWN_LEFT;
            }
        }else {
            if(results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || f[FUNCTION_RIGHT].isPointOwned(pos) || f[FUNCTION_LEFT].isPointOwned(pos)){
                if(INVERTED_Y){
                    return DIR_DOWN;
                }
                return DIR_UP;
            }else if(results[FUNCTION_RIGHT]){
                if(INVERTED_Y){
                    return DIR_DOWN_RIGHT;
                }
                return DIR_UP_RIGHT;
            }else {
                if(INVERTED_Y){
                    return DIR_DOWN_RIGHT;
                }
                return DIR_UP_LEFT;
            }
        }

    }


    //Collider3D overrides
    public boolean isColliding(Collider2D col) {

        if(!this.isActive || !col.isActive){
            return false;
        }

        if(col.getClass() == BoxCollider.class){
            BoxCollider rectCol = (BoxCollider)col;

            //false false
            if(getPositionAbs().distance2D(col.getPositionAbs()) > Math.sqrt((size.x+rectCol.size.x)*(size.x+rectCol.size.x)+(size.y+rectCol.size.y)*(size.y+rectCol.size.y))/2){
                //System.out.println(getPositionAbs().distance2D(col.getPositionAbs()) +"  "+ ((size.y+rectCol.size.y)/2/Math.sin(Math.atan2((size.x+rectCol.size.x)/2,(size.y+rectCol.size.y)/2))));
                //System.out.println("fast false");
                return false;
            }
            Function[] myDiagonals = getDiagonals(), hisDiagonals = rectCol.getDiagonals();
            Point3D[] myPoints = this.getPoints(myDiagonals), hisPoints = rectCol.getPoints(hisDiagonals);

            for(int i = 0; i < 2; i++){
                Point3D a1 = myPoints[i],a2 = myPoints[i+2],b1 = hisPoints[i],b2 = hisPoints[i+2];
                if(myDiagonals[i].m == null){
                    a1 = new Point3D(a1.y,0);
                    a2 = new Point3D(a2.y,0);
                }
                if(hisDiagonals[i].m == null){
                    b1 = new Point3D(b1.y,0);
                    b2 = new Point3D(b2.y,0);
                }

                for(int j = 0; j < 4; j++){

                    if(getSegmentsCollision(myDiagonals[i],Math.min(a1.x,a2.x),Math.max(a1.x,a2.x),hisPoints[j],hisPoints[(j+1)%4]) != null
                            || getSegmentsCollision(hisDiagonals[i],Math.min(b1.x,b2.x),Math.max(b1.x,b2.x),myPoints[j],myPoints[(j+1)%4]) != null) {
                        return true;
                    }
                }
            }//check both collisions diagonals X sides



            Function[] myFunctions = getFunctions(myPoints), hisFunctions = getFunctions(hisPoints);
            {
                Point3D currPoint = hisPoints[POINT_CENTER];
                boolean[] results = new boolean[4];

                for(int i = 0; i < myFunctions.length; i++){
                    results[i] = myFunctions[i].isPointUnder(currPoint);

                }

                if((results[FUNCTION_DOWN] != results[FUNCTION_UP] || myFunctions[FUNCTION_DOWN].isPointOwned(currPoint) || myFunctions[FUNCTION_UP].isPointOwned(currPoint))
                        && (results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || myFunctions[FUNCTION_RIGHT].isPointOwned(currPoint) || myFunctions[FUNCTION_LEFT].isPointOwned(currPoint))){
                    //System.out.println("true2");
                    return true;
                }
            }//check if my center is inside
            {
                Point3D currPoint = myPoints[POINT_CENTER];
                boolean[] results = new boolean[4];

                for(int i = 0; i < hisFunctions.length; i++){
                    results[i] = hisFunctions[i].isPointUnder(currPoint);

                }

                if((results[FUNCTION_DOWN] != results[FUNCTION_UP] || hisFunctions[FUNCTION_DOWN].isPointOwned(currPoint) || hisFunctions[FUNCTION_UP].isPointOwned(currPoint))
                        && (results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || hisFunctions[FUNCTION_RIGHT].isPointOwned(currPoint) || hisFunctions[FUNCTION_LEFT].isPointOwned(currPoint))){
                    //System.out.println("true1");
                    return true;
                }
            }//check if his center is inside



            //System.out.println("implicit false");
            return false;

        }

        else if(col.getClass() == CircularCollider.class){
            CircularCollider circleCol = (CircularCollider)col;
            //System.out.println("rotation: "+rotation.z);

            if(getPositionAbs().distance2D(col.getPositionAbs()) > (circleCol.radius)+Math.sqrt((size.x*size.x+size.y*size.y))/2){
                //System.out.println("fast false");
                return false;
            }


            boolean[] results = new boolean[4];
            Point3D[] vertices = this.getVertices();
            Point3D circleCenter = circleCol.getPositionAbs();
            Function[] functions = this.getFunctions();

            //debug
            //Function[] diagonals = getDiagonals();
            //System.out.println("posizione:"+circleCenter);
            //System.out.println(results[0]+"    "+results[1]);
            //System.out.println("inclinazione diagonali: "+diagonals[0].m +"   "+diagonals[1].m);
            //for (int i = 0 ; i < vertices.length; i++){
                //System.out.println(vertices[i].toString());
            //}
            //for (int i = 0 ; i < functions.length; i++){
                //System.out.println(functions[i].toString());
            //}


            for(int i = 0; i < functions.length; i++){
                results[i] = functions[i].isPointOver(circleCenter);
                //System.out.println(functions[i].toString());
            }


            if(functions[FUNCTION_RIGHT].m != null &&functions[FUNCTION_RIGHT].m > 0){
                results[FUNCTION_LEFT]  = !results[FUNCTION_LEFT];
                results[FUNCTION_RIGHT]  = !results[FUNCTION_RIGHT];
            }
            if(INVERTED_Y){
                results[FUNCTION_UP]  = !results[FUNCTION_UP];
                results[FUNCTION_DOWN]  = !results[FUNCTION_DOWN];
            }


            if(results[FUNCTION_LEFT] != results[FUNCTION_RIGHT]){//centred L_R
                if(results[FUNCTION_UP] != results[FUNCTION_DOWN]){//centred U_D
                    //System.out.println("center contained true");
                    return true;
                }else {//non centred U_D
                    if(results[FUNCTION_UP]){//up
                        //System.out.println(1);
                        return functions[FUNCTION_UP].distance2D(circleCenter) <= circleCol.radius;
                    }else {//down
                        //System.out.println(2);
                        return functions[FUNCTION_DOWN].distance2D(circleCenter) <= circleCol.radius;
                    }
                }
            }else {//non centred L_R
                if(results[FUNCTION_RIGHT]){//to the right
                    if(results[FUNCTION_UP] != results[FUNCTION_DOWN]){//centred U_D
                        //System.out.println(3);
                        return functions[FUNCTION_RIGHT].distance2D(circleCenter) <= circleCol.radius;
                    }else {//non centred U_D
                        if(results[FUNCTION_UP]){//up
                            //.out.println(4);
                            return vertices[POINT_UP_RIGHT].distance2D(circleCenter) <= circleCol.radius;
                        }else {//down
                            //System.out.println(5);
                            return vertices[POINT_DOWN_RIGHT].distance2D(circleCenter) <= circleCol.radius;
                        }
                    }
                }else {//to the left
                    if(results[FUNCTION_UP] != results[FUNCTION_DOWN]){//centred U_D
                        //System.out.println(6);
                        return functions[FUNCTION_LEFT].distance2D(circleCenter) <= circleCol.radius;
                    }else {//non centred U_D
                        if(results[FUNCTION_UP]){//up
                            //System.out.println(7);
                            return vertices[POINT_UP_LEFT].distance2D(circleCenter) <= circleCol.radius;
                        }else {//down
                            //System.out.println(9);
                            return vertices[POINT_DOWN_LEFT].distance2D(circleCenter) <= circleCol.radius;
                        }
                    }
                }
            }
        }

        return false;
    }
}












