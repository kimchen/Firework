package st.kimsmik.firework;

/**
 * Created by chenk on 2016/2/2.
 */
public class FireworkHeart extends FireworkCircle{

    public FireworkHeart(int size, Vector3 anchor, float strength){
        super(size,anchor,strength);
    }

    @Override
    protected void init() {
        for(int i=0; i <= mSize; i++){
            Partical partical = new Partical();
            partical.setPosition(mAnchor);
            mParticalList.add(partical);

            float x = ((float)i-(float)mSize/2)/((float)mSize)*4f;
            float y = (float)Math.sqrt(1 - (Math.abs(x) - 1) * (Math.abs(x) - 1));
            Vector3 v = new Vector3(x * mStrength,y * mStrength,0f);
            vList.add(v);
        }

        for(int i=0; i <= mSize; i++){
            Partical partical = new Partical();
            partical.setPosition(mAnchor);
            mParticalList.add(partical);

            float x = ((float)i-(float)mSize/2)/((float)mSize)*4f;
            float y = (float)Math.acos( 1 - (Math.abs(x))) - (float)Math.PI;
            Vector3 v = new Vector3(x * mStrength,y * mStrength,0f);
            vList.add(v);
        }
    }
}
