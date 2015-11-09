package com.ncr.jiris.ncrtask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jiris on 09.11.2015.
 */
public class InvalidListAdapter extends RecyclerView.Adapter<InvalidListAdapter.ViewHolder> {

    ArrayList<String> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }

    }

    public InvalidListAdapter(ArrayList<String> invalidList) {
        mData = invalidList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invalid_textview, parent, false);

        ViewHolder viewHolder = new ViewHolder((TextView) v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}