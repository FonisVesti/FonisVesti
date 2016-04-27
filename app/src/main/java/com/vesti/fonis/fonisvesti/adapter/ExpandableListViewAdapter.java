package com.vesti.fonis.fonisvesti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.R;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.model.Project;

import java.util.List;

/**
 * Created by Dusan on 27.4.2016..
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private Context mContext;
    private List<Project> mData;

    public ExpandableListViewAdapter(Context context, List<Project> newsList) {
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



        convertView = li.inflate(R.layout.list_item, null);





        TextView tvData = (TextView) convertView.findViewById(R.id.tvData);
        tvData.setText(mData.get(position).getText());
        ImageView imNewsImage = (ImageView) convertView.findViewById(R.id.imNewsImage);
        imNewsImage.setImageBitmap(mData.get(position).getImg());

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



}
