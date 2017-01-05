package ECS;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by lenov on 03/12/2016.
 */

public class BitComponent extends Component {
    public BitComponent()
    {
        name_ = "Ze Images";
        allTheImages = new HashMap<String, Bitmap>();
        HistoryOfSprite = new LinkedList<Bitmap>();
    }
    public BitComponent(Bitmap zeImage)
    {
        name_ = "Ze Images";
        allTheImages = new HashMap<String, Bitmap>();
        HistoryOfSprite = new LinkedList<Bitmap>();
        setImages(zeImage);
    }
    public void setImages(Bitmap zeImage)
    {
        if (HistoryOfSprite.isEmpty())
            HistoryOfSprite.add(zeImage);
        allTheImages.put(Long.toString(num++), zeImage);
    }
    public void setImages(Bitmap zeImage, String zeName)
    {
        if (HistoryOfSprite.isEmpty())
            HistoryOfSprite.add(zeImage);
        allTheImages.put(zeName, zeImage);
    }
   public Bitmap getCurrImage()
    {
        return  HistoryOfSprite.getLast();
    }
    public LinkedList<Bitmap> HistoryOfSprite;
    HashMap<String, Bitmap> allTheImages;
    private static long num = 0;
}
