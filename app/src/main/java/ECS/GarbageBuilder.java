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

    public Entity buildSmalleGarbage(String zeName, Short[] zeGridCoordinate, float timeToActivate)
    {
        if (zeGridCoordinate.length == 0 || zeGridCoordinate.length > 2)
            return null;
        short row = 0, col = 0, sizeOfGarbageRow = 2, sizeOfGarbageCol = 1;
        row = zeGridCoordinate[0];
        col = zeGridCoordinate[1];
        Entity zeEntity = new Entity(zeName);
        zeEntity.setComponent(new TransformationComponent());
        GarbageComponent zeGarbage = new GarbageComponent();
        zeEntity.setComponent(zeGarbage);
        zeEntity.setComponent(new BitComponent(GraphicsSystem.getInstance().getImage("RottenApple")));  // setting the images
        zeGarbage.setRowCol((byte)sizeOfGarbageRow, (byte)sizeOfGarbageCol);
        zeGarbage.zeGrids = allTheBoxes;
        zeGarbage.onNotify(1);
        //zeGarbage.setSpaces(zeGridCoordinate);
        // Checking through the grids whether you can put the garbage in the grids or not, else put in the inactiveList
        if (timeToActivate < TransformationComponent.EPSILON && CheckingThroughEmptyBoxes(row, col, sizeOfGarbageRow, sizeOfGarbageCol))
        {
            zeGarbage.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
            activeList.add(zeEntity);
        }
        else
        {
            if (timeToActivate <= TransformationComponent.EPSILON)  // Need to make sure that the timeToActivate isn't 0. If it is, set the time to spawn to be 1
                zeGarbage.timeToSpawn = 1;
            else
                zeGarbage.timeToSpawn = timeToActivate;
            zeEntity.turnOnFlag_ = 0;// Need to turn off the active flag of the entity
            innactiveList.add(zeEntity);
        }
        zeGarbage.onNotify(150.f);
        zeGarbage.onNotify(thePlayer.getComponent("zePlayer"));
        Garbage.add(zeEntity);
        return zeEntity;
    }
    public Entity buildPaperBin(String zeName, Short []zeGridCoordinate)
    {
        Entity zeEntity = new Entity(zeName);
        zeEntity.setComponent(new TransformationComponent());
        GarbageCollectorComponent zeCollector = new GarbageCollectorComponent();
        zeEntity.setComponent(zeCollector);
        zeEntity.setComponent(new BitComponent(GraphicsSystem.getInstance().getImage("PaperBin")));
        int row = zeGridCoordinate[0], col = zeGridCoordinate[1];
        zeCollector.setRowCol((byte)1, (byte)1);
        zeCollector.zeGrids = GridSystem.getInstance().allTheBoxes;
        if (CheckingThroughEmptyBoxes(row, col, zeCollector))
            zeCollector.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
        zeCollector.onNotify(thePlayer.getComponent("zePlayer"));
        zeCollector.onNotify(0);
        activeList.add(zeEntity);
        return zeEntity;
    }
    public Entity buildGeneralBin(String zeName, Short []zeGridCoordinate)
    {
        Entity zeEntity = new Entity(zeName);
        zeEntity.setComponent(new TransformationComponent());
        GarbageCollectorComponent zeCollector = new GarbageCollectorComponent();
        zeEntity.setComponent(zeCollector);
        zeEntity.setComponent(new BitComponent(GraphicsSystem.getInstance().getImage("GeneralBin")));
        int row = zeGridCoordinate[0], col = zeGridCoordinate[1];
        zeCollector.setRowCol((byte)1, (byte)1);
        zeCollector.zeGrids = GridSystem.getInstance().allTheBoxes;
        if (CheckingThroughEmptyBoxes(row, col, zeCollector))
            zeCollector.setSpaces((short)(col + row*GridSystem.getInstance().getNumOfBoxesPerCol()));
        zeCollector.onNotify(thePlayer.getComponent("zePlayer"));
        zeCollector.onNotify(1);
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

    public boolean CheckingThroughEmptyBoxes(int specificRow, int specificCol, int RowToOccupy, int ColToOccupy)
    {
        // This will help to check whether it has exceed the total number of row and coloumns to begin with by getting the maximum number of row and coloumn that it will occupy
        // Let say, specificRow is 5 and RowToOccupy is just 1. Then it will just be occupying the 5th row only
        if ((specificRow + RowToOccupy) >= GridSystem.getInstance().getNumOfBoxesPerRow() && (specificCol + ColToOccupy) >= GridSystem.getInstance().getNumOfBoxesPerCol())
            return false;
        // This will help to get the starting index starting from top left. For example, specificCol is 5 and specificRow is 5 and Total number of boxes per coloumn is 8. The starting index will be 5 + (5 * 8) = 45
        // Do remember that it always start from 0!
        int startingIndex = specificCol + (specificRow * GridSystem.getInstance().getNumOfBoxesPerCol());
        // Here comes the complex nested loop
        for (int zeRow = 0; zeRow < RowToOccupy; ++zeRow)   // Get the number of Row that it will occupy
        {
            for (int zeCol = 0; zeCol < ColToOccupy; ++zeCol)   // Get the number of Coloumn that it will occupy
            {
                int addingUpOfIndex = zeCol + (zeRow * GridSystem.getInstance().getNumOfBoxesPerCol()); // Same logic as getting the starting index
                if (!GridSystem.getInstance().checkGridAvailable(startingIndex + addingUpOfIndex))  // Getting the specific index and checking whether grid is available
                    return false;
            }
        }
        return true;
    }
    // This function will make use of GarbageComponent row and coloumn!
    public boolean CheckingThroughEmptyBoxes(int specificRow, int specificCol, GarbageComponent zeGarbage)
    {
        return CheckingThroughEmptyBoxes(specificRow, specificCol, zeGarbage.row, zeGarbage.col);
    }

    GarbageBuilder()
    {

    }
    static GarbageBuilder zeBuilderNoOneCanAccessed_ = null;
    LinkedList<Entity> activeList, innactiveList, Garbage, allTheBoxes;
    Entity thePlayer;
}
