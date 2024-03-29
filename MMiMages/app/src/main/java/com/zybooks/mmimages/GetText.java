package com.zybooks.mmimages;


import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class GetText  extends AsyncTask<String, Void, String>{

    //Interface to return results from background task
    public interface AsyncResponse{
        void processFinish(String textOut);
    }

    public AsyncResponse delegate = null;

    public GetText(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... string){
        try {

            URL url = new URL(string[0]);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while((str = in.readLine()) != null){
                sb.append(str);
                sb.append("\n");
            }

            return new String(sb);

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(String textOut){
        super.onPostExecute(textOut);
        delegate.processFinish(textOut);
    }
}
