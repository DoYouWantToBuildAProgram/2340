package com.example.erica.recsfromtechs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;

public class webImageGetter extends AsyncTask<String, Void, Bitmap> {
    Bitmap returnThis;
    String theURL;

    public    webImageGetter(String url) {
        theURL = url;
    }


    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            System.out.println("DID NOT WORK " + urldisplay);
            System.out.println(e.toString());
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        returnThis = result;
    }
    public Bitmap getBitmap() {
        return returnThis;
    }
}
