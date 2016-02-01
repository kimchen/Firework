package st.kimsmik.firework;

import android.opengl.GLES20;

/**
 * Created by chenk on 2016/2/1.
 */
public class FireworkUtility {
    public static final float SCENE_HEIGHT = 100f;
    public static float screenHeight = 0f;
    public static float screenWidth = 0f;
    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public static Vector3 convert3DPostion(float x, float y){
        float sceneWidth = (float)screenWidth / screenHeight * SCENE_HEIGHT;
        float newX = x / screenWidth * sceneWidth - sceneWidth / 2;
        float newY = (screenHeight - y) / SCENE_HEIGHT * screenHeight;
        return new Vector3(newX,newY,0f);
    }
}
