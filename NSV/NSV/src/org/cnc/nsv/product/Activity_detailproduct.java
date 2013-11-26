package org.cnc.nsv.product;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.store;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnc.nsv.order.OrderActivity;

public class Activity_detailproduct extends Activity implements Constant
{
	
	TextView	title, title_name, titleLoai, quycach, loaihang,
				tieuchuan, duongkinh, gioihanhchay, gioihannut, dogianday, thuuonnguoi,
				tvSoLuongThanh, tvTrongLuong, tvSaiLech;
	ImageView	image;
	OjProduct	ojProduct;
	private int	position;
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_detailproduct );
		
		new createmenu( ).create( Activity_detailproduct.this );
		findViewById( R.id.menu_product ).setSelected( true );
		
		Intent t = getIntent( );
		position = t.getIntExtra( "position_product", -1 );
		
		title = ( TextView ) findViewById( R.id.detapro_title );
		title_name = ( TextView ) findViewById( R.id.detailpro_titleName );
		titleLoai = ( TextView ) findViewById( R.id.datailpro_titleloai );
		quycach = ( TextView ) findViewById( R.id.detapro_quycach );
		loaihang = ( TextView ) findViewById( R.id.detapro_loaihang );
		tieuchuan = ( TextView ) findViewById( R.id.detapro_tieuchuan );
		duongkinh = ( TextView ) findViewById( R.id.detapro_duongkinh );
		gioihanhchay = ( TextView ) findViewById( R.id.detapro_gioihanchay );
		gioihannut = ( TextView ) findViewById( R.id.detapro_gioihandut );
		dogianday = ( TextView ) findViewById( R.id.detapro_dogianday );
		thuuonnguoi = ( TextView ) findViewById( R.id.detapro_thuuongnguoi );
		tvSoLuongThanh = ( TextView ) findViewById( R.id.tvSoLuongThanh );
		tvTrongLuong = ( TextView ) findViewById( R.id.tvTrongLuong );
		tvSaiLech = ( TextView ) findViewById( R.id.tvSaiLech );
		image = ( ImageView ) findViewById( R.id.detailpro_image );
		
		TextView add_to_order = ( TextView ) findViewById( R.id.add_to_order );
		add_to_order.setOnClickListener( new OnClickListener( )
		{
			
			@Override
			public void onClick( View v )
			{
				Intent t = new Intent( Activity_detailproduct.this, OrderActivity.class );
				t.putExtra( "name_product", title.getText( ).toString( ) );
				startActivity( t );
			}
		} );
		
		ojProduct = store.OJ_PRODUCT;
		
		title.setText( ojProduct.getName( ) );
		title_name.setText( ojProduct.getName( ) );
		titleLoai.setText( ojProduct.getLoai_hang( ) );
		quycach.setText( Html.fromHtml( "<b>" + getString( R.string.quycach ) + " </b>" + ojProduct.getQuy_cach( ) ) );
		loaihang.setText( Html.fromHtml( "<b>" + getString( R.string.loaihang ) + " </b>" + ojProduct.getLoai_hang( ) ) );
		tieuchuan
				.setText( Html.fromHtml( "<b>" + getString( R.string.tieuchuan ) + " </b>" + ojProduct.getTieu_chuan( ) ) );
		duongkinh
				.setText( Html.fromHtml( "<b>" + getString( R.string.duongkinh ) + " </b>" + ojProduct.getDuong_kinh( ) ) );
		gioihanhchay.setText( Html.fromHtml( "<b>" + getString( R.string.gioihanchay ) + " </b>"
				+ ojProduct.getGioi_han_chay( ) ) );
		gioihannut.setText( Html.fromHtml( "<b>" + getString( R.string.gioihandut ) + " </b>"
				+ ojProduct.getGioi_han_nut( ) ) );
		dogianday
				.setText( Html.fromHtml( "<b>" + getString( R.string.dogiandai ) + " </b>" + ojProduct.getDo_dan_dai( ) ) );
		thuuonnguoi.setText( Html.fromHtml( "<b>" + getString( R.string.thuuonnguoi ) + " </b>"
				+ ojProduct.getThu_uong_nguoi( ) ) );
		if ( ojProduct.getTrongLuong( ).equals( "" ) )
			tvTrongLuong.setVisibility( View.GONE );
		else
			tvTrongLuong.setText( Html.fromHtml( "<b>" + getString( R.string.trongluong ) + " </b>"
					+ ojProduct.getTrongLuong( ) ) );
		if ( ojProduct.getSoLuongThanh( ).equals( "" ) )
			tvSoLuongThanh.setVisibility( View.GONE );
		else
			tvSoLuongThanh.setText( Html.fromHtml( "<b>" + getString( R.string.soluongthanh ) + " </b>"
					+ ojProduct.getSoLuongThanh( ) ) );
		if ( ojProduct.getSaiLech( ).equals( "" ) )
			tvSaiLech.setVisibility( View.GONE );
		else
			tvSaiLech
					.setText( Html.fromHtml( "<b>" + getString( R.string.sailech ) + " </b>" + ojProduct.getSaiLech( ) ) );
		
		String imageUrl = ojProduct.getImageUrl( );
		Handler loadImageHandler = new Handler( )
		{
			@Override
			public void handleMessage( Message msg )
			{
				super.handleMessage( msg );
				if ( msg.what == 1 && msg.obj != null )
				{
					try
					{
						image.setImageBitmap( ( Bitmap ) msg.obj );
					}
					catch ( Exception e )
					{
						e.printStackTrace( );
					}
				}
			}
		};
		if ( !imageUrl.equalsIgnoreCase( "" ) )
		{
			new Utils.LoadImageTask( loadImageHandler ).execute( imageUrl );
		}
		ImageView button_back = ( ImageView ) findViewById( R.id.button_back );
		button_back.setOnClickListener( new OnClickListener( )
		{
			
			@Override
			public void onClick( View v )
			{
				finish( );
				
			}
		} );
	}
	
}
