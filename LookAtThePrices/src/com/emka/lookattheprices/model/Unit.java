package com.emka.lookattheprices.model;

import org.json.JSONException;
import org.json.JSONObject;


public class Unit implements IFromJSONObject<Unit>
{
	private int id;
	
	private String name;

	public Unit()
	{	
	}
	public Unit(String name)
	{
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
	@Override
	public Unit convertFromJSONObject(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
