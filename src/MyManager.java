import engine2D_V1.EngineManager;

public class MyManager extends EngineManager {
    Main.MyCanvas canvas;
    public MyManager(int fps, boolean deleteTimeExceed, Main.MyCanvas canvas) {
        super(fps, deleteTimeExceed);
        this.canvas = canvas;
    }


    @Override
    public synchronized void doCycle() {
        super.doCycle();

        if(canvas.needRedraw){
            canvas.paint(canvas.getGraphics());
        }


    }
}
