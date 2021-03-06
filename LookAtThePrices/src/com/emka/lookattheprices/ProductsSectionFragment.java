package com.emka.lookattheprices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.emka.lookattheprices.datasource.DatabaseDataSource;
import com.emka.lookattheprices.model.Category;
import com.emka.lookattheprices.model.Product;

public class ProductsSectionFragment extends Fragment
{
	private AutoCompleteTextView mActionBarEditText;
	private ListView listView;
	private View mRootView;
	private InputMethodManager mImm;
	private ActionBar mActionBar;
	private SeparatedListAdapter mAdapter;
	private int mDisplayOptions;
	private boolean mIsSearchModeEnabled = false;
	private Menu mMenu;

	final static String PRODUCT_SELECTED = "selected_product";
	public final static String ITEM_NAME = "name";
	public final static String ITEM_PRICE = "price";

	public static final int GET_BARCODE_REQUEST = 1;
	public static final String BARCODE = "barcode";
	private String mBarcode = "";
	private boolean mReturnedFromScanning = false;

	int selectedProductId = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		mRootView = inflater.inflate(R.layout.fragment_list, container, false);

		mActionBarEditText = (AutoCompleteTextView) inflater.inflate(R.layout.actionbar_search_product_edittext, null);
		setUpActionBarEditText();

		mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		mActionBar = getActivity().getActionBar();
		setUpListView();
		mDisplayOptions = mActionBar.getDisplayOptions();

		return mRootView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		mMenu = menu;
		getActivity().getMenuInflater().inflate(R.menu.menu_products, menu);
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_new:
				Intent i = new Intent(getActivity(), NewProductActivity.class);
				startActivity(i);
				break;
			case R.id.action_search:
				enableSearchMode(true);
				break;
			case R.id.action_scan:
				startScanningActivity();
				break;
			case R.id.action_edit_item:
				onEditItemClick();
				break;
			case R.id.action_delete_item:
				onDeleteItemClick();
				break;
			case R.id.action_list_prices:
				onListPriceItemClick();
				break;
			case R.id.action_list_barcodes:
				onListBarcodesItemClick();
				break;
			case R.id.action_add_price:
				onAddPriceItemClick();
				break;
			case R.id.action_add_barcode:
				onAddBarcodeItemClick();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onListBarcodesItemClick()
	{
		onCreateBarcodesDialog().show();
	}

	private void onListPriceItemClick()
	{
		onCreatePricesDialog().show();
	}

	private void onAddPriceItemClick()
	{
		Intent i = new Intent(getActivity(), AddPriceActivity.class);
		i.putExtra(PRODUCT_SELECTED, selectedProductId);
		getActivity().startActivity(i);
	}

	private void onAddBarcodeItemClick()
	{
		Intent i = new Intent(getActivity(), AddBarcodeActivity.class);
		i.putExtra(PRODUCT_SELECTED, selectedProductId);
		getActivity().startActivity(i);
	}

	private void onDeleteItemClick()
	{
		DatabaseDataSource.deleteProduct(selectedProductId);
		Toast.makeText(getActivity(), "Produkt usuni�to pomy�lnie", Toast.LENGTH_LONG).show();
		onResume();
	}

	private void onEditItemClick()
	{
		Intent i = new Intent(getActivity(), EditProductActivity.class);
		i.putExtra(PRODUCT_SELECTED, selectedProductId);
		getActivity().startActivity(i);

	}

	@Override
	public void onResume()
	{
		if (false == isSearchModeEnabled() && false == mReturnedFromScanning)
		{
			addAll(DatabaseDataSource.getAllProducts(this.getActivity()));
		}
		mReturnedFromScanning = false;
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == GET_BARCODE_REQUEST)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				mBarcode = data.getExtras().getString(BARCODE);
				performSearchByBarcode(mBarcode);
			}
		}
	}

	public void enableSearchMode(boolean enable)
	{
		mIsSearchModeEnabled = enable;

		if (false != enable)
		{
			mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
			mActionBar.setCustomView(mActionBarEditText);
			mActionBarEditText.requestFocus();
			showKeybord(true);
			mMenu.findItem(R.id.action_scan).setVisible(true);
			mMenu.findItem(R.id.action_search).setVisible(false);
			mMenu.findItem(R.id.action_new).setVisible(false);
		}
		else
		{
			mActionBarEditText.setText("");
			mActionBar.setDisplayOptions(mDisplayOptions);
			mMenu.findItem(R.id.action_search).setVisible(true);
			mMenu.findItem(R.id.action_new).setVisible(true);
			mMenu.findItem(R.id.action_scan).setVisible(false);
			addAll(DatabaseDataSource.getAllProducts(this.getActivity()));
		}
	}

	public boolean isSearchModeEnabled()
	{
		return mIsSearchModeEnabled;
	}

	public void updateListAdapter(List<Product> resultList)
	{
		if (null != resultList)
		{
			addAll(resultList);
		}
		else
		{
			mAdapter.clear();
			mAdapter.notifyDataSetChanged();
		}
	}

	private void sortList(List<Product> list)
	{
		Collections.sort(list);
	}

	private void setUpAdapter()
	{
		mAdapter = new SeparatedListAdapter(getActivity());
		List<Category> catList = DatabaseDataSource.getAllCategories();

		List<Product> list = null;
		SimpleAdapter adapter = null;

		for (int i = 0; i < catList.size(); i++)
		{
			List<Map<String, ?>> listMap = new LinkedList<Map<String, ?>>();
			list = DatabaseDataSource.getProductsOfCategory(catList.get(i).getId());
			sortList(list);
			for (int j = 0; j < list.size(); j++)
			{
				listMap.add(createItem(list.get(j)));
			}

			adapter = new CarListAdapter(getActivity(), listMap, R.layout.rowlayout, new String[] { ITEM_NAME, ITEM_PRICE }, new int[] { R.id.text1, R.id.secondLine });
			if (0 < list.size())
			{
				mAdapter.addSection(catList.get(i).getName(), adapter);
			}
		}
	}

	private void addAll(List<Product> resultList)
	{
		mAdapter.clear();
		List<Category> catList = DatabaseDataSource.getAllCategories();

		List<Product> list = null;
		SimpleAdapter adapter = null;

		for (int i = 0; i < catList.size(); i++)
		{
			List<Map<String, ?>> listMap = new LinkedList<Map<String, ?>>();
			list = getProductsOfCategory(resultList, catList.get(i).getId());
			sortList(list);
			for (int j = 0; j < list.size(); j++)
			{
				listMap.add(createItem(list.get(j)));
			}

			adapter = new CarListAdapter(getActivity(), listMap, R.layout.rowlayout, new String[] { ITEM_NAME, ITEM_PRICE }, new int[] { R.id.text1, R.id.secondLine });
			if (0 < list.size())
			{
				mAdapter.addSection(catList.get(i).getName(), adapter);
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	private void setUpActionBarEditText()
	{
		mActionBarEditText.setAdapter(new ProductsAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1));
		mActionBarEditText.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3)
			{
				List<Product> list = new ArrayList<Product>();
				list.add((Product) adapterView.getItemAtPosition(position));
				addAll(list);
			}
		});
	}

	private void setUpListView()
	{
		setUpAdapter();
		listView = (ListView) mRootView.findViewById(R.id.list);
		listView.setAdapter(mAdapter);
	}

	private Map<String, ?> createItem(Product product)
	{
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_NAME, product.getName());
		item.put(ITEM_PRICE, product.getBestPrice().unitPriceToString());
		return item;
	}

	private void showKeybord(boolean state)
	{
		if (false != state)
		{
			mImm.showSoftInput(mActionBarEditText, InputMethodManager.SHOW_IMPLICIT);
		}
		else
		{
			mImm.hideSoftInputFromWindow(mActionBarEditText.getWindowToken(), 0);
		}
	}

	private List<Product> getProductsOfCategory(List<Product> resultList, long catId)
	{
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < resultList.size(); i++)
		{
			if (resultList.get(i).getCategory().getId() == catId)
			{
				products.add(resultList.get(i));
			}
		}
		return products;
	}

	private void startScanningActivity()
	{
		Intent i = new Intent(getActivity(), ScanditActivity.class);
		startActivityForResult(i, GET_BARCODE_REQUEST);
	}

	private void performSearchByBarcode(String barcode)
	{
		//TODO: List<Barcode> barcodesList = DatabaseDataSource.getBarcodesFromDatabase(mBarcode);
		List<Product> resultList = new ArrayList<Product>();

/*		for (int i = 0; i < barcodesList.size(); i++)
		{
			resultList.add(DatabaseDataSource.getProduct(barcodesList.get(i).getProductId()));
		}*/

		if (resultList.size() > 0)
		{
			mReturnedFromScanning = true;
			addAll(resultList);
		}
		else
		{
			Toast.makeText(getActivity(), "Nie znaleziono produktu", Toast.LENGTH_LONG).show();
		}
	}

	public void showPopupMenu(View v)
	{
		PopupMenu popup = new PopupMenu(getActivity(), v);
		getActivity().getMenuInflater().inflate(R.menu.menu_product_more, popup.getMenu());
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{

			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				onOptionsItemSelected(item);
				return false;
			}
		});
		popup.show();
	}

	public void setSelectedProductId(int id)
	{
		selectedProductId = id;
	}

	public Dialog onCreateBarcodesDialog()
	{
		Product product = DatabaseDataSource.getProduct(selectedProductId);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.action_list_barcodes).setItems(product.barcodesListToArray(), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				// The 'which' argument contains the index position
				// of the selected item
			}
		});
		return builder.create();
	}

	public Dialog onCreatePricesDialog()
	{
		Product product = DatabaseDataSource.getProduct(selectedProductId);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.action_list_prices).setItems(product.priceHistoryToArray(), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				// The 'which' argument contains the index position
				// of the selected item
			}
		});
		return builder.create();
	}
}
