package com.bits.cts.mycafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Item;

public class FavItemAdapter extends ArrayAdapter<Item> {
	private final Context context;
	private final Item[] itemvalues;
	View rowView;
	TextView textView3;
	String addasfav = " (+) Add as Favourite      ";
	String removeasfav = " (-) Remove from Favourite ";

	public FavItemAdapter(Context context, Item[] values) {
		super(context, R.layout.favitem_layout, values);
		this.context = context;
		this.itemvalues = values;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.favitem_layout, parent, false);
		TextView itemName = (TextView) rowView.findViewById(R.id.textView1);
		TextView itemPrice = (TextView) rowView.findViewById(R.id.textView8);
		TextView vendorname = (TextView) rowView.findViewById(R.id.vendorname);
		// textView3 = (TextView) rowView.findViewById(R.id.textView3);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		itemName.setText(itemvalues[position].getName());
		vendorname.setText(itemvalues[position].getVendorName());

		itemPrice.setText(Constants.df.format(itemvalues[position].getPrice()));

		CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkBox1);

		cb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				if (cb.isChecked()) {

					itemvalues[position].setSelected(true);
				} else {
					itemvalues[position].setSelected(false);
				}

			}
		});

		return rowView;
	}
}
