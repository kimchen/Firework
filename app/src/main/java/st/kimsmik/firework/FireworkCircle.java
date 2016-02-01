package st.kimsmik.firework;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;


/**
 * Created by Kim on 2016/2/1.
 */
public class FireworkCircle {
    private List<Partical> mParticalList = new ArrayList<>();
    private List<Vector3> vList = new ArrayList<>();
    private boolean animing = false;
    private Vector3 mAnchor = new Vector3();

    public FireworkCircle(int particalNum, Vector3 anchor, float strength){
        mAnchor = anchor;
        for(int i=0; i < particalNum; i++){
            Partical partical = new Partical();
            partical.setPosition(anchor);
            mParticalList.add(partical);

            float randomStrength = ((float)Math.random()*0.1f+0.9f)*strength;
            double theta = (((double)i/(double)particalNum)*2*Math.PI);
            float x = (float)Math.sin(theta);
            float y = (float)Math.cos(theta);
            Vector3 v = new Vector3(x * strength,y * strength,0f);
            vList.add(v);
//            for(int j=0; j < particalNum; j++){
//                Partical partical = new Partical();
//                partical.setPosition(anchor);
//                mParticalList.add(partical);
//
//                double omega = (((double)j/(double)particalNum)*2*Math.PI);
//                float newX = (float)Math.cos(omega) * x;
//                float newY = y;
//                float newZ = (float)Math.sin(omega) * x;
//                Vector3 v = new Vector3(newX * strength,newY * strength,newZ * strength);
//                vList.add(v);
//            }

        }
    }

    public void setColor(float r, float g, float b, float a){
        for(Partical partical : mParticalList){
            partical.setColor(r, g, b, a);
        }
    }

    public void drawParticals(float[] mvpMatrix){
        for(Partical partical : mParticalList){
            partical.draw(mvpMatrix);
        }
    }

    public void release(){
        animing = false;
        mParticalList.clear();
        vList.clear();
    }

//    public void update(float deltaTime) {
//        for (int i = 0; i < mParticalList.size(); i++) {
//            Partical partical = mParticalList.get(i);
//            Vector3 v = vList.get(i);
//
//            float newX = v.getX() * deltaTime;
//            float newY = v.getY() * deltaTime + FireworkUtility.GRAVITY * deltaTime * deltaTime / 2;
//            float newZ = v.getZ() * deltaTime;
//            Vector3 oriPosition = partical.getPosition();
//            partical.setPosition(new Vector3(oriPosition.getX() + newX, oriPosition.getY() + newY, oriPosition.getZ() + newZ));
//        }
//    }
    public void startAnim(final long delay){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                animing = true;
                long startTime = Calendar.getInstance().getTimeInMillis();
                while(animing){
                    long deltaTime = Calendar.getInstance().getTimeInMillis() - startTime;
                    float deltaSec = ((float)deltaTime)/1000f;
                    for(int i=0; i < mParticalList.size(); i++){
                        Partical partical = mParticalList.get(i);
                        if(partical == null)
                            continue;
                        Vector3 v = vList.get(i);

                        float newX = v.getX() * deltaSec;
                        float newY = v.getY() * deltaSec + FireworkUtility.GRAVITY * deltaSec * deltaSec / 2;
                        float newZ = v.getZ() * deltaSec;
                        //Vector3 oriPosition = partical.getPosition();
                        partical.setPosition(new Vector3(mAnchor.getX() + newX, mAnchor.getY() + newY, mAnchor.getZ() + newZ));
                        //v.setY(v.getY()+FireworkUtility.GRAVITY * deltaSec);
                    }

                    try {
                        Thread.sleep((long)(1000f/FireworkUtility.FPS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        animing = false;
                        break;
                    }
                }

            }
        });
        thread.start();
    }
}
