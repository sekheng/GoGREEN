package ECS;

/**
 * Created by lenov on 03/12/2016.
 */

public class PhysicComponent extends Component {
    public PhysicComponent()
    {
        name_ = "zePhysic";
        speed = 0;
        zePosToGo = new TransformationComponent(0,0,0,0);
    }
    public void Update(float dt)
    {
        if (!zePosToGo.isPosZero())
        {
            TransformationComponent zePos = (TransformationComponent)(owner_.getComponent("Transformation Stuff"));

        }
    }
    public void setNextPosToGo(float zeX, float zeY)
    {
        zePosToGo.posX = zeX;
        zePosToGo.posY = zeY;
    }


    public float speed;
    TransformationComponent zePosToGo;
}
