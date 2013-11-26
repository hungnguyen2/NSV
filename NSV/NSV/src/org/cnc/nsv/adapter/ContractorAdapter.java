package org.cnc.nsv.adapter;
import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.activity.MapActivity;
import org.cnc.nsv.entity.Contractor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ContractorAdapter extends BaseAdapter {
	
	Context context;
	List<Contractor> data;

	public ContractorAdapter(Context context, List<Contractor> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Contractor getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return data.get(arg0).getId();
	}

	@Override
	public View getView(int p, View v, ViewGroup arg2) {
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.item_search, null);
		}
		TextView tvTitle, tvSubtitle, tvPhone, tvEmail, tvWebsite;
		ImageView ivMap;
		final ImageView ivThumb;
		tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvSubtitle = (TextView) v.findViewById(R.id.tvSubtitle);
		ivMap = (ImageView) v.findViewById(R.id.ivMap);
		
		final Contractor a = data.get(p);
		tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvSubtitle = (TextView) v.findViewById(R.id.tvSubtitle);
		tvPhone = (TextView) v.findViewById(R.id.tvPhone);
		tvEmail = (TextView) v.findViewById(R.id.tvEmail);
		tvWebsite = (TextView) v.findViewById(R.id.tvWebsite);
		ivMap = (ImageView) v.findViewById(R.id.ivMap);
		ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
		
		tvTitle.setText(a.getName());
		tvSubtitle.setText(a.getAddress());
		tvPhone.setText(a.getPhone());
		tvEmail.setText(a.getEmail());
		tvWebsite.setText(a.getWebsite());
		if (p % 2 == 1) {
			v.setBackgroundColor(Color.parseColor("#dfedf8"));
		} else {
			v.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		v.setPadding(10, 5, 10, 5);
		if (p == (getCount() - 1)) {
			v.setPadding(10, 5, 10, 120);
		}
		String thumbUrl = a.getThumbUrl();
		Handler loadImageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1 && msg.obj != null) {
					try {
						a.setThumb((Bitmap) msg.obj);
						ivThumb.setImageBitmap(a.getThumb());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		if (a.getThumb() != null) {
			ivThumb.setImageBitmap(a.getThumb());
		}  else if (!thumbUrl.equalsIgnoreCase("")) {
			new Utils.LoadImageTask(loadImageHandler).execute(thumbUrl);
		}
		ivMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MapActivity.class);
				a.setThumb(null);
				i.putExtra("nsv-contractor", a);
				context.startActivity(i);
				
			}
		});
		ivThumb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MapActivity.class);
				a.setThumb(null);
				i.putExtra("nsv-contractor", a);
				context.startActivity(i);
			}
		});
		return v;
	}

}
