package com.sidm.mylab2mgp;

/**
 * Created by - on 27/1/2017.
 */

import android.content.Context;
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

public class PostToFacebookDialog {
    private Button btn_back, btn_post;
    private String messgae;

    boolean loggedin = false;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    ProfilePictureView profile_pic;
    List<String> PERMISSIONS = Arrays.asList("publish_actions");

    public void PostToFacebookDialog(Context context, String message)
    {

    }
}
