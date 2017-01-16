package ECS;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.sidm.mylab2mgp.R;

import java.util.LinkedList;

/**
 * Created by - on 8/12/2016.
 */

public class ProtaganistAnimComponent extends Component {

    LinkedList<AnimationComponent> animList = new LinkedList<AnimationComponent>();
    private AnimationComponent currAnimation;
    public ProtaganistAnimComponent ()
    {
        name_ = "zeProtagAnimations";

        /*AnimationComponent anim1 = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.protagonist),
            Screenwidth/4, Screenheight/17,true),448,64,1.f,8,2,3);*/
    }

    public ProtaganistAnimComponent (Resources res)
    {
        name_ = "zeProtagAnimations";
        //front
        AnimationComponent zeAnimation = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.protagonist),
                GridSystem.getInstance().getScreenWidth()/4, GridSystem.getInstance().getScreenHeight()/17,true),448,64,0.5f,8,2,3);
        //back
        AnimationComponent zeAnimation1 = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.protagonist),
                GridSystem.getInstance().getScreenWidth()/4, GridSystem.getInstance().getScreenHeight()/17,true),448,64,0.5f,8,0,1);
        //left
        AnimationComponent zeAnimation2 = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.protagonist),
                GridSystem.getInstance().getScreenWidth()/4, GridSystem.getInstance().getScreenHeight()/17,true),448,64,0.5f,8,6,7);
        //right
        AnimationComponent zeAnimation3 = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.protagonist),
                GridSystem.getInstance().getScreenWidth()/4, GridSystem.getInstance().getScreenHeight()/17,true),448,64,0.5f,8,4,5);
        animList.add(zeAnimation);
        animList.add(zeAnimation1);
        animList.add(zeAnimation2);
        animList.add(zeAnimation3);
        currAnimation = zeAnimation;
    }

    public void setAnimList(AnimationComponent anim)
    {
        animList.add(anim);
    }


    public AnimationComponent getAnimComponent()
    {
        return currAnimation;
    }

    public void draw(Canvas canvas)
    {
        currAnimation.draw(canvas);
    }

    public void setPosX(int i)
    {
        currAnimation.setX(i);
    }

    public void setPosY(int i) {
        currAnimation.setY(i);
    }

    public void Update(float dt)
    {

        for(int i = 0; i < animList.size(); ++i)
        {
            animList.get(i).setX(currAnimation.getX());
            animList.get(i).setY(currAnimation.getY());
        }
        Entity thePlayer = this.owner_;
        PhysicComponent transform = (PhysicComponent)thePlayer.getComponent("zePhysic");
        if(transform.getCurrDirection().posX < 0.f)
        {
            currAnimation = animList.get(2);
        }
        else if(transform.getCurrDirection().posX > 0.f)
        {
            currAnimation = animList.get(3);
        }
        currAnimation.Update(dt);
        /*if(transform.getCurrDirection().posY == 0.f)
        {
            currAnimation = animList.get(0);
        }
        else if(transform.getCurrDirection().posY < 0.f)
        {
            currAnimation = animList.get(1);
        }
        else if(transform.getCurrDirection().posY > 0.f)
        {
            currAnimation = animList.get(0);
        }*/

    }
}
