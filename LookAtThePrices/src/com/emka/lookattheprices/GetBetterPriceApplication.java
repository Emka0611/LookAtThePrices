package com.emka.lookattheprices;

import com.emka.lookattheprices.datasource.DatabaseDataSource;

import android.app.Application;

public class GetBetterPriceApplication extends Application
{
	@Override
	public void onCreate()
	{
		new DatabaseDataSource();
		super.onCreate();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
}