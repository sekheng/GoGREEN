package ECS;

import java.util.HashMap;

/**
 * Created by lenov on 02/12/2016.
 */

public class Entity implements BaseInterface {
    public Entity()
    {
        name_ = "";
        allTheComponents = new HashMap<String, Component>();
    }
    public Entity(String zeName)
    {
        name_ = new String(zeName);
        allTheComponents = new HashMap<String, Component>();
    }
    public void Update(long dt)
    {
        for (Component zeComponent : allTheComponents.values())
        {
            zeComponent.Update(dt);
        }
    }
    public void Update(float dt)
    {
        for (Component zeComponent : allTheComponents.values())
        {
            zeComponent.Update(dt);
        }

    }
    public void Exit()
    {
        allTheComponents.clear();
    }
    public Component getComponent(String zeName)
    {
        return allTheComponents.get(zeName);
    }
    public boolean setComponent(Component zeComp)
    {
        if (!allTheComponents.containsKey(zeComp.name_))
        {
            allTheComponents.put(zeComp.name_, zeComp);
            zeComp.owner_ = this;
            return true;
        }
        return false;
    }

    String name_;
    private HashMap<String, Component> allTheComponents;
}
