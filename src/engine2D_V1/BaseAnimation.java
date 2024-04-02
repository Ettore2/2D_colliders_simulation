package engine2D_V1;

import java.util.Vector;

public class BaseAnimation <E> {
    private Vector<AnimationFrame> animFrames;
    private double timer;
    public double speed;
    private int currFrame;


    //constructors
    public BaseAnimation(Vector<AnimationFrame> frames){
        this.animFrames = frames;
        this.speed = 1;

        timer = 0;
        currFrame = 0;
    }
    public BaseAnimation(Vector<E> info, double infoDuration){
        this(null);


        animFrames = new Vector<>();
        for(int i = 0; i < info.size(); i++){
            animFrames.add(new AnimationFrame<E>(info.get(i),infoDuration));
        }
    }


    //other methods
    public AnimationFrame<E> update(double deltaT){
        if(deltaT >= 0){
            timer += deltaT*speed;

            if(timer >= animFrames.get(currFrame).duration){
                while (timer >= animFrames.get(currFrame).duration){
                    timer -= animFrames.get(currFrame).duration;
                    currFrame = (currFrame + 1)% animFrames.size();
                }
            } else if (timer < 0) {
                while (timer < 0){
                    currFrame = (currFrame - 1 + animFrames.size())% animFrames.size();
                    timer += animFrames.get(currFrame).duration;
                }
            }
        }

        return animFrames.get(currFrame);
    }
    public AnimationFrame<E> getCurrFrame(){
        return animFrames.get(currFrame);

    }
    public void addFrame(AnimationFrame<E> frame, int position){
        this.animFrames.add(position,frame);

    }
    public void removeFrame(AnimationFrame<E> frame){
        this.animFrames.remove(frame);

    }
    public void removeFrame(int pos){
        this.animFrames.remove(pos);

    }
    public AnimationFrame<E> getFrame(int pos){
        return animFrames.get(pos);

    }
    public double getTotalDuration(){
        double duration = 0;
        for(int i = 0 ; i < animFrames.size(); i++){
            duration += animFrames.get(i).duration;
        }

        return duration;
    }
    public void setTotalTime(double newTime){
        this.timer = newTime%getTotalDuration();
        currFrame = 0;

        update(0);

    }
    public void setCurrFrame(int framePos){
        currFrame = framePos%animFrames.size();

    }


}
