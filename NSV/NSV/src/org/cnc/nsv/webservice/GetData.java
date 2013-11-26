package org.cnc.nsv.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.cnc.nsv.Constant;

public class GetData
{
	HttpClient		mClient;
	HttpPost		mPost;
	HttpResponse	mResponse;
	HttpEntity		mEntity;
	HttpParams		mParams;
	BufferedReader	reader;
	String			line	= "";
	
	public GetData( )
	{
		mClient = new DefaultHttpClient( );
		mParams = new BasicHttpParams( );
		mParams.setParameter( CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1 );
		mClient = new DefaultHttpClient( mParams );
		
	}
	
	public String get( String url )
	{
		HttpContext localContext = new BasicHttpContext( );
		HttpGet httpGet = new HttpGet( Constant.API_URL + url );
		String text = null;
		try
		{
			HttpResponse response = mClient.execute( httpGet, localContext );
			HttpEntity entity = response.getEntity( );
			text = EntityUtils.toString( entity );// getASCIIContentFromEntity(entity);
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
		return text;
	}
	
	protected String getASCIIContentFromEntity( HttpEntity entity )
			throws IllegalStateException, IOException
	{
		InputStream in = entity.getContent( );
		StringBuffer out = new StringBuffer( );
		int n = 1;
		while ( n > 0 )
		{
			byte[] b = new byte[ 4096 ];
			n = in.read( b );
			if ( n > 0 )
				out.append( new String( b, 0, n ) );
		}
		return out.toString( );
	}
	
}
