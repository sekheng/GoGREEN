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

    GridSystem()
    {
        allTheBoxes = new LinkedList<Entity>();
        ScreenWidth = ScreenHeight = 0;
        boundaryOfMap = new TransformationComponent();
        averageBoxSize = new TransformationComponent();
    }
    static GridSystem CantTouchTheGrid = null;
    int ScreenWidth, ScreenHeight;
    public LinkedList<Entity> allTheBoxes;
    TransformationComponent boundaryOfMap, averageBoxSize;
}

