package com.bits.cts.mycafeteria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Item;

public class ItemAdapter extends ArrayAdapter<Item> {
	private final Context context;
	private final Item[] itemValues;
	TextView favouriteOptionTextView;
	String addasfav = " (+) Add as Favourite      ";
	String removeasfav = " (-) Remove from Favourite ";

	public ItemAdapter(Context context, Item[] values) {
		super(context, R.layout.itemlayout, values);
		this.context = context;
		this.itemValues = values;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.itemlayout, parent, false);
		TextView itemNameTextView = (TextView) rowView.findViewById(R.id.textView1);
		TextView itemPriceTextView = (TextView) rowView.findViewById(R.id.textView2);
		favouriteOptionTextView = (TextView) rowView.findViewById(R.id.textView3);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		itemNameTextView.setText(itemValues[position].getName());
		if (itemValues[position].isIsfav()) {

			favouriteOptionTextView.setText(removeasfav);
		} else {

			favouriteOptionTextView.setText(addasfav);
		}
		itemNameTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// display item description with vendor name
			}
		});
		itemPriceTextView.setText(Constants.df.format(itemValues[position].getPrice()));

		CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkBox1);
		favouriteOptionTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// add to favorites
				TextView tv = (TextView) v;
				if (Constants.isLoggedIn()) {
					if (tv.getText().toString().equals(addasfav)) {

						itemValues[position].setIsfav(true);
						Constants.getFavourites().add(itemValues[position]);
						tv.setText(removeasfav);

					} else {//
							// remove from favorites
						if (Constants.getFavourites()
								.contains(itemValues[position])) {

							Constants.getFavourites().remove(itemValues[position]);
							itemValues[position].setIsfav(false);
							tv.setText(addasfav);

						}

					}
				} else {
					AlertDialog.Builder builder2 = new AlertDialog.Builder(
							context);
					builder2.setMessage("Kindly Signin to mark favourite Items")

					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();

								}
							});

					// Create the AlertDialog object and return it

					builder2.create().show();

				}
			}
		});

		cb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				if (cb.isChecked()) {

					itemValues[position].setSelected(true);
				} else {
					itemValues[position].setSelected(false);
				}

			}
		});
		

		return rowView;
	}
}
