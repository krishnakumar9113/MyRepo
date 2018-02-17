package com.bits.cts.mycafeteria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mycafeteria.bean.CartItem;
import com.mycafeteria.bean.Constants;

public class CartAdapter extends ArrayAdapter<CartItem> {
	private final Context context;
	private final CartItem[] values;
	TextView selectedview;

	public CartAdapter(Context context, CartItem[] values) {
		super(context, R.layout.cart_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.cart_layout, parent, false);
		TextView itemName = (TextView) rowView.findViewById(R.id.textView1);
		TextView itemPrice = (TextView) rowView.findViewById(R.id.textView8);
		TextView vendorname = (TextView) rowView.findViewById(R.id.vendorname);
		TextView itemcount = (TextView) rowView.findViewById(R.id.countval);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		itemName.setText(values[position].getCartItem().getName());
		itemPrice.setText(Constants.df.format(values[position].getCartItem()
				.getPrice()));
		vendorname.setText(values[position].getCartItem().getVendorName());
		itemcount.setText(String.valueOf(values[position].getCount()));
		itemcount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				selectedview = (TextView) view;
				AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
				final NumberPicker picker = new NumberPicker(context);
				picker.setMinValue(1);
				picker.setMaxValue(10);
				final FrameLayout parent = new FrameLayout(context);
				parent.addView(picker, new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
				builder2.setView(parent);
				builder2.setTitle("Number of Items")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// FIRE ZE MISSILES!
										// post the order
										selectedview.setText(String
												.valueOf(picker.getValue()));
										values[position].setCount(picker
												.getValue());

										dialog.dismiss();
									}
								})
						.setNegativeButton("cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// FIRE ZE MISSILES!
										// post the order
										dialog.dismiss();
									}
								});

				builder2.create().show();

			}
		});
		CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkBox1);

		checkbox.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				if (cb.isChecked()) {

					values[position].getCartItem().setSelected(true);
				} else {
					values[position].getCartItem().setSelected(false);
				}

			}
		});

		return rowView;
	}
}
