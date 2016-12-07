package ECS;

/**
 * Created by lenov on 07/12/2016.
 */

public class PlayerSprite extends Component {
    public PlayerSprite()
    {
        name_ = "zePlayerSprite";
        zePlayerStuff = null;
        allTheSprite = null;
    }
    public void Update(float dt)
    {
        if (zePlayerStuff != null && allTheSprite != null)
        {

        }
    }

    public boolean onNotify(Component zeEvent)
    {
        if (zeEvent.name_ == "zePlayer")
        {
            zePlayerStuff = (PlayerComponent)(zeEvent);
            return true;
        }
        else if (zeEvent.name_ == "Ze Images")
        {
            allTheSprite = (BitComponent)zeEvent;
            return true;
        }
        return false;
    }

    PlayerComponent zePlayerStuff;
    BitComponent allTheSprite;
}
