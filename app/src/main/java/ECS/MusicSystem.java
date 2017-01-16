package ECS;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by lenov on 06/01/2017.
 */

// This is experimenting with music and sound effects and it appears to be a failure. Don't use any of it's function
public class MusicSystem extends ECSystem {
    public static MusicSystem getInstance() {
        if (cantTouchThis == null)
            cantTouchThis = new MusicSystem();
        return cantTouchThis;
    }

    public boolean playSoundEffect(String zeName)
    {
        if (allTheSoundIndex_.containsValue(zeName))
        {
            allSound_.play(allTheSoundIndex_.get(zeName), 1, 1, 1,0,1);
            return true;
        }
        return false;
    }
    public boolean addSoundEffect(int zeID, String zeName)
    {
        if (allTheSoundIndex_.containsKey(zeName))
        {
            
            allTheSoundIndex_.put(zeName, zeID);
            return true;
        }
        return true;
    }
    public boolean addBGM(int zeID, String zeName)
    {
        if (allTheBGMIndex_.containsKey(zeName))
            return false;
        allTheBGMIndex_.put(zeName, zeID);
        return true;
    }
    public boolean playBGM(String zeID)
    {
        if (currentBGM_ != null)
        {
            currentBGM_.stop();
        }
        currentBGM_.start();
        return true;
    }
    public boolean stopCurrentBGM()
    {
        if (currentBGM_ != null)
        {
            currentBGM_.pause();
            currentBGM_.reset();
            currentBGM_ = null;
            return true;
        }
        return false;
    }
    public void setCurrentContext(Context zeContext)
    {
        currentContext = zeContext;
    }

    private static MusicSystem cantTouchThis = null;
    private MusicSystem()
    {
        BGM_Volume_ = 1.0f;
        allTheSoundIndex_ = new HashMap<String, Integer>();
        audioAttributes_ = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        allSound_ = new SoundPool.Builder().setAudioAttributes(audioAttributes_).setMaxStreams(5).build();
        allTheBGMIndex_ = new HashMap<>();
        currentBGM_ = null;
        currentContext = null;
    }

    public float BGM_Volume_;
    private SoundPool allSound_;
    private AudioAttributes audioAttributes_;
    private MediaPlayer currentBGM_;
    private HashMap<String, Integer> allTheSoundIndex_, allTheBGMIndex_;
    private Context currentContext;
}
