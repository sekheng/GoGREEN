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
        zePlayer = null;
        row = col = 0;
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
    public boolean onNotify(String zeEvent)
    {
        if (zeEvent.equalsIgnoreCase("interact"))
        {
            if (zePlayer.onNotify(scoreGive_)) {
                owner_.turnOnFlag_ = 0;
                for (Short zeShort : spacesToOccupy) {
                    zeGrids.get(zeShort.intValue()).getComponent("zeBox").onNotify("reset");
                }
            }
        }
        return false;
    }
    public boolean onNotify(Component zeEvent)
    {
        if (zeEvent.name_ == "zePlayer")
        {
            zePlayer = (PlayerComponent)(zeEvent);
            return true;
        }
        return false;
    }
    public boolean onNotify(float zeEvent)
    {
        if (zeEvent > TransformationComponent.EPSILON)
        {
            scoreGive_ = zeEvent;
            return true;
        }
        return false;
    }

    public float scoreGive_;
    LinkedList<Short> spacesToOccupy;
    public LinkedList<Entity> zeGrids;
    PlayerComponent zePlayer;
    short row, col;
}
