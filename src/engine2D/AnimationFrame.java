package engine2D;

public class AnimationFrame<E> {
    public E info;
    public double duration;

    public AnimationFrame(E info, double duration){
        this.info = info;
        this.duration = duration;
    }
}
