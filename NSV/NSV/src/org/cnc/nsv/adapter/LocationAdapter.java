package org.cnc.nsv.adapter;
import java.util.List;

import org.cnc.nsv.entity.Location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


public class LocationAdapter extends BaseAdapter implements SpinnerAdapter {
	
	Context context;
	List<Location> data;

	public LocationAdapter(Context context, List<Location> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Location getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return data.get(arg0).getId();
	}

	@Override
	public View getView(int p, View v, ViewGroup arg2) {
		if (v == null) {
			v = LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line, null);
		}
		TextView tvTitle;
		tvTitle = (TextView) v.findViewById(android.R.id.text1);
		
		final Location a = data.get(p);
		tvTitle.setText(a.getName());
		return v;
	}
}
