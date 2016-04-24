package com.example.erica.recsfromtechs.gifTools;

import android.content.Context;
import android.webkit.WebView;

/**
 * Created by Niklas on 4/19/2016.
 */
public class GifWebView extends WebView {

    public GifWebView(Context context, String path) {
        super(context);

        loadUrl(path);
    }
}