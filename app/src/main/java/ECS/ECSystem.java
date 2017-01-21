package ECS;

/**
 * Created by lenov on 07/12/2016.
 */

public class ECSystem implements BaseInterface {
    protected ECSystem()
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
    String getName()
    {
        return name_;
    }
    protected String name_;
}
