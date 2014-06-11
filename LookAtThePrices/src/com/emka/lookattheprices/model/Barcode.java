package com.emka.lookattheprices.model;


public class Barcode
{
	private int id;
	private String barcode;

	public Barcode()
	{
	}

	public Barcode(String barcode)
	{
		this.barcode = barcode;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getBarcode()
	{
		return barcode;
	}

	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}

	@Override
	public String toString()
	{
		return barcode;
	}
}