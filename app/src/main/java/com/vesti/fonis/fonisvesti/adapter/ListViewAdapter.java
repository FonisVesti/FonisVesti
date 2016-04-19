package com.vesti.fonis.fonisvesti.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.R;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<OnePieceOfNews> mData;

    public ListViewAdapter(Context context, List<OnePieceOfNews> newsList) {
        mContext = context;
        mData = newsList;
    }

    public int getCount() {
        return mData.size();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(position != 0) {
            if (convertView == null)
                convertView = parent.inflate(mContext, R.layout.list_item, null);

            TextView tvData = (TextView) convertView.findViewById(R.id.tvData); // title

            tvData.setText((position + 1) + ". " + mData.get(position).getTitle());
        }else{
            if (convertView == null)
                convertView = parent.inflate(mContext, R.layout.list_item, null);

            TextView tvData = (TextView) convertView.findViewById(R.id.tvData); // title

            tvData.setText((position + 1) + ". " + mData.get(position).getTitle());
        }

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
        return null;
    }
}