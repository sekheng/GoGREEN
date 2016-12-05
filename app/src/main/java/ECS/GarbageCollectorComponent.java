package ECS;

/**
 * Created by lenov on 05/12/2016.
 */

public class GarbageCollectorComponent extends GarbageComponent {
    public GarbageCollectorComponent()
    {
        name_ = "zeGarbageBin";
    }
    public boolean onNotify(String zeEvent)
    {

        return false;
    }
    public boolean onNotify(int zeEvent)
    {
        if (zeEvent > 0)
            scoreGive_ = zeEvent;
        return false;
    }
}
