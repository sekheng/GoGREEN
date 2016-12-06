package ECS;

import java.util.LinkedList;

/**
 * Created by lenov on 05/12/2016.
 */

public class GarbageCollectorComponent extends GarbageComponent {
    public GarbageCollectorComponent()
    {
        name_ = "zeGarbage";
        spacesToOccupy = new LinkedList<Short>();
        zeGrids = null;
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
    public boolean setSpaces(short[] bunchOfStuff)
    {
        if (spacesToOccupy.isEmpty() && zeGrids != null)
        {
            for (short num : bunchOfStuff)
            {
                spacesToOccupy.add(num);
                zeGrids.get(num).getComponent("zeBox").onNotify(this);
                zeGrids.get(num).getComponent("zeBox").onNotify(BoxType.BIN.Value_);
            }
            return true;
        }
        return false;
    }

}
