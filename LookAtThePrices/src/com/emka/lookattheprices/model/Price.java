package com.emka.lookattheprices.model;

import java.util.Date;

public class Price
{
	private int id;
	
	private double price;
	private Unit unit;
	private double quantity;	
	private Date date;

	public Price()
	{		
	}
	
	public Price(double price, Unit unit, double quantity)
	{
		this.price=price;
		this.unit=unit;
		this.quantity=quantity;
		this.date = new Date();
	}
	public int getId()
	{
		return id;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public Unit getUnit()
	{
		return unit;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public void setUnit(Unit unit)
	{
		this.unit = unit;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public double getUnitPrice()
	{
		double unitPrice = price/quantity;
		unitPrice *= 100;
		unitPrice = Math.round(unitPrice);
	    unitPrice/= 100; 
		
	    return unitPrice;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(price);
		builder.append("zl ");
		builder.append(getUnitPrice() + "zl/");
		builder.append(unit.getName());
		return builder.toString();
	}

	public String unitPriceToString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getUnitPrice() + "zl/");
		builder.append(unit.getName());
		return builder.toString();
	}

}
