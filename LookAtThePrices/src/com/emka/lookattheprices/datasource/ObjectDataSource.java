package com.emka.lookattheprices.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.emka.lookattheprices.webservice.WebServiceTask;

public class ObjectDataSource implements IObjectDataSource
{
	String name = "";

	ObjectDataSource(String name)
	{
		this.name = name;
	}
	
	@Override
	public JSONArray findAll()
	{
		String output = "";
		
        WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, "Getting data...");
        try
        {
        	Log.d("WebServiceTask", "Execute::http://emkaserviceexample.emka0611.cloudbees.net/webapi/" + name);
			output = wst.execute(new String[] { "http://emkaserviceexample.emka0611.cloudbees.net/webapi/" + name} ).get();
			Log.d("WebServiceTask", "Output::" + output);
		}
        catch (InterruptedException e)
        {
        	e.printStackTrace();
		}
        catch (ExecutionException e)
        {
        	e.printStackTrace();
		}
        
        JSONArray jsonArray = null;
        
        try
        {
			jsonArray = new JSONArray(output);			
		}
        catch (JSONException e)
		{
			e.printStackTrace();
		}
        
		return jsonArray;
	}

	@Override
	public JSONObject findById(int id)
	{
		JSONObject element = new JSONObject();
		// TODO Auto-generated method stub
		return element;
	}

	@Override
	public JSONObject create(JSONObject element)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject update(JSONObject element)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id)
	{
		return false;
		// TODO Auto-generated method stub	
	}

}
