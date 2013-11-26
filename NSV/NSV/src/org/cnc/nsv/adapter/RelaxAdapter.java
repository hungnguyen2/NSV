package org.cnc.nsv.adapter;
import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.entity.Relax;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class RelaxAdapter extends BaseAdapter {
	
	Context context;
	List<Relax> data;

	public RelaxAdapter(Context context, List<Relax> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Relax getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return data.get(arg0).getId();
	}

	@Override
	public View getView(int p, View v, ViewGroup arg2) {
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.item_relax, null);
		}
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		TextView tvSummary = (TextView) v.findViewById(R.id.tvSummary);
		final ImageView ivThumb=(ImageView) v.findViewById(R.id.ivThumb);
		final Relax a = data.get(p);
		tvTitle.setText(a.getTitle());
		String s = a.getSummary();
		s = s.replaceAll("<[^>]*>", "");
		if (s.length() > 70) {
			s = s.substring(0, 70) + "...";
		}
		tvSummary.setText(s);
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
		v.setPadding(10, 10, 10, 10);
		if (p == (getCount() - 1)) {
			//v.setPadding(10, 5, 10, 120);
		}
		return v;
	}

}
