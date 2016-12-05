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
    }
    public void Update(float dt)
    {

    }
    public boolean onNotify(Component zeEvent)
    {
        if (zeEvent.name_ != "zeBox")
        {
            whichBoxPlayerIn = (BoxComponent)(zeEvent.owner_.getComponent("zeBox"));
        }
        else
            whichBoxPlayerIn = (BoxComponent)zeEvent;
        return true;
    }

    protected BoxComponent whichBoxPlayerIn;
    public int score_;
}
