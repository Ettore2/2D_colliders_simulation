import engine2D_V1.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import engine2D_V1.Function;

public class Main extends JFrame{
    public static final int FRAME_WIDTH = 1000, FRAME_HEIGHT = 800;
    public static final float FONT_SIZE_MULTIPLIER = 1.5f;

    public static final class MyCanvas extends Canvas{
        Vector<BoxCollider> boxColliders;
        Vector<CircularCollider> circColliders;
        Vector<EngineObjectModel> objs;
        MyCanvas(){
            setSize(FRAME_WIDTH,FRAME_HEIGHT);
            setBackground(new Color(255,255,255));
            boxColliders = new Vector<>();
            circColliders = new Vector<>();
            objs = new Vector<>();
        }

        @Override
        public void paint(Graphics g) {
            //System.out.println("paint canvas");
            g.clearRect(0,0,getWidth(),getHeight());


            if(true){
                Function f;
                Point3D p1, p2;
                if(!Collider2D.INVERTED_Y){
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_UP];
                    g.setColor(new Color(0,255,0));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, FRAME_HEIGHT-(int) p1.y, (int) p2.x, FRAME_HEIGHT-(int) p2.y);
                    //System.out.println(f.toString()+"gggggggggggggggggggggg");
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_RIGHT];
                    g.setColor(new Color(255, 221, 0));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, FRAME_HEIGHT-(int) p1.y, (int) p2.x, FRAME_HEIGHT-(int) p2.y);
                    //System.out.println(f.toString()+"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_DOWN];
                    g.setColor(new Color(255, 0, 0));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, FRAME_HEIGHT-(int) p1.y, (int) p2.x, FRAME_HEIGHT-(int) p2.y);
                    //System.out.println(f.toString()+"rrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_LEFT];
                    g.setColor(new Color(0,0,255));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, FRAME_HEIGHT-(int) p1.y, (int) p2.x, FRAME_HEIGHT-(int) p2.y);
                    //System.out.println(f.toString()+"bbbbbbbbbbbbbbbbbbbbbbbbbb");
                }else {

                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_UP];
                    g.setColor(new Color(0,255,0));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                    //System.out.println(f.toString()+"gggggggggggggggggggggg");
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_RIGHT];
                    g.setColor(new Color(255, 221, 0));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                    //System.out.println(f.toString()+"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_DOWN];
                    g.setColor(new Color(255, 0, 0));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                    //System.out.println(f.toString()+"rrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                    f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_LEFT];
                    g.setColor(new Color(0,0,255));
                    p1 = f.getImagePoint(-2000);
                    p2 = f.getImagePoint(2000);
                    g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                    //System.out.println(f.toString()+"bbbbbbbbbbbbbbbbbbbbbbbbbb");
                }
            }//funzioni lati

            for (int i = 0; i < boxColliders.size(); i++){


                g.setColor(new Color(0,0,0));

                /*
                 */
                for(int j = 0; j < boxColliders.size(); j++){
                    if(boxColliders.get(j) != boxColliders.get(i)){
                        if(boxColliders.get(i).isColliding(boxColliders.get(j))){
                            g.setColor(new Color(255,0,0));
                        }
                    }
                }
                for(int j = 0; j < circColliders.size(); j++){
                    if(boxColliders.get(i).isColliding(circColliders.get(j))){
                        g.setColor(new Color(255,0,0));
                    }
                }
                for(int j = 0; j < objs.size(); j++){
                    if(objs.get(j).collidingWith(objs.get(j))){
                        for(int l  =0 ; l < objs.get(j).colliders.size(); l++){
                            if(boxColliders.get(i).isColliding(objs.get(j).colliders.get(l))){
                                g.setColor(new Color(255,0,0));
                            }
                        }
                    }
                }



                BoxCollider currColl = boxColliders.get(i);
                if(!Collider2D.INVERTED_Y){

                    //g.setColor(new Color(255, 0, 0));
                    g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownRightPoint().y);
                    //g.setColor(new Color(0,255,0));
                    g.drawLine((int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpLeftPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpRightPoint().y);
                    //g.setColor(new Color(0,0,255));
                    g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpLeftPoint().y);
                    //g.setColor(new Color(255, 221, 0));
                    g.drawLine((int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpRightPoint().y);

                    //g.setColor(new Color(0,0,0));
                    g.drawLine((int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpLeftPoint().y);
                    g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpRightPoint().y);
                }else {
                    //g.setColor(new Color(255, 0, 0));
                    g.drawLine((int) currColl.getMostDownLeftPoint().x, (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostDownRightPoint().x, (int) currColl.getMostDownRightPoint().y);
                    //g.setColor(new Color(0,255,0));
                    g.drawLine((int) currColl.getMostUpLeftPoint().x, (int) currColl.getMostUpLeftPoint().y, (int) currColl.getMostUpRightPoint().x, (int) currColl.getMostUpRightPoint().y);
                    //g.setColor(new Color(0,0,255));
                    g.drawLine((int) currColl.getMostDownLeftPoint().x, (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpLeftPoint().x, (int) currColl.getMostUpLeftPoint().y);
                    //g.setColor(new Color(255, 221, 0));
                    g.drawLine((int) currColl.getMostDownRightPoint().x, (int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpRightPoint().x, (int) currColl.getMostUpRightPoint().y);

                    //g.setColor(new Color(0,0,0));
                    g.drawLine((int) currColl.getMostDownRightPoint().x, (int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpLeftPoint().x, (int) currColl.getMostUpLeftPoint().y);
                    g.drawLine((int) currColl.getMostDownLeftPoint().x, (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpRightPoint().x, (int) currColl.getMostUpRightPoint().y);

                }

            }

            for (int i = 0; i < circColliders.size(); i++){
                g.setColor(new Color(0,0,0));
                /*
                 */
                for(int j = 0; j < boxColliders.size(); j++){
                    if(circColliders.get(i).isColliding(boxColliders.get(j))){
                        g.setColor(new Color(255,0,0));
                    }
                }
                for(int j = 0; j < circColliders.size(); j++){
                    if(circColliders.get(j) != circColliders.get(i)){
                        if(circColliders.get(i).isColliding(circColliders.get(j))){
                            g.setColor(new Color(255,0,0));
                        }
                    }
                }
                for(int j = 0; j < objs.size(); j++){
                    if(objs.get(j).collidingWith(objs.get(j))){
                        for(int l  =0 ; l < objs.get(j).colliders.size(); l++){
                            if(circColliders.get(i).isColliding(objs.get(j).colliders.get(l))){
                                g.setColor(new Color(255,0,0));
                            }
                        }
                    }
                }
                CircularCollider currColl = circColliders.get(i);
                if(!Collider2D.INVERTED_Y){
                    g.drawOval((int) (currColl.getPositionAbs().x-currColl.radius), (int) (FRAME_HEIGHT-currColl.getPositionAbs().y-currColl.radius), (int) currColl.radius*2, (int) currColl.radius*2);
                    g.fillOval((int) (currColl.getPositionAbs().x), (int) (FRAME_HEIGHT-currColl.getPositionAbs().y),2,2);
                }else {
                    g.drawOval((int) (currColl.getPositionAbs().x-currColl.radius), (int) (currColl.getPositionAbs().y-currColl.radius), (int) currColl.radius*2, (int) currColl.radius*2);
                    g.fillOval((int) (currColl.getPositionAbs().x), (int) (currColl.getPositionAbs().y),2,2);
                }
            }

            for(int i = 0; i < objs.size(); i++){
                g.setColor(new Color(0,0,0));

                for(int j = 0; j < boxColliders.size(); j++){
                    for(int l = 0 ; l < objs.get(i).colliders.size(); l++){
                        if(boxColliders.get(j).isColliding(objs.get(i).colliders.get(l))){
                            g.setColor(new Color(255,0,0));
                        }
                    }
                }
                for(int j = 0; j < circColliders.size(); j++){
                    for(int l = 0 ; l < objs.get(i).colliders.size(); l++){
                        if(circColliders.get(j).isColliding(objs.get(i).colliders.get(l))){
                            g.setColor(new Color(255,0,0));
                        }
                    }
                }
                for(int j = 0; j < objs.size(); j++){
                    if(objs.get(j).collidingWith(objs.get(i))){
                        if(!objs.get(i).equals(objs.get(j))){
                            g.setColor(new Color(255,0,0));
                        }
                    }
                }
                EngineObjectModel currObj = objs.get(i);
                if(!Collider2D.INVERTED_Y){
                    g.fillRect((int) (currObj.getPosition().x)-2, (int) (FRAME_HEIGHT-currObj.getPosition().y)-2,4,4);
                }else {
                    g.fillRect((int) (currObj.getPosition().x)-2, (int) (currObj.getPosition().y)-2,4,4);
                }

                for(int j = 0; j < currObj.colliders.size(); j++){
                    if(currObj.colliders.get(j).type == Collider2D.SQUARE){
                        BoxCollider currColl = (BoxCollider) currObj.colliders.get(j);
                        if(!Collider2D.INVERTED_Y){

                            //g.setColor(new Color(255, 0, 0));
                            g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownRightPoint().y);
                            //g.setColor(new Color(0,255,0));
                            g.drawLine((int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpLeftPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpRightPoint().y);
                            //g.setColor(new Color(0,0,255));
                            g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpLeftPoint().y);
                            //g.setColor(new Color(255, 221, 0));
                            g.drawLine((int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpRightPoint().y);

                            //g.setColor(new Color(0,0,0));
                            g.drawLine((int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpLeftPoint().y);
                            g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT-(int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT-(int) currColl.getMostUpRightPoint().y);
                        }else {
                            //g.setColor(new Color(255, 0, 0));
                            g.drawLine((int) currColl.getMostDownLeftPoint().x, (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostDownRightPoint().x, (int) currColl.getMostDownRightPoint().y);
                            //g.setColor(new Color(0,255,0));
                            g.drawLine((int) currColl.getMostUpLeftPoint().x, (int) currColl.getMostUpLeftPoint().y, (int) currColl.getMostUpRightPoint().x, (int) currColl.getMostUpRightPoint().y);
                            //g.setColor(new Color(0,0,255));
                            g.drawLine((int) currColl.getMostDownLeftPoint().x, (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpLeftPoint().x, (int) currColl.getMostUpLeftPoint().y);
                            //g.setColor(new Color(255, 221, 0));
                            g.drawLine((int) currColl.getMostDownRightPoint().x, (int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpRightPoint().x, (int) currColl.getMostUpRightPoint().y);

                            //g.setColor(new Color(0,0,0));
                            g.drawLine((int) currColl.getMostDownRightPoint().x, (int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpLeftPoint().x, (int) currColl.getMostUpLeftPoint().y);
                            g.drawLine((int) currColl.getMostDownLeftPoint().x, (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpRightPoint().x, (int) currColl.getMostUpRightPoint().y);

                        }
                    }else {
                        CircularCollider currColl = (CircularCollider) currObj.colliders.get(j);
                        if(!Collider2D.INVERTED_Y){
                            g.drawOval((int) (currColl.getPositionAbs().x-currColl.radius), (int) (FRAME_HEIGHT-currColl.getPositionAbs().y-currColl.radius), (int) currColl.radius*2, (int) currColl.radius*2);
                            g.fillOval((int) (currColl.getPositionAbs().x), (int) (FRAME_HEIGHT-currColl.getPositionAbs().y),2,2);
                        }else {
                            g.drawOval((int) (currColl.getPositionAbs().x-currColl.radius), (int) (currColl.getPositionAbs().y-currColl.radius), (int) currColl.radius*2, (int) currColl.radius*2);
                            g.fillOval((int) (currColl.getPositionAbs().x), (int) (currColl.getPositionAbs().y),2,2);
                        }
                    }
                }
            }


        }
    }



    MyCanvas canvas;
    public Main(){
        super("colliders simulation");
        //Collider2D.INVERTED_Y = true;


        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        getContentPane().setLayout(null);
        //setLocation(750,560);


        BoxCollider bColl1;
        BoxCollider bColl2;
        CircularCollider cColl1;
        CircularCollider cColl2;
        EngineObject obj1 = new EngineObject();
        if(!Collider2D.INVERTED_Y){
            bColl1 = new BoxCollider(null,new Point3D(100,750),new Vector3D(50,70));
            bColl2 = new BoxCollider(null,new Point3D(200,600),new Vector3D(30,40));
            cColl1 = new CircularCollider(null,new Point3D(160,700),30);
            cColl2 = new CircularCollider(null,new Point3D(370,630),75);
            obj1.setPosition(new Point3D(30,660));
            obj1.colliders.add(new BoxCollider(obj1, new Point3D(0,25),new Vector3D(20,20)));
        }else {
            bColl1 = new BoxCollider(null,new Point3D(100,150),new Vector3D(50,70));
            bColl2 = new BoxCollider(null,new Point3D(200,200,0),new Vector3D(30,40));
            cColl1 = new CircularCollider(null,new Point3D(100,100),30);
            cColl2 = new CircularCollider(null,new Point3D(250,330,0),75);
            obj1.setPosition(new Point3D(30,120));
            obj1.colliders.add(new BoxCollider(obj1, new Point3D(0,25),new Vector3D(20,20)));
        }
        canvas = new MyCanvas();
        canvas.setFocusable(false);
        this.addKeyListener(new KeyAdapter() {public void keyReleased(KeyEvent e) {
                Collider2D collA = bColl1,collB = cColl1;
                EngineObjectModel obj = obj1;
                if(e.getKeyChar() == 'a'){
                    collA.setPositionRel(collA.getPositionRel().sum(new Point3D(-10,0,0)));
                }
                if(e.getKeyChar() == 'd'){
                    collA.setPositionRel(collA.getPositionRel().sum(new Point3D(10,0,0)));
                }
                if(e.getKeyChar() == 's'){
                    if(!Collider2D.INVERTED_Y){
                        collA.setPositionRel(collA.getPositionRel().sum(new Point3D(0,-10,0)));
                    }else {
                        collA.setPositionRel(collA.getPositionRel().sum(new Point3D(0,10,0)));
                    }


                }
                if(e.getKeyChar() == 'w'){
                    if(!Collider2D.INVERTED_Y){
                        collA.setPositionRel(collA.getPositionRel().sum(new Point3D(0,10,0)));
                    }else {
                        collA.setPositionRel(collA.getPositionRel().sum(new Point3D(0,-10,0)));
                    }
                }

                if(e.getKeyChar() == 'q'){
                    collA.rotate(5);
                }
                if(e.getKeyChar() == 'e'){
                    collA.rotate(-5);
                }


                if(e.getKeyChar() == 'j'){
                    collB.setPositionRel(collB.getPositionRel().sum(new Point3D(-10,0,0)));
                }
                if(e.getKeyChar() == 'l'){
                    collB.setPositionRel(collB.getPositionRel().sum(new Point3D(10,0,0)));
                }
                if(e.getKeyChar() == 'k'){
                    if(!Collider2D.INVERTED_Y){
                        collB.setPositionRel(collB.getPositionRel().sum(new Point3D(0,-10,0)));
                    }else {
                        collB.setPositionRel(collB.getPositionRel().sum(new Point3D(0,10,0)));
                    }
                }
                if(e.getKeyChar() == 'i'){
                    if(!Collider2D.INVERTED_Y){
                        collB.setPositionRel(collB.getPositionRel().sum(new Point3D(0,10,0)));
                    }else {
                        collB.setPositionRel(collB.getPositionRel().sum(new Point3D(0,-10,0)));
                    }
                }

                if(e.getKeyChar() == 'u'){
                    collB.rotate(5);
                }
                if(e.getKeyChar() == 'o'){
                    collB.rotate(-5);
                }


                if(e.getKeyChar() == 'f'){
                    obj1.setPosition(obj1.getPosition().sum(new Point3D(-10,0,0)));
                }
                if(e.getKeyChar() == 'h'){
                    obj1.setPosition(obj1.getPosition().sum(new Point3D(10,0,0)));
                }
                if(e.getKeyChar() == 'g'){
                    if(!Collider2D.INVERTED_Y){
                        obj1.setPosition(obj1.getPosition().sum(new Point3D(0,-10,0)));
                    }else {
                        obj1.setPosition(obj1.getPosition().sum(new Point3D(0,10,0)));
                    }
                }
                if(e.getKeyChar() == 't'){
                    if(!Collider2D.INVERTED_Y){
                        obj1.setPosition(obj1.getPosition().sum(new Point3D(0,10,0)));
                    }else {
                        obj1.setPosition(obj1.getPosition().sum(new Point3D(0,-10,0)));
                    }
                }

                if(e.getKeyChar() == 'r'){
                    obj1.rotate(0,0,5);
                }
                if(e.getKeyChar() == 'y'){
                    obj1.rotate(0,0,-5);
                }


                //System.out.println("coll1: "+collA.getPositionAbs().toString());
                //System.out.println("coll2: "+collB.getPositionAbs().toString());
                //System.out.println("colliding: "+collA.isColliding(collB)+"-------------------------------------------------");
                //System.out.println(collA.getRotationAbs().z);
                //System.out.println(collA.getPositionAbs().toString());
                /*

                Function[] fs = coll1.getFunctions();
                Point3D[] ps = coll1.getPoints();
                for(int i = 0; i < fs.length; i++){
                    System.out.println(fs[i].toString());
                }
                System.out.println();
                for(int i = 0; i < ps.length; i++){
                    System.out.println(ps[i].toString());
                }
                 */

                //System.out.println("obj pos: "+obj.getPosition().toString());
                //System.out.println("coll pos abs: "+obj.colliders.get(0).getPositionAbs().toString());
                //System.out.println("coll pos rel: "+obj.colliders.get(0).getPositionRel().toString());
                //System.out.println("obj rot: "+obj.getRotation().toString());
                //System.out.println("coll rot abs: "+obj.colliders.get(0).getRotationAbs().toString());
                //System.out.println("coll rot rel: "+obj.colliders.get(0).getRotationRel().toString());
                //System.out.println("---------------------------------------------");
                canvas.paint(canvas.getGraphics());
            }});
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JTextArea instructions = new JTextArea();
        String s;
        s = "commands: wasd+qe | tfgh+ry | ijkl+uo";
        if(Collider2D.INVERTED_Y){
            s = s+"\t!using inverted coordinates!";
        }
        instructions.setText(s);
        add(instructions);
        instructions.setEditable(false);
        Font f = instructions.getFont();
        instructions.setFont(new Font(f.getName(),f.getStyle(), (int) (f.getSize()* FONT_SIZE_MULTIPLIER)));
        instructions.setSize(instructions.getPreferredSize());
        instructions.setLocation(10,0);

        add(canvas);
        canvas.setLocation(0,instructions.getY()+instructions.getHeight());
        canvas.boxColliders.add(bColl1);
        canvas.boxColliders.add(bColl2);
        canvas.circColliders.add(cColl1);
        canvas.circColliders.add(cColl2);
        canvas.objs.add(obj1);





    }


    public static void main(String[] args){
        new Main();
    }
}
