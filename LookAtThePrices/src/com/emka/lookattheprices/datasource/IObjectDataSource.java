package com.emka.lookattheprices.datasource;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IObjectDataSource
{
	JSONArray findAll();

	JSONObject findById(int id);

	JSONObject create(JSONObject element);

	JSONObject update(JSONObject element);

	boolean delete(int id);
}
