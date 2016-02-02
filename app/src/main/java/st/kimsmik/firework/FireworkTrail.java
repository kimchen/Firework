package st.kimsmik.firework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenk on 2016/2/2.
 */
public class FireworkTrail {

    private List<Partical> mParticalList = new ArrayList<>();
    private int mLength = 5;

    public FireworkTrail(int len){
        mLength = len;
    }
    public void setPosition(Vector3 position){
        synchronized (mParticalList) {
            if (mParticalList.size() < mLength) {
                Partical partical = new Partical();
                partical.setPosition(position);
                mParticalList.add(partical);
            } else {
                mParticalList.remove(0);
                Partical partical = new Partical();
                partical.setPosition(position);
                mParticalList.add(partical);
            }
        }
    }

    public void setColor(float r, float g, float b, float a){
        for(Partical partical : mParticalList){
            partical.setColor(r, g, b, a);
        }
    }

    public void release(){
        mParticalList.clear();
    }

    public void draw(float[] mvpMatrix) {
        synchronized (mParticalList) {
            for (Partical partical : mParticalList) {
                partical.draw(mvpMatrix);
            }
        }
    }
}
