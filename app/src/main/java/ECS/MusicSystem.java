package ECS;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by lenov on 06/01/2017.
 */

public class MusicSystem extends ECSystem {
    public static MusicSystem getInstance() {
        if (cantTouchThis == null)
            cantTouchThis = new MusicSystem();
        return cantTouchThis;
    }

    public void setBGMVolume(float zeNum)
    {
        BGM_Volume_ = zeNum;
    }
    public MediaPlayer getBGM(String zeName)
    {
        return allTheBGM_.get(zeName);
    }
    public boolean playSoundEffect(String zeName)
    {
        if (allTheSoundIndex_.containsValue(zeName))
        {
            return true;
        }
        return false;
    }

    private static MusicSystem cantTouchThis = null;
    private MusicSystem()
    {
        BGM_Volume_ = 1.0f;
        allTheBGM_ = new HashMap<String, MediaPlayer>();
        allTheSoundIndex_ = new HashMap<String, Integer>();
    }

    private float BGM_Volume_;
    private SoundPool allSound_;
    private AudioAttributes audioAttributes_;
    private HashMap<String, MediaPlayer> allTheBGM_;
    private HashMap<String, Integer> allTheSoundIndex_;
}
