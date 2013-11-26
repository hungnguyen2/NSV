package org.cnc.nsv.adapter;
import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.entity.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MusicAdapter extends BaseAdapter {
	
	Context context;
	List<Music> data;

	public MusicAdapter(Context context, List<Music> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Music getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return data.get(arg0).getId();
	}

	@Override
	public View getView(int p, View v, ViewGroup arg2) {
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.item_music, null);
		}
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		final Music a = data.get(p);
		tvTitle.setText(a.getTitle());
		
		v.setPadding(10, 10, 10, 10);
		if (p == (getCount() - 1)) {
			//v.setPadding(10, 5, 10, 120);
		}
		return v;
	}

}
