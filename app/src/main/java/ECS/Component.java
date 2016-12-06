package ECS;

/**
 * Created by 150629Z on 12/2/2016.
 */

public class Component implements BaseInterface {
    Component()
    {
        name_ = "";
    }
    public void Update(long dt)
    {

    }
    public void Update(float dt)
    {

    }
    public void Exit()
    {

    }
    public boolean onNotify(Component zeEvent)
    {
        return false;
    }
    public boolean onNotify(float zeEvent)
    {
        return false;
    }
    public boolean onNotify(String zeEvent)
    {
        return false;
    }
    public boolean onNotify(int zeEvent)
    {
        return false;
    }

    public String name_;
    public Entity owner_;
}
