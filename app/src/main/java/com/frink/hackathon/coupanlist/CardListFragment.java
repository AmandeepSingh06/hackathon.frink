package com.frink.hackathon.coupanlist;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.frink.hackathon.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class CardListFragment extends ListFragment {

    public LinkedList<CardListModel.CardModel> items = new LinkedList<CardListModel.CardModel>();
    CardListAdapater cl;
    CardListModel.CardModel cm = new CardListModel.CardModel(null, null, null, null);
    String userId;

    static public CardListFragment getInstance(String id) {
        CardListFragment cf = new CardListFragment();
        Bundle b = new Bundle();
        b.putString("id", id);
        cf.setArguments(b);
        return cf;
    }

    @Override
    public void onCreate(Bundle onSaveInstance) {
        super.onCreate(onSaveInstance);

        cl = new CardListAdapater(getActivity(), R.layout.card_list_row, items);
        JsonTask task = new JsonTask(cl);
        int i = 0;
        if (getArguments() != null) {
            userId = getArguments().getSerializable("id").toString();
        }
        String userid = "http://khandeshb.housing.com:5678/api/v0/get_coupons_of_friends?fb_id=";
        userid += userId;
        //userid += "912790462141979";
        Log.d("shashwat", "userid is " + userid);
        task.execute(userid);

    }

    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);
        setListAdapter(cl);
    }

    /**
     * Created by shashwatsinha on 16/10/15.
     */
    public static class JsonTask extends AsyncTask<String, Void, CardListModel> {

        CardListAdapater cardListAdapater;


        public JsonTask(CardListAdapater cl) {
            cardListAdapater = cl;
        }


        @Override
        protected CardListModel doInBackground(String... parmas) {


            CardListModel cl = null;
            InputStream is;
            try {
                URL url = new URL(parmas[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                is = conn.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String str;
                final Gson gson = new Gson();
                cl = gson.fromJson(reader, CardListModel.class);
                return cl;

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return cl;
        }

        @Override
        public void onPostExecute(CardListModel cl) {

            ArrayList<CardListModel.CardModel> cards = cl.getCoupans();
            Iterator<CardListModel.CardModel> ie = cards.iterator();

            while (ie.hasNext()) {
                CardListModel.CardModel cm = ie.next();

                cardListAdapater.add(cm);
            }
            cardListAdapater.notifyDataSetChanged();

        }

    }
}
