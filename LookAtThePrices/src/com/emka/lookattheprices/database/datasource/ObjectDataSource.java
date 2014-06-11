package com.emka.lookattheprices.database.datasource;

import java.util.ArrayList;
import java.util.List;

public class ObjectDataSource<T> implements IObjectDataSource<T>
{
	@Override
	public List<T> findAll()
	{
		// TODO Auto-generated method stub
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

}
