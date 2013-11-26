package org.cnc.nsv.product;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.store;
import org.cnc.nsv.json.ParseProductJs;
import org.cnc.nsv.webservice.GetData;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_production extends Activity implements Constant
{
	
	class results extends AsyncTask< Void, Void, Void >
	{
		ProgressDialog	dialog;
		
		@Override
		protected Void doInBackground( Void... params )
		{
			GetData getData = new GetData( );
			String json = getData.get( API_PRODUCT );
			Utils.setCatcheText( getApplicationContext( ), KEY_PRODUCT_LIST, json );
			setListView( json );
			return null;
		}
		
		@Override
		protected void onPostExecute( Void result )
		{
			super.onPostExecute( result );
			if ( adapter.getCount( ) == 0 )
			{
				Toast.makeText( getBaseContext( ), getString( R.string.check_internet ), Toast.LENGTH_LONG ).show( );
			}
			lvProduct.setAdapter( adapter );
			dialog.dismiss( );
		}
		
		@Override
		protected void onPreExecute( )
		{
			dialog = new ProgressDialog( Activity_production.this );
			dialog.setMessage( getString( R.string.loading ) );
			dialog.show( );
			lvProduct.setAdapter( null );
			super.onPreExecute( );
		}
		
		@Override
		protected void onProgressUpdate( Void... values )
		{
			
			super.onProgressUpdate( values );
		}
		
	}
	
	String			TAG	= Activity_production.class.getSimpleName( );
	ListView		lvProduct;
	ProductAdapter	adapter;
	ImageView		button_back;
	private int		position;
	
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
		setContentView( R.layout.activity_production );
		
		new createmenu( ).create( Activity_production.this );
		findViewById( R.id.menu_product ).setSelected( true );
		
		Intent t = getIntent( );
		position = t.getIntExtra( "position_product", -1 );
		
		button_back = ( ImageView ) findViewById( R.id.button_back );
		button_back.setOnClickListener( new OnClickListener( )
		{
			
			@Override
			public void onClick( View v )
			{
				finish( );
				
			}
		} );
		lvProduct = ( ListView ) findViewById( R.id.lvProduction );
		lvProduct.setOnItemClickListener( new OnItemClickListener( )
		{
			
			@Override
			public void onItemClick( AdapterView< ? > parent, View arg1, int position,
					long arg3 )
			{
				
				ProductAdapter productAdapter = ( ProductAdapter ) parent.getAdapter( );
				store.OJ_PRODUCT = ( OjProduct ) productAdapter.getItem( position );
				
				Intent t = new Intent( getBaseContext( ), Activity_detailproduct.class );
				t.putExtra( "position_product", position );
				startActivity( t );
				
			}
		} );
		
		String json = Utils.getCatcheText( getBaseContext( ), KEY_PRODUCT_LIST );
		if ( json != null )
		{
			setListView( json );
			lvProduct.setAdapter( adapter );
		}
		else
		{
			new results( ).execute( );
		}
	}
	
	void setListView( String json )
	{
		List< OjProduct > products = new ArrayList< OjProduct >( );
		try
		{
			products = new ParseProductJs( ).parse( json );
		}
		catch ( JSONException e )
		{
			Log.d( TAG, "" + e );
			e.printStackTrace( );
		}
		adapter = new ProductAdapter( this, products );
	}
}
