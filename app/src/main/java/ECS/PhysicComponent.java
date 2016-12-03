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
            TransformationComponent zeDirection = zePos.minusPos(zePosToGo);
            zePos.plusPosRef(zeDirection.multiplyPosRef(dt * speed));
            if (zePos.minusPos(zePosToGo).LengthSquared() <= 4)
            {
                zePosToGo.SetPosZero();
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
    }


    public float speed;
    private TransformationComponent zePosToGo;
}
