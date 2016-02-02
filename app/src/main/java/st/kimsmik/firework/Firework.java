package st.kimsmik.firework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kim on 2016/2/1.
 */
public class Firework {
    public enum FIREWORK_TYPE{
        CIRCLE(0),
        HEART(1),
        STAR(2),
        MAX_NUM(3);
        public final int value;
        FIREWORK_TYPE(int i){
            value = i;
        }
    }

    private List<FireworkCircle> mCircleList = new ArrayList<>();
    private List<Vector3> mTrailPos = new ArrayList<>();
    private FireworkTrail mTrail = null;
    private long mTrailTime = 0;
    private Vector3 mColor = null;
    private int mSize = 0;

    public Firework(int size, Vector3 anchor){
        mSize = size;
        randomColor();
        explore(anchor);
    }

    public Firework(int size, List<Vector3> trailPos, long trailTime){
        mSize = size;
        randomColor();
        mTrailPos = trailPos;
        mTrail = new FireworkTrail(size);
        mTrail.setColor(mColor);
        mTrailTime = trailTime;
        startTrail();
    }

    private void randomColor(){
        mColor = new Vector3((float)Math.random()/2f+0.5f,(float)Math.random()/2f+0.5f,(float)Math.random()/2f+0.5f);
    }

    public void explore(Vector3 anchor){
        Random random = new Random();
        int type = random.nextInt()%FIREWORK_TYPE.MAX_NUM.value;
        synchronized (mCircleList) {
            for (int i = 0; i < mSize; i++) {
                FireworkCircle circle;
                if (type == FIREWORK_TYPE.HEART.value) {
                    circle = new FireworkHeart(i * 10, anchor, i * 0.5f + 0.5f);
                } else if (type == FIREWORK_TYPE.STAR.value) {
                    circle = new FireworkStar(i * 10, anchor, i * 0.5f + 0.5f);
                } else {
                    circle = new FireworkCircle(i * 10, anchor, i * 0.5f + 0.5f);
                }
                circle.startAnim(0);
                circle.setColor(mColor.getX(), mColor.getY(), mColor.getZ(), 1f);
                mCircleList.add(circle);
            }
        }
    }

    public void drawParticals(float[] mvpMatrix){
        synchronized (mCircleList) {
            for (FireworkCircle circle : mCircleList) {
                circle.drawParticals(mvpMatrix);
            }
        }
        if(mTrail != null){
            synchronized (mTrail) {
                mTrail.draw(mvpMatrix);
            }
        }
    }

    public void release(){
        synchronized (mCircleList) {
            for (FireworkCircle circle : mCircleList) {
                circle.release();
            }
            mCircleList.clear();
        }
        if(mTrail != null) {
            synchronized (mTrail) {
                mTrail.release();
                mTrail = null;
            }
        }
        mTrailPos.clear();
    }

    public void startTrail(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int posIndex = 0;
                while(posIndex < mTrailPos.size()){
                    mTrail.setPosition(mTrailPos.get(posIndex));
                    posIndex++;

                    long sleepTime = 0;
                    if(mTrailPos.size() > 1){
                        sleepTime = mTrailTime/(mTrailPos.size()-1);
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                synchronized (mTrail) {
                    mTrail.release();
                    mTrail = null;
                }
                explore(mTrailPos.get(mTrailPos.size()-1));
            }
        });
        thread.start();
    }
}
