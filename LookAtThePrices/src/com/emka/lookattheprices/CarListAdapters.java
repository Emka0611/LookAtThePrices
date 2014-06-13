package com.emka.lookattheprices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import com.emka.lookattheprices.datasource.DatabaseDataSource;
import com.emka.lookattheprices.model.Product;

class CarListAdapter extends SimpleAdapter
{
	Context context;

	public CarListAdapter(Context context, List<Map<String, ?>> listMap, int resource, String[] from, int[] to)
	{
		super(context, listMap, resource, from, to);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = super.getView(position, convertView, parent);

		final int pos = position;
		@SuppressWarnings("unchecked")
		final HashMap<String, String> item = (HashMap<String, String>) getItem(pos);

		ImageButton btn = (ImageButton) view.findViewById(R.id.action_more);
		if (null != btn)
		{
			btn.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Product product = DatabaseDataSource.getProduct(item.get("name"));

					ProductsSectionFragment frag = (ProductsSectionFragment) ((MainActivity) context).getCurrentFragment();
					frag.setSelectedProductId(product.getId());
					frag.showPopupMenu(v);
				}
			});
		}
		return view;
	}
}