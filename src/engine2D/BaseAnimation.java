package engine2D;

import java.util.Vector;

public class BaseAnimation <E> {
    private Vector<AnimationFrame> animFrames;
    private double timer;
    public double speed;
    private int currFrame;
    private boolean looping;


    //constructors
    public BaseAnimation(Vector<AnimationFrame> frames,boolean looping){
        this.animFrames = frames;
        this.speed = 1;
        this.looping = looping;

        timer = 0;
        currFrame = 0;
    }
    public BaseAnimation(Vector<E> info, double infoDuration,boolean looping){
        this(null,looping);


        animFrames = new Vector<>();
        for(int i = 0; i < info.size(); i++){
            animFrames.add(new AnimationFrame<E>(info.get(i),infoDuration));
        }
    }
    public BaseAnimation(Vector<AnimationFrame> frames){
        this(frames, true);

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
                    if(looping){
                        currFrame = (currFrame + 1)% animFrames.size();
                        timer -= animFrames.get(currFrame).duration;
                    }else {
                        currFrame += 1;
                        if(currFrame == animFrames.size()){
                            currFrame = animFrames.size()-1;
                            timer = 0;
                        }else {
                            timer -= animFrames.get(currFrame).duration;
                        }
                    }
                }
            } else if (timer < 0) {
                while (timer < 0){
                    if(looping){
                        currFrame = (currFrame - 1 + animFrames.size())% animFrames.size();
                        timer += animFrames.get(currFrame).duration;
                    }else {
                        currFrame -= 1;
                        if(currFrame == -1){
                            currFrame = 0;
                            timer = 0;
                        }else {
                            timer += animFrames.get(currFrame).duration;
                        }
                    }


                }
            }
        }

        return animFrames.get(currFrame);
    }
    public AnimationFrame<E> getCurrFrame(){
        return animFrames.get(currFrame);

    }
    public int getCurrFrameNumber(){
        return currFrame;

    }
    public boolean getLooping(){
        return looping;

    }
    public void setLooping(boolean looping) {
        this.looping = looping;

    }
    public boolean hasFinished(){
        return !looping && ((speed>=0 && currFrame == animFrames.size()-1) || (speed<0 && currFrame == 0));

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
