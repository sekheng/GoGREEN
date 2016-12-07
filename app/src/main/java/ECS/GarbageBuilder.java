package ECS;

/**
 * Created by lenov on 07/12/2016.
 */

public class GarbageBuilder {
    public static GarbageBuilder getInstance()
    {
        if (zeBuilderNoOneCanAccessed_ == null)
            zeBuilderNoOneCanAccessed_ = new GarbageBuilder();
        return zeBuilderNoOneCanAccessed_;
    }

    public Entity buildSmalleGarbage(String zeName)
    {
        Entity zeEntity = new Entity(zeName);

        return zeEntity;
    }

    GarbageBuilder()
    {

    }
    static GarbageBuilder zeBuilderNoOneCanAccessed_ = null;
}
