package org.cnc.nsv;

import org.cnc.nsv.news.Ojnews;
import org.cnc.nsv.product.OjProduct;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class store {
	public static  OjProduct OJ_PRODUCT;
	public static  Ojnews OJNEWS;
	public static int nW;
	public static int nH;
	
	
	public static  void toast(Context context,String text){
		Toast.makeText(context,text , Toast.LENGTH_LONG).show();
	}
	public static  void log(String text){
		Log.i("-auto-", text);
	}
	
	
	

}


