package com.twitter.humblebrag.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.twitter.humblebrag.R;
import com.twitter.humblebrag.volley.response.ReTweet;
import com.twitter.humblebrag.volley.logic.VolleyLogic;

/**
 * Created by vipulsomani on 1/28/14.
 */
public class TweetListAdapter extends ArrayAdapter<ReTweet> {

    public TweetListAdapter(Context context){
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_volley_layout, null);
            holder = new ViewHolder();
            holder.imageView = (NetworkImageView) convertView.findViewById(R.id.image_volley);
            holder.nameView = (TextView) convertView.findViewById(R.id.user_volley);
            holder.textView = (TextView) convertView.findViewById(R.id.tweet_volley);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReTweet currentItem = getItem(position);
        if(currentItem != null){
            holder.imageView.setImageUrl(currentItem.getImageUrlMedium(), VolleyLogic.mImageLoader);
            holder.nameView.setText("@" + currentItem.getScreen_name());
            holder.textView.setText(currentItem.getTweet());
        }else{
            holder.nameView.setText("Loading..");
            holder.textView.setText("");
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }
        return convertView;
    }

    private static class ViewHolder {
        private NetworkImageView imageView;
        private TextView nameView;
        private TextView textView;
    }
}
