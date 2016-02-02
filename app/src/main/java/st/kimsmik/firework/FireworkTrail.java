package st.kimsmik.firework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenk on 2016/2/2.
 */
public class FireworkTrail {

    private List<List<Partical>> mParticalList = new ArrayList<>();
    private int mLength = 5;
    private Vector3 mColor = new Vector3();

    public FireworkTrail(int len){
        mLength = len;
    }
    public void setPosition(Vector3 position){
        synchronized (mParticalList) {
            Vector3 lastPosition = null;
            for(int i=0; i<mLength; i++){
                Vector3 newPosition = lastPosition;
                if(newPosition == null){
                    newPosition = position;
                }

                if(mParticalList.size() <= i){
                    int particalNum = (mLength - i) * 3;
                    List<Partical> particals = new ArrayList<>();
                    for(int j=0; j<particalNum; j++){
                        Partical partical = new Partical();
                        partical.setPosition(newPosition);
                        partical.Translate(new Vector3(i>0?(float)(Math.random()-0.5f):0f,i>0?(float)(Math.random()-0.5f):0f,0f));
                        partical.setColor(mColor.getX(), mColor.getY(), mColor.getZ(), 1f);
                        particals.add(partical);
                    }
                    mParticalList.add(particals);
                    break;
                }else{
                    List<Partical> particals = mParticalList.get(i);
                    lastPosition = particals.get(0).getPosition();
                    for(Partical partical : particals){
                        partical.setPosition(newPosition);
                        partical.Translate(new Vector3(i>0?(float)(Math.random()-0.5f):0f,i>0?(float)(Math.random()-0.5f):0f,0f));
                    }
                }
            }
        }
    }

    public void setColor(Vector3 color){
        mColor = color;
    }

    public void release(){
        mParticalList.clear();
    }

    public void draw(float[] mvpMatrix) {
        synchronized (mParticalList) {
            for(List<Partical> particals : mParticalList){
                for (Partical partical : particals) {
                    partical.draw(mvpMatrix);
                }
            }
        }
    }
}
