package com.emka.lookattheprices.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Product implements Comparable<Product>, IFromJSONObject<Product>
{
	private int id;

	private String name;
	private Category category;
	private List<Price> priceHistory = new ArrayList<Price>();
	private List<Barcode> barcodesList = new ArrayList<Barcode>();
	
	public Product()
	{		
	}
	
	public Product(String name, Category category)
	{
		this.name = name;
		this.category = category;
	}
	
	public Product(int id, String name, Category category)
	{
		this.name = name;
		this.category = category;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Category getCategory()
	{
		return category;
	}
	
	public void setCategory(Category category)
	{
		this.category = category;
	}

	public List<Price> getPriceHistory()
	{
		return priceHistory;
	}
	
	public String[] priceHistoryToArray()
	{
		String[] tab = new String[priceHistory.size()];
		for(int i=0; i<priceHistory.size(); i++)
		{
			tab[i] = priceHistory.get(i).toString();
		}
		return tab;
	}

	public void setPriceHistory(List<Price> priceHistory)
	{
		this.priceHistory = priceHistory;
	}
	
	public Price getLatestPrice()
	{
		int location = priceHistory.size()-1;
		return priceHistory.get(location);
	}
	
	public void addPrice(Price price)
	{
		priceHistory.add(price);
	}

	public List<Barcode> getBarcodesList()
	{
		return barcodesList;
	}
	
	public String[] barcodesListToArray()
	{
		String[] tab = new String[barcodesList.size()];
		for(int i=0; i<barcodesList.size(); i++)
		{
			tab[i] = barcodesList.get(i).toString();
		}
		return tab;
	}

	public void setBarcodesList(List<Barcode> barcodesList)
	{
		this.barcodesList = barcodesList;
	}

	public void addBarcode(Barcode barcode)
	{
		barcodesList.add(barcode);		
	}

	@Override
	public int compareTo(Product arg)
	{
		return getName().compareTo(arg.getName());
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		return builder.toString();
	}

	public Price getBestPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product convertFromJSONObject(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
}
