package com.bits.cts.mycafeteria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Utilities;

@SuppressWarnings("deprecation")
public class Index extends ActionBarActivity {

	ProgressDialog pd;
	String[] values;
	Intent intent = null;
	Bundle bundle = null;
	AlertDialog.Builder builder = null;
	TabHost tabHost;
	Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		File dir = Environment.getExternalStorageDirectory();

		File ipconfigfile = new File(dir, "ip.txt");
		if (ipconfigfile.exists()) {
			FileReader reader = null;
			try {
				reader = new FileReader(ipconfigfile);
				char[] filecontents = new char[(int) ipconfigfile.length()];
				reader.read(filecontents);
				Constants.IP = new String(filecontents);

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
				//ignore
		}

		builder = new AlertDialog.Builder(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		Constants.df = new DecimalFormat();
		Constants.df.setMaximumFractionDigits(2);
		Constants.df.setMinimumFractionDigits(2);
		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
		}
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Utilities.isNetworkConnected(connMgr)) {
			new LocationDetailsAsync("http://" + Constants.IP + "/webapi/location")
					.execute();

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
							finish();
						}
					});
			builder.create().show();
		}

		spinner = (Spinner) findViewById(R.id.location);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		GridView gridview = (GridView) findViewById(R.id.grid);
		gridview.setAdapter(new ImageAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				switch (position) {
				case 0:
					bundle = new Bundle();
					intent = new Intent(Index.this,
							FirstLevelListActivity.class);
					bundle.putString("listtype", "Categories");
					intent.putExtras(bundle);
					startActivity(intent);
					// bundle.putString("listjson","[{'categoryid':1},{}]");
					break;
				case 1:
					bundle = new Bundle();
					intent = new Intent(Index.this,
							FirstLevelListActivity.class);
					bundle.putString("listtype", "Vendors");
					intent.putExtras(bundle);
					startActivity(intent);
					// bundle.putString("listjson","[{'categoryid':1},{}]");
					break;
				case 2:
					// bundle.putString("orderList","[{'categoryid':1},{}]");
					bundle = new Bundle();
					intent = new Intent(Index.this, OrderListActivity.class);
					intent.putExtras(bundle);
					if (Constants.isLoggedIn()) {

						startActivity(intent);
					} else {

						new Utilities().AuthenticationPopup(Index.this, intent);
					}
					break;
				case 3:
					bundle = new Bundle();
					bundle.putString("listtype", "Favorites");
					// bundle.putString("favoritesList","[{'categoryid':1},{}]");
					intent = new Intent(Index.this,
							FavouritesListActivity.class);
					intent.putExtras(bundle);
					if (Constants.isLoggedIn()) {

						startActivity(intent);
					} else {

						new Utilities().AuthenticationPopup(Index.this, intent);

					}

					break;

				default:
					break;
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.user) {

			intent = new Intent(Index.this, UserDescriptionActivity.class);

			if (Constants.isLoggedIn()) {

				startActivity(intent);
			} else {

				new Utilities().AuthenticationPopup(Index.this, intent);
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class LocationDetailsAsync extends AsyncTask<String, Void, String> {
		String URL;

		public LocationDetailsAsync(String url) {
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
				values = new String[jsonResultObject.length()];
				for (int i = 0; i < jsonResultObject.length(); i++) {
					JSONObject jsonObject = jsonResultObject.getJSONObject(i);
					String locationCode = jsonObject.optString("code");
					Constants.LocationMap.put(locationCode,
							jsonObject.optInt("id"));

					values[i] = jsonObject.optString("location") + "-"
							+ locationCode;
					// System.out.println(id+":"+name);
				}
				ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(
						Index.this, android.R.layout.simple_spinner_item,
						values);
				// Specify the layout to use when the list of choices appears
				locationAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				spinner.setAdapter(locationAdapter);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String[] values = arg0.getItemAtPosition(arg2)
								.toString().split("-");
						Constants.SelectedLocation = Constants.LocationMap
								.get(values[1]);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(Index.this, "Nothing  is selected",
								Toast.LENGTH_SHORT).show();
					}

				});

			} catch (Exception e) {
			}
			pd.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(Index.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
