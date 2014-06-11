package com.emka.lookattheprices;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emka.lookattheprices.database.DatabaseDataSource;
import com.emka.lookattheprices.model.Category;
import com.emka.lookattheprices.model.Price;
import com.emka.lookattheprices.model.Product;
import com.emka.lookattheprices.model.Unit;

public class NewProductActivity extends Activity
{
	// to create product
	private EditText mProductNameField = null;
	private Spinner mCategorySpinner = null;

	// to create price
	private EditText mPriceValueField = null;
	private EditText mQuantityField = null;
	private Spinner mUnitsSpinner = null;

	// unit price
	private TextView mUnitPriceValue = null;
	private TextView mUnitName = null;

	// barcode
	private EditText mBarcodeField = null;
	private String mBarcode = "";

	private static final int GET_BARCODE_REQUEST = 1;
	public static final String BARCODE = "barcode";

	// scan
	private MenuItem menuItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_product);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		initSpinners();
		initEditTexts();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == GET_BARCODE_REQUEST)
		{
			if (resultCode == RESULT_OK)
			{
				mBarcode = data.getExtras().getString(BARCODE);
				mBarcodeField.setText(mBarcode);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_scan, menu);
		menuItem = menu.findItem(R.id.action_scan);
		enableScanButton();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
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

	public void onCalculateButtonClick(View view)
	{
		updateUnitPrice();
	}

	public void onCancelButtonClick(View view)
	{
		onBackPressed();
	}

	public void onSaveButtonClick(View view)
	{
		if (false != validateForm())
		{
			// to create product
			String generalName = mProductNameField.getText().toString();
			Category category = (Category) mCategorySpinner.getSelectedItem();

			// to create price
			double priceValue = Double.parseDouble(mPriceValueField.getText().toString());
			double quantity = Double.parseDouble(mQuantityField.getText().toString());
			Unit unit = (Unit) mUnitsSpinner.getSelectedItem();

			// to create barcode
			String barcode = mBarcodeField.getText().toString();

			Price price = new Price(priceValue, unit, quantity);
			Product newProduct = DatabaseDataSource.addProduct(generalName, category, price);

			if (null != newProduct)
			{
				if (0 != barcode.length())
				{
					DatabaseDataSource.addBarcode(newProduct.getId(), barcode);
				}
				
				Toast.makeText(this, "Produkt dodano pomyslnie", Toast.LENGTH_SHORT).show();
				onBackPressed();
			}
			else
			{
				reportError();
			}
		}
		else
		{
			Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_SHORT).show();

		}
	}

	private void initSpinners()
	{
		List<Category> categoriesList = DatabaseDataSource.getAllCategories();
		ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoriesList);
		categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner = (Spinner) findViewById(R.id.cat_spinner);
		mCategorySpinner.setAdapter(categoriesAdapter);

		List<Unit> unitsList = DatabaseDataSource.getAllUnits();
		ArrayAdapter<Unit> unitsAdapter = new ArrayAdapter<Unit>(this, android.R.layout.simple_spinner_item, unitsList);
		unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mUnitsSpinner = (Spinner) findViewById(R.id.unit_spinner);
		mUnitsSpinner.setAdapter(unitsAdapter);
		mUnitsSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				updateUnitPrice();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
	}

	private void initEditTexts()
	{
		mProductNameField = (EditText) findViewById(R.id.product_name);
		mPriceValueField = (EditText) findViewById(R.id.price_input);
		mQuantityField = (EditText) findViewById(R.id.quantity_input);
		mUnitPriceValue = (TextView) findViewById(R.id.unit_price_value);
		mUnitName = (TextView) findViewById(R.id.unit_name);
		mBarcodeField = (EditText) findViewById(R.id.barcode_input);

		updateUnitPrice();
	}

	private void enableScanButton()
	{
		menuItem.setActionView(R.layout.actionbar_scan_button);
		menuItem.getActionView().findViewById(R.id.actionbar_scan).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startScanningActivity();
			}

		});
	}

	private void startScanningActivity()
	{
		Intent i = new Intent(this, ScanditActivity.class);
		startActivityForResult(i, GET_BARCODE_REQUEST);
	}

	private void updateUnitPrice()
	{
		if(null != mUnitsSpinner.getSelectedItem())
		{
			String unitName = ((Unit) mUnitsSpinner.getSelectedItem()).getName();	
			String unitCurrency = getResources().getString(R.string.zloty);
	
			mUnitName.setText(unitCurrency + "/" + unitName);
	
			if (0 != mPriceValueField.getText().toString().length() && 0 != mQuantityField.getText().toString().length())
			{
				double priceValue = Double.parseDouble(mPriceValueField.getText().toString());
				double quantity = Double.parseDouble(mQuantityField.getText().toString());
	
				Double unitPrice = priceValue / quantity;
				unitPrice *= 100;
				unitPrice = (double) Math.round(unitPrice);
				unitPrice /= 100;
	
				mUnitPriceValue.setText(unitPrice.toString());
			}
		}
	}

	private boolean validateForm()
	{
		boolean fRes = false;

		if (0 != mProductNameField.getText().toString().length())
		{
			fRes = true;
		}

		try
		{
			Double.parseDouble(mPriceValueField.getText().toString());
			Double.parseDouble(mQuantityField.getText().toString());
		}
		catch (NumberFormatException e)
		{
			fRes = false;
		}

		return fRes;
	}

	private void reportError()
	{
		Toast.makeText(this, "Blad przy zapisywaniu produktu", Toast.LENGTH_SHORT).show();
	}
}
