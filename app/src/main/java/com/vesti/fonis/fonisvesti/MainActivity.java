package com.vesti.fonis.fonisvesti;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vesti.fonis.fonisvesti.utils.Util;

public class MainActivity extends BaseActivity {
    private static final String FB_URL = "https://www.facebook.com/fonis.rs/?fref=ts";
    private static final String INSTAGRAM_ID = "fonis_fon";
    private static final String TWITTER_ID = "fonis_fon";
    private static final String YOUTUBE_URL = "https://www.youtube.com/user/FonisFON";
    private static final String LINKEDIN_ID = "fonis";



    private LinearLayout btnNews, btnMSDNAA, btnProjects, btnAbout;
    private ImageButton ibtnFacebook, ibtnInstagram, ibtnTwitter, ibtnYoutube, ibtnLinkedin;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btnNews = (LinearLayout) findViewById(R.id.llNews);
        btnMSDNAA = (LinearLayout) findViewById(R.id.llMsdnaa);
        btnAbout = (LinearLayout) findViewById(R.id.llAbout);
        btnProjects = (LinearLayout) findViewById(R.id.llProjects);
        ibtnFacebook = (ImageButton) findViewById(R.id.ibtnFacebook);
        ibtnTwitter = (ImageButton) findViewById(R.id.ibtnTwitter);
        ibtnInstagram = (ImageButton) findViewById(R.id.ibtnInstagram);
        ibtnYoutube = (ImageButton) findViewById(R.id.ibtnYoutube);
        ibtnLinkedin = (ImageButton) findViewById(R.id.ibtnLinkedin);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mIntent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(mIntent);
            }
        });

        btnMSDNAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(MainActivity.this, MSDNAAActivity.class);
                startActivity(mIntent);
            }
        });

        btnProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(MainActivity.this, ProjectsActivity.class);
                startActivity(mIntent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(mIntent);
            }
        });

        ibtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = newFacebookIntent(MainActivity.this.getPackageManager(), FB_URL);
                startActivity(mIntent);
            }
        });

        ibtnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = newInstagramIntent(MainActivity.this.getPackageManager(), INSTAGRAM_ID);
                startActivity(mIntent);
            }
        });

        ibtnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = newTwitterIntent(MainActivity.this.getPackageManager(), TWITTER_ID);
                startActivity(mIntent);
            }
        });

        ibtnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = newYoutubeIntent(MainActivity.this.getPackageManager(), YOUTUBE_URL);
                startActivity(mIntent);
            }
        });

        ibtnLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = newLinkedinIntent(MainActivity.this.getPackageManager(), LINKEDIN_ID);
                startActivity(mIntent);

            }
        });
    }

    /**
     * <p>Intent to open the official Facebook app. If the Facebook app is not installed then the
     * default web browser will be used.</p>
     * <p/>
     * <p>Example usage:</p>
     * <p/>
     * {@code newFacebookIntent(ctx.getPackageManager(), "https://www.facebook.com/JRummyApps");}
     *
     * @param pm  The {@link PackageManager}. You can find this class through {@link
     *            Context#getPackageManager()}.
     * @param url The full URL to the Facebook page or profile.
     * @return An intent that will open the Facebook page/profile.
     */
    private Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private Intent newInstagramIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse("http://instagram.com/" + url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.instagram.android", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("http://instagram.com/_u/" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private Intent newTwitterIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse("https://twitter.com/" + url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.twitter.android", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("twitter://user?user_id=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private Intent newYoutubeIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private Intent newLinkedinIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse("https://www.linkedin.com/company/"+url);
        return new Intent(Intent.ACTION_VIEW, uri);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
