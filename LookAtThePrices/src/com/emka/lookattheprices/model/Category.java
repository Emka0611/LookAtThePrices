package com.emka.lookattheprices.model;

import org.json.JSONException;
import org.json.JSONObject;


public class Category implements IFromJSONObject<Category>
{
	private int id;

	private String name;
	
	public Category()
	{
	}
	
	public Category(String name)
	{
		this.name = name;
	}
	
	public Category(int id, String name)
	{
		this.id = id;
		this.name = name;
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

	@Override
	public String toString()
	{
		return name;
	}
	
	public Category convertFromJSONObject(JSONObject json) throws JSONException
	{
		int id = json.getInt("id");
		String name = json.getString("name");
		return new Category(id, name);
	}
}
