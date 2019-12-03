package com.zybooks.mmimages;


import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetText  extends AsyncTask<String, Void, Void>{

    //Interface to return results from background task
    public interface AsyncResponse{
        void processFinish(String textOut);
    }

    public AsyncResponse delegate = null;

    public GetText(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(String... string){
        try {

            String fileName = "test_obj.txt";
            PrintStream out = new PrintStream(new FileOutputStream("/mnt/sdcard/"+fileName));

            URL url = new URL(string[0]);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while((str = in.readLine()) != null){
                sb.append(str);
                sb.append("\n");
            }
            out.print(new String(sb));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
