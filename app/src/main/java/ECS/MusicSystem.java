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
        if (loadedSoundIndex_.containsKey(zeName))
        {
            allSound_.play(loadedSoundIndex_.get(zeName), BGM_Volume_, BGM_Volume_, 1,0,1);
            return true;
        }
        return false;
    }
    public boolean addSoundEffect(int zeID, String zeName)
    {
        if (!allTheSoundIndex_.containsKey(zeName))
        {
            allTheSoundIndex_.put(zeName, zeID);
            return true;
        }
        return false;
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
        if (currentBGMPlaying != zeID && currentBGM_ != null)   // This will help check whether is the BGM is playing and the ID matches the BGM that the user wants to play.
        {
            currentBGM_.stop();
            currentBGM_.release();
            currentBGM_ = null;
        }
        if (currentBGM_ == null) {  // If only there is no BGM playing!
            currentBGM_ = MediaPlayer.create(currentContext, allTheBGMIndex_.get(zeID));
            currentBGM_.start();
            currentBGM_.setLooping(true);
            currentBGMPlaying = zeID;
            currentBGM_.setVolume(BGM_Volume_, BGM_Volume_);
        }
        return true;
    }
    public boolean loadSoundEffect(String zeID)
    {
        if (allSound_ == null)
            allSound_ = new SoundPool.Builder().setAudioAttributes(audioAttributes_).setMaxStreams(5).build();
        // checking that the loaded sounds have not loaded it yet and Sound Index has the resource of that sound
        if (!loadedSoundIndex_.containsKey(zeID) && allTheSoundIndex_.containsKey(zeID))
        {
            int zeSoundIndex = allTheSoundIndex_.get(zeID);
            loadedSoundIndex_.put(zeID, allSound_.load(currentContext, zeSoundIndex, 1));
            return true;
        }
        return false;
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
    public boolean pauseCurrentBGM()
    {
        if (currentBGM_ != null)
        {
            currentBGM_.pause();
            return true;
        }
        return false;
    }
    public boolean resumeCurrentBGM()
    {
        if (currentBGM_ != null)
        {
            currentBGM_.start();
            return true;
        }
        return false;
    }
    public boolean stopAllSoundEffect()
    {
        if (loadedSoundIndex_.size() > 0)
        {
            for (int zeNumber : loadedSoundIndex_.values())
            {
                allSound_.unload(zeNumber);
            }
            loadedSoundIndex_.clear();
            allSound_.release();
            allSound_ = null;
            return true;
        }
        return false;
    }
    public void setCurrentContext(Context zeContext)
    {
        currentContext = zeContext;
    }

    // Not sure will it work, ZY pls carry as you always do
    // What this will affect will be sound effects and BGM!
    public void turnOnOffAllSounds(boolean zeOn)    // passing in true means turning on sound, passing in false means turning off sound
    {
        if (zeOn)
        {
            BGM_Volume_ = 1.0f;
        }
        else
        {
            BGM_Volume_ = 0;
        }
        if (currentBGM_ != null)    // Need to check whether the object has been initialized!
            currentBGM_.setVolume(BGM_Volume_, BGM_Volume_);
        isMusicOn = zeOn;
    }

    public boolean getisMusicOn()
    {
        return isMusicOn;
    }

    private static MusicSystem cantTouchThis = null;
    private MusicSystem()
    {
        BGM_Volume_ = 1.0f;
        allTheSoundIndex_ = new HashMap<>();
        audioAttributes_ = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        allSound_ = new SoundPool.Builder().setAudioAttributes(audioAttributes_).setMaxStreams(5).build();
        allTheBGMIndex_ = new HashMap<>();
        currentBGM_ = null;
        currentContext = null;
        loadedSoundIndex_ = new HashMap<>();
        currentBGMPlaying = "";
    }

    private float BGM_Volume_;
    private SoundPool allSound_;
    private AudioAttributes audioAttributes_;
    private MediaPlayer currentBGM_;
    private HashMap<String, Integer> allTheSoundIndex_, allTheBGMIndex_, loadedSoundIndex_;
    private String currentBGMPlaying;   // This is to keep track of what BGM is playing!
    private Context currentContext;
    private boolean isMusicOn;
}
