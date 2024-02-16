package engine2D_V1;

public class AnimationFrame<E> {
    public E info;
    public double duration;

    public AnimationFrame(E info, double duration){
        this.info = info;
        this.duration = duration;
    }
}
