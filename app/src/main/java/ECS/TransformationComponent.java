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
    public TransformationComponent(float zePosX, float zePosY, float zeScaleX, float zeScaleY)
    {
        name_ = "Transformation Stuff";
        setPosition(zePosX, zePosY);
        setScale(zeScaleX, zeScaleY);
    }
    public TransformationComponent(TransformationComponent rhs)
    {
        name_ = rhs.name_;
        setPosition(rhs.posX, rhs.posY);
        setScale(rhs.scaleX, rhs.scaleY);
    }
    public void setPosition(float zeX, float zeY)
    {
        posX = zeX;
        posY = zeY;
    }
    public void setScale(float zeX, float zeY)
    {
        scaleX = zeX;
        scaleY = zeY;
    }
    public boolean isPosZero()
    {
        if (posX <= EPSILON && posX >= -EPSILON
                && posY <= EPSILON && posY >= -EPSILON)
            return true;
        return false;
    }
    public boolean isScaleZero()
    {
        if (scaleX <= EPSILON && scaleX >= -EPSILON
                && scaleY <= EPSILON && scaleY >= -EPSILON)
            return true;
        return false;
    }
    public TransformationComponent minusPos(TransformationComponent zeOther)
    {
        TransformationComponent zeNewTrans = new TransformationComponent(this);
        zeNewTrans.posX -= zeOther.posX;
        zeNewTrans.posY -= zeOther.posY;
        return zeNewTrans;
    }
    public void NegatePos()
    {
        posX *= -1;
        posY *= -1;
    }
    public float LengthSquared()
    {
        return (posX * posX) + (posY * posY);
    }
    public TransformationComponent mulitplyPos(float value)
    {
        TransformationComponent zeNewTrans = new TransformationComponent(this);
        zeNewTrans.posX *= value;
        zeNewTrans.posY *= value;
        return zeNewTrans;
    }
    public void plusRef(TransformationComponent rhs)
    {
        posX += rhs.posX;
        posY += rhs.posY;
    }

    static final public float EPSILON = 0.00001f;

    public float posX,posY, scaleX, scaleY;
}
