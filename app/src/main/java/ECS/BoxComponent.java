package ECS;

/**
 * Created by lenov on 05/12/2016.
 */

public class BoxComponent extends Component {
    public BoxComponent()
    {
        name_ = "zeBox";
        whatBox = BoxType.EMPTY;
        garbageOwner = null;
    }
    public void Exit()
    {
        garbageOwner = null;
        whatBox = BoxType.EMPTY;
    }
    public boolean onNotify(String zeEvent)
    {
        if (zeEvent.equalsIgnoreCase("reset"))
        {
            Exit();
            return true;
        }
        else if (zeEvent.equalsIgnoreCase("tellowner") && garbageOwner != null)
        {
            garbageOwner.onNotify("interact");
            return true;
        }
            return false;
    }
    public boolean onNotify(Component zeEvent)
    {
        if (zeEvent.name_ == "zeGarbage")
            garbageOwner = (GarbageComponent)zeEvent;
        else
            garbageOwner = (GarbageComponent)(zeEvent.owner_.getComponent("garbageOwner"));
        whatBox = BoxType.FILL;
        return true;
    }

    public BoxType whatBox;
    public GarbageComponent garbageOwner;
}
