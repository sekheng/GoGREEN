package com.sidm.mylab2mgp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by - on 19/1/2017.
 */

public class EditFileForNameScore {

    StringBuilder str;



    public EditFileForNameScore(Context context)
    {
        try {
            InputStream inputStream = context.openFileInput("highscores");
            if(inputStream == null) {
                FileOutputStream outputStreamWriter = context.openFileOutput("highscores", context.MODE_PRIVATE);//create file if dont have
                outputStreamWriter.close();



            }
            else
                inputStream.close();

        }
            catch (IOException io) {

            }

    }

    //since i dont think we will keep updating the file with the score, im gonna
    public void UpdateTextFile(String name, int score, Context context)
    {
       try {
           FileOutputStream outputStreamWriter = (context.openFileOutput("highscores",context.MODE_APPEND));//open the fill to read, doesnt overwrite
           str = new StringBuilder(name);//store the name then append then write to file
           str.append("-" + score+"\n");
           outputStreamWriter.write(str.toString().getBytes());
           NameAndScoreStorer.getInstance().setCurrNameAndScore(new NameAndScore(name,score));
           outputStreamWriter.close();
       }
       catch (IOException io) {

       }




    }
    public void UpdateListOfNameAndScore(Context context)
    {
        try {
           InputStream inputStream = context.openFileInput("highscores");
            if(inputStream != null)
            {
                //inputStream.reset();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String temp = "";
                int numberOfNames = 0;
                NameAndScoreStorer.getInstance().clearList();
                while((temp = bufferedReader.readLine()) != null)
                {
                    String[] stringSplit = temp.split("-");
                    int tempscore = Integer.parseInt(stringSplit[1]);
                    NameAndScoreStorer.getInstance().getList().add(new NameAndScore(stringSplit[0], tempscore));
                    numberOfNames += 1;
                }
                inputStream.close();
                NameAndScoreStorer.getInstance().setNumberOfScores(numberOfNames);
            }
        }
        catch(FileNotFoundException fnf)
        {

        }
        catch (IOException io) {

        }
    }

    public void refreshText(Context context)
    {
        try {
            FileOutputStream outputStreamWriter = context.openFileOutput("highscores", context.MODE_PRIVATE);//create file if dont have
            outputStreamWriter.close();
        }
        catch (IOException io) {

        }
    }
}
