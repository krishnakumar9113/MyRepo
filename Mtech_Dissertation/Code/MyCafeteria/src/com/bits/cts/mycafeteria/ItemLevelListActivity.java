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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mycafeteria.bean.CartItem;
import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Item;
import com.mycafeteria.bean.Utilities;

@SuppressWarnings("deprecation")
public class ItemLevelListActivity extends ActionBarActivity {
	String Header = "Select items";
	ListView listView;
	int vendorCode;
	int category_id;
	Item[] items_array;
	ItemAdapter itemadapter;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_item_level_list);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Header = bundle.getString("listtype");
			vendorCode = bundle.getInt("vendor_code");
			category_id = bundle.getInt("category_id");
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar()
				.setTitle(
						(Html.fromHtml("<font color=\"#FF4444\">" + Header
								+ "</font>")));
		listView = (ListView) findViewById(R.id.listView);
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Utilities.isNetworkConnected(connMgr)) {
			new ItemDetailsListAsync("http://" + Constants.IP
					+ "/webapi/item/filter?VendorID="
					+ String.valueOf(vendorCode) + "&CategoryID="
					+ String.valueOf(category_id)).execute();

		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// you should edit this to fit your needs
			builder.setMessage("No Data Connection.Kindly check your network connection");
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// get the two inputs
							dialog.cancel();
							Intent intent = new Intent(getApplicationContext(),
									Index.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("EXIT", true);
							startActivity(intent);
						}
					});
			builder.create().show();
		}
		OnItemClickListener listPairedClickItem = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String item = (String) arg0.getAdapter().getItem(position);
				// Toast.makeText( getApplicationContext(), item + " selected",
				// Toast.LENGTH_LONG).show();

			}
		};
		listView.setOnItemClickListener(listPairedClickItem);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (items_array != null) {
			for (int i = 0; i < items_array.length; i++) {
				items_array[i].setSelected(false);
			}
			itemadapter = new ItemAdapter(this, items_array);

			listView.setAdapter(itemadapter);
			itemadapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_level_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.addtocart:
			for (int i = 0; i < items_array.length; i++) {
				if (items_array[i].isSelected()) {
					if (Constants.getCart().contains(
							new CartItem(items_array[i], 1))) {

						Constants.getCart().remove(
								new CartItem(items_array[i], 1));

					}
					items_array[i].setSelected(false);
					Constants.getCart().add(new CartItem(items_array[i], 1));
				}
			}
			Toast.makeText(getApplicationContext(),
					"Selected Items added to cart", Toast.LENGTH_LONG).show();
			break;
		case R.id.cart:

			Intent i = new Intent(ItemLevelListActivity.this, Cart.class);

			startActivity(i);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	class ItemDetailsListAsync extends AsyncTask<String, Void, String> {
		String URL;

		public ItemDetailsListAsync(String url) {
			this.URL = url;
		}

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
				JSONArray jsonResultObject = new JSONArray(result);
				if (jsonResultObject.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"No items under the selected option",
							Toast.LENGTH_LONG).show();

				}
				items_array = new Item[jsonResultObject.length()];
				for (int i = 0; i < jsonResultObject.length(); i++) {
					JSONObject jsonObject = jsonResultObject.getJSONObject(i);
					Item item = new Item(jsonObject.optInt("id"), jsonObject
							.optString("name").toString(),
							jsonObject.optDouble("price"), false,
							jsonObject.optInt("count"), false, jsonObject
									.optString("vendorName").toString());

					if (Constants.getFavourites().contains(item)) {
						item.setIsfav(true);
					} else {
						item.setIsfav(false);
					}
					items_array[i] = item;
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			itemadapter = new ItemAdapter(ItemLevelListActivity.this,
					items_array);
			listView.setAdapter(itemadapter);
			pd.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(ItemLevelListActivity.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
