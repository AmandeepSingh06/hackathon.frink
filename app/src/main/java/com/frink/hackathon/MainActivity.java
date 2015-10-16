package com.frink.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.frink.hackathon.models.UserFBData;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public static final String FACEBOOK_PERMISSION_PUBLIC_PROFILE = "public_profile";
    public static final String FACEBOOK_PERMISSION_EMAIL = "email";
    public static final String FACEBOOK_PERMISSION_USER_FRIENDS = "user_friends";
    public static final String FACEBOOK_PERMISSION_USER_BIRTHDAY = "user_birthday";
    public static final String FACEBOOK_PERMISSION_USER_EDUCATION_HISTORY
            = "user_education_history";
    public static final String FACEBOOK_PERMISSION_USER_WORK_HISTORY = "user_work_history";
    public static final List<String> PERMISSIONS = Arrays
            .asList(FACEBOOK_PERMISSION_PUBLIC_PROFILE, FACEBOOK_PERMISSION_EMAIL,
                    FACEBOOK_PERMISSION_USER_FRIENDS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
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
        final UserFBData fbUser = new UserFBData();
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
        //facebook getting friend list start

        //facebook getting friend list end
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getFacebookFriendListUrl(String id, String accessToken) {
        String url = null;
        if (id != null && accessToken != null) {
            url = "https://graph.facebook.com/v2.3/" + id + "/friends?access_token=" + accessToken;
        }
        return url;
    }
}

