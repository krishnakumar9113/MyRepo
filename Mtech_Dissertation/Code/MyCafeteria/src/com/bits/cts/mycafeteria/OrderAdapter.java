package com.bits.cts.mycafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Order;

public class OrderAdapter extends ArrayAdapter<Order> {
	private final Context context;
	private final Order[] values;

	public OrderAdapter(Context context, Order[] values) {
		super(context, R.layout.orderlayout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.orderlayout, parent, false);
		TextView timeStampTextView = (TextView) rowView
				.findViewById(R.id.textView1);
		timeStampTextView.setText(values[position].getTimestamp());
		TextView vendorNameTextView = (TextView) rowView
				.findViewById(R.id.textView2);
		vendorNameTextView.setText(values[position].getVendorName());
		TextView amountTextView = (TextView) rowView
				.findViewById(R.id.textView3);
		amountTextView
				.setText(Constants.df.format(values[position].getAmount()));

		return rowView;
	}
}
