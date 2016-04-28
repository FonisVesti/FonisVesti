package com.vesti.fonis.fonisvesti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.vesti.fonis.fonisvesti.adapter.ProjectsListAdapter;
import com.vesti.fonis.fonisvesti.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dusan on 24.3.2016..
 */
public class ProjectsActivity extends BaseActivity {

    private ExpandableListView mListView;
    private ProjectsListAdapter mAdapter;
    private List<Project> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_projects);

        // Add projects here
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));
        list.add(new Project("DULLEEE", "STA RAIDS", createImage(R.drawable.fonis)));


        mListView = (ExpandableListView) findViewById(R.id.list);
        mAdapter = new ProjectsListAdapter(this, list);
        mListView.setAdapter(mAdapter);
    }

    private Bitmap createImage(int resId) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        Bitmap b = BitmapFactory.decodeResource(ProjectsActivity.this.getResources(), resId, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int targetW = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, ProjectsActivity.this.getResources().getDisplayMetrics());
        int targetH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, ProjectsActivity.this.getResources().getDisplayMetrics());

        // Determine how much to scale down the image
        int scaleFactor = 1;
        while ((photoW / scaleFactor) > targetW
                && (photoH / scaleFactor) > targetH) {
            scaleFactor *= 2;
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeResource(ProjectsActivity.this.getResources(), resId, bmOptions);
    }
}
