package ECS;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by lenov on 07/12/2016.
 */

public class GraphicsSystem extends ECSystem {
    public static GraphicsSystem getInstance()
    {
        if (zeCantTouchGraphic == null)
            zeCantTouchGraphic = new GraphicsSystem();
        return zeCantTouchGraphic;
    }
    public Bitmap getImage(String zeName)
    {
        if (whrAllImagesGather.containsKey(zeName))
        {
            return whrAllImagesGather.get(zeName);
        }
        return null;
    }
    public boolean putImage(Bitmap zeImage, String zeName)
    {
        whrAllImagesGather.put(zeName, zeImage);
        return true;
    }

    GraphicsSystem() {
        name_ = "zeGraphicsPlace";
        whrAllImagesGather = new HashMap<String, Bitmap>();
    }
    static GraphicsSystem zeCantTouchGraphic = null;
    HashMap<String, Bitmap> whrAllImagesGather;
}
