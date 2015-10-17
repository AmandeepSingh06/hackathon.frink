package com.frink.hackathon.addcardlist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.frink.hackathon.R;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by shashwatsinha on 17/10/15.
 */
public class PostCardServer extends AsyncTask<Void, Void, String> {


    private String userid;
    private String cardId;
    private String base = "http://vipulg.housing.com:3002/";
    private String post = "api/v0/post_card";
    private OnMessageSent callback;
    private Context context;

    public PostCardServer(Context context, String userid, String cardId) {
        this.context = context;
        this.userid = userid;
        this.cardId = cardId;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            SendMessageJsonBuilder jsonBuilder = new SendMessageJsonBuilder();
            jsonBuilder.fb_id = userid;
            jsonBuilder.id = cardId;


            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(base + post);
            Gson gson = new Gson();
            String json = gson.toJson(jsonBuilder);
            StringEntity se = new StringEntity(json.toString());
            httpost.setEntity(se);
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(httpost);
            return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String httpStatusCode) {
        Toast.makeText(context, R.string.message, Toast.LENGTH_LONG).show();
    }


    public interface OnMessageSent {

        void onSendSuccess();

        void onSendFailure();
    }

    private class SendMessageJsonBuilder {

        String fb_id, id;
    }
}
