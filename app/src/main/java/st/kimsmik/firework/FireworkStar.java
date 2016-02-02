package st.kimsmik.firework;

/**
 * Created by chenk on 2016/2/2.
 */
public class FireworkStar extends FireworkCircle{

    public FireworkStar(int size, Vector3 anchor, float strength){
        super(size,anchor,strength);
    }

    @Override
    protected void init(){
        for(int i=0; i < mSize; i++){
            Partical partical = new Partical();
            partical.setPosition(mAnchor);
            mParticalList.add(partical);

            double theta = (((double)i/(double)mSize)*2*Math.PI);
            float x = (float)(Math.cos(theta)*Math.cos(theta)*Math.cos(theta));
            float y = (float)(Math.sin(theta)*Math.sin(theta)*Math.sin(theta));
            Vector3 v = new Vector3(x * mStrength,y * mStrength,0f);
            vList.add(v);
        }
    }
}
