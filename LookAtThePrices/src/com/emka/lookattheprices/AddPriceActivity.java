package com.emka.lookattheprices;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emka.lookattheprices.database.DatabaseDataSource;
import com.emka.lookattheprices.model.Product;
import com.emka.lookattheprices.model.Unit;

public class AddPriceActivity extends Activity
{
	// name
	private TextView mProductNameField = null;
	
	// to create price
	private EditText mPriceValueField = null;
	private EditText mQuantityField = null;
	private Spinner mUnitsSpinner = null;

	// unit price
	private TextView mUnitPriceValue = null;
	private TextView mUnitName = null;

	// product
	private int mProductId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_price);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getProductId();
		initSpinners();
		initEditTexts();
	}
	
	private void initSpinners()
	{
		List<Unit> unitsList = DatabaseDataSource.getAllUnits();
		ArrayAdapter<Unit> unitsAdapter = new ArrayAdapter<Unit>(this, android.R.layout.simple_spinner_item, unitsList);
		unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mUnitsSpinner = (Spinner) findViewById(R.id.unit_spinner);
		mUnitsSpinner.setAdapter(unitsAdapter);
		mUnitsSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				updateUnitPrice();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
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
	
	private void initEditTexts()
	{
		mProductNameField = (TextView) findViewById(R.id.product_name);
		mPriceValueField = (EditText) findViewById(R.id.price_input);
		mQuantityField = (EditText) findViewById(R.id.quantity_input);
		mUnitPriceValue = (TextView) findViewById(R.id.unit_price_value);
		mUnitName = (TextView) findViewById(R.id.unit_name);
		
		initProductName();
		updateUnitPrice();
	}
	
	private void updateUnitPrice()
	{
		String unitCurrency = getResources().getString(R.string.zloty);
		String unitName = ((Unit) mUnitsSpinner.getSelectedItem()).getName();

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
	
	private void initProductName()
	{
		String productName = DatabaseDataSource.getProduct(mProductId).getName();
		mProductNameField.setText(productName);
	}
	
	private void getProductId()
	{
		if (false != getIntent().getExtras().containsKey(ProductsSectionFragment.PRODUCT_SELECTED))
		{
			mProductId = getIntent().getExtras().getInt(ProductsSectionFragment.PRODUCT_SELECTED);
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
			// to create price
			double priceValue = Double.parseDouble(mPriceValueField.getText().toString());
			double quantity = Double.parseDouble(mQuantityField.getText().toString());
			Unit unit = (Unit) mUnitsSpinner.getSelectedItem();

			Product updatedPrice = DatabaseDataSource.addPrice(mProductId, priceValue, quantity, unit);
			
			if (null != updatedPrice)
			{
				Toast.makeText(this, "Cene dodano pomyslnie", Toast.LENGTH_SHORT).show();
				onBackPressed();
			}
			else
			{
				Toast.makeText(this, "Blad przy zapisywaniu ceny", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_SHORT).show();
			
		}
	}

}
