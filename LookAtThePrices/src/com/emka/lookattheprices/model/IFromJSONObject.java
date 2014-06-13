package com.emka.lookattheprices.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface IFromJSONObject<T>
{
	public T convertFromJSONObject(JSONObject json) throws JSONException;
	public String getName();
}
