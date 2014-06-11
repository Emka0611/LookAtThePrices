package com.emka.lookattheprices.database.datasource;

import java.util.List;

public interface IObjectDataSource<T>
{
	List<T> findAll();

	T findById(int id);

	T create(T element);

	T update(T element);

	boolean delete(int id);
}
