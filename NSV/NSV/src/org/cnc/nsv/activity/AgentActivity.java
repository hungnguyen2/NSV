package org.cnc.nsv.activity;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.adapter.AgentAdapter;
import org.cnc.nsv.adapter.LocationAdapter;
import org.cnc.nsv.entity.Agent;
import org.cnc.nsv.entity.Geo;
import org.cnc.nsv.entity.Location;
import org.cnc.nsv.json.ParseAgent;
import org.cnc.nsv.json.ParseLocation;
import org.cnc.nsv.webservice.GetData;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class AgentActivity extends Activity implements Constant
{
	
	class SearchAgentTask extends AsyncTask< Void, Void, Void >
	{
		ProgressDialog	dialog;
		
		@Override
		protected Void doInBackground( Void... params )
		{
			GetData getData;
			String json;
			String agentParam = "?agent_type=" + agentType;
			String searchParam = searchText != null ? "&name=" + Uri.encode( searchText ) : "";
			String locationParam = ( locationId > 0 ) ? ( "&location_id=" + locationId ) : "";
			String geoParams = ( geo == null || locationId > 0 ) ? "" : "&lat=" + geo.getLat( ) + "&lng="
					+ geo.getLng( );
			if ( loadLocation )
			{
				getData = new GetData( );
				json = getData.get( API_LOCATION );
				Utils.setCatcheText( getApplicationContext( ), KEY_LOCATION_LIST, json );
				setLocationList( json );
			}
			getData = new GetData( );
			String requestUrl = API_AGENT + agentParam + searchParam + locationParam + geoParams;
			json = getData.get( requestUrl );
			// Utils.setCatcheText(getApplicationContext(), KEY_AGENT_LIST, json);
			setListView( json );
			return null;
		}
		
		@Override
		protected void onPostExecute( Void result )
		{
			super.onPostExecute( result );
			if ( agentAdapter.getCount( ) == 0 )
			{
				Toast.makeText( getBaseContext( ), getString( R.string.no_items_found ), Toast.LENGTH_LONG ).show( );
			}
			if ( loadLocation )
			{
				setLocationAdapter( );
			}
			lvResult.setAdapter( agentAdapter );
			dialog.dismiss( );
		}
		
		@Override
		protected void onPreExecute( )
		{
			super.onPreExecute( );
			dialog = new ProgressDialog( AgentActivity.this );
			dialog.setMessage( getString( R.string.loading ) );
			dialog.show( );
			lvResult.setAdapter( null );
		}
	}
	
	ListView		lvResult;
	ImageView		ivBack;
	Spinner			spLocation;
	RadioButton		rbAgent1, rbAgent2;
	EditText		etSearch;
	
	TextView		tvTitle;
	AgentAdapter	agentAdapter;
	LocationAdapter	locationAdapter;
	String			searchText, openActivity;
	Geo				geo;
	int				locationId		= 0, agentType = 1;
	
	Boolean			loadLocation	= true;
	
	@Override
	public void onBackPressed( )
	{
		Intent i = new Intent( this, Activity_main.class );
		i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		startActivity( i );
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_agent );
		
		new createmenu( ).create( this );
		
		ivBack = ( ImageView ) findViewById( R.id.button_back );
		ivBack.setOnClickListener( new OnClickListener( )
		{
			@Override
			public void onClick( View v )
			{
				finish( );
			}
		} );
		
		lvResult = ( ListView ) findViewById( R.id.lvResult );
		spLocation = ( Spinner ) findViewById( R.id.spLocation );
		rbAgent1 = ( RadioButton ) findViewById( R.id.rbAgent1 );
		rbAgent2 = ( RadioButton ) findViewById( R.id.rbAgent2 );
		etSearch = ( EditText ) findViewById( R.id.etSearch );
		tvTitle = ( TextView ) findViewById( R.id.tvTitle );
		
		openActivity = getIntent( ).getStringExtra( OPEN_ACTIVITY );
		if ( openActivity != null )
		{
			if ( openActivity.equals( KEY_SEARCH ) )
			{
				// tvTitle.setText(getString(R.string.search));
				findViewById( R.id.menu_search ).setSelected( true );
			}
			if ( openActivity.equals( KEY_DISTRIBUTOR ) )
			{
				// tvTitle.setText(getString(R.string.distributors));
				agentType = 1;
				rbAgent1.setChecked( true );
			}
			if ( openActivity.equals( KEY_RETAILER ) )
			{
				// tvTitle.setText(getString(R.string.retailers));
				agentType = 2;
				rbAgent2.setChecked( true );
			}
			setRadioButtonActive( );
		}
		tvTitle.setText( getString( R.string.search ) );
		lvResult.setOnItemClickListener( new OnItemClickListener( )
		{
			@Override
			public void onItemClick( AdapterView< ? > parent, View arg1,
					int position, long arg3 )
			{
				Agent a = agentAdapter.getItem( position );
				Intent i = new Intent( AgentActivity.this, MapActivity.class );
				i.putExtra( "nsv-agent", a );
				startActivity( i );
			}
		} );
		
		rbAgent1.setOnCheckedChangeListener( new OnCheckedChangeListener( )
		{
			@Override
			public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
			{
				if ( isChecked )
					agentType = 1;
				setRadioButtonActive( );
			}
		} );
		
		rbAgent2.setOnCheckedChangeListener( new OnCheckedChangeListener( )
		{
			@Override
			public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
			{
				if ( isChecked )
					agentType = 2;
				setRadioButtonActive( );
			}
		} );
		
		etSearch.setImeOptions( EditorInfo.IME_ACTION_SEARCH );
		etSearch.setOnEditorActionListener( new OnEditorActionListener( )
		{
			@Override
			public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
			{
				if ( actionId == EditorInfo.IME_ACTION_SEARCH )
				{
					searchText = etSearch.getText( ).toString( );
					if ( !searchText.equalsIgnoreCase( "" ) )
					{
						new SearchAgentTask( ).execute( );
						InputMethodManager imm = ( InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE );
						imm.hideSoftInputFromWindow( etSearch.getWindowToken( ), 0 );
					}
				}
				return false;
			}
		} );
		
		spLocation.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener( )
		{
			@Override
			public void onItemSelected( AdapterView< ? > arg0, View arg1,
					int arg2, long arg3 )
			{
				Location l = locationAdapter.getItem( arg2 );
				locationId = l.getId( );
				if ( locationId > 0 )
				{
					new SearchAgentTask( ).execute( );
				}
			}
			
			@Override
			public void onNothingSelected( AdapterView< ? > arg0 )
			{
				
			}
		} );
		
		geo = Utils.getCurrentLocation( this );
		loadLocation = true;
		String jsonLocation = Utils.getCatcheText( getBaseContext( ), KEY_LOCATION_LIST );
		if ( jsonLocation != null )
		{
			loadLocation = false;
			setLocationList( jsonLocation );
			setLocationAdapter( );
		}
		// String jsonAgent = Utils.getCatcheText(getBaseContext(), KEY_AGENT_LIST);
		// if (jsonAgent != null) {
		// setListView(jsonAgent);
		// lvResult.setAdapter(agentAdapter);
		// } else {
		// new SearchAgentTask().execute();
		// }
		
	}
	
	void setListView( String json )
	{
		List< Agent > list = new ArrayList< Agent >( );
		try
		{
			list = new ParseAgent( ).parse( json );
		}
		catch ( JSONException e )
		{
			e.printStackTrace( );
		}
		agentAdapter = new AgentAdapter( this, list, true );
	}
	
	void setLocationAdapter( )
	{
		spLocation.setAdapter( locationAdapter );
		spLocation.setSelection( 0 ); // spLocation.getCount() - 1
	}
	
	void setLocationList( String json )
	{
		List< Location > list = new ArrayList< Location >( );
		Location defaultLocation = new Location( );
		defaultLocation.setId( -1 );
		defaultLocation.setName( getString( R.string.select ) );
		list.add( defaultLocation );
		try
		{
			list.addAll( new ParseLocation( ).parse( json ) );
		}
		catch ( JSONException e )
		{
			e.printStackTrace( );
		}
		locationAdapter = new LocationAdapter( this, list );
	}
	
	@SuppressWarnings( "deprecation" )
	void setRadioButtonActive( )
	{
		if ( agentType == 1 )
		{
			rbAgent1.setTextColor( Color.parseColor( "#f21900" ) );
			rbAgent1.setBackgroundResource( R.drawable.tabact );
			rbAgent2.setTextColor( Color.parseColor( "#000000" ) );
			rbAgent2.setBackgroundDrawable( null );
			
		}
		else
		{
			rbAgent2.setTextColor( Color.parseColor( "#f21900" ) );
			rbAgent2.setBackgroundResource( R.drawable.tabact );
			rbAgent1.setTextColor( Color.parseColor( "#000000" ) );
			rbAgent1.setBackgroundDrawable( null );
		}
		lvResult.setAdapter( null );
		if ( !openActivity.equals( KEY_SEARCH ) )
		{
			new SearchAgentTask( ).execute( );
		}
	}
}
