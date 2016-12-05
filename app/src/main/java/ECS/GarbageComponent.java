package ECS;

import java.util.LinkedList;

/**
 * Created by lenov on 05/12/2016.
 */

public class GarbageComponent extends Component {
    public GarbageComponent()
    {
        name_ = "zeGarbage";
        scoreGive_ = 1;
        spacesToOccupy = new LinkedList<Short>();
        zeGrids = null;
    }
    public boolean setSpaces(LinkedList<Short> zeSpace)
    {
        if (spacesToOccupy.isEmpty()) {
            for (Short zeShort : zeSpace) {

            }
            return true;
        }
        return false;
    }


    public int scoreGive_;
    LinkedList<Short> spacesToOccupy;
    public LinkedList<Entity> zeGrids;
}
