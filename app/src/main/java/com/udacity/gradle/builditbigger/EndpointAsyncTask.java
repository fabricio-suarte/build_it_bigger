package com.udacity.gradle.builditbigger;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.suarte.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * The AsyncTask implementation for accessing the backend
 */
public class EndpointAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = EndpointAsyncTask.class.getCanonicalName();

    private static MyApi myApiService = null;

    private Runnable onPostExecuteRunnable;

    public EndpointAsyncTask(Runnable onPostExecuteRunnable) {
        this.onPostExecuteRunnable = onPostExecuteRunnable;
    }

    @Override
    protected String doInBackground(Void... params) {

        //"http://10.0.2.2:8080/_ah/api/"
        String url = Uri.parse(BuildConfig.BACKEND_ROOT_ADDRESS)
                .buildUpon()
                .appendEncodedPath("_ah/api/")
                .build()
                .toString();

        Log.d(TAG, "Full url: " + url);

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(url)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.d(TAG, "Obtained Joke: " + s);

        if(this.onPostExecuteRunnable != null)
            this.onPostExecuteRunnable.run();
    }
}
