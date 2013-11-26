package org.cnc.nsv.product;

import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	String TAG=ProductAdapter.class.getSimpleName();
	Context context;
	List<OjProduct> listProduct;
	
	public ProductAdapter(Context context,List<OjProduct> listProduct) {
		this.context=context;
		this.listProduct=listProduct;
		Log.i(TAG, ""+listProduct.size());

	}
	
	@Override
	public int getCount() {
		return listProduct.size();
	}

	@Override
	public Object getItem(int position) {
		return listProduct.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		final OjProduct product=listProduct.get(pos);
		
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(org.cnc.nsv.R.layout.product_lv_child, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.prod_name);
		TextView info = (TextView) convertView.findViewById(R.id.product_info);
		TextView kind=(TextView) convertView.findViewById(R.id.product_kind);
		final ImageView ivThumb = (ImageView) convertView.findViewById(R.id.product_image);
		
		name.setText(product.getName());
		info.setText(Html.fromHtml( "<b>"+ context.getString(R.string.quycach)+" </b>"+ product.getQuy_cach()));
		kind.setText( Html.fromHtml("<b>"+ context.getString(R.string.loaihang)+" </b>"+product.getLoai_hang()));

		String thumbUrl = product.getThumbUrl();
		Handler loadImageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1 && msg.obj != null) {
					try {
						product.setThumb((Bitmap) msg.obj);
						ivThumb.setImageBitmap(product.getThumb());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		if (product.getThumb() != null) {
			ivThumb.setImageBitmap(product.getThumb());
		}  else if (!thumbUrl.equalsIgnoreCase("")) {
			new Utils.LoadImageTask(loadImageHandler).execute(thumbUrl);
		}
		if (pos % 2 == 1) {
			convertView.setBackgroundColor(Color.parseColor("#dfedf8"));
		} else {
			convertView.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		convertView.setPadding(5, 5, 5, 5);
		if (pos == (getCount() - 1)) {
			convertView.setPadding(5, 5, 5, 100);
		}
		return convertView;
	}
	
	

}
