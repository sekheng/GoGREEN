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
        timeToSpawn = 0;
        typeOfGarbage = 0;
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
        boolean settingPosition = true;
        if (row > 0 && col > 0 && spacesToOccupy.isEmpty() && zeGrids != null)
        {
            for (byte numOfRow = 0; numOfRow < row; ++numOfRow)
            {
                for (byte numOfCol = 0; numOfCol < col; ++numOfCol)
                {
                    int TheExactIndex = (numOfRow * GridSystem.getInstance().getNumOfBoxesPerCol()) + numOfCol + num1;
                    spacesToOccupy.add((short)TheExactIndex);
                    zeGrids.get(TheExactIndex).getComponent("zeBox").onNotify(this);
                    if (settingPosition)
                    {
                        settingPosition = !settingPosition;
                        TransformationComponent zePos = (TransformationComponent)owner_.getComponent("Transformation Stuff");
                        TransformationComponent zeBoxTransform = (TransformationComponent)zeGrids.get(TheExactIndex).getComponent("Transformation Stuff");
                        zePos.setPosition(zeBoxTransform);
                    }
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
            String zeValue = "garbage|" + typeOfGarbage + "|" + scoreGive_; // Sending "garbage|TYPE|SCORE"
            if (zePlayer.onNotify(zeValue)) {
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
        if (zeEvent > TransformationComponent.EPSILON)  // The 1st part will be if zeEvent is more than 0, then it will become the score
        {
            scoreGive_ = zeEvent;
            return true;
        }
        else if (zeEvent < -TransformationComponent.EPSILON) // This part check if zeEvent is less than 0, then it will be the time for it to spawn
        {
            timeToSpawn = -zeEvent;
            return true;
        }
        return false;
    }
    public boolean onNotify(int zeEvent)
    {
        if (zeEvent >= 0)   // This will help set what is the type of garbage
        {
            typeOfGarbage = (byte)zeEvent;
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
    public void setTypeOfGarbage(byte zeValue)
    {
        typeOfGarbage = zeValue;
    }

    public byte getTypeOfGarbage()
    {
        return typeOfGarbage;
    }

    public float scoreGive_, timeToSpawn;
    LinkedList<Short> spacesToOccupy;
    public LinkedList<Entity> zeGrids;
    PlayerComponent zePlayer;
    byte row, col;
    protected byte typeOfGarbage;
}
