package com.vesti.fonis.fonisvesti.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.R;
import com.vesti.fonis.fonisvesti.utils.Util;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<OnePieceOfNews> mData;
    private ImageLoader mImageLoader;

    public ListViewAdapter(Context context, List<OnePieceOfNews> newsList) {
        mContext = context;
        mData = newsList;

        mImageLoader = Util.getImageLoader(context);
    }



    public int getOnePieceOfNewsID(int position) {
        return mData.get(position).getId();
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (getItemViewType(position) != 0) {
            if (convertView == null) {

                convertView = li.inflate(R.layout.news_list_item, null);
            }
        } else {
            if (convertView == null) {
                convertView = li.inflate(R.layout.news_first_list_item, null);
            }
        }
        TextView tvData = (TextView) convertView.findViewById(R.id.tvData);
        tvData.setText(mData.get(position).getTitle());
        ImageView imNewsImage = (ImageView) convertView.findViewById(R.id.imNewsImage);
        if (mData.get(position).getImageUrl() != null)
            mImageLoader.displayImage(mData.get(position).getImageUrl(), imNewsImage);


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

    private class ImageDownloaderTask extends AsyncTask<String, Void, Void> {

        private Context context;
        private WeakReference<ImageView> imageView;

        public ImageDownloaderTask(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Void doInBackground(String... params) {
            String URL = params[0];


            return null;
        }
    }
}