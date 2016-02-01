package st.kimsmik.firework;

import java.util.ArrayList;
import java.util.List;

import st.kimsmik.firework.shape.Triangle;

/**
 * Created by Kim on 2016/2/2.
 */
public class Partical extends Triangle {
    private static final int TAIL_NUM = 5;
    private List<Triangle> tailList = new ArrayList<>();

    @Override
    public void setPosition(Vector3 position) {
//        synchronized (tailList) {
//            if (tailList.size() < TAIL_NUM) {
//                Triangle triangle = new Triangle();
//                triangle.setPosition(getPosition());
//                tailList.add(triangle);
//            } else {
//                tailList.remove(0);
//                Triangle triangle = new Triangle();
//                triangle.setPosition(getPosition());
//                tailList.add(triangle);
//            }
//        }
        super.setPosition(position);
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.draw(mvpMatrix);
        synchronized (tailList) {
            for (Triangle triangle : tailList) {
                triangle.draw(mvpMatrix);
            }
        }
    }
}
