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

    public BoxType whatBox;
    public GarbageComponent garbageOwner;
}
