package com.frink.hackathon;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.frink.hackathon.fragments.LandingScreenFragment;
import com.frink.hackathon.homescreen.ViewPagerAdapter;
import com.frink.hackathon.models.UserFBData;
import com.frink.hackathon.models.UserFBFriendList;
import com.frink.hackathon.task.GettingFriendListAsyncTask;
import com.frink.hackathon.task.SendingUserNAppDataAsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GettingFriendListAsyncTask.GettingFriendListCallBack, SendingUserNAppDataAsyncTask.OnMessageSent {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private UserFBData fbUser;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button button1, button2, button3;
    private LinearLayout footer;
    // private FrameLayout frameLayoutFragmentContainer;

    public static final String FACEBOOK_PERMISSION_PUBLIC_PROFILE = "public_profile";
    public static final String FACEBOOK_PERMISSION_EMAIL = "email";
    public static final String FACEBOOK_PERMISSION_USER_FRIENDS = "user_friends";
    public static final List<String> PERMISSIONS = Arrays
            .asList(FACEBOOK_PERMISSION_PUBLIC_PROFILE, FACEBOOK_PERMISSION_EMAIL,
                    FACEBOOK_PERMISSION_USER_FRIENDS);
    private FrameLayout frameLayoutFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        footer = (LinearLayout) findViewById(R.id.footer);
        frameLayoutFragmentContainer = (FrameLayout) findViewById(R.id.top_fragment_container);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(PERMISSIONS);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                fetchDataFromFacebook(loginResult);


            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });
    }

    private void fetchDataFromFacebook(final LoginResult loginResult) {
        fbUser = new UserFBData();
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        fbUser.setName(object.optString("name"));
                        fbUser.setEmail(object.optString("email"));
                        fbUser.setId(object.optString("id"));
                        getFacebookFriendList(fbUser.getId(), loginResult.getAccessToken().getToken());

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();


    }

    public void getFacebookFriendList(String id, String accessToken) {
        GettingFriendListAsyncTask task = new GettingFriendListAsyncTask(UserFBFriendList.class, this);
        task.execute(getFacebookFriendListUrl(id, accessToken));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public String getFacebookFriendListUrl(String id, String accessToken) {
        String url = null;
        if (id != null && accessToken != null) {
            url = "https://graph.facebook.com/v2.3/" + id + "/friends?access_token=" + accessToken;
        }
        return url;
    }

    @Override
    public void onSuccess(UserFBFriendList list) {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> applicationLists = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> applicationNames = new ArrayList<>();
        for (int i = 0; i < applicationLists.size(); i++) {
            applicationNames.add(applicationLists.get(i).packageName);
        }
        SendingUserNAppDataAsyncTask task = new SendingUserNAppDataAsyncTask(fbUser.getName(), fbUser.getId(), fbUser.getEmail(), applicationNames, list.getData(),
                this);
        task.execute();

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSendSuccess() {

        viewPager.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);


        getFragmentManager().beginTransaction().add(R.id.top_fragment_container, LandingScreenFragment.getInstance(fbUser.getId())).commit();
    }

    @Override
    public void onSendFailure() {

    }
}

