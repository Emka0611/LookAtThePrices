package com.example.products;

import java.util.List;

import com.example.database.DatabaseDataSources;

public class BarcodeList
{
	private List<Barcode> m_barcodesVector;

	public BarcodeList(long product_id)
	{
		DatabaseDataSources.barcodesDataSource.open();
		m_barcodesVector = DatabaseDataSources.barcodesDataSource.getAllBarcodes(product_id);
		DatabaseDataSources.barcodesDataSource.close();
	}

	public List<Barcode> getBarcodesVector()
	{
		return m_barcodesVector;
	}

	public String[] toArray()
	{
		String[] tab = new String[m_barcodesVector.size()];
		for(int i=0; i<m_barcodesVector.size(); i++)
		{
			tab[i] = m_barcodesVector.get(i).toString();
		}
		return tab;
	}

}
