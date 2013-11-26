package org.cnc.nsv.adapter;
import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.entity.Survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class SurveyAdapter extends BaseAdapter {
	
	Context context;
	List<Survey> data;

	public SurveyAdapter(Context context, List<Survey> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Survey getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return data.get(arg0).getId();
	}

	@Override
	public View getView(int p, View v, ViewGroup arg2) {
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.item_survey, null);
		}
		TextView tvTitle;
		tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		
		final Survey a = data.get(p);
		tvTitle.setText(a.getTitle());
		//v.setPadding(10, 5, 10, 5);
		if (p == (getCount() - 1)) {
			//v.setPadding(10, 5, 10, 120);
		}
		return v;
	}

}
