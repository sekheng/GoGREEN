package com.sidm.mylab2mgp;

/**
 * Created by - on 27/1/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

public class PostToFacebookDialog extends Activity implements OnClickListener{
    private Button btn_back, btn_post, btn_login;
    private boolean createDialog = false, inDialog = false, posted = false;


    boolean loggedin = false;

    private Dialog dialog;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    ProfilePictureView profile_pic;
    List<String> PERMISSIONS = Arrays.asList("publish_actions");

    private Context currContext;
    private Toastbox toastmaker1,toastmaker2;

    public PostToFacebookDialog(Context context)
    {
        //init the facebook sdk
        currContext = context;
        FacebookSdk.sdkInitialize(context.getApplicationContext());

        createDialog = true;
        inDialog = true;
        posted = false;
        //this.message = message;


        dialog = new Dialog(context);
        dialog.setCancelable(false);//disable the player to back if press back button
        dialog.setContentView(R.layout.facebookposting);
        Drawable drawable = context.getDrawable(R.drawable.sandbackground);
        drawable.setAlpha(150);
        dialog.getWindow().setBackgroundDrawable(drawable);

        btn_login = (LoginButton)dialog.findViewById(R.id.fb_login_button);
        btn_login.setOnClickListener(this);

        btn_back = (Button)dialog.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        profile_pic = (ProfilePictureView)dialog.findViewById(R.id.picture);
        callbackManager = CallbackManager.Factory.create();

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    //if user logged out
                    profile_pic.setProfileId("");
                    loggedin = false;
                }
                else
                {
                    profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                    loggedin = true;
                }
            }
        };

        accessTokenTracker.startTracking();

        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this,PERMISSIONS);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                loggedin = true;
                //shareScore();
            }

            @Override
            public void onCancel() {
                System.out.println("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Login attempt failed.");
            }
        });


        toastmaker1 = new Toastbox();
        toastmaker1.toastmessageShort(this, "Posted!");
        toastmaker1.setShowMessageOnce(true);

        toastmaker2 = new Toastbox();
        toastmaker2.toastmessageShort(this, "You have already posted!");
        toastmaker2.setShowMessageOnce(true);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_back)
        {
            createDialog = true;
            inDialog = false;
            posted = false;
            toastmaker1.reset();
            toastmaker2.reset();
            dialog.dismiss();
        }
        else if(view == btn_post && loggedin && !posted)
        {
            toastmaker1.showToast();
            shareScore();
            posted = true;
        }
        else if(view == btn_post && loggedin && posted)
        {
            toastmaker2.showToast();
        }
    }

    public void shareScore()
    {
        Bitmap image = BitmapFactory.decodeResource(currContext.getResources(),R.drawable.gogreenlogo);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Just Finished playing GOGREEN! The score I got was: " + NameAndScoreStorer.getInstance().getCurrNameAndSCore().score)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);
    }

    public void showDialog()
    {
        if(createDialog && inDialog)
        {
            createDialog = false;
            Handler handler = new Handler(Looper.getMainLooper());
            // Returns the application's main looper, which lives in the main thread of the application

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dialog.show();
                }
            }, 100); // 1000  Delay in milliseconds until the runnable is executed
            //dialog.show();
        }
    }
    public void setInDialog(boolean bool)
    {
        inDialog = bool;
    }

    public boolean getIndialog()
    {
        return inDialog;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
