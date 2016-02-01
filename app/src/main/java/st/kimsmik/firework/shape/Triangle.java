package st.kimsmik.firework.shape;


import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import st.kimsmik.firework.FireworkUtility;
import st.kimsmik.firework.Vector3;

/**
 * Created by chenk on 2016/2/1.
 */
public class Triangle {
    private final String vertexShaderCode =
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
                    "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private static final int COORDS_PER_VERTEX = 3;
    // in counterclockwise order:
    private static float triangleCoords[] = {
            0.0f,  1f, 0.0f, // top
            -0.5f, 0f, 0.0f, // bottom left
            0.5f, 0f, 0.0f  // bottom right
    };
    // rgba
    private float mColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private Vector3 mPosition = new Vector3(0f,0f,0f);
    private Vector3 mScale = new Vector3(1f,1f,1f);
    private Vector3 mRoateAxis = new Vector3(0f,0f,1f);
    private float mRotateAngle = 0f;
    private final int mProgram;
    private FloatBuffer vertexBuffer = null;

    public Triangle() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        int vertexShader = FireworkUtility.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = FireworkUtility.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();
        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);
        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);
        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    public void setPosition(Vector3 position){
        mPosition = position;
    }
    public void setScale(Vector3 scale){
        mScale = scale;
    }
    public void setColor(float r, float g, float b, float a){
        mColor = new float[]{r,g,b,a};
    }
    public void setRotate(float angle, Vector3 axis){
        this.mRotateAngle = angle;
        this.mRoateAxis = axis;
    }

    public void draw(float[] mvpMatrix) {
        int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        int vertexStride = COORDS_PER_VERTEX * 4;
        GLES20.glUseProgram(mProgram);

        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, mColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        float []resultMatrix = new float[16];
        float []rotationMatrix = new float[16];
        Matrix.setRotateM(rotationMatrix, 0, mRotateAngle, mRoateAxis.getX(), mRoateAxis.getY(), 1f);
        Matrix.multiplyMM(resultMatrix, 0, mvpMatrix, 0, rotationMatrix, 0);
        Matrix.translateM(resultMatrix, 0, mPosition.getX(), mPosition.getY(), mPosition.getZ());
        Matrix.scaleM(resultMatrix, 0, mScale.getX(), mScale.getY(), mScale.getZ());


        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, resultMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
