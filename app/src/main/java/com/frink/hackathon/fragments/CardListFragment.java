package com.frink.hackathon.fragments;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;

import com.frink.hackathon.task.JsonTask;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class CardListFragment extends ListFragment {


    static public CardListFragment getInstance() {
        CardListFragment cf = new CardListFragment();
        return cf;
    }

    @Override
    public void onCreate(Bundle onSaveInstance) {
        super.onCreate(onSaveInstance);
        new JsonTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                "http://khandeshb.housing.com:5678/Cards/get_all_available_cards");
    }
}
