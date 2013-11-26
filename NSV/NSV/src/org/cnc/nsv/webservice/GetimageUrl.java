package org.cnc.nsv.webservice;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class GetimageUrl {

	public Drawable LoadImage(String url) {

	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        Log.e("getimage", "Error load image from web: " + e);
	        return null;
	    }

	}

}
