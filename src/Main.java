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

    public static final class MyCanvas extends Canvas {
        Player p;
        Floor f;
        Vector<EngineObjectModel> objs;
        boolean needRedraw;


        public MyCanvas(){
            this.objs = new Vector<>();
            needRedraw = false;
        }


        @Override
        public void paint(Graphics g) {
            if(needRedraw){
                //System.out.println("paint canvas");
                g.clearRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(255, 0, 255));
                g.drawRect(0, 0, getWidth(), getHeight());
                Vector<BoxCollider> boxColliders = new Vector<>();

                for (int i = 0; i < objs.size(); i++){
                    boxColliders.add((BoxCollider) objs.get(i).colliders.get(0));
                }

                {
                    Function f;
                    Point3D p1, p2;
                    if (!Collider2D.INVERTED_Y) {
                        f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_UP];
                        g.setColor(new Color(0, 255, 0));
                        p1 = f.getImagePoint(-2000);
                        p2 = f.getImagePoint(2000);
                        g.drawLine((int) p1.x, FRAME_HEIGHT - (int) p1.y, (int) p2.x, FRAME_HEIGHT - (int) p2.y);
                        //System.out.println(f.toString()+"gggggggggggggggggggggg");
                        f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_RIGHT];
                        g.setColor(new Color(255, 221, 0));
                        p1 = f.getImagePoint(-2000);
                        p2 = f.getImagePoint(2000);
                        g.drawLine((int) p1.x, FRAME_HEIGHT - (int) p1.y, (int) p2.x, FRAME_HEIGHT - (int) p2.y);
                        //System.out.println(f.toString()+"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                        f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_DOWN];
                        g.setColor(new Color(255, 0, 0));
                        p1 = f.getImagePoint(-2000);
                        p2 = f.getImagePoint(2000);
                        g.drawLine((int) p1.x, FRAME_HEIGHT - (int) p1.y, (int) p2.x, FRAME_HEIGHT - (int) p2.y);
                        //System.out.println(f.toString()+"rrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                        f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_LEFT];
                        g.setColor(new Color(0, 0, 255));
                        p1 = f.getImagePoint(-2000);
                        p2 = f.getImagePoint(2000);
                        g.drawLine((int) p1.x, FRAME_HEIGHT - (int) p1.y, (int) p2.x, FRAME_HEIGHT - (int) p2.y);
                        //System.out.println(f.toString()+"bbbbbbbbbbbbbbbbbbbbbbbbbb");
                    } else {

                        f = boxColliders.get(0).getFunctions()[BoxCollider.FUNCTION_UP];
                        g.setColor(new Color(0, 255, 0));
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
                        g.setColor(new Color(0, 0, 255));
                        p1 = f.getImagePoint(-2000);
                        p2 = f.getImagePoint(2000);
                        g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                        //System.out.println(f.toString()+"bbbbbbbbbbbbbbbbbbbbbbbbbb");
                    }
                }//funzioni lati

                for (int i = 0; i < boxColliders.size(); i++) {


                    g.setColor(new Color(0, 0, 0));

                    /*
                     */
                    for (int j = 0; j < boxColliders.size(); j++) {
                        if (boxColliders.get(j) != boxColliders.get(i)) {
                            if (boxColliders.get(i).isColliding(boxColliders.get(j))) {
                                g.setColor(new Color(255, 0, 0));
                            }
                        }
                    }


                    BoxCollider currColl = boxColliders.get(i);
                    if (!Collider2D.INVERTED_Y) {

                        //g.setColor(new Color(255, 0, 0));
                        g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT - (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT - (int) currColl.getMostDownRightPoint().y);
                        //g.setColor(new Color(0,255,0));
                        g.drawLine((int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT - (int) currColl.getMostUpLeftPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT - (int) currColl.getMostUpRightPoint().y);
                        //g.setColor(new Color(0,0,255));
                        g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT - (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT - (int) currColl.getMostUpLeftPoint().y);
                        //g.setColor(new Color(255, 221, 0));
                        g.drawLine((int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT - (int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT - (int) currColl.getMostUpRightPoint().y);

                        //g.setColor(new Color(0,0,0));
                        g.drawLine((int) currColl.getMostDownRightPoint().x, FRAME_HEIGHT - (int) currColl.getMostDownRightPoint().y, (int) currColl.getMostUpLeftPoint().x, FRAME_HEIGHT - (int) currColl.getMostUpLeftPoint().y);
                        g.drawLine((int) currColl.getMostDownLeftPoint().x, FRAME_HEIGHT - (int) currColl.getMostDownLeftPoint().y, (int) currColl.getMostUpRightPoint().x, FRAME_HEIGHT - (int) currColl.getMostUpRightPoint().y);
                    } else {
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


                needRedraw = false;
            }
        }
    }



    MyCanvas canvas;
    public Main(){
        super("colliders simulation");
        Collider2D.INVERTED_Y = true;

        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        getContentPane().setLayout(null);

        canvas = new MyCanvas();
        canvas.setFocusable(false);

        Player player1 = new Player(canvas);
        Player player2 = new Player(canvas);
        Floor floor = new Floor(canvas);
        player1.setPosition(new Point3D(210,190,0));
        player2.setPosition(new Point3D(310,220,0));
        floor.setPosition(new Point3D(340,400,0));
        ((BoxCollider)player1.colliders.get(0)).size = new Vector3D(20,20);
        ((BoxCollider)player2.colliders.get(0)).size = new Vector3D(100,20);

        MyManager manager = new MyManager(140,false,canvas);
        manager.addObject(player1);
        manager.addObject(player2);
        manager.addObject(floor);
        {
            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == 'a') {
                        player1.inpL = true;
                    }
                    if (e.getKeyChar() == 'd') {
                        player1.inpR = true;
                    }
                    if (e.getKeyChar() == 's') {
                        player1.inpD = true;
                    }
                    if (e.getKeyChar() == 'w') {
                        player1.inpU = true;
                    }
                    if (e.getKeyChar() == 'q') {
                        player1.inp1 = true;
                    }
                    if (e.getKeyChar() == 'e') {
                        player1.inp2 = true;
                    }

                    if (e.getKeyChar() == 'j') {
                        player2.inpL = true;
                    }
                    if (e.getKeyChar() == 'l') {
                        player2.inpR = true;
                    }
                    if (e.getKeyChar() == 'k') {
                        player2.inpD = true;
                    }
                    if (e.getKeyChar() == 'i') {
                        player2.inpU = true;
                    }
                    if (e.getKeyChar() == 'u') {
                        player2.inp1 = true;
                    }
                    if (e.getKeyChar() == 'o') {
                        player2.inp2 = true;
                    }
                }

            });

        }//controls

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JTextArea instructions = new JTextArea();
        String s;
        s = "commands: wasd qe | ijkl uo";
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
        canvas.setSize(FRAME_WIDTH,FRAME_HEIGHT-this.getY());

        canvas.paint(canvas.getGraphics());


        new Thread(()->{
            while (true){
                //System.out.println("thread");
                if(manager.shouldCycle()){
                    //System.out.println("doCycle");
                    manager.doCycle();
                }
            }

        }).start();



    }


    public static void main(String[] args){
        new Main();
    }
}
