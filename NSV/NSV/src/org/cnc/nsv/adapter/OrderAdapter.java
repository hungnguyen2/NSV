package org.cnc.nsv.adapter;

import java.util.List;

import org.cnc.nsv.R;
import org.cnc.nsv.product.Activity_production;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.cnc.nsv.order.OrderActivity;

public class OrderAdapter extends ArrayAdapter< OrderItem >
{
	class ViewHolder
	{
		private TextView	product;
		private EditText	quantity;
	}
	
	List< OrderItem >	listOrder;
	
	Activity			activity;
	
	public OrderAdapter( Activity context, List< OrderItem > objects )
	{
		super( context, R.layout.item_order, objects );
		
		listOrder = objects;
		activity = context;
	}
	
	@Override
	public View getView( final int position, View convertView, ViewGroup parent )
	{
		View rowView = convertView;
		
		if ( rowView == null )
		{
			final LayoutInflater inflater = this.activity.getLayoutInflater( );
			rowView = inflater.inflate( R.layout.item_order, null );
			
			final ViewHolder viewHolder = new ViewHolder( );
			viewHolder.product = ( TextView ) rowView.findViewById( R.id.tv_product );
			viewHolder.quantity = ( EditText ) rowView.findViewById( R.id.edt_quantity );
			rowView.setTag( viewHolder );
		}
		final ViewHolder holder = ( ViewHolder ) rowView.getTag( );
		
		OrderItem orderItem = listOrder.get( position );
		
		if ( !orderItem.getProduct( ).equals( "" ) )
		{
			holder.product.setText( orderItem.getProduct( ) );
		}
		
		if ( !orderItem.getQuantity( ).equals( "" ) )
		{
			holder.quantity.setText( orderItem.getQuantity( ) );
			
		}
		
		holder.product.setOnClickListener( new OnClickListener( )
		{
			
			@Override
			public void onClick( View v )
			{
				( ( OrderActivity ) OrderAdapter.this.activity ).setProductPosition( position );
				
				Intent t = new Intent( OrderAdapter.this.activity, Activity_production.class );
				t.putExtra( "position_product", position );
				( ( OrderActivity ) OrderAdapter.this.activity ).startActivity( t );
			}
		} );
		
		// holder.quantity.setText( orderItem.getQuantity( ) );
		
		return rowView;
	}
	
}
