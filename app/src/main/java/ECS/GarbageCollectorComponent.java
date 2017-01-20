package ECS;

import java.util.LinkedList;

/**
 * Created by lenov on 05/12/2016.
 */

public class GarbageCollectorComponent extends GarbageComponent {
    public GarbageCollectorComponent()
    {
        name_ = "zeGarbageBin";
        spacesToOccupy = new LinkedList<Short>();
        zeGrids = null;
        typeOfGarbage = 0;
    }
    public boolean onNotify(String zeEvent)
    {
        if (zeEvent.equalsIgnoreCase("interact"))
        {
            zePlayer.onNotify("emptytrash|"+typeOfGarbage);
            return true;
        }
        return false;
    }
    public boolean setSpaces(short[] bunchOfStuff)
    {
        if (spacesToOccupy.isEmpty() && zeGrids != null)
        {
            for (short num : bunchOfStuff)
            {
                spacesToOccupy.add(num);
                zeGrids.get(num).getComponent("zeBox").onNotify(this);
            }
            return true;
        }
        return false;
    }
}
