package ECS;

import java.util.LinkedList;

/**
 * Created by lenov on 30/12/2016.
 */

public class GridSystem extends ECSystem {
    public static GridSystem getInstance()
    {
        if (CantTouchTheGrid == null)
            CantTouchTheGrid = new GridSystem();
        return CantTouchTheGrid;
    }


    public void setScreenWidthHeight(int zeWidth, int zeHeight)
    {
        ScreenWidth = zeWidth;
        ScreenHeight = zeHeight;
        float zeNewTotHeight = ScreenHeight/10;
        boundaryOfMap = new TransformationComponent(0, zeNewTotHeight, GridSystem.getInstance().getScreenWidth(), GridSystem.getInstance().getScreenHeight() - (zeNewTotHeight * 2.f));
        averageBoxSize.setScale(boundaryOfMap.scaleX / numOfBoxesPerRow, boundaryOfMap.scaleY / numOfBoxesPerRow);
        for (int numRow = 0; numRow < numOfBoxesPerRow; ++numRow)
        {
            for (int numCol = 0; numCol < numOfBoxesPerCol; ++numCol)
            {
                Entity boxEntity = new Entity("Box");
                TransformationComponent boxTransform = new TransformationComponent(
                        boundaryOfMap.posX + (numCol * (boundaryOfMap.scaleX / numOfBoxesPerCol)),
                        boundaryOfMap.posY + (numRow * (boundaryOfMap.scaleY / numOfBoxesPerRow)),
                        boundaryOfMap.posX + ((numCol+1) * (boundaryOfMap.scaleX / numOfBoxesPerCol)),
                        (boundaryOfMap.posY) + ((numRow+1) * (boundaryOfMap.scaleY / numOfBoxesPerRow)));
                boxEntity.setComponent(boxTransform);
                boxEntity.setComponent(new BoxComponent());
                allTheBoxes.add(boxEntity);
            }
        }
    }
    public void Exit()
    {
        for (Entity zeBox : allTheBoxes)
                zeBox.getComponent("zeBox").Exit();
    }
    public TransformationComponent getBoundary()
    {
        return boundaryOfMap;
    }
    public TransformationComponent getAverageBoxSize()
    {
        return averageBoxSize;
    }
    public int getScreenWidth()
    {
        return ScreenWidth;
    }
    public int getScreenHeight()
    {
        return ScreenHeight;
    }
    public int getNumOfBoxesPerRow()
    {
        return numOfBoxesPerRow;
    }
    public int getNumOfBoxesPerCol()
    {
        return numOfBoxesPerCol;
    }

    GridSystem()
    {
        allTheBoxes = new LinkedList<Entity>();
        ScreenWidth = ScreenHeight = 0;
        boundaryOfMap = null;
        averageBoxSize = new TransformationComponent();
        numOfBoxesPerRow = numOfBoxesPerCol = 8;
    }
    static GridSystem CantTouchTheGrid = null;
    int ScreenWidth, ScreenHeight, numOfBoxesPerRow, numOfBoxesPerCol;
    public LinkedList<Entity> allTheBoxes;
    TransformationComponent boundaryOfMap, averageBoxSize;
}

