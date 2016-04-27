package com.vesti.fonis.fonisvesti;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.vesti.fonis.fonisvesti.adapter.ExpandableListViewAdapter;
import com.vesti.fonis.fonisvesti.model.Project;
import com.vesti.fonis.fonisvesti.model.Projects;

/**
 * Created by Dusan on 24.3.2016..
 */
public class ProjectsActivity extends BaseActivity {

    private ExpandableListView mListView;
    private ExpandableListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_projects);
        Projects.projects.add(new Project("DULLEEE", "STA RAIDS", null));
        mListView=(ExpandableListView) findViewById(R.id.expandableListView);
        mAdapter= new ExpandableListViewAdapter(this, Projects.projects);
    }
}
