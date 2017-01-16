package com.sidm.mylab2mgp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by - on 16/1/2017.
 */

public class Toastbox {
    CharSequence text;
    int toastTime;
    Toast toast;

    public Toastbox()
    {
        text = "";
        toastTime = Toast.LENGTH_SHORT;
        toast = null;
    }
    public void toastmessageShort(Context context
            , CharSequence wordToPrint)// to be placed in constructor
    {
        text = wordToPrint;
        toastTime = Toast.LENGTH_SHORT;
        toast = Toast.makeText(context, text, toastTime);
    }

    public void toastmessageLong(Context context
            , CharSequence wordToPrint)// to be placed in constructor
    {
        text = wordToPrint;
        toastTime = Toast.LENGTH_LONG;
        toast = Toast.makeText(context, text, toastTime);
    }

    public void showToast()// print the toast, put this when you want the toast
    {
        toast.show();
    }



}
