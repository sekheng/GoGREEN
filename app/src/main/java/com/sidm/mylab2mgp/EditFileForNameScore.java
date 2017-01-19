package com.sidm.mylab2mgp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

/**
 * Created by - on 19/1/2017.
 */

public class EditFileForNameScore {

    Context context;
    File file;
    FileWriter saveFile;
    public EditFileForNameScore(Context context)
    {
        this.context = context;
        //ClassLoader classLoader = this.getClass().getClassLoader();

        String x = context.getResources().getResourcePackageName(R.raw.scores);
        try {
           // OutputStream gg = new OutputStreamWriter((context.getAssets().open("scores.txt")))
            file = new File((context.getAssets().open("scores.txt")).toString());
            if (!file.exists()) {
                file.createNewFile();//qw lied to me about tis
            }

        }
        catch (IOException io) {
            int i = 0;
        }
    }

    //since i dont think we will keep updating the file with the score, im gonna
    public void UpdateTextFile(String name, int score)
    {

       try {

           saveFile = new FileWriter(file.getAbsoluteFile());
           saveFile.write(name + "\n");
           saveFile.write(score + "\n");

           saveFile.close();
       }
       catch (IOException io) {
           int fml = 0;
       }




    }
    public void getTextInfo()
    {
        try {
            String x,y;//works
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("scores.txt")));
            x = reader.readLine();
            y = reader.readLine();
            reader.close();
        }
        catch (IOException io) {
            int saded = 0;
        }
    }
}
