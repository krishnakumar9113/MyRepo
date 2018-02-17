package com.bits.cts.mycafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mycafeteria.bean.Constants;

public class OrderDescriptionActivity extends ActionBarActivity {
	ProgressDialog pd;
	Bundle bundle;
	Integer orderid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_description);

		bundle = getIntent().getExtras();
		orderid = bundle.getInt("orderid");
		getSupportActionBar().setTitle(
				(Html.fromHtml("<font color=\"#FF4444\">" + "Order Id : "
						+ String.valueOf(orderid) + "</font>")));
		new OrderDescriptionAsync().execute();

	}

	class OrderDescriptionAsync extends AsyncTask<String, Void, String> {
		String URL = "http://" + Constants.IP + "/webapi/order/"
				+ String.valueOf(orderid);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e(this.getClass().toString(), "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return builder.toString();

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			try {
				ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);

				JSONObject jsonResultObject = new JSONObject(result);
				TextView amount = (TextView) findViewById(R.id.textView6);
				amount.setText("Total amount: "
						+ jsonResultObject.optDouble("amount"));
				TextView timestamp = (TextView) findViewById(R.id.textView2);
				timestamp.setText("Time : "
						+ jsonResultObject.optString("timeStamp"));
				TextView vendorName = (TextView) findViewById(R.id.textView1);
				vendorName.setText("VendorName : "
						+ jsonResultObject.optString("vendorName"));
				TextView secretcode = (TextView) findViewById(R.id.textView5);
				secretcode.setText("SecretCode : "
						+ jsonResultObject.optString("secretCode"));

				TextView status = (TextView) findViewById(R.id.textView12);
				status.setText("Status : "
						+ jsonResultObject.optString("orderStatus"));

				jsonResultObject.getJSONArray("itemList").toString();
				JSONArray itemlist = new JSONArray(jsonResultObject
						.getJSONArray("itemList").toString());
				LinearLayout scrollLayout = new LinearLayout(
						OrderDescriptionActivity.this);
				scrollLayout.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				scrollLayout.setOrientation(1);
				for (int i = 0; i < itemlist.length(); i++) {

					LinearLayout itemlayout = new LinearLayout(
							OrderDescriptionActivity.this);
					itemlayout.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					// layout position params);
					JSONObject item = itemlist.getJSONObject(i);
					TextView itemNameTextView = new TextView(
							OrderDescriptionActivity.this);

					itemNameTextView.setTextColor(Color.BLACK);
					itemNameTextView
							.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
					itemNameTextView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					TextView countTextView = new TextView(
							OrderDescriptionActivity.this);
					countTextView.setTextColor(Color.BLACK);
					countTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
					countTextView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					TextView priceTextView = new TextView(
							OrderDescriptionActivity.this);
					priceTextView.setTextColor(Color.BLACK);
					priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
					TextView itemTotalTextView = new TextView(
							OrderDescriptionActivity.this);
					itemTotalTextView.setTextColor(Color.BLACK);
					itemTotalTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							16);
					itemTotalTextView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					String itemcount = item.optString("itemCount");
					Double price = item.optDouble("price");
					Double valueitem = price * Integer.parseInt(itemcount);
					itemNameTextView.setText(item.optString("itemName")
							.toString() + " : ");
					countTextView.setText(itemcount + " * ");
					priceTextView.setText(String.valueOf(Constants.df
							.format(price)) + " = ");
					itemTotalTextView.setText(String.valueOf(Constants.df
							.format(valueitem)));
					itemlayout.addView(itemNameTextView);
					itemlayout.addView(countTextView);
					itemlayout.addView(priceTextView);
					itemlayout.addView(itemTotalTextView);
					scrollLayout.addView(itemlayout);

				}
				scrollView.addView(scrollLayout);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			pd.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(OrderDescriptionActivity.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
