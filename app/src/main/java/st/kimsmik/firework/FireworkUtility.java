package st.kimsmik.firework;

import android.opengl.GLES20;

/**
 * Created by chenk on 2016/2/1.
 */
public class FireworkUtility {
    public static long FIREWORK_LIFE_TIME = 5000;
    public static final float SCENE_HEIGHT = 100f;
    public static float FPS = 60f;
    public static float GRAVITY = -9.8f * 0.1f;
    public static float screenHeight = 0f;
    public static float screenWidth = 0f;

    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public static Vector3 convert3DPostion(float x, float y){
        float sceneWidth = (float)screenWidth / screenHeight * SCENE_HEIGHT;
        float newX = x / screenWidth * sceneWidth - sceneWidth / 2;
        float newY = (screenHeight - y) / screenHeight * SCENE_HEIGHT;

        return new Vector3(newX,newY,0f);
    }
}
