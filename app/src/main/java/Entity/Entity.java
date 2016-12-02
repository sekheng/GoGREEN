package Entity;

/**
 * Created by lenov on 02/12/2016.
 */

public class Entity implements BaseInterface {
    Entity()
    {
        name_ = "";
    }
    Entity(String zeName)
    {
        name_ = new String(zeName);
    }
    public void Update(long dt)
    {

    }
    public void Exit() {

    }
    String name_;
}
