package ECS;

/**
 * Created by lenov on 02/12/2016.
 */

public class TransformationComponent extends Component {
    public TransformationComponent()
    {
        name_ = "Transformation Stuff";
        posX = 0;
        posY = 0;
        scaleX = 1;
        scaleY = 1;
    }
    public TransformationComponent(short zePosX, short zePosY, short zeScaleX, short zeScaleY)
    {
        name_ = "Transformation Stuff";
        setPosition(zePosX, zePosY);
        setScale(zeScaleX, zeScaleY);
    }
    public void setPosition(short zeX, short zeY)
    {
        posX = zeX;
        posY = zeY;
    }
    public void setScale(short zeX, short zeY)
    {
        scaleX = zeX;
        scaleY = zeY;
    }

    public short posX,posY, scaleX, scaleY;
}
