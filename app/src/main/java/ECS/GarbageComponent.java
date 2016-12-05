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
            for (Short zeShort : zeSpace)
            {
                spacesToOccupy.add(zeShort);
            }
            return true;
        }
        return false;
    }
    public boolean setSpaces(short num1)
    {
        short []arraysShort = {num1};
        return setSpaces(arraysShort);
    }
    public boolean setSpaces(short[] bunchOfStuff)
    {
        if (spacesToOccupy.isEmpty())
        {
            for (short num : bunchOfStuff)
            {
                spacesToOccupy.add(num);
            }
            return true;
        }
        return false;
    }


    public int scoreGive_;
    LinkedList<Short> spacesToOccupy;
    public LinkedList<Entity> zeGrids;
}
