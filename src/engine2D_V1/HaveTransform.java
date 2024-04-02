package engine2D_V1;

public interface HaveTransform {
    public void setPosition(Point3D p);
    public void translate(Vector3D v);
    public void translate(double x, double y, double z);
    public Point3D getPosition();
    public void setRotation(Rotation3D r);
    public void rotate(Vector3D v);
    public void rotate(double x, double y, double z);
    public Rotation3D getRotation();
}
