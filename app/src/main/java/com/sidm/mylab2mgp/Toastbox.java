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
        showMessageOnce = false;
        toggleMessageShown = false;
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
        // ShowMessageOnce is to check whether the message will be shown continuously whenever this function is called.
        // toggleMessageShown is to make sure that if showMessageOnce is true, it will ensure that the message is shown once and no more
        // till being reset function is called
        if (!showMessageOnce || !toggleMessageShown) {
            toast.show();
            toggleMessageShown = true;
        }
    }
    public boolean getToggleMessageShown()
    {
        return toggleMessageShown;
    }
    public void setShowMessageOnce(boolean zeLogic)
    {
        showMessageOnce = zeLogic;
    }
    public void reset()
    {
        toggleMessageShown = false;
    }
    private boolean showMessageOnce, toggleMessageShown;


}
