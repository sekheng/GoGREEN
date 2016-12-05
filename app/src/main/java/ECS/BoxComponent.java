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
    public boolean onNotify(String zeEvent)
    {
        if (zeEvent.equalsIgnoreCase("reset"))
        {
            garbageOwner = null;
            whatBox = BoxType.EMPTY;
            return true;
        }
        return false;
    }

    public BoxType whatBox;
    public GarbageComponent garbageOwner;
}
