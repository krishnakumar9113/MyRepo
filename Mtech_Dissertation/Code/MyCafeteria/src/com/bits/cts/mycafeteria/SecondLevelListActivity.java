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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mycafeteria.bean.Category;
import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Utilities;
import com.mycafeteria.bean.Vendor;

@SuppressWarnings("deprecation")
public class SecondLevelListActivity extends ActionBarActivity {
	ListView listView;
	String listtype;
	int vendorCode;
	int categoryid;
	ProgressDialog pd;
	Category[] categories = null;
	Vendor[] vendors = null;
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_level_list);
		bundle = getIntent().getExtras();
		listtype = bundle.getString("listtype");
		listView = (ListView) findViewById(R.id.listView);
		getSupportActionBar().setTitle(
				(Html.fromHtml("<font color=\"#FF4444\">" + listtype
						+ "</font>")));
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Utilities.isNetworkConnected(connMgr)) {
			if (listtype.equals("Categories")) {
				//
				vendorCode = bundle.getInt("vendor_code");

				new SecondLevelListAsyncTask("http://" + Constants.IP
						+ "/webapi/category/mobile/VenCat/filter/"
						+ String.valueOf(vendorCode)).execute();

			} else {
				categoryid = bundle.getInt("category_id");
				new SecondLevelListAsyncTask("http://" + Constants.IP
						+ "/webapi/vendor/mobile/CatVen/filter/"
						+ String.valueOf(categoryid) + "/"
						+ Constants.SelectedLocation.toString()).execute();
			}

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

				Bundle bundle = new Bundle();
				Intent i = new Intent(SecondLevelListActivity.this,
						ItemLevelListActivity.class);
				if (listtype.equals("Vendors")) {

					Vendor vendor = (Vendor) arg0.getAdapter()
							.getItem(position);
					bundle.putInt("vendor_code", vendor.getid());
					bundle.putInt("category_id", categoryid);

				} else {
					Category category = (Category) arg0.getAdapter().getItem(
							position);
					bundle.putInt("category_id", category.getId());
					bundle.putInt("vendor_code", vendorCode);
				}
				bundle.putString("listtype", "Items");
				i.putExtras(bundle);
				startActivity(i);

			}
		};
		listView.setOnItemClickListener(listPairedClickItem);

	}

	class SecondLevelListAsyncTask extends AsyncTask<String, Void, String> {
		String URL;

		public SecondLevelListAsyncTask(String url) {
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
			if (listtype.equals("Categories")) {

				try {
					JSONArray jsonResulttObject = new JSONArray(result);
					if (jsonResulttObject.length() == 0) {
						Toast.makeText(getApplicationContext(),
								"No items under the selected option",
								Toast.LENGTH_LONG).show();

					}
					categories = new Category[jsonResulttObject.length()];
					for (int i = 0; i < jsonResulttObject.length(); i++) {
						JSONObject jsonObject = jsonResulttObject
								.getJSONObject(i);
						Category category = new Category();
						category.setCategory(jsonObject.optString(
								"categoryName").toString());
						category.setId(Integer.parseInt(jsonObject.optString(
								"id").toString()));
						categories[i] = category;

					}
					ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
							SecondLevelListActivity.this, R.layout.rowlayout,
							R.id.label, categories);
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				try {
					JSONArray jsonResulttObject = new JSONArray(result);
					if (jsonResulttObject.length() == 0) {
						Toast.makeText(getApplicationContext(),
								"No items under the selected option",
								Toast.LENGTH_LONG).show();

					}
					vendors = new Vendor[jsonResulttObject.length()];
					for (int i = 0; i < jsonResulttObject.length(); i++) {
						JSONObject jsonObject = jsonResulttObject
								.getJSONObject(i);
						Vendor vendor = new Vendor(jsonObject.optInt("id"),
								jsonObject.optString("name").toString());
						vendors[i] = vendor;
					}
					ArrayAdapter<Vendor> adapter = new ArrayAdapter<Vendor>(
							SecondLevelListActivity.this, R.layout.rowlayout,
							R.id.label, vendors);
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			pd.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(SecondLevelListActivity.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
