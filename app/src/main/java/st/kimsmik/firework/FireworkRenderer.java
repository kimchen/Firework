package st.kimsmik.firework;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import st.kimsmik.firework.shape.Triangle;

/**
 * Created by chenk on 2016/2/1.
 */
public class FireworkRenderer implements GLSurfaceView.Renderer {

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private List<Firework> mFireworkList = new ArrayList<>();

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Triangle.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        FireworkUtility.screenHeight = height;
        FireworkUtility.screenWidth = width;

        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix,0,-ratio*FireworkUtility.SCENE_HEIGHT/2,ratio*FireworkUtility.SCENE_HEIGHT/2,0f,1f*FireworkUtility.SCENE_HEIGHT,1f,100f);
    }

    public void addFirework(Vector3 position){
        final Firework firework = new Firework(5,position);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized(mFireworkList) {
                    firework.release();
                    mFireworkList.remove(firework);
                }
            }
        },5000);
        synchronized(mFireworkList) {
            mFireworkList.add(firework);
        }
    }

    private long lastMillis = 0;
    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        synchronized(mFireworkList) {
            for (Firework firework : mFireworkList) {
                firework.drawParticals(mMVPMatrix);
            }
        }
        //onUpdate();
    }

//    public void onUpdate(){
//        if(lastMillis == 0){
//            lastMillis = Calendar.getInstance().getTimeInMillis();
//            return;
//        }
//        long nowMillis = Calendar.getInstance().getTimeInMillis();
//        float deltaTime = ((float)nowMillis-lastMillis)/1000f;
//        synchronized(mFireworkList) {
//            for (Firework firework : mFireworkList) {
//                firework.update(deltaTime);
//            }
//        }
//        lastMillis = nowMillis;
//    }
}
