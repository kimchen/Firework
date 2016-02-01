package st.kimsmik.firework;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by chenk on 2016/2/1.
 */
public class FireworkSurfaceView extends GLSurfaceView {
    private FireworkRenderer mRenderer = null;

    public FireworkSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new FireworkRenderer();
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();
        Log.w("Firework","pos:"+x+","+y);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRenderer.addFirework(FireworkUtility.convert3DPostion(x, y));
        }

        return true;
    }
}
