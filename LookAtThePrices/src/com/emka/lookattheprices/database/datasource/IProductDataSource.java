package com.emka.lookattheprices.database.datasource;

import java.util.List;

import com.emka.lookattheprices.model.Product;

public interface IProductDataSource extends IObjectDataSource<Product>
{
	public List<Product> getProductsByCategoryId(long catId);
	public Product findByName(String name);
}
