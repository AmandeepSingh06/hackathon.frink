package com.frink.hackathon.addcardlist;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.frink.hackathon.R;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class BankCardFragment extends Fragment {

    private Spinner bankListView;
    private ListView listView;
    String url = "http://vipulg.housing.com:3002/api/v0/list_card";


    public static BankCardFragment getInstance() {
        BankCardFragment bankCardFragment = new BankCardFragment();
        return bankCardFragment;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle onSaveInstance) {
        View view = inflater.inflate(R.layout.bankcard_layout, parent, false);
        bankListView = (Spinner) view.findViewById(R.id.bank_spinner_bank_name);
        listView = (ListView) view.findViewById(R.id.listview_bank);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        new BankCardLoadAsyncTask(getActivity(), listView, bankListView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }
}
