package engine2D;

public abstract class EngineEntityModel implements HaveTag, HaveTransform{
    public static final String DEFAULT_TAG = "gameEntity";
    public static final int DIR_STOP = 0, DIR_UP = 1, DIR_DOWN = -DIR_UP, DIR_RIGHT = 2, DIR_LEFT = -DIR_RIGHT;
    public static final int DIR_UP_LEFT = 2,DIR_UP_RIGHT = 3, DIR_DOWN_LEFT = -3,DIR_DOWN_RIGHT = -2;


    public String tag;


    //constructors
    public EngineEntityModel(String tag){
        setTag(tag);

    }


    //interfaces
    @Override
    public void setTag(String tag) {
        if(tag == null){
            this.tag = DEFAULT_TAG;
        }else {
            this.tag = tag;
        }

    }
    @Override
    public String getTag() {
        return tag;
    }


    //other methods
    public void logicUpdate(double deltaT) {}
    public void draw(){}


}
