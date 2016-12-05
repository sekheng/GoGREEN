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

    }
    public boolean setSpaces(short num1, short num2, short num3, short num4, short num5, short num6, short num7)
    {
        setSpaces(num1, num2, num3, num4, num5,num6,num7,(short)-1);
    }
    public boolean setSpaces(short num1, short num2, short num3, short num4, short num5, short num6, short num7, short num8)
    {
        if (spacesToOccupy.isEmpty())
        {
            for (short num = 0; num < 8; ++num)
            {
                
            }
        }
        return false;
    }


    public int scoreGive_;
    LinkedList<Short> spacesToOccupy;
    public LinkedList<Entity> zeGrids;
}
