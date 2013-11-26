package org.cnc.nsv.activity;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.entity.Music;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayActivity extends Activity implements Constant {

	ImageView ivBack;
	Music music;
	TextView tvTitle, tvDetail;
	MediaPlayer player;
	ImageButton btPlay;
	SeekBar seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		new createmenu().create(this);
		findViewById(R.id.menu_more).setSelected(true);

		ivBack = (ImageView) findViewById(R.id.button_back);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvDetail = (TextView) findViewById(R.id.tvDetail);
		btPlay = (ImageButton) findViewById(R.id.btPlay);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		music = (Music) getIntent().getSerializableExtra(
				Constant.KEY_MUSIC_LIST);
		if (music != null) {
			tvTitle.setText(music.getTitle());
			tvDetail.setText(music.getDetail());
			playAudio();
			btPlay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (player.isPlaying()) {
						player.pause();
						btPlay.setImageResource(R.drawable.ic_play);
					} else {
						player.start();
						btPlay.setImageResource(R.drawable.ic_stop);
					}
				}
			});
		}

	}

	class LoadMusic extends AsyncTask<Void, Void, Void> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			killMediaPlayer();
			dialog = new ProgressDialog(PlayActivity.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				player = new MediaPlayer();
				player.setDataSource(music.getUrl());
				player.prepare();
				player.start();
			} catch (Exception e) {
				killMediaPlayer();
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (player != null) {
				btPlay.setImageResource(R.drawable.ic_stop);
				dialog.dismiss();
			}
		}
	}
	
	void playAudio() {
		new LoadMusic().execute();
	}

	private void killMediaPlayer() {
		if (player != null) {
			try {
				player.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		killMediaPlayer();
	}

}
