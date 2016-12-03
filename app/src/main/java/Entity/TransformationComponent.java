package Entity;

/**
 * Created by lenov on 02/12/2016.
 */

public class TransformationComponent extends Component {
    TransformationComponent()
    {
        name_ = "Transformation Stuff";
        posX = 0;
        posY = 0;
        scaleX = 1;
        scaleY = 1;
    }
    TransformationComponent(short zePosX, short zePosY, short zeScaleX, short zeScaleY)
    {
        name_ = "Transformation Stuff";
        setPosition(zePosX, zePosY);
        setScale(zeScaleX, zeScaleY);
    }
    void setPosition(short zeX, short zeY)
    {
        posX = zeX;
        posY = zeY;
    }
    void setScale(short zeX, short zeY)
    {
        scaleX = zeX;
        scaleY = zeY;
    }

    public short posX,posY, scaleX, scaleY;
}
