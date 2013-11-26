package org.cnc.nsv.otheractivity;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseAnalytics;

public class NSV extends Activity
{
	Boolean	Activity	= true;
	int		FlashTime	= 1000;
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_splash );
		
		ParseAnalytics.trackAppOpened( getIntent( ) );
		
		Utils.preLoadData( getApplicationContext( ) );
		
		Thread FlashThread = new Thread( )
		{
			@Override
			public void run( )
			{
				try
				{
					int waited = 0;
					while ( Activity && waited < FlashTime )
					{
						sleep( 100 );
						if ( Activity )
						{
							waited += 100;
						}
					}
					
				}
				catch ( InterruptedException e )
				{
					
				}
				finally
				{
					
					finish( );
					startActivity( new Intent( getBaseContext( ),
							Activity_main.class ) );
					
				}
			}
			
		};
		FlashThread.start( );
		
	}
	
}
