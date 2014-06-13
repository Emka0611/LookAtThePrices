package com.emka.lookattheprices.datasource;

import java.util.ArrayList;
import java.util.List;

import com.emka.lookattheprices.model.Product;

public class ProductDataSource extends ObjectDataSource implements IProductDataSource
{
	
	ProductDataSource(String name)
	{
		super(name);
	}

	@Override
	public List<Product> getProductsByCategoryId(long catId) {
		// TODO Auto-generated method stub
		return new ArrayList<Product>();
	}

	@Override
	public Product findByName(String name) {
		// TODO Auto-generated method stub
		return new Product();
	}

}
