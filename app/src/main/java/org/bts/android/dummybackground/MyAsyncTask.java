package org.bts.android.dummybackground;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by pedrodelmonte on 14/03/17.
 */

public class MyAsyncTask extends AsyncTask<Integer, Integer, String>{

    private Context mContext;

    private static final String TAG = MyAsyncTask.class.getSimpleName();

    public MyAsyncTask(Context anyContext) {
        this.mContext = anyContext;
    }

    @Override
    protected String doInBackground(Integer... params) {
        for (int idx = 1; idx <= 5; idx++) {
            sleepForAWhile(params[0]);
            publishProgress(idx * 20);
            Log.i(MyAsyncTask.TAG, "Iteration " + idx + " done!");
        }
        return "AsyncTask finished";
    }

    private void sleepForAWhile(Integer numSecs) {
        long currentTime = System.currentTimeMillis();
        long finishTime = currentTime + numSecs * 1000;

        while (currentTime < finishTime) {
            currentTime = System.currentTimeMillis();
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        ((MainActivity) this.mContext).updateProgressBar(values[0]);

    }

    @Override
    protected void onPostExecute(String retString) {
        super.onPostExecute(retString);
        Toast.makeText(this.mContext, retString,Toast.LENGTH_SHORT).show();
    }
}
