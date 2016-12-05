package ECS;

/**
 * Created by lenov on 03/12/2016.
 */

public class PhysicComponent extends Component {
    public PhysicComponent()
    {
        name_ = "zePhysic";
        speed = 1;
        zePosToGo = new TransformationComponent(0,0,0,0);
    }
    public void Update(float dt)
    {
        if (!zePosToGo.isPosZero())
        {
            TransformationComponent zePos = (TransformationComponent)(owner_.getComponent("Transformation Stuff"));
            if (zePos.minusPos(zePosToGo).LengthSquared() <= 9)
            {
                zePosToGo.SetPosZero();
            }
            else
            {
                TransformationComponent zeDirection = (zePosToGo.minusPos(zePos)).normalizeRef();
                zePos.plusPosRef(zeDirection.multiplyPosRef(dt * speed));
            }
        }
    }
    public void setNextPosToGo(float zeX, float zeY)
    {
        zePosToGo.posX = zeX;
        zePosToGo.posY = zeY;
    }
    public void setNextPosToGo(TransformationComponent zePos)
    {
        zePosToGo.posX = zePos.posX;
        zePosToGo.posY = zePos.posY;
        zePosToGo.owner_ = zePos.owner_;
    }


    public float speed;
    private TransformationComponent zePosToGo;
}
