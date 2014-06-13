package com.emka.lookattheprices;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.emka.lookattheprices.datasource.DatabaseDataSource;
import com.emka.lookattheprices.model.Product;

class ProductsAutoCompleteAdapter extends ArrayAdapter<Product> implements Filterable
{
	private List<Product> resultList;
	ProductsSectionFragment fragment;

	public ProductsAutoCompleteAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
		fragment = (ProductsSectionFragment) ((MainActivity) context).getCurrentFragment();
		resultList = DatabaseDataSource.getAllProducts(context);
	}

	@Override
	public int getCount()
	{
		return resultList.size();
	}

	@Override
	public Product getItem(int index)
	{
		return resultList.get(index);
	}

	@Override
	public Filter getFilter()
	{
		Filter filter = new Filter()
		{
			@Override
			protected FilterResults performFiltering(CharSequence constraint)
			{
				FilterResults filterResults = new FilterResults();
				if (constraint != null)
				{
					resultList = autocomplete(constraint.toString());

					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			private List<Product> autocomplete(String substring)
			{
				return DatabaseDataSource.getProducts(substring);
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results)
			{
				if (results != null && results.count > 0)
				{
					notifyDataSetChanged();
					fragment.updateListAdapter(resultList);
				}
				else
				{
					notifyDataSetInvalidated();
					if (null == constraint)
					{
						fragment.updateListAdapter(DatabaseDataSource.getAllProducts(fragment.getActivity()));
					}
					else
					{
						fragment.updateListAdapter(null);
					}
				}
			}
		};
		return filter;
	}
}