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
        if (row > 0 && col > 0 && spacesToOccupy.isEmpty() && zeGrids != null)
        {
            for (byte numOfRow = 0; numOfRow < row; ++numOfRow)
            {
                for (byte numOfCol = 0; numOfCol < col; ++numOfCol)
                {
                    int TheExactIndex = (numOfRow * GridSystem.getInstance().getNumOfBoxesPerCol()) + numOfCol + num1;
                    spacesToOccupy.add((short)TheExactIndex);
                    zeGrids.get(TheExactIndex).getComponent("zeBox").onNotify(this);
                }
            }
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
    public boolean setRowCol(byte zeRow, byte zeCol)
    {
        if (zeRow > 0 && zeCol > 0)
        {
            row = zeRow;
            col = zeCol;
            return true;
        }
        return false;
    }

    public float scoreGive_;
    LinkedList<Short> spacesToOccupy;
    public LinkedList<Entity> zeGrids;
    PlayerComponent zePlayer;
    byte row, col;
}
