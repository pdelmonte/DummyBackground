package org.bts.android.dummybackground;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        MyAsyncTask myTask = new MyAsyncTask(this);
        //myTask.execute(1);


        //---------------
        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netwInfo = connMngr.getActiveNetworkInfo();

        if (netwInfo != null && netwInfo.isConnected()) {
            MyDownloadThread mDownThread = new MyDownloadThread(this);
            mDownThread.execute();

        } else {
            Log.w(MainActivity.TAG,"Connection Failed");
            Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateProgressBar(Integer value) {

        this.mProgressBar.setProgress(value);
    }
}
