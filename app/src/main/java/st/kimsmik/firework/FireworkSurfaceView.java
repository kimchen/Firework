package st.kimsmik.firework;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private long touchStartTime = 0;
    private List<Vector3> touchPos = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStartTime = Calendar.getInstance().getTimeInMillis();
                touchPos = new ArrayList<>();
                break;
            case MotionEvent.ACTION_MOVE:
                Vector3 newPos = FireworkUtility.convert3DPostion(x, y);
                if(touchPos.size() > 0){
                    Vector3 lastPos = touchPos.get(touchPos.size() - 1);
                    double distance = Math.sqrt(Math.pow(lastPos.getX() - newPos.getX(), 2) + Math.pow(lastPos.getY() - newPos.getY(), 2));
                    if(distance < 1)
                        break;
                }
                touchPos.add(newPos);
                break;
            case MotionEvent.ACTION_UP:
                long deltaTime = Calendar.getInstance().getTimeInMillis() - touchStartTime;
                int size = (int)deltaTime / 500 + 3;
                size = size>10?10:size;
                if(touchPos.size() < 5){
                    mRenderer.addFirework(size,FireworkUtility.convert3DPostion(x, y));
                }else{
                    mRenderer.addFirework(size,touchPos,deltaTime);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mRenderer.addFirework(3,FireworkUtility.convert3DPostion(x, y));
                break;
        }
        return true;
    }
}
