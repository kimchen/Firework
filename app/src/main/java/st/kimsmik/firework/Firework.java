package st.kimsmik.firework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kim on 2016/2/1.
 */
public class Firework {
    private List<FireworkCircle> mCircleList = new ArrayList<>();

    public Firework(int circleNum, Vector3 anchor){
        for(int i=0; i < circleNum; i++){
            FireworkCircle circle = new FireworkCircle(20,anchor,i*0.5f+0.5f);
            circle.startAnim(0);
            mCircleList.add(circle);
        }
        setColor((float)Math.random(),(float)Math.random(),(float)Math.random(),1f);
    }

    public void setColor(float r, float g, float b, float a){
        for(FireworkCircle circle : mCircleList){
            circle.setColor(r, g, b, a);
        }
    }
    public void drawParticals(float[] mvpMatrix){
        for(FireworkCircle circle : mCircleList){
            circle.drawParticals(mvpMatrix);
        }
    }

    public void release(){
        for(FireworkCircle circle : mCircleList){
            circle.release();
        }
        mCircleList.clear();
    }

//    public void update(float deltaTime){
//        for(FireworkCircle circle : mCircleList){
//            circle.update(deltaTime);
//        }
//    }
}
