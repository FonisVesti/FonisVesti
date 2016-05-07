package com.vesti.fonis.fonisvesti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.R;
import com.vesti.fonis.fonisvesti.model.Project;

import java.util.List;

/**
 * Created by Dusan on 27.4.2016..
 */
public class ProjectsListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Project> mData;

    public ProjectsListAdapter(Context context, List<Project> projects) {
        mContext = context;
        mData = projects;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
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
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getGroupType(groupPosition) != 0) {
            if (convertView == null) {
                convertView = li.inflate(R.layout.projects_list_item, null);
            }
        } else {
            if (convertView == null) {
                convertView = li.inflate(R.layout.projects_first_list_item, null);
                return convertView;
            }
        }

        if(groupPosition == 0)
            return convertView;


        TextView tvTittle = (TextView)convertView.findViewById(R.id.tvProjectTittle);
        ImageView ivProjectPic  = (ImageView)convertView.findViewById(R.id.ivProjectImage);

        tvTittle.setText(mData.get(groupPosition).getName());
        if(mData.get(groupPosition).getImg() != null){
            ivProjectPic.setImageBitmap(mData.get(groupPosition).getImg());
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getGroupType(groupPosition) != 0)
                convertView = li.inflate(R.layout.projects_list_item_expanded, null);
        else
            return null;

        TextView tvTittle = (TextView)convertView.findViewById(R.id.tvProjectText);
        tvTittle.setText(mData.get(groupPosition).getText());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return groupPosition == 0 ? 0 : 1;
    }
}
