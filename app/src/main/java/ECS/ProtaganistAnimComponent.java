package ECS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sidm.mylab2mgp.R;

import java.util.LinkedList;

/**
 * Created by - on 8/12/2016.
 */

public class ProtaganistAnimComponent extends Component {

    LinkedList<Component> animList = new LinkedList<Component>();
    public ProtaganistAnimComponent ()
    {
        name_ = "zeProtagAnimations";
        /*AnimationComponent anim1 = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist),
            Screenwidth/4, Screenheight/17,true),448,64,1.f,8,2,3);*/
    }

    public void setAnimList(Component anim)
    {
        animList.add(anim);
    }


    public void Update(float dt)
    {

    }
}
