package org.bts.android.dummybackground;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by pedrodelmonte on 20/03/17.
 */

public class MyDownloadThread extends AsyncTask <String, Void, String>{

    private Context mContext;
    private static final String TAG = MyDownloadThread.class.getSimpleName();

    public MyDownloadThread(Context context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {

        URL feedURL = null;
        String titleList = "";

        try {
            feedURL = new URL("https://www.theguardian.com/international/rss");
            HttpsURLConnection myConnection = (HttpsURLConnection) feedURL.openConnection();
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            myConnection.connect();
            Log.i(MyDownloadThread.TAG, "Response code: " + myConnection.getResponseCode());

            if (myConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                Log.i(MyDownloadThread.TAG,"Connection set up");

                InputStream inputStream = myConnection.getInputStream();

                XmlPullParser xmlParser = Xml.newPullParser();
                xmlParser.setInput(inputStream, null);

                int event = xmlParser.nextTag();


                while (xmlParser.getEventType() !=  XmlPullParser.END_DOCUMENT) {
                    //Log.i(MyDownloadThread.TAG, "Inside Parsing");
                    //Log.i(MyDownloadThread.TAG, "Tag: "+xmlParser.getName());
                    switch (event) {
                        case XmlPullParser.START_TAG:
                            //Log.i(MyDownloadThread.TAG, "Starting Tag found");
                            if (xmlParser.getName().equals("item")) {
                                xmlParser.nextTag();
                                xmlParser.next();
                                titleList += "- " + xmlParser.getText()+ "\n";
                            }

                            break;

                        default:

                    }
                    event = xmlParser.next();
                }

                inputStream.close();
                Log.i(MyDownloadThread.TAG, titleList);

            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return titleList;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(this.mContext, s, Toast.LENGTH_LONG).show();
    }
}
