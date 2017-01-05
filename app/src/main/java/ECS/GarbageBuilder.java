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
        zeEntity.setComponent(new TransformationComponent());
        GarbageComponent zeGarbage = new GarbageComponent();
        zeEntity.setComponent(zeGarbage);
        zeEntity.setComponent(new BitComponent(GraphicsSystem.getInstance().getImage("RottenApple")));  // setting the images
        zeGarbage.setRowCol((byte)2, (byte)1);
        zeGarbage.zeGrids = allTheBoxes;
        //zeGarbage.setSpaces(zeGridCoordinate);
        zeGarbage.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
        zeGarbage.onNotify(150.f);
        zeGarbage.onNotify(thePlayer.getComponent("zePlayer"));
        Garbage.add(zeEntity);
        activeList.add(zeEntity);
        return zeEntity;
    }
    public Entity buildGarbageBin(String zeName, short []zeGridCoordinate)
    {
        Entity zeEntity = new Entity("Garbage Bin");
        zeEntity.setComponent(new TransformationComponent());
        GarbageCollectorComponent zeCollector = new GarbageCollectorComponent();
        zeEntity.setComponent(zeCollector);
        zeEntity.setComponent(new BitComponent(GraphicsSystem.getInstance().getImage("RecycleBin")));
        int row = 1, col = 1;
        zeCollector.setRowCol((byte)row, (byte)col);
        zeCollector.zeGrids = GridSystem.getInstance().allTheBoxes;
        zeCollector.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
        zeCollector.onNotify(thePlayer.getComponent("zePlayer"));
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
