package com.frink.hackathon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.frink.hackathon.R;

/**
 * Created by amandeepsingh on 17/10/15.
 */
public class LandingScreenFragment extends Fragment implements View.OnClickListener {
    private String id;
    private Button button1, button2;

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

                getFragmentManager().beginTransaction().replace(R.id.top_fragment_container, FriendListFragment.getInstance(id)).commit();
                break;
            case R.id.button2:
                getFragmentManager().beginTransaction().replace(R.id.top_fragment_container, FriendListFragment.getInstance(id)).commit();
                break;
        }
    }
}