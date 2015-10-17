package com.frink.hackathon.homescreen;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frink.hackathon.R;

/**
 * Created by amandeepsingh on 16/10/15.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    public static int totalPage = 3;

    public ViewPagerAdapter(Context context) {

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View customView = inflater
                .inflate(R.layout.view_pager_layout, container, false);
        int imageId;
        if (position == 0) {
            imageId = R.color.alto;
            //imageId = R.drawable.com_facebook_button_background;
        } else if (position == 1) {
            imageId = R.color.alabaster;
            //imageId = R.drawable.com_facebook_button_send_icon;
        } else {
            imageId = R.color.back_green;
            //imageId = R.drawable.com_facebook_button_icon;
        }
        //((ImageView) customView.findViewById(R.id.image)).setImageDrawable(context.getResources().getDrawable(imageId));
        Holder holder = new Holder(customView);
        holder.image.setBackgroundColor(context.getResources().getColor(imageId));
        container.addView(customView);
        return customView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    @Override
    public int getCount() {
        return totalPage;
    }

    class Holder {
        private ImageView image;

        public Holder(View customView) {
            this.image = (ImageView) customView.findViewById(R.id.image);
        }
    }

}



























