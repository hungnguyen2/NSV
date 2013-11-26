package org.cnc.nsv.otheractivity;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.entity.Relax;
import org.cnc.nsv.entity.Survey;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

@SuppressLint( "SetJavaScriptEnabled" )
public class Activity_info extends YouTubeBaseActivity implements
		OnInitializedListener
{
	ImageView				button_back;
	String					openActivity	= null;
	TextView				tvTitle;
	ScrollView				svWeb;
	LinearLayout			llPhoto;
	HorizontalScrollView	hsvPhoto;
	ViewPager				vpPhoto;
	ProgressDialog			dialog;
	
	@Override
	public void onBackPressed( )
	{
		if ( openActivity != null )
		{
			Intent i = new Intent( this, Activity_main.class );
			i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			startActivity( i );
		}
		else
		{
			super.onBackPressed( );
		}
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_info );
		new createmenu( ).create( Activity_info.this );
		
		findViewById( R.id.menu_more ).setSelected( true );
		
		tvTitle = ( TextView ) findViewById( R.id.tvTitle );
		svWeb = ( ScrollView ) findViewById( R.id.svWeb );
		hsvPhoto = ( HorizontalScrollView ) findViewById( R.id.hsvPhoto );
		llPhoto = ( LinearLayout ) findViewById( R.id.llPhoto );
		button_back = ( ImageView ) findViewById( R.id.button_back );
		vpPhoto = ( ViewPager ) findViewById( R.id.vpPhoto );
		
		button_back.setOnClickListener( new OnClickListener( )
		{
			@Override
			public void onClick( View v )
			{
				finish( );
				
			}
		} );
		String openActivity = getIntent( )
				.getStringExtra( Constant.OPEN_ACTIVITY );
		WebView wvContent = ( WebView ) findViewById( R.id.wvContent );
		wvContent.getSettings( ).setAllowFileAccess( true );
		wvContent.getSettings( ).setJavaScriptEnabled( true );
		wvContent.setWebChromeClient( new WebChromeClient( )
		{
			@Override
			public void onProgressChanged( WebView view, int newProgress )
			{
				super.onProgressChanged( view, newProgress );
				if ( newProgress >= 100 )
				{
					if ( dialog != null )
						dialog.dismiss( );
				}
			}
		} );
		
		if ( openActivity != null )
		{
			if ( openActivity.equals( "info" ) )
			{
				tvTitle.setText( R.string.company_info );
				String langCode = getResources( ).getConfiguration( ).locale.getLanguage( );
				String fileAbout = "about.html";
				// if (!langCode.equals("") && !langCode.equals("en")) fileAbout = "about-" + langCode + ".html";
				if ( langCode.equals( "vi" ) )
					fileAbout = "about-vi.html";
				wvContent.loadUrl( "file:///android_asset/" + fileAbout );
			}
			if ( openActivity.equals( "chart" ) )
			{
				tvTitle.setText( R.string.org_chart );
				wvContent.getSettings( ).setSupportZoom( true );
				wvContent.getSettings( ).setBuiltInZoomControls( true );
				wvContent.loadUrl( "file:///android_asset/sdtc.html" );
			}
			if ( openActivity.equals( "history" ) )
			{
				tvTitle.setText( R.string.dev_history );
				wvContent.loadUrl( "file:///android_asset/history.html" );
			}
			if ( openActivity.equals( "photo" ) )
			{
				tvTitle.setText( R.string.photo_album );
				wvContent.loadUrl( "file:///android_asset/photos/photos.html" );
				/*
				 * svWeb.setVisibility(View.INVISIBLE);
				 * vpPhoto.setVisibility(View.VISIBLE);
				 * PhotoPagerAdapter adapter = new PhotoPagerAdapter(this);
				 * vpPhoto.setAdapter(adapter);
				 * vpPhoto.setCurrentItem(1);
				 */
				
			}
			if ( openActivity.equals( "video" ) )
			{
				tvTitle.setText( R.string.videos );
				YouTubePlayerView youTubeView = ( YouTubePlayerView ) findViewById( R.id.youtube_view );
				svWeb.setVisibility( View.INVISIBLE );
				youTubeView.setVisibility( View.VISIBLE );
				String apiKey = "";
				try
				{
					apiKey = getPackageManager( ).getApplicationInfo(
							getPackageName( ), PackageManager.GET_META_DATA ).metaData
							.getString( "com.google.android.maps.v2.API_KEY" );
				}
				catch ( NameNotFoundException e )
				{
					e.printStackTrace( );
				}
				youTubeView.initialize( apiKey, this );
			}
			if ( openActivity.equals( "survey" ) )
			{
				Survey s = ( Survey ) getIntent( ).getSerializableExtra( Constant.KEY_SURVEY_LIST );
				if ( s != null )
				{
					dialog = new ProgressDialog( Activity_info.this );
					dialog.setMessage( getString( R.string.loading ) );
					dialog.show( );
					tvTitle.setText( s.getTitle( ) );
					wvContent.loadUrl( s.getUrl( ) );
				}
			}
			if ( openActivity.equals( "relax" ) )
			{
				Relax r = ( Relax ) getIntent( ).getSerializableExtra( Constant.KEY_RELAX_LIST );
				if ( r != null )
				{
					dialog = new ProgressDialog( Activity_info.this );
					dialog.setMessage( getString( R.string.loading ) );
					dialog.show( );
					tvTitle.setText( r.getTitle( ) );
					wvContent.loadData( r.getSummary( ), "text/html", "utf-8" );
				}
			}
			button_back.setVisibility( View.VISIBLE );
		}
		
	}
	
	@Override
	public void onInitializationFailure( Provider arg0,
			YouTubeInitializationResult arg1 )
	{
		
	}
	
	@Override
	public void onInitializationSuccess( Provider arg0, YouTubePlayer arg1,
			boolean arg2 )
	{
		if ( !arg2 )
		{
			arg1.cueVideo( Constant.YOUTUBE_VIDEO );
		}
	}
}
