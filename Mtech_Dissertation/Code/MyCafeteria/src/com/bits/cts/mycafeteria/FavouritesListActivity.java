package com.bits.cts.mycafeteria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mycafeteria.bean.CartItem;
import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Item;
import com.mycafeteria.bean.Utilities;

@SuppressWarnings("deprecation")
public class FavouritesListActivity extends ActionBarActivity {
	String Header = "Favourites";
	ListView listView;
	Item[] items_array;
	FavItemAdapter adapter;

	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_item_level_list);
		makeActionOverflowMenuShown();
		items_array = new Item[Constants.getFavourites().size()];
		Constants.getFavourites().toArray(items_array);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar()
				.setTitle(
						(Html.fromHtml("<font color=\"#FF4444\">" + Header
								+ "</font>")));
		listView = (ListView) findViewById(R.id.listView);
		OnItemClickListener listPairedClickItem = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String item = (String) arg0.getAdapter().getItem(position);

			}
		};
		listView.setOnItemClickListener(listPairedClickItem);
	}

	private void makeActionOverflowMenuShown() {
		// devices with hardware menu button don't show action overflow menu
		try {
			ViewConfiguration config = ViewConfiguration
					.get(FavouritesListActivity.this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			Log.d("hack", e.getLocalizedMessage());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (items_array != null) {
			for (int i = 0; i < items_array.length; i++) {
				items_array[i].setSelected(false);
			}
			adapter = new FavItemAdapter(this, items_array);

			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favitem_level_list, menu);
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
			// Bundle bundle = new Bundle();
			Intent i = new Intent(FavouritesListActivity.this, Cart.class);
			// i.putExtras(bundle);
			startActivity(i);
			break;

		case R.id.savefav:
			if (Constants.getFavourites().size() != 0) {
				final ConnectivityManager connMgr = (ConnectivityManager) this
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (Utilities.isNetworkConnected(connMgr)) {
					new PostFavsAsync().execute("");
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
									Intent intent = new Intent(
											getApplicationContext(),
											Index.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.putExtra("EXIT", true);
									startActivity(intent);
								}
							});
					builder.create().show();
				}

			} else {
				Toast.makeText(FavouritesListActivity.this,
						"Save Failed.Favourites is empty", Toast.LENGTH_LONG)
						.show();

			}

			break;

		case R.id.removefromfav:
			for (int j = 0; j < items_array.length; j++) {
				if (items_array[j].isSelected()
						&& Constants.getFavourites().contains(items_array[j])) {

					Constants.getFavourites().remove(items_array[j]);
					items_array[j].setIsfav(false);
				}
				items_array[j].setSelected(false);
			}
			if (items_array != null) {
				items_array = new Item[Constants.getFavourites().size()];
				Constants.getFavourites().toArray(items_array);
				adapter = new FavItemAdapter(this, items_array);

				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	class PostFavsAsync extends AsyncTask<String, Void, String> {
		String URL;
		HttpURLConnection urlConnection;
		String url;
		String data;// json
		String result = null;

		@Override
		protected String doInBackground(String... params) {
			try {
				// Connect
				urlConnection = (HttpURLConnection) ((new java.net.URL(
						"http://" + Constants.IP + "/webapi/user/favs")
						.openConnection()));
				urlConnection.setDoOutput(true);
				urlConnection.setRequestProperty("Content-Type",
						"application/json");
				urlConnection.setRequestProperty("Accept", "application/json");
				urlConnection.setRequestMethod("POST");
				urlConnection.connect();

				// Write
				OutputStream outputStream = urlConnection.getOutputStream();
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(outputStream, "UTF-8"));
				// create the json
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String strDate = sdf.format(c.getTime());

				JSONObject resultJson = new JSONObject();

				resultJson.put("userid", Constants.getCurrentUser().getId());
				StringBuilder favs = new StringBuilder();
				for (Item item : Constants.getFavourites()) {
					favs = favs.append(new StringBuilder(String.valueOf(item
							.getId())).append(","));
				}

				resultJson.put("favorites",
						favs.substring(0, favs.length() - 1));

				writer.write(resultJson.toString());
				writer.close();
				outputStream.close();

				// Read
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlConnection.getInputStream(),
								"UTF-8"));

				String line = null;
				StringBuilder sb = new StringBuilder();

				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}

				bufferedReader.close();
				result = sb.toString();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(FavouritesListActivity.this, "Save failed",
						Toast.LENGTH_LONG).show();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			Toast.makeText(FavouritesListActivity.this, "Saved successfully",
					Toast.LENGTH_LONG).show();
			pd.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(FavouritesListActivity.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
