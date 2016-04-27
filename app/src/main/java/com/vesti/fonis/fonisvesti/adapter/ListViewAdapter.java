package com.vesti.fonis.fonisvesti.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
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

        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (getItemViewType(position) != 0) {
            if (convertView == null) {

                convertView = li.inflate(R.layout.list_item, null);
            }
        } else {
            if (convertView == null) {
                convertView = li.inflate(R.layout.first_list_item, null);
            }
        }
        TextView tvData = (TextView) convertView.findViewById(R.id.tvData);
        tvData.setText(mData.get(position).getTitle());
        ImageView imNewsImage = (ImageView) convertView.findViewById(R.id.imNewsImage);
        imNewsImage.setImageBitmap(mData.get(position).getThumbnail());

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < 16) {

            if (position != 0 && position % 2 != 0) {
                imNewsImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.text_color_white));
                tvData.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                imNewsImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.colorAccent));
                tvData.setTextColor(mContext.getResources().getColor(R.color.text_color_white));
            }

        } else {

            if (position != 0 && position % 2 != 0) {
                imNewsImage.setBackground(mContext.getResources().getDrawable(R.color.text_color_white));
                tvData.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                imNewsImage.setBackground(mContext.getResources().getDrawable(R.color.colorAccent));
                tvData.setTextColor(mContext.getResources().getColor(R.color.text_color_white));
            }
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
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
//                if (constraint.toString().isEmpty()){
//                    Log.d(Util.TAG,"Filtered string is empty.");
//                    results.values = mData;
//                    results.count = mData.size();
//                }

                // removes all extra space between words
                //  String filterString = constraint.toString().toLowerCase().replaceAll("\\s+", " ");

                // Search logic
//                final List<OnePieceOfNews> list = new ArrayList<>();
//                for (int i = 0; i < mData.size(); i++) {
//                    if (mData.get(i).hasSubstring(filterString))
//                        list.add(mData.get(i));
//                }
                List<OnePieceOfNews> list = News.searchNews(constraint.toString());

                results.values = list;
                results.count = list.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (List<OnePieceOfNews>) results.values;
                notifyDataSetChanged();
            }
        };


        return filter;
    }
}