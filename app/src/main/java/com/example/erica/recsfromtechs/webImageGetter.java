package com.example.erica.recsfromtechs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class webImageGetter extends AsyncTask<String, Void, Bitmap> {

    private final ImageView thisView;

    /**
     * The picture image view to be used
     * @param x the image view
     */

    public webImageGetter(ImageView x) {
        thisView = x;

    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay;

        urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            System.out.println("Could not find the image at URL:" + urlDisplay);
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (thisView != null) {
            thisView.setImageBitmap(result);
        }
    }

}
