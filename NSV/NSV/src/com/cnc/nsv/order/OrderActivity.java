package com.cnc.nsv.order;

import java.util.ArrayList;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import org.cnc.nsv.BuildConfig;
import org.cnc.nsv.R;
import org.cnc.nsv.adapter.OrderAdapter;
import org.cnc.nsv.adapter.OrderItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cnc.vcontract.help.ConnectionDetector;
import com.cnc.vcontract.help.Mail;
import com.cnc.vcontract.help.MyOkDialog;
import com.cnc.vcontract.help.MyProgcessDialog;

public class OrderActivity extends MActivity
{
	
	class SendEmailAsyncTask extends AsyncTask< Void, Void, Boolean >
	{
		Mail	m	= new Mail( "hnydstar@gmail.com", "hnydforever" );
		
		public SendEmailAsyncTask( String fromEmail, String Body )
		{
			if ( BuildConfig.DEBUG )
				Log.v( SendEmailAsyncTask.class.getName( ), "SendEmailAsyncTask()" );
			// String[] toArr =
			// { "loveviet.90bkh@gmail.com" };
			String[] toArr =
			{ "sales@natsteelvina.com" };
			
			m.setTo( toArr );
			if ( fromEmail.equals( "" ) )
			{
				fromEmail = "noname@gmail.com";
			}
			m.setFrom( fromEmail );
			m.setSubject( "Order NSV" );
			m.setBody( Body );
		}
		
		@Override
		protected Boolean doInBackground( Void... params )
		{
			if ( BuildConfig.DEBUG )
				Log.v( SendEmailAsyncTask.class.getName( ), "doInBackground()" );
			try
			{
				m.send( );
				return true;
			}
			catch ( AuthenticationFailedException e )
			{
				Log.e( SendEmailAsyncTask.class.getName( ), "Bad account details" );
				e.printStackTrace( );
				return false;
			}
			catch ( MessagingException e )
			{
				Log.e( "MailApp", "Could not send email", e );
				e.printStackTrace( );
				return false;
			}
			catch ( Exception e )
			{
				e.printStackTrace( );
				return false;
			}
		}
		
		@Override
		protected void onPostExecute( Boolean result )
		{
			super.onPostExecute( result );
			MyProgcessDialog.hide( );
			if ( result )
			{
				MyOkDialog.showDialog( OrderActivity.this.getString( R.string.re_success ),
						OrderActivity.this );
			}
			else
			{
				MyOkDialog.showDialog( OrderActivity.this.getString( R.string.send_fail ),
						OrderActivity.this );
			}
		}
		
		@Override
		protected void onPreExecute( )
		{
			super.onPreExecute( );
			MyProgcessDialog.show( OrderActivity.this, OrderActivity.this.getString( R.string.sending_mail ) );
		}
	}
	
	private EditText			edt_full_name, edt_sex, edt_address, edt_province, edt_tell, edt_email;
	private TextView			tv_full_name, tv_sex, tv_address, tv_province, tv_tell, tv_email;
	
	private EditText			edt_full_name2, edt_sex2, edt_address2, edt_province2, edt_tell2, edt_email2;
	private TextView			tv_full_name2, tv_sex2, tv_address2, tv_province2, tv_tell2, tv_email2;
	private TextView			customer, receiver, product, quantity, product_info;
	
	private ListView			listViewOrder;
	private List< OrderItem >	listOrderItem	= new ArrayList< OrderItem >( );
	private OrderAdapter		orderAdapter;
	
	private int					position;
	public static final String	PREFS_REMEMBER	= "remember account";
	private int					n_item;
	
	private String[]			key;
	
	private boolean check( )
	{
		if ( edt_full_name.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.full_name_customer ), this );
			return false;
		}
		
		if ( edt_address.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.address_customer ), this );
			return false;
		}
		
		if ( edt_tell.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.tell_customer ), this );
			return false;
		}
		
		if ( edt_province.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.province_customer ), this );
			return false;
		}
		
		if ( edt_full_name.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.full_name_receiver ), this );
			return false;
		}
		
		if ( edt_address.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.address_receiver ), this );
			return false;
		}
		
		if ( edt_tell.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.tell_receiver ), this );
			return false;
		}
		
		if ( edt_province.length( ) < 1 )
		{
			MyOkDialog.showDialog( getString( R.string.province_receiver ), this );
			return false;
		}
		
		key = new String[ listOrderItem.size( ) ];
		int i = 0;
		for ( int k = 0; k < listOrderItem.size( ); k++ )
		{
			key[ k ] = listOrderItem.get( k ).getProduct( );
			View view = listViewOrder.getChildAt( k );
			EditText editText = ( EditText ) view.findViewById( R.id.edt_quantity );
			String string = editText.getText( ).toString( );
			i++;
			if ( !key[ k ].equals( "" ) && string != null && !string.equals( "" ) )
			{
				return true;
			}
			if ( i == k + 1 )
			{
				MyOkDialog.showDialog( getString( R.string.product_quantity ), this );
				return false;
			}
		}
		return true;
		
	}
	
	@Override
	public void onBackPressed( )
	{
		super.onBackPressed( );
		MyConstant.ORDER_FIRST = false;
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		getWindow( ).setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN );
		
		setContentView( R.layout.activity_order );
		
		Log.d( "", "on create" );
		
		edt_full_name = ( EditText ) findViewById( R.id.edt_full_name );
		edt_sex = ( EditText ) findViewById( R.id.edt_sex );
		edt_address = ( EditText ) findViewById( R.id.edt_address );
		edt_province = ( EditText ) findViewById( R.id.edt_province );
		edt_tell = ( EditText ) findViewById( R.id.edt_tell );
		edt_email = ( EditText ) findViewById( R.id.edt_email2 );
		
		tv_full_name = ( TextView ) findViewById( R.id.tv_full_name );
		tv_sex = ( TextView ) findViewById( R.id.tv_sex );
		tv_address = ( TextView ) findViewById( R.id.tv_address );
		tv_province = ( TextView ) findViewById( R.id.tv_province );
		tv_tell = ( TextView ) findViewById( R.id.tv_tell );
		tv_email = ( TextView ) findViewById( R.id.tv_email2 );
		
		tv_full_name2 = ( TextView ) findViewById( R.id.tv_full_name_2 );
		tv_sex2 = ( TextView ) findViewById( R.id.tv_sex2 );
		tv_address2 = ( TextView ) findViewById( R.id.tv_address2 );
		tv_province2 = ( TextView ) findViewById( R.id.tv_province2 );
		tv_tell2 = ( TextView ) findViewById( R.id.tv_tell2 );
		tv_email2 = ( TextView ) findViewById( R.id.tv_email22 );
		
		customer = ( TextView ) findViewById( R.id.customer );
		receiver = ( TextView ) findViewById( R.id.receiver );
		product = ( TextView ) findViewById( R.id.product );
		quantity = ( TextView ) findViewById( R.id.quantity );
		product_info = ( TextView ) findViewById( R.id.product_info );
		
		edt_full_name2 = ( EditText ) findViewById( R.id.edt_full_name_2 );
		edt_sex2 = ( EditText ) findViewById( R.id.edt_sex2 );
		edt_address2 = ( EditText ) findViewById( R.id.edt_address2 );
		edt_province2 = ( EditText ) findViewById( R.id.edt_province2 );
		edt_tell2 = ( EditText ) findViewById( R.id.edt_tell2 );
		edt_email2 = ( EditText ) findViewById( R.id.edt_email22 );
		
		TextView order = ( TextView ) findViewById( R.id.order );
		order.setOnClickListener( new OnClickListener( )
		{
			
			@Override
			public void onClick( View v )
			{
				if ( !ConnectionDetector.isConnectingToInternet( OrderActivity.this ) )
				{
					MyOkDialog.showDialog( getString( R.string.a_connect_internet ), OrderActivity.this );
					return;
				}
				
				order( );
			}
		} );
		
		listViewOrder = ( ListView ) findViewById( R.id.lv_order );
		orderAdapter = new OrderAdapter( this, listOrderItem );
		listViewOrder.setAdapter( orderAdapter );
		listViewOrder.setOnTouchListener( new OnTouchListener( )
		{
			@Override
			public boolean onTouch( View v, MotionEvent event )
			{
				v.getParent( ).requestDisallowInterceptTouchEvent( true );
				return false;
			}
		} );
		
		Intent t = getIntent( );
		String name = t.getStringExtra( "name_product" );
		int type_activity = t.getIntExtra( "type_activity", -1 );
		
		if ( name == null )
		{
			listOrderItem.add( new OrderItem( "", "" ) );
			SharedPreferences settings = getSharedPreferences( PREFS_REMEMBER, 0 );
			SharedPreferences.Editor editor = settings.edit( );
			editor.clear( );
			editor.commit( );
		}
		else
		{
			if ( !MyConstant.ORDER_FIRST )
			{
				listOrderItem.add( new OrderItem( name, "" ) );
				listOrderItem.add( new OrderItem( "", "" ) );
				MyConstant.ORDER_FIRST = true;
			}
		}
		
		SharedPreferences settings = getSharedPreferences( PREFS_REMEMBER, 0 );
		this.position = settings.getInt( "position", 0 );
		this.n_item = settings.getInt( "n_item", 0 );
		
		Log.d( "n_item", n_item + "" );
		Log.d( "position", position + "" );
		
		for ( int k = 0; k < n_item; k++ )
		{
			Log.d( "", "run for" );
			
			String name_pro = settings.getString( k + "", "" );
			String quantity = settings.getString( "n" + k, "" );
			listOrderItem.add( new OrderItem( name_pro, quantity ) );
			if ( position == n_item - 1 && k == n_item - 1 )
			{
				listOrderItem.add( new OrderItem( "", "" ) );
			}
			if ( this.position == k )
			{
				listOrderItem.get( k ).setProduct( name );
				listOrderItem.get( k ).setQuantity( quantity );
			}
		}
		
		orderAdapter.notifyDataSetChanged( );
		Helper.getListViewSize( listViewOrder );
		
		edt_full_name.setText( settings.getString( "full_name", "" ) );
		edt_sex.setText( settings.getString( "sex", "" ) );
		edt_address.setText( settings.getString( "address", "" ) );
		edt_province.setText( settings.getString( "full_name", "" ) );
		edt_tell.setText( settings.getString( "province", "" ) );
		edt_email.setText( settings.getString( "email", "" ) );
		
		edt_full_name2.setText( settings.getString( "full_name2", "" ) );
		edt_sex2.setText( settings.getString( "sex2", "" ) );
		edt_address2.setText( settings.getString( "address2", "" ) );
		edt_province2.setText( settings.getString( "full_name2", "" ) );
		edt_tell2.setText( settings.getString( "province2", "" ) );
		edt_email.setText( settings.getString( "email", "" ) );
	}
	
	@Override
	protected void onDestroy( )
	{
		SharedPreferences settings = getSharedPreferences( PREFS_REMEMBER, 0 );
		SharedPreferences.Editor editor = settings.edit( );
		editor.clear( );
		editor.commit( );
		
		super.onDestroy( );
	}
	
	@Override
	protected void onPause( )
	{
		super.onPause( );
		Log.d( "", "onPause" );
		SharedPreferences settings = getSharedPreferences( PREFS_REMEMBER, 0 );
		SharedPreferences.Editor editor = settings.edit( );
		editor.putInt( "position", position );
		editor.putInt( "n_item", listOrderItem.size( ) );
		key = new String[ listOrderItem.size( ) ];
		for ( int k = 0; k < listOrderItem.size( ); k++ )
		{
			key[ k ] = listOrderItem.get( k ).getProduct( );
			editor.putString( k + "", key[ k ] );
			
			View view = listViewOrder.getChildAt( k );
			EditText editText = ( EditText ) view.findViewById( R.id.edt_quantity );
			String string = editText.getText( ).toString( );
			Log.d( "", k + " " + key[ k ] + " " + string );
			editor.putString( "n" + k + "", string );
		}
		editor.putString( "full_name", edt_full_name.getText( ).toString( ) );
		editor.putString( "sex", edt_sex.getText( ).toString( ) );
		editor.putString( "address", edt_address.getText( ).toString( ) );
		editor.putString( "province", edt_province.getText( ).toString( ) );
		editor.putString( "tel", edt_tell.getText( ).toString( ) );
		editor.putString( "email", edt_email.getText( ).toString( ) );
		
		editor.putString( "full_name2", edt_full_name2.getText( ).toString( ) );
		editor.putString( "sex2", edt_sex2.getText( ).toString( ) );
		editor.putString( "address2", edt_address2.getText( ).toString( ) );
		editor.putString( "province2", edt_province2.getText( ).toString( ) );
		editor.putString( "tel2", edt_tell2.getText( ).toString( ) );
		editor.putString( "email2", edt_email2.getText( ).toString( ) );
		
		editor.commit( );
	}
	
	@Override
	protected void onResume( )
	{
		super.onResume( );
		Log.d( "", "onResume" );
		
	}
	
	@Override
	protected void onStop( )
	{
		super.onStop( );
		Log.d( "", "onStop" );
	}
	
	private void order( )
	{
		if ( check( ) )
		{
			StringBuilder stringBuilder = new StringBuilder( );
			for ( int k = 0; k < listOrderItem.size( ); k++ )
			{
				key[ k ] = listOrderItem.get( k ).getProduct( );
				
				View view = listViewOrder.getChildAt( k );
				EditText editText = ( EditText ) view.findViewById( R.id.edt_quantity );
				String string = editText.getText( ).toString( );
				
				String row = key[ k ] + "       " + string;
				if ( key[ k ] != "" && !string.equals( "" ) )
				{
					stringBuilder.append( row + "\n" );
				}
			}
			
			String content = customer.getText( ).toString( ) + "\n" +
					tv_full_name.getText( ).toString( ) + ": " + edt_full_name.getText( ).toString( ) + "\n" +
					tv_sex.getText( ).toString( ) + ": " + edt_sex.getText( ).toString( ) + "\n" +
					tv_address.getText( ).toString( ) + ": " + edt_address.getText( ).toString( ) + "\n" +
					tv_province.getText( ).toString( ) + ": " + edt_province.getText( ).toString( ) + "\n" +
					tv_tell.getText( ).toString( ) + ": " + edt_tell.getText( ).toString( ) + "\n" +
					tv_email.getText( ).toString( ) + ": " + edt_email.getText( ).toString( ) + "\n" + "\n" +
					product_info.getText( ).toString( ) + "\n" +
					product.getText( ).toString( ) + "      " + quantity.getText( ).toString( ) + "\n" +
					stringBuilder.toString( ) + "\n" +
					receiver.getText( ).toString( ) + "\n" +
					tv_full_name2.getText( ).toString( ) + ": " + edt_full_name2.getText( ).toString( ) + "\n" +
					tv_sex2.getText( ).toString( ) + ": " + edt_sex2.getText( ).toString( ) + "\n" +
					tv_address2.getText( ).toString( ) + ": " + edt_address2.getText( ).toString( ) + "\n" +
					tv_province2.getText( ).toString( ) + ": " + edt_province2.getText( ).toString( ) + "\n" +
					tv_tell2.getText( ).toString( ) + ": " + edt_tell2.getText( ).toString( ) + "\n" +
					tv_email2.getText( ).toString( ) + ": " + edt_email2.getText( ).toString( ) + "\n";
			
			SendEmailAsyncTask sendEmailAsyncTask = new SendEmailAsyncTask( edt_email2.getText( ).toString( ), content );
			sendEmailAsyncTask.execute( );
		}
	}
	
	public void setProductPosition( int position )
	{
		Log.d( "", "position first= " + position );
		this.position = position;
	}
}
