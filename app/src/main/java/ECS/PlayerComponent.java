package ECS;

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
        limitOfGarbage = 200;
        startUpdating = false;
    }
    public void Update(float dt)
    {
        if (whichBoxPlayerIn != null && startUpdating)
        {
            switch (whichBoxPlayerIn.whatBox)
            {
                case FILL:

                    break;
                default:
                    break;
            }
            whichBoxPlayerIn = null;
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
        return false;
    }

    protected BoxComponent whichBoxPlayerIn;
    protected boolean startUpdating;
    public int score_;
    public float amountOfGarbageCollected, limitOfGarbage;
}
