package org.cnc.nsv.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PhotoPagerAdapter extends PagerAdapter {

	Context context;
	final int numberImages = 8;

	public int getCount() {
		return numberImages;
	}

	public PhotoPagerAdapter(Context context) {
		this.context = context;
	}
	
	public Object instantiateItem(View collection, int position) {
		ImageView iv = new ImageView(context);
		try {
			Bitmap b;
			b = BitmapFactory.decodeStream(context.getAssets().open("photos/i" + (position + 1) + ".jpg"));
			if (b != null) {
				iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				iv.setPadding(10, 0, 10, 0);
				iv.setAdjustViewBounds(true);
				iv.setScaleType(ScaleType.FIT_CENTER);
				iv.setImageBitmap(b);
				((ViewPager) collection).addView(iv, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iv;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}