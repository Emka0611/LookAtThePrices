package com.emka.lookattheprices.model;

import java.util.List;

import com.emka.lookattheprices.database.DatabaseDataSources;

public class PriceHistory
{
	private List<Price> m_priceEntriesVector;

	public PriceHistory(long product_id)
	{
		DatabaseDataSources.pricesDataSource.open();
		m_priceEntriesVector = DatabaseDataSources.pricesDataSource.getAllPrices(product_id);
		DatabaseDataSources.pricesDataSource.close();
	}

	public List<Price> getPriceEntriesVector()
	{
		return m_priceEntriesVector;
	}

	public Price getBestPrice()
	{
		Price price = m_priceEntriesVector.get(0);

		for (int i = 0; i < m_priceEntriesVector.size(); i++)
		{
			if (m_priceEntriesVector.get(i).getUnitPrice() < price.getUnitPrice())
			{
				price = m_priceEntriesVector.get(i);
			}
		}

		return price;
	}

	public String[] toArray()
	{
		String[] tab = new String[m_priceEntriesVector.size()];
		for(int i=0; i<m_priceEntriesVector.size(); i++)
		{
			tab[i] = m_priceEntriesVector.get(i).toString();
		}
		return tab;
	}
}
