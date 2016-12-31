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
        if (zeGridCoordinate.length == 0 || zeGridCoordinate.length > 2)
            return null;
        short row = 0, col = 0;
        row = zeGridCoordinate[0];
        col = zeGridCoordinate[1];
        Entity zeEntity = new Entity(zeName);
        GarbageComponent zeGarbage = new GarbageComponent();
        zeGarbage.setRowCol((byte)1, (byte)2);
        zeGarbage.zeGrids = allTheBoxes;
        //zeGarbage.setSpaces(zeGridCoordinate);
        zeGarbage.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
        zeGarbage.onNotify(150.f);
        zeGarbage.onNotify(thePlayer.getComponent("zePlayer"));
        zeEntity.setComponent(zeGarbage);
        Garbage.add(zeEntity);
        activeList.add(zeEntity);
        return zeEntity;
    }
    public Entity buildGarbageBin(String zeName, short []zeGridCoordinate)
    {
        Entity zeEntity = new Entity("Garbage Bin");
        GarbageCollectorComponent zeCollector = new GarbageCollectorComponent();
        int row = 2, col = 2;
        zeCollector.setRowCol((byte)row, (byte)col);
        zeCollector.zeGrids = GridSystem.getInstance().allTheBoxes;
        zeCollector.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
        zeCollector.onNotify(thePlayer.getComponent("zePlayer"));
        zeEntity.setComponent(zeCollector);
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
