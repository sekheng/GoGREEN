package ECS;

import com.sidm.mylab2mgp.GamePanelSurfaceView;

/**
 * Created by lenov on 05/12/2016.
 */

public class PlayerComponent extends Component {
    public PlayerComponent()
    {
        name_ = "zePlayer";
        whichBoxPlayerIn = null;
        score_ = 0;
        amountOfGarbageCollected = 0;
        startUpdating = false;
        currCapacity = 0;
        maxCapacity = 1;
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
        return true;
    }
    public boolean onNotify(String zeEvent)
    {
        if (zeEvent.equalsIgnoreCase("reached"))
        {
            startUpdating = true;
            return true;
        }
        else if (zeEvent.equalsIgnoreCase("emptytrash"))
        {
            score_ += amountOfGarbageCollected;
            amountOfGarbageCollected = 0;
            currCapacity = 0;
            return true;
        }
        return false;
    }
    public boolean onNotify(float zeEvent)
    {
        if (zeEvent > TransformationComponent.EPSILON && currCapacity + 1 <= maxCapacity)
        {
            amountOfGarbageCollected += zeEvent;
            currCapacity += 1;  // lets put 1 because ain't no time to complex stuff
            if (theCurrentGamePlayerOn != null)
            {
                theCurrentGamePlayerOn.onNotify("GarbagePicked");
            }
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
    // This is for checking on how much capacity of garbage is the player carrying
    public float gettingThePercentageOfFullCapacity()
    {
        return (float)(currCapacity/maxCapacity);
    }

    protected BoxComponent whichBoxPlayerIn;
    protected boolean startUpdating;
    public float amountOfGarbageCollected, score_;
    public GamePanelSurfaceView theCurrentGamePlayerOn = null;
    private short currCapacity, maxCapacity;    // This is for checking how much space the player has left
}
