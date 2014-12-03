package com.mediaview.mediaview.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.R;
import com.mediaview.mediaview.tools.DBManager;
import com.mediaview.mediaview.tools.Manager;
import com.mediaview.mediaview.tools.tasks.DataInitializationTask;



import java.util.List;

public class MainActivity extends Activity {

    private TextView test;
    private DataInitializationTask.InitializationCallback mCallback = new DataInitializationTask.InitializationCallback() {
        @Override
        public void postExecute() {
            String text = "";
            List<Media> medias = Manager.getInstance().getDbManager().getAllMedia();
            for(Media media : medias){
                text+=media.toString() + "\n\n";
            }
            test.setText(text);
        }

        @Override
        public void preExecute() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBManager db = new DBManager(this);
        Manager.getInstance().setDbManager(db);
        test = (TextView) findViewById(R.id.test);
        new DataInitializationTask(mCallback);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
