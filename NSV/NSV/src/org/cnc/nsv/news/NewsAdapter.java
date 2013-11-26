package org.cnc.nsv.news;

import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	String TAG=NewsAdapter.class.getSimpleName();
	Context context;
	List<Ojnews> listNews;
	
	public NewsAdapter(Context context,List<Ojnews> listNews) {
		this.context=context;
		this.listNews=listNews;
		Log.i(TAG, ""+listNews.size());

	}
	
	@Override
	public int getCount() {
		
		return listNews.size();
	}

	@Override
	public Object getItem(int position) {
		return listNews.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		final Ojnews news=listNews.get(pos);
		
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(org.cnc.nsv.R.layout.child_lv_news, null);
		}
		TextView title = (TextView) convertView.findViewById(R.id.news_title);
		TextView summary = (TextView) convertView.findViewById(R.id.news_summary);
		final ImageView ivThumb=(ImageView) convertView.findViewById(R.id.News_image);
		
		title.setText(news.getTitle());
		String s = news.getSummary();
		if (s.length() > 100) {
			s = s.substring(0, 100) + "...";
		}
		summary.setText(s);
		String thumbUrl = news.getThumbUrl();
		Handler loadImageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1 && msg.obj != null) {
					try {
						news.setThumb((Bitmap) msg.obj);
						ivThumb.setImageBitmap(news.getThumb());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		if (news.getThumb() != null) {
			ivThumb.setImageBitmap(news.getThumb());
		}  else if (!thumbUrl.equalsIgnoreCase("")) {
			new Utils.LoadImageTask(loadImageHandler).execute(thumbUrl);
		}
		convertView.setPadding(10, 5, 5, 5);
		if (pos == (getCount() - 1)) {
			convertView.setPadding(10, 5, 5, 100);
		}
		return convertView;
	}

}
