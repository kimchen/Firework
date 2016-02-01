package st.kimsmik.firework;

/**
 * Created by chenk on 2016/2/1.
 */
public class Vector3 {
    private float x = 0f;
    private float y = 0f;
    private float z = 0f;

    public Vector3(){}

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX(){
        return this.x;
    }
    public void setX(float f){
        this.x = f;
    }
    public float getY(){
        return this.y;
    }
    public void setY(float f){
        this.y = f;
    }
    public float getZ(){
        return this.z;
    }
    public void setZ(float f){
        this.z = f;
    }
}
