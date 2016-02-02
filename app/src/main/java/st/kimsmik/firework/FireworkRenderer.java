package st.kimsmik.firework;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
        Matrix.frustumM(mProjectionMatrix, 0, -ratio * FireworkUtility.SCENE_HEIGHT / 2, ratio * FireworkUtility.SCENE_HEIGHT / 2, 0f, 1f * FireworkUtility.SCENE_HEIGHT, 0.1f, 100.0f);
    }

    public void addFirework(int size, Vector3 position){
        final Firework firework = new Firework(size,position);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (mFireworkList) {
                    firework.release();
                    mFireworkList.remove(firework);
                }
            }
        }, FireworkUtility.FIREWORK_LIFE_TIME);
        synchronized(mFireworkList) {
            mFireworkList.add(firework);
        }
    }

    public void addFirework(int size, List<Vector3> trailPos, long trailTime){
        final Firework firework = new Firework(size,trailPos,trailTime);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized(mFireworkList) {
                    firework.release();
                    mFireworkList.remove(firework);
                }
            }
        },FireworkUtility.FIREWORK_LIFE_TIME+trailTime);
        synchronized(mFireworkList) {
            mFireworkList.add(firework);
        }
    }

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
    }
}
