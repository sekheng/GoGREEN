package ECS;

/**
 * Created by lenov on 06/01/2017.
 */

public class MusicSystem extends ECSystem {
    public static MusicSystem getInstance() {
        if (cantTouchThis == null)
            cantTouchThis = new MusicSystem();
        return cantTouchThis;
    }

    private static MusicSystem cantTouchThis = null;
    private MusicSystem()
    {

    }
}
