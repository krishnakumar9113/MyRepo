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
import android.widget.ListView;

import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Order;
import com.mycafeteria.bean.Utilities;

@SuppressWarnings("deprecation")
public class OrderListActivity extends ActionBarActivity {
	String Header = "Latest Orders";
	ListView orderListView;
	ProgressDialog pd;
	Order[] orders;
	OrderAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Utilities.isNetworkConnected(connMgr)) {
			new OrderListAsync().execute("");

		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
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

		getSupportActionBar()
				.setTitle(
						(Html.fromHtml("<font color=\"#FF4444\">" + Header
								+ "</font>")));

		orderListView = (ListView) findViewById(R.id.listView2);
		OnItemClickListener orderListItemClick = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Order order = (Order) arg0.getAdapter().getItem(position);
				// Toast.makeText( getApplicationContext(), order.getOrderID() +
				// " selected", Toast.LENGTH_LONG).show();

				Intent i = new Intent(OrderListActivity.this,
						OrderDescriptionActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("orderid", order.getOrderID());
				i.putExtras(bundle);
				startActivity(i);
			}
		};
		orderListView.setOnItemClickListener(orderListItemClick);
	}

	class OrderListAsync extends AsyncTask<String, Void, String> {
		String URL = "http://" + Constants.IP + "/webapi/order/mobile/list/"
				+ String.valueOf(Constants.getCurrentUser().getId());

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
				orders = new Order[jsonResultObject.length()];
				for (int i = 0; i < jsonResultObject.length(); i++) {
					JSONObject jsonOrder = jsonResultObject.getJSONObject(i);
					Order order = new Order(jsonOrder.optDouble("amount"),
							jsonOrder.optInt("orderId"), jsonOrder.optString(
									"vendorName").toString(), jsonOrder
									.optString("timeStamp").toString());

					orders[i] = order;

				}
				adapter = new OrderAdapter(OrderListActivity.this, orders);
				orderListView.setAdapter(adapter);
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
			pd = new ProgressDialog(OrderListActivity.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
