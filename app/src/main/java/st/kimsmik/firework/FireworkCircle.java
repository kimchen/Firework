package st.kimsmik.firework;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kim on 2016/2/1.
 */
public class FireworkCircle {
    protected List<Partical> mParticalList = new ArrayList<>();
    protected boolean animing = false;
    protected List<Vector3> vList = new ArrayList<>();
    protected Vector3 mAnchor = new Vector3();
    protected float mStrength = 0f;
    protected int mSize = 0;
    public FireworkCircle(int size, Vector3 anchor, float strength){
        mSize = size;
        mAnchor = anchor;
        mStrength = strength;
        init();
    }

    protected void init(){
        for(int i=0; i < mSize; i++){
            Partical partical = new Partical();
            partical.setPosition(mAnchor);
            mParticalList.add(partical);

            double theta = (((double)i/(double)mSize)*2*Math.PI);
            float x = (float)Math.sin(theta);
            float y = (float)Math.cos(theta);
            Vector3 v = new Vector3(x * mStrength,y * mStrength,0f);
            vList.add(v);
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
                        partical.setPosition(new Vector3(mAnchor.getX() + newX, mAnchor.getY() + newY, mAnchor.getZ() + newZ));
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
