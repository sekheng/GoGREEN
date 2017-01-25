package ECS;

import com.sidm.mylab2mgp.GamePanelSurfaceView;

import java.util.LinkedList;

/**
 * Created by lenov on 05/12/2016.
 */

public class PlayerComponent extends Component {
    public PlayerComponent()
    {
        name_ = "zePlayer";
        whichBoxPlayerIn = null;
        score_ = 0;
        startUpdating = false;
        maxCapacity = 2;
        carryGarbageType = new LinkedList<>();
        reachedGarbageBin = false;
        WhatGarbageBin = "";
    }
    public void Update(float dt)
    {
        if (whichBoxPlayerIn != null && startUpdating)
        {
            switch (whichBoxPlayerIn.whatBox)
            {
                case EMPTY:
                    break;
                default:
                    whichBoxPlayerIn.onNotify("tellowner");
            }
            whichBoxPlayerIn = null;
            startUpdating = false;
        }
    }
    public boolean onNotify(Component zeEvent)
    {
        if (zeEvent.name_ != "zeBox")
            whichBoxPlayerIn = (BoxComponent)(zeEvent.owner_.getComponent("zeBox"));
        else
            whichBoxPlayerIn = (BoxComponent)zeEvent;
        startUpdating = false;
        reachedGarbageBin = false;
        return true;
    }
    public boolean onNotify(String zeEvent)
    {
        if (zeEvent.equalsIgnoreCase("reached"))
        {
            startUpdating = true;
            return true;
        }
        else if (zeEvent.contains("emptytrash"))    // Checking whether is it meant to empty Trash
        {
            reachedGarbageBin = true;
            // Trying to get type of bin from "emptytrash|TYPE"
            int binFirstOr = zeEvent.indexOf('|');
            WhatGarbageBin = zeEvent.substring(binFirstOr+1);
//            String typeOfBinStr = zeEvent.substring(binFirstOr+1);
//            LinkedList<Integer> whichArrayToRemove = new LinkedList<>();    // Need to remove the elements in carryGarbageType
//            for (int num = carryGarbageType.size()-1; num >= 0; --num)  // Iterating from back to front!
//            {
//                int firstOR = carryGarbageType.get(num).indexOf('|');   // Get '|' from "TYPE|SCORE"
//                String typeGarbageStr = carryGarbageType.get(num).substring(0, firstOR);
//                if (typeGarbageStr.equalsIgnoreCase(typeOfBinStr))
//                {
//                    // Get the score and add the score
//                    String garbageScoreStr = carryGarbageType.get(num).substring(firstOR+1);
//                    score_ += Float.parseFloat(garbageScoreStr);
//                    whichArrayToRemove.add(num);
//                }
//            }
//            for (int zeIndexRemoved : whichArrayToRemove)
//                carryGarbageType.remove(zeIndexRemoved);
            return true;
        }
        else if (zeEvent.contains("garbage") && carryGarbageType.size() < maxCapacity)   // Checking whether is it a garbage
        {
            // trying to get the "garbage|TYPE|SCORE"
            //                           ^
            int firstOR = zeEvent.indexOf('|');
            carryGarbageType.add(zeEvent.substring(firstOR+1));
            if (theCurrentGamePlayerOn != null)
            {
                theCurrentGamePlayerOn.onNotify("GarbagePicked");
            }
            return true;
        }
        else if (zeEvent.contains("ShakedTooMuch") && reachedGarbageBin) // When the player shake it forward and backward, then it will empty the trash
        {
            LinkedList<Integer> whichArrayToRemove = new LinkedList<>();    // Need to remove the elements in carryGarbageType
            for (int num = carryGarbageType.size()-1; num >= 0; --num)  // Iterating from back to front!
            {
                int firstOR = carryGarbageType.get(num).indexOf('|');   // Get '|' from "TYPE|SCORE"
                String typeGarbageStr = carryGarbageType.get(num).substring(0, firstOR);
                if (typeGarbageStr.equalsIgnoreCase(WhatGarbageBin))
                {
                    // Get the score and add the score
                    String garbageScoreStr = carryGarbageType.get(num).substring(firstOR+1);
                    score_ += Float.parseFloat(garbageScoreStr);
                    whichArrayToRemove.add(num);
                }
            }
            for (int zeIndexRemoved : whichArrayToRemove)
                carryGarbageType.remove(zeIndexRemoved);
            return true;
        }
        return false;
    }
    public boolean onNotify(int zeEvent)
    {
        if (zeEvent > 0) {
            maxCapacity = (short)zeEvent;
            return true;
        }
        return false;
    }
    public short MaxCapacity()
    {
        return maxCapacity;
    }

    public float getScore_()
    {
        return score_;
    }

    public boolean getReachedGarbageBin()
    {
        return reachedGarbageBin;
    }



    protected BoxComponent whichBoxPlayerIn;
    protected boolean startUpdating, reachedGarbageBin;
    protected String WhatGarbageBin;
    public float score_;
    public GamePanelSurfaceView theCurrentGamePlayerOn = null;
    private short maxCapacity;    // This is for checking how much space the player has left
    public LinkedList<String> carryGarbageType;   // What type of garbage inside
}
