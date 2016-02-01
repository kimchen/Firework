package st.kimsmik.firework;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import st.kimsmik.firework.shape.Triangle;

/**
 * Created by chenk on 2016/2/1.
 */
public class FireworkRenderer implements GLSurfaceView.Renderer {
    Triangle triangle = null;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        triangle = new Triangle();
        triangle.setScale(new Vector3(10f, 10f, 1f));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        FireworkUtility.screenHeight = height;
        FireworkUtility.screenWidth = width;

        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix,0,-ratio*FireworkUtility.SCENE_HEIGHT/2,ratio*FireworkUtility.SCENE_HEIGHT/2,0f,1f*FireworkUtility.SCENE_HEIGHT,1f,100f);
    }

    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public Triangle getTriangle(){
        return triangle;
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw triangle
        triangle.draw(mMVPMatrix);
    }

}
