package com.example.erica.recsfromtechs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;

public class webImageGetter extends AsyncTask<String, Void, Bitmap> {

    ImageView thisView;
    String urldisplay;

    public    webImageGetter(ImageView x) {
        thisView = x;

    }

    protected Bitmap doInBackground(String... urls) {
        urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            System.out.println("Could not find the image at URL:" + urldisplay);
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        System.out.println(thisView == null);
        thisView.setImageBitmap(result);
    }

}
