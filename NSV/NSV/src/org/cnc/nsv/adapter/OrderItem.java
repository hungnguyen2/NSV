package org.cnc.nsv.adapter;

public class OrderItem
{
	private String	product, quantity;
	
	public OrderItem( String product, String quantity )
	{
		this.product = product;
		this.quantity = quantity;
	}
	
	public String getProduct( )
	{
		return product;
	}
	
	public String getQuantity( )
	{
		return quantity;
	}
	
	public void setProduct( String product )
	{
		this.product = product;
	}
	
	public void setQuantity( String quantity )
	{
		this.quantity = quantity;
	}
}
