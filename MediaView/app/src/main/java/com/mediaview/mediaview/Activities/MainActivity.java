package com.mediaview.mediaview.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.R;
import com.mediaview.mediaview.fragments.MediasListFragment;
import com.mediaview.mediaview.fragments.MediasViewFragment;
import com.mediaview.mediaview.fragments.nav_drawer.ObjectDrawerItem;
import com.mediaview.mediaview.fragments.nav_drawer.DrawerItemCustomAdapter;
import com.mediaview.mediaview.tools.Constants;
import com.mediaview.mediaview.tools.DBManager;
import com.mediaview.mediaview.tools.Manager;
import com.mediaview.mediaview.tools.services.DownloadService;
import com.mediaview.mediaview.tools.services.UpDateReceiver;

import java.util.Calendar;


public class MainActivity extends Activity implements MediasListFragment.OnElementSelectedListener {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAlarmBroadcast();

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            setContentView(R.layout.activity_main_double);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else
            setContentView(R.layout.activity_main_simple);

        initComponent();
        initNavigationDrawer();

    }

    private void initComponent(){
        mTitle = mDrawerTitle = getTitle();

        // Initialize properties for Drawer Layout
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initNavigationDrawer(){
        // Add items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_images, (getResources().getStringArray(R.array.navigation_drawer_items_array))[0]);
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_texts, (getResources().getStringArray(R.array.navigation_drawer_items_array))[1]);
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_audios, (getResources().getStringArray(R.array.navigation_drawer_items_array))[2]);
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_videos, (getResources().getStringArray(R.array.navigation_drawer_items_array))[3]);

        // Adapter to Navigation Drawer
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.menu_listview_item, drawerItem);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_menu,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onElementSelected(Media m) {
        createFragmentView(m);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {

            Media.EType type = null;

            switch (position) {
                case 0:
                    type = Media.EType.Image;
                    break;
                case 1:
                    type = Media.EType.Texte;
                    break;
                case 2:
                    type = Media.EType.Audio;
                    break;
                case 3:
                    type = Media.EType.Video;
                    break;
                default:
                    break;
            }

            if(createFragmentList(type)){
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        }
    }

    private boolean createFragmentList(Media.EType type) {
        Fragment fragment = new MediasListFragment();
        ( (MediasListFragment) fragment).setMediaType(type);

        return createFragment(fragment);
    }

    private boolean createFragmentView(Media media) {
        Fragment fragment = new MediasListFragment();
        ( (MediasViewFragment) fragment).setMedia(media);

        return createFragment(fragment);
    }

    private boolean createFragment(Fragment fragment) {
        boolean isFragementCreated = false;

        // TODO : Créer le fragment sur le bon frameLayout en fonction de "isTablet" (first -> replace / second)
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame_first, fragment).commit();
            isFragementCreated = true;
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        return isFragementCreated;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void initAlarmBroadcast(){
        Intent intent = new Intent(this, UpDateReceiver.class);
        intent.setAction(Constants.WAKE_TO_UPDATE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
