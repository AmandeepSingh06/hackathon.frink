package com.frink.hackathon.coupanlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frink.hackathon.R;

import java.util.List;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class CardListAdapater extends ArrayAdapter<CardListModel.CardModel> {

    List<CardListModel.CardModel> list;

    public CardListAdapater(Context context, int resource) {
        super(context, resource);
    }

    public CardListAdapater(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CardListAdapater(Context context, int resource, CardListModel.CardModel[] objects) {
        super(context, resource, objects);

    }

    public CardListAdapater(Context context, int resource, int textViewResourceId, CardListModel.CardModel[] objects) {
        super(context, resource, textViewResourceId, objects);

    }

    public CardListAdapater(Context context, int resource, List<CardListModel.CardModel> objects) {
        super(context, resource, objects);
        list = objects;

    }

    public CardListAdapater(Context context, int resource, int textViewResourceId, List<CardListModel.CardModel> objects) {
        super(context, resource, textViewResourceId, objects);
        list = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (View) layoutInflater.inflate(R.layout.card_list_row, parent, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView textView = (TextView) view.findViewById(R.id.coupan);
        textView.setText(list.get(position).getTitle() + "\n" + list.get(position).getExpiry() + "\n" + list.get(position).getCoupon_company());
        return view;

    }
}
