package com.sidm.mylab2mgp;

import android.content.Context;
import android.content.res.AssetManager;

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
            AssetManager myAssetsMGR = CantTouchThisContext.getAssets();
            InputStream inputStream = myAssetsMGR.open("leveldesign.txt");
            if (inputStream != null)    // then begin file loading!
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String readingOfLine;
                int NumOfCharKey = 0;   // This just means to find '['
                HashMap<String, String> CurrentKey = null;
                while ((readingOfLine = bufferedReader.readLine()) != null) // Reading the file line by line
                {
                    if (readingOfLine.equalsIgnoreCase("\n") || readingOfLine.equalsIgnoreCase("\r"))   // Need to ignore the extra lines
                        continue;
                    NumOfCharKey = readingOfLine.indexOf('[');
                    if (NumOfCharKey != 0)  // This means that it is not a key!
                    {
                        // We will extract the section followed by value from "SECTION="VALUES""
                        int theFirstEqualSign = readingOfLine.indexOf('=');
                        String zeSection = readingOfLine.substring(0, theFirstEqualSign);
                        int lastDoubleQuote = readingOfLine.lastIndexOf('"');
                        String zeValue = readingOfLine.substring(theFirstEqualSign+2, lastDoubleQuote); // plus 2 here so we can go straight to the VALUE
                        CurrentKey.put(zeSection, zeValue);
                    }
                    else    // It is a key!
                    {
                        // We need to extract the Key from "[KEY]"
                        int LastOfBracket = readingOfLine.lastIndexOf(']');
                        String zeKey = readingOfLine.substring(1, LastOfBracket);
                        CurrentKey = new HashMap<>();
                        TitleSectionAndValue.put(zeKey, CurrentKey); // Initializes the Key and a new hashmap for it
                    }
                }
            }
        }
        catch(FileNotFoundException fnf)
        {
            assert(1 ==0);
        }
        catch (IOException io) {
            assert(1 ==0);
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
