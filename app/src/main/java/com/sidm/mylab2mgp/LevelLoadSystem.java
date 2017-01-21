package com.sidm.mylab2mgp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import ECS.*;

/**
 * Created by lenov on 21/01/2017.
 */

public class LevelLoadSystem extends ECSystem {
    public static LevelLoadSystem getInstance()
    {
        if (cantTouchThis == null)
            cantTouchThis = new LevelLoadSystem();
        return cantTouchThis;
    }
    public String getValue(String zeTitle, String zeSection)
    {
        // The basic idea here is simper
        // There is a key with Mulitple sections!
        // Each Section will have a value regardless of what!
        if (TitleSectionAndValue.containsKey(zeTitle))
        {
            HashMap<String, String> TheSectionAndValue = TitleSectionAndValue.get(zeTitle);
            if (TheSectionAndValue.containsKey(zeSection))
            {
                return TheSectionAndValue.get(zeSection);   // Finally we will get the value
            }
        }
        return null;
    }

    private LevelLoadSystem() {
        name_ = "LevelLoading";
        TitleSectionAndValue = new HashMap<>();
        try {
            InputStream inputStream = CantTouchThisContext.openFileInput("highscores.txt");
            if (inputStream != null)    // then begin file loading!
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String readingOfLine;
                while ((readingOfLine = bufferedReader.readLine()) != null) // Reading the file line by line
                {

                }
            }
        }
        catch(FileNotFoundException fnf)
        {

        }
        catch (IOException io) {

        }
    }
    static public void setContext(Context zeContext)
    {
        CantTouchThisContext = zeContext;
    }
    private static LevelLoadSystem cantTouchThis = null;
    private HashMap<String, HashMap<String, String>> TitleSectionAndValue;
    private static Context CantTouchThisContext = null;
}
