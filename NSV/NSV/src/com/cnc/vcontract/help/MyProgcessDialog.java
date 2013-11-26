package com.cnc.vcontract.help;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgcessDialog
{
	private static ProgressDialog	progressDialog;
	
	public static final void hide( )
	{
		if ( progressDialog != null )
		{
			progressDialog.dismiss( );
		}
	}
	
	public static final void show( Context context, String message )
	{
		progressDialog = new ProgressDialog( context );
		progressDialog.setMessage( message );
		progressDialog.show( );
	}
}
