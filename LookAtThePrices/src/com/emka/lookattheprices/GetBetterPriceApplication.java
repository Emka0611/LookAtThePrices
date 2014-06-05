package com.emka.lookattheprices;

import com.example.database.DatabaseDataSources;

import android.app.Application;

public class GetBetterPriceApplication extends Application
{
	@Override
	public void onCreate()
	{
		new DatabaseDataSources(this);
		super.onCreate();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
}