package com.emka.lookattheprices.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.util.Log;

import com.emka.lookattheprices.webservice.AsyncResponse;
import com.emka.lookattheprices.webservice.WebServiceTask;

public class ObjectDataSource<T> implements IObjectDataSource<T>, AsyncResponse
{

	@Override
	public List<T> findAll()
	{
        WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, "Getting data...");
        try
        {
        	Log.d("ObjectDataSource", "Execute::http://emkaserviceexample.emka0611.cloudbees.net/webapi/products...");
			String output = wst.execute(new String[] { "http://emkaserviceexample.emka0611.cloudbees.net/webapi/products"} ).get();
			Log.d("ObjectDataSource", "Output::" + output);
		}
        catch (InterruptedException e)
        {
        	e.printStackTrace();
		}
        catch (ExecutionException e)
        {
        	e.printStackTrace();
		}
        
		return new ArrayList<T>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(int id)
	{
		T element = (T) new Object();
		// TODO Auto-generated method stub
		return element;
	}

	@Override
	public T create(T element)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T update(T element)
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

	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		
	}

}
