package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by sheshloksamal on 01/08/16.
 *
 */
public class JokeAsyncTask extends AsyncTask<JokeAsyncTaskListener, Integer, String> {

    private static MyApi myApiService = null;
    private JokeAsyncTaskListener mJokeAsyncTaskListener = null;
    private Exception mError = null;

    @Override
    protected String doInBackground(JokeAsyncTaskListener... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    .setRootUrl("https://build-it-bigger-b0d21.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        this.mJokeAsyncTaskListener = params[0];

        try {
            return myApiService.getJokeOfTheDay().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
//        if (this.mJokeAsyncTaskListener != null) {
//            this.mJokeAsyncTaskListener.setProgress(values[0]);
//        }
//    }

    @Override
    protected void onPostExecute(String result) {
        if (this.mJokeAsyncTaskListener != null) {
            this.mJokeAsyncTaskListener.onComplete(result, mError);
        }

    }

    @Override
    protected void onCancelled() {
        if (this.mJokeAsyncTaskListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mJokeAsyncTaskListener.onComplete(null, mError);
        }
    }


}

