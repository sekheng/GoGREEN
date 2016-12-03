package ECS;

import android.graphics.Bitmap;

import java.util.LinkedList;

/**
 * Created by lenov on 03/12/2016.
 */

public class BitComponent extends Component {
    public BitComponent()
    {
        name_ = "Ze Images";
        allTheImages = new LinkedList<Bitmap>();
        HistoryOfSprite = new LinkedList<Bitmap>();
    }
    public void setImages(Bitmap zeImage)
    {
        if (HistoryOfSprite.isEmpty())
            HistoryOfSprite.add(zeImage);
        allTheImages.add(zeImage);
    }
   public Bitmap getCurrImage()
    {
        return  HistoryOfSprite.getLast();
    }
    public LinkedList<Bitmap> allTheImages, HistoryOfSprite;
}
