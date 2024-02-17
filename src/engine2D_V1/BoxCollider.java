package engine2D_V1;

public class BoxCollider extends Collider2D {
    public static final int FUNCTION_UP = 0,FUNCTION_RIGHT = 1,FUNCTION_DOWN = 2,FUNCTION_LEFT = 3;
    public static final int POINT_UP_RIGHT = 0,POINT_DOWN_RIGHT = 1,POINT_DOWN_LEFT = 2,POINT_UP_LEFT = 3,POINT_CENTER = 5;
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


    //other methods
    public double getHalfDiagonal(){
        return Math.sqrt(size.x*size.x + size.y*size.y)/2;

    }
    public double getLeftMostCoordinate(){
        Function[] functions = getFunctions();
        Double winner = null;
        double candidate;
        for(int i = 0; i < functions.length; i++){
            candidate = functions[i].getIntersection(functions[(i+1)%functions.length]).x;
            if(winner == null || winner > candidate){
                winner = candidate;
            }
        }

        return winner;
    }
    public double getRightMostCoordinate(){
        Function[] functions = getFunctions();
        Double winner = null;
        double candidate;
        for(int i = 0; i < functions.length; i++){
            candidate = functions[i].getIntersection(functions[(i+1)%functions.length]).x;
            if(winner == null || winner < candidate){
                winner = candidate;
            }
        }

        return winner;
    }
    public double getUpMostCoordinate(){
        Function[] functions = getFunctions();
        Double winner = null;
        double candidate;
        for(int i = 0; i < functions.length; i++){
            candidate = functions[i].getIntersection(functions[(i+1)%functions.length]).y;
            if(winner == null){
                winner = candidate;
            }else {
                if(!INVERTED_Y && winner < candidate){
                    winner = candidate;
                }else if(winner > candidate){
                    winner = candidate;
                }
            }
        }

        return winner;
    }
    public double getDownMostCoordinate(){
        Function[] functions = getFunctions();
        Double winner = null;
        double candidate;
        for(int i = 0; i < functions.length; i++){
            candidate = functions[i].getIntersection(functions[(i+1)%functions.length]).y;
            if(winner == null){
                winner = candidate;
            }else {
                if(!INVERTED_Y && winner > candidate){
                    winner = candidate;
                }else if(winner < candidate){
                    winner = candidate;
                }
            }
        }

        return winner;
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
        Function[] result = new Function[4];
        result[FUNCTION_DOWN] = new Function(getMostDownLeftPoint(), getMostDownRightPoint());
        result[FUNCTION_UP] = new Function(getMostUpLeftPoint(), getMostUpRightPoint());
        result[FUNCTION_LEFT] = new Function(getMostDownLeftPoint(), getMostUpLeftPoint());
        result[FUNCTION_RIGHT] = new Function(getMostDownRightPoint(), getMostUpRightPoint());

        return result;
    }
    public Point3D[] getVertices(){
        Function[] diagonals = getDiagonals();
        Point3D pos = getPositionAbs();
        double distance = Math.sqrt(size.x* size.x+size.y* size.y)/2;
        Point3D[] result = new Point3D[4];
        double rotation;
        //System.out.println("diagonals: "+diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m + "   " + diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m);
        //System.out.println("rotations: "+Math.toDegrees(Math.atan2(diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m,1))+"    "+Math.toDegrees(Math.atan2(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m,1)));

        if(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m != null){
            rotation = Math.atan2(diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT].m,1);
            result[POINT_DOWN_RIGHT] = new Point3D(pos.x+distance*Math.cos(rotation), pos.y+distance*Math.sin(rotation));
            result[POINT_UP_LEFT] = new Point3D(pos.x-distance*Math.cos(rotation), pos.y-distance*Math.sin(rotation));
        }else {
            if(INVERTED_Y){
                result[POINT_UP_LEFT] = new Point3D(pos.x, pos.y-distance);
                result[POINT_DOWN_RIGHT] = new Point3D(pos.x, pos.y+distance);
            }else {
                result[POINT_UP_LEFT] = new Point3D(pos.x, pos.y+distance);
                result[POINT_DOWN_RIGHT] = new Point3D(pos.x, pos.y-distance);
            }
        }
        rotation = Math.atan2(diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT].m,1);
        result[POINT_UP_RIGHT] = new Point3D(pos.x+distance*Math.cos(rotation), pos.y+distance*Math.sin(rotation));
        result[POINT_DOWN_LEFT] = new Point3D(pos.x-distance*Math.cos(rotation), pos.y-distance*Math.sin(rotation));

        //for(int i = 0 ; i < result.length; i++){
        //    System.out.println(result[i].toString());
        //}
        return result;

    }
    public Point3D[] getPoints(){
        Point3D[] vertices = getVertices();
        Point3D[] points = new Point3D[vertices.length+1];
        for(int i = 0; i < vertices.length; i++){
            points[i] = vertices[i];
        }
        points[points.length-1] = getPositionAbs();
        return points;

    }
    public Function[] getDiagonals(){
        Point3D pos = getPositionAbs();
        double rotation = getRotationAbs().z;
        Function fTmp;
        Function[] diagonals = new Function[2];
        diagonals[DIAGONAL_UP_LEFT_TO_DOWN_RIGHT] = new Function(null,rotation,pos,pos.sum(new Point3D(size.x, -size.y)));
        diagonals[DIAGONAL_DOWN_LEFT_TO_UP_RIGHT] = new Function(null,rotation,pos,pos.sum(new Point3D(size.x, size.y)));

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


    @Override
    public double getArea() {
        return size.x * size.y;

    }


    //Collider3D overrides
    public boolean isColliding(Collider2D col) {

        if(!this.isActive || !col.isActive){
            return false;
        }

        if(col.getClass() == BoxCollider.class){
            BoxCollider rectCol = (BoxCollider)col;

            if(getPositionAbs().distance2D(col.getPositionAbs()) > (size.y+rectCol.size.y)/2/Math.sin(Math.atan2((size.x+rectCol.size.x)/2,(size.y+rectCol.size.y)/2))){
                //System.out.println(getPositionAbs().distance2D(col.getPositionAbs()) +"  "+ ((size.y+rectCol.size.y)/2/Math.sin(Math.atan2((size.x+rectCol.size.x)/2,(size.y+rectCol.size.y)/2))));
                //System.out.println("fast false");
                return false;
            }

            Function[] functions;
            Point3D[] points;

            functions = this.getFunctions();
            points = rectCol.getPoints();
            for(int i = 0; i < points.length; i++){
                Point3D currPoint = points[i];
                boolean[] results = new boolean[4];

                for(int j = 0; j < functions.length; j++){
                    results[j] = functions[j].isPointUnder(currPoint);

                }

                if((results[FUNCTION_DOWN] != results[FUNCTION_UP] || functions[FUNCTION_DOWN].isPointOwned(currPoint) || functions[FUNCTION_UP].isPointOwned(currPoint))
                        && (results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || functions[FUNCTION_RIGHT].isPointOwned(currPoint) || functions[FUNCTION_LEFT].isPointOwned(currPoint))){
                    //System.out.println("true1");
                    return true;
                }
            }

            functions = rectCol.getFunctions();
            points = this.getPoints();
            for(int i = 0; i < points.length; i++){
                Point3D currPoint = points[i];
                boolean[] results = new boolean[4];

                for(int j = 0; j < functions.length; j++){
                    results[j] = functions[j].isPointUnder(currPoint);

                }

                if((results[FUNCTION_DOWN] != results[FUNCTION_UP] || functions[FUNCTION_DOWN].isPointOwned(currPoint) || functions[FUNCTION_UP].isPointOwned(currPoint))
                        && (results[FUNCTION_LEFT] != results[FUNCTION_RIGHT] || functions[FUNCTION_RIGHT].isPointOwned(currPoint) || functions[FUNCTION_LEFT].isPointOwned(currPoint))){
                    //System.out.println("true2");
                    return true;
                }
            }


            //System.out.println("implicit false");
            return false;
        }

        if(col.getClass() == CircularCollider.class){
            CircularCollider circleCol = (CircularCollider)col;
            //System.out.println("rotation: "+rotation.z);

            if(getPositionAbs().distance2D(col.getPositionAbs()) > (circleCol.radius+size.y/2)/Math.sin(Math.atan2(size.y,size.x))){
                //7System.out.println("fast false");
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
                        return functions[FUNCTION_UP].distance(circleCenter) <= circleCol.radius;
                    }else {//down
                        //System.out.println(2);
                        return functions[FUNCTION_DOWN].distance(circleCenter) <= circleCol.radius;
                    }
                }
            }else {//non centred L_R
                if(results[FUNCTION_RIGHT]){//to the right
                    if(results[FUNCTION_UP] != results[FUNCTION_DOWN]){//centred U_D
                        //System.out.println(3);
                        return functions[FUNCTION_RIGHT].distance(circleCenter) <= circleCol.radius;
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
                        return functions[FUNCTION_LEFT].distance(circleCenter) <= circleCol.radius;
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












