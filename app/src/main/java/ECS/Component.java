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
    public String name_;
    public Entity owner_;
}
