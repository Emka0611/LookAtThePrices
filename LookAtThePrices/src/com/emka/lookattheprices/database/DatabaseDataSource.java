package com.emka.lookattheprices.database;

import java.util.ArrayList;
import java.util.List;

import com.emka.lookattheprices.database.datasource.ObjectDataSource;
import com.emka.lookattheprices.database.datasource.ProductDataSource;
import com.emka.lookattheprices.model.Barcode;
import com.emka.lookattheprices.model.Category;
import com.emka.lookattheprices.model.Price;
import com.emka.lookattheprices.model.Product;
import com.emka.lookattheprices.model.Unit;

public class DatabaseDataSource
{
	public static ObjectDataSource<Category> categoriesDataSource;
	public static ObjectDataSource<Unit> unitsDataSource;
	
	public static ProductDataSource productsDataSource;

	public DatabaseDataSource()
	{
		categoriesDataSource = new ObjectDataSource<Category>();
		unitsDataSource = new ObjectDataSource<Unit>();
		productsDataSource = new ProductDataSource();
	}

	public static Product addPrice(int productId, double price, double quantity, Unit unit)
	{
		Price newPrice = new Price(price, unit, quantity);
		
		Product product = productsDataSource.findById(productId);
		product.addPrice(newPrice);
		
		product = productsDataSource.update(product);
		
		return product;
	}

	public static Product addProduct(String name, Category category)
	{
		Product newProduct = new Product(name, category);
		newProduct = productsDataSource.create(newProduct);
		return newProduct;
	}
	
	public static Product addProduct(String name, Category category, Price price)
	{
		Product newProduct = new Product(name, category);
		newProduct.addPrice(price);
		
		newProduct = productsDataSource.create(newProduct);
		return newProduct;
	}
	
	public static Product updateProduct(Product updatedProduct)
	{
		updatedProduct = productsDataSource.update(updatedProduct);
		return updatedProduct;
	}

	public static Unit addUnit(String name)
	{
		Unit newUnit = new Unit(name);
		newUnit = unitsDataSource.create(newUnit);
		return newUnit;
	}

	public static Category addCategory(String name)
	{
		Category newCat = new Category(name);
		newCat = categoriesDataSource.create(newCat);
		return newCat;
	}

	public static Product addBarcode(int productId, String barcode)
	{
		Barcode newBarcode = new Barcode(barcode);
		
		Product product = productsDataSource.findById(productId);
		product.addBarcode(newBarcode);
		
		product = productsDataSource.update(product);

		return product;
	}

	public static boolean deleteUnit(Unit unit)
	{
		boolean fRes = true;
		fRes = unitsDataSource.delete(unit.getId());
		return fRes;
	}

	public static boolean deleteCategory(Category category)
	{
		boolean fRes = true;
		fRes = categoriesDataSource.delete(category.getId());
		return fRes;
	}

	public static boolean deleteProduct(int productId)
	{
		boolean fRes = true;
		fRes = productsDataSource.delete(productId);
		return fRes;
	}

	public static List<Product> getProductsOfCategory(long catId)
	{
		return productsDataSource.getProductsByCategoryId(catId);
	}

	public static Product getProduct(String name)
	{
		return productsDataSource.findByName(name);
	}

	public static Product getProduct(int id)
	{
		return productsDataSource.findById(id);
	}

	public static Category getCategory(int id)
	{
		return categoriesDataSource.findById(id);
	}

	public static List<Unit> getAllUnits()
	{
		return unitsDataSource.findAll();
	}

	public static List<Category> getAllCategories()
	{
		return categoriesDataSource.findAll();
	}

	public static List<Barcode> getAllBarcodes(int id)
	{
		return productsDataSource.findById(id).getBarcodesList();
	}

	public static List<Product> getAllProducts()
	{
		return productsDataSource.findAll();
	}

	public static List<Product> getProducts(String substring)
	{
		ArrayList<Product> list = (ArrayList<Product>) productsDataSource.findAll();
		ArrayList<Product> newList = new ArrayList<Product>(); 
	
		for (int i = 0; i < list.size(); i++)
		{
			if(false != list.get(i).getName().contains(substring))
			{
				newList.add(list.get(i));
			}
	
		}
		return newList;
	}

	
	/*	public static List<Barcode> getBarcodesFromDatabase(String barcode)
	{
		return barcodesDataSource.getAllBarcodes(barcode);
	} TODO: Barcode nie wie do jakiego jest produktu przypisany
	 */

}
