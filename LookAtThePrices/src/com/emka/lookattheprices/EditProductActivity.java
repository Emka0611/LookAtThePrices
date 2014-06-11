package com.emka.lookattheprices;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.emka.lookattheprices.database.DatabaseDataSource;
import com.emka.lookattheprices.model.Category;
import com.emka.lookattheprices.model.Product;

public class EditProductActivity extends Activity
{
	// to update product
	private EditText mProductNameField = null;
	private Spinner mCategorySpinner = null;

	// spinner
	List<Category> categoriesList = null;
	private ArrayAdapter<Category> categoriesAdapter = null;

	// product
	private int mProductId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getProductId();	
		initSpinners();
		initEditTexts();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	public void onCancelButtonClick(View view)
	{
		onBackPressed();
	}

	public void onSaveButtonClick(View view)
	{
		if (false != validateForm())
		{
			// to update product
			String name = mProductNameField.getText().toString();
			Category category= (Category) mCategorySpinner.getSelectedItem();
			Product updatedProduct = new Product(mProductId, name, category);

			DatabaseDataSource.updateProduct(updatedProduct);
			Toast.makeText(this, "Produkt zmieniono pomyslnie", Toast.LENGTH_SHORT).show();
			onBackPressed();
		}
		else
		{
			Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_SHORT).show();

		}
	}
	
	private void initSpinners()
	{
		categoriesList = DatabaseDataSource.getAllCategories();
		categoriesAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoriesList);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner = (Spinner) findViewById(R.id.cat_spinner);
		mCategorySpinner.setAdapter(categoriesAdapter);
	}

	private void initEditTexts()
	{
		mProductNameField = (EditText) findViewById(R.id.product_name);
		setProductParameters();
	}

	private void setProductParameters()
	{
		Product product = DatabaseDataSource.getProduct(mProductId);
		Category category = getCategory(product.getCategory().getId());

		int categoryPosition = categoriesAdapter.getPosition(category);

		mProductNameField.setText(product.getName());
		mCategorySpinner.setSelection(categoryPosition);
	}

	private Category getCategory(long categoryId)
	{
		Category category = null;

		for (int i = 0; i < categoriesList.size(); i++)
		{
			if(categoriesList.get(i).getId() == categoryId)
			{
				category = categoriesList.get(i);
			}
		}

		return category;
	}

	private boolean validateForm()
	{
		boolean fRes = false;

		if (0 != mProductNameField.getText().toString().length())
		{
			fRes = true;
		}

		return fRes;
	}

	private void getProductId()
	{
		if (false != getIntent().getExtras().containsKey(ProductsSectionFragment.PRODUCT_SELECTED))
		{
			mProductId = getIntent().getExtras().getInt(ProductsSectionFragment.PRODUCT_SELECTED);
		}
	}

}
