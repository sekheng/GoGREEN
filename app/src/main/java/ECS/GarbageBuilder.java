package ECS;

import java.util.LinkedList;

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

    public Entity buildSmalleGarbage(String zeName, short[] zeGridCoordinate)
    {
        Entity zeEntity = new Entity(zeName);
        GarbageComponent zeGarbage = new GarbageComponent();
        zeGarbage.zeGrids = allTheBoxes;
        zeGarbage.setSpaces(zeGridCoordinate);
        zeGarbage.onNotify(150.f);
        zeGarbage.onNotify(thePlayer.getComponent("zePlayer"));
        zeEntity.setComponent(zeGarbage);
        Garbage.add(zeEntity);
        activeList.add(zeEntity);
        return zeEntity;
    }
    public void setObjectPools(LinkedList<Entity> zeActive, LinkedList<Entity> zeInactive, LinkedList<Entity> zeGarbage, LinkedList<Entity> zeBoxes, Entity zePlayer)
    {
        activeList = zeActive;
        innactiveList = zeInactive;
        Garbage = zeGarbage;
        allTheBoxes = zeBoxes;
        thePlayer = zePlayer;
    }

    GarbageBuilder()
    {

    }
    static GarbageBuilder zeBuilderNoOneCanAccessed_ = null;
    LinkedList<Entity> activeList, innactiveList, Garbage, allTheBoxes;
    Entity thePlayer;
}
