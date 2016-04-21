package com.vesti.fonis.fonisvesti.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.R;
import com.vesti.fonis.fonisvesti.utils.Util;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<OnePieceOfNews> mOriginalData;
    private List<OnePieceOfNews> mFilteredData;

    public ListViewAdapter(Context context, List<OnePieceOfNews> newsList) {
        mContext = context;
        mOriginalData = newsList;
        mFilteredData = newsList;
    }

    public int getCount() {
        return mFilteredData.size();
    }

    public Object getItem(int position) {
        return mFilteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // TODO - add viewholder
        if (getItemViewType(position) != 0) {
            if (convertView == null) {

                convertView = li.inflate( R.layout.list_item, null);
            }
        } else {
            if (convertView == null) {
                convertView = li.inflate( R.layout.first_list_item, null);
            }
        }
        TextView tvData = (TextView) convertView.findViewById(R.id.tvData);
        tvData.setText(mFilteredData.get(position).getTitle());
        ImageView imNewsImage=(ImageView) convertView.findViewById(R.id.imNewsImage);
        imNewsImage.setImageBitmap(mFilteredData.get(position).getImage());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                if (constraint.toString().isEmpty()){
                    Log.d(Util.TAG,"Filtered string is empty.");
                    results.values = mOriginalData;
                    results.count = mOriginalData.size();
                }

                // removes all extra space between words
                String filterString = constraint.toString().toLowerCase().replaceAll("\\s+", " ");

                // Search logic
                final List<OnePieceOfNews> list = new ArrayList<>();
                for (int i = 0; i < mOriginalData.size(); i++) {
                    if (mOriginalData.get(i).isSubstring(filterString))
                        list.add(mOriginalData.get(i));
                }

                results.values = list;
                results.count = list.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredData = (List<OnePieceOfNews>) results.values;
                notifyDataSetChanged();
            }
        };


        return filter;
    }
}