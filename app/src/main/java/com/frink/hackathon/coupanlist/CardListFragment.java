package com.frink.hackathon.coupanlist;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;

import com.frink.hackathon.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class CardListFragment extends ListFragment {

    public LinkedList<CardListModel> items = new LinkedList<CardListModel>();
    CardListAdapater cl;
    CardListModel cm = new CardListModel("placeholder", null);

    static public CardListFragment getInstance() {
        CardListFragment cf = new CardListFragment();
        return cf;
    }

    @Override
    public void onCreate(Bundle onSaveInstance) {
        super.onCreate(onSaveInstance);

        cl = new CardListAdapater(getActivity(), R.layout.card_list_row, items);
        JsonTask task = new JsonTask(cl);
        int i = 0;
        task.execute("http://vipulg.housing.com:3002/Cards/get_all_available_cards");
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);
        setListAdapter(cl);
    }

    /**
     * Created by shashwatsinha on 16/10/15.
     */
    public static class JsonTask extends AsyncTask<String, Void, CardListModel.CardListModelList> {

        CardListAdapater cardListAdapater;


        public JsonTask(CardListAdapater cl) {
            cardListAdapater = cl;
        }


        @Override
        protected CardListModel.CardListModelList doInBackground(String... parmas) {

            CardListModel.CardListModelList cl = null;
            InputStream is;
            try {
                URL url = new URL(parmas[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                is = conn.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String str;
                final Gson gson = new Gson();
                cl = gson.fromJson(reader, CardListModel.CardListModelList.class);
                return cl;

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return cl;
        }

        @Override
        public void onPostExecute(CardListModel.CardListModelList cl) {
            LinkedList<CardListModel> cards = cl.getCards();
            Iterator<CardListModel> ie = cards.iterator();
            while (ie.hasNext()) {
                CardListModel cm = ie.next();
                cardListAdapater.add(cm);
                cardListAdapater.notifyDataSetChanged();
            }

        }

    }
}
