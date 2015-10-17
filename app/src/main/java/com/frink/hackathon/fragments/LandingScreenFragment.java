package com.frink.hackathon.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.frink.hackathon.FragmentConstant;
import com.frink.hackathon.R;
import com.frink.hackathon.fragmentCallBack;
import com.frink.hackathon.task.UserFirstTimeLogin;

/**
 * Created by amandeepsingh on 17/10/15.
 */
public class LandingScreenFragment extends Fragment implements View.OnClickListener, UserFirstTimeLogin.Callback {
    private String id;
    private Button button1, button2;
    private ProgressDialog progressDialogue;

    static public LandingScreenFragment getInstance(String id) {
        LandingScreenFragment cf = new LandingScreenFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View baseView = inflater
                .inflate(R.layout.landing_screen_layout, container, false);
        if (getArguments() != null) {
            id = getArguments().getSerializable("id").toString();
        }
        button1 = (Button) baseView.findViewById(R.id.button1);
        button2 = (Button) baseView.findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return baseView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                progressDialogue = new ProgressDialog(getActivity());
                progressDialogue.setTitle("Please Wait");
                progressDialogue.setMessage("Data getting Loaded");
                progressDialogue.show();
                new UserFirstTimeLogin(this, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                break;
            case R.id.button2:
                ((fragmentCallBack) getActivity()).transactionCallBack(FragmentConstant.CARD_LIST_FRAGMENT, id);
                //getFragmentManager().beginTransaction().replace(R.id.top_fragment_container, CardListFragment.getInstance(id)).addToBackStack("CardListFragment").commit();

                break;
        }
    }

    @Override
    public void onSuccess(boolean result) {
        progressDialogue.dismiss();
        if (result) {
            ((fragmentCallBack) getActivity()).transactionCallBack(FragmentConstant.FRIENDLIST_FRAGMENT, id);
            //  getFragmentManager().beginTransaction().replace(R.id.top_fragment_container, FriendListFragment.getInstance(id)).addToBackStack("FriendListFragment").commit();
        } else {
            ((fragmentCallBack) getActivity()).transactionCallBack(FragmentConstant.BANK_CARD_FRAGMENT, id);
            // getFragmentManager().beginTransaction().replace(R.id.top_fragment_container, BankCardFragment.getInstance(id)).addToBackStack("BankCardFragment").commit();
        }
    }
}
