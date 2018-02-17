package com.bits.cts.mycafeteria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mycafeteria.bean.CartItem;
import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Utilities;

@SuppressWarnings("deprecation")
public class Cart extends ActionBarActivity {
	String Header = "Cart";
	ListView listView;
	CartAdapter cartAdapter;
	CartItem[] cartitemArray;
	ProgressDialog pd;
	Double totalamount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_item_level_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar()
				.setTitle(
						(Html.fromHtml("<font color=\"#FF4444\">" + Header
								+ "</font>")));
		for (CartItem item : Constants.getCart()) {
			item.getCartItem().setSelected(false);
		}

		listView = (ListView) findViewById(R.id.listView);

		cartitemArray = new CartItem[Constants.getCart().size()];
		Constants.getCart().toArray(cartitemArray); // fill the array

		cartAdapter = new CartAdapter(this, cartitemArray);
		listView.setAdapter(cartAdapter);

		OnItemClickListener listPairedClickItem = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String item = (String) arg0.getAdapter().getItem(position);
				Toast.makeText(getApplicationContext(), item + " selected",
						Toast.LENGTH_LONG).show();

			}
		};
		listView.setOnItemClickListener(listPairedClickItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.removefromcart:

			for (int i = 0; i < cartitemArray.length; i++) {
				if (cartitemArray[i].getCartItem().isSelected()) {
					Constants.getCart().remove(cartitemArray[i]);
				}
			}
			cartitemArray = new CartItem[Constants.getCart().size()];
			Constants.getCart().toArray(cartitemArray);
			cartAdapter = new CartAdapter(this, cartitemArray);
			listView.setAdapter(cartAdapter);
			cartAdapter.notifyDataSetChanged();
			break;
		case R.id.pay:

			if (Constants.isLoggedIn()) {
				if (Constants.getCart().size() == 0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Cart.this);
					// you should edit this to fit your needs
					builder.setMessage("No item is present in the cart for processing an order !");
					builder.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// get the two inputs
									dialog.cancel();

								}
							});
					builder.create().show();
					break;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				LayoutInflater inflator = LayoutInflater.from(this);
				View view = inflator.inflate(
						R.layout.activity_payment_description, null);
				TextView totalAmount = (TextView) view
						.findViewById(R.id.textView6);
				TextView username = (TextView) view
						.findViewById(R.id.textView4);
				builder.setView(view);
				builder.setTitle("Pay");
				username.setText(Constants.getCurrentUser().getUserName());
				totalamount = 0.00;
				for (CartItem cartitem : Constants.getCart()) {
					Double totalAmountItem = cartitem.getCartItem().getPrice()
							* cartitem.getCount();
					totalamount = totalamount + totalAmountItem;
				}
				totalAmount.setText(Constants.df.format(totalamount));
				final AlertDialog dialog = builder.create();
				dialog.show();
				Button pay_wallet = (Button) view.findViewById(R.id.pay_wallet);
				pay_wallet.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*
						 * Toast.makeText(Cart.this, "is selected",
						 * Toast.LENGTH_LONG).show();
						 */
						dialog.dismiss();

						if (totalamount < Constants.getCurrentUser()
								.getAvail_Amt()) {
							AlertDialog.Builder builder2 = new AlertDialog.Builder(
									Cart.this);
							builder2.setMessage(
									"Available balance in MyWallet is Rs."
											+ Constants.df.format(Constants
													.getCurrentUser()
													.getAvail_Amt())
											+ "."
											+ "Are you sure to pay using my wallet?")

									.setPositiveButton(
											"Confirm",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// FIRE ZE MISSILES!
													// post the order
													final ConnectivityManager connMgr = (ConnectivityManager) Cart.this
															.getSystemService(Context.CONNECTIVITY_SERVICE);
													if (Utilities
															.isNetworkConnected(connMgr)) {
														new PostPaymentAsync()
																.execute("");
													} else {
														AlertDialog.Builder builder = new AlertDialog.Builder(
																Cart.this);
														// you should edit this
														// to fit your needs
														builder.setMessage("No Data Connection.Kindly check your network connection");
														builder.setPositiveButton(
																"Ok",
																new DialogInterface.OnClickListener() {
																	public void onClick(
																			DialogInterface dialog,
																			int whichButton) {
																		// get
																		// the
																		// two
																		// inputs
																		dialog.cancel();
																		Intent intent = new Intent(
																				getApplicationContext(),
																				Index.class);
																		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
																		intent.putExtra(
																				"EXIT",
																				true);
																		startActivity(intent);
																	}
																});
														builder.create().show();
													}

												}
											})
									.setNegativeButton(
											"Cancel",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// User cancelled the dialog
													dialog.cancel();
												}
											});
							// Create the AlertDialog object and return it

							builder2.create().show();
						} else {
							AlertDialog.Builder builder3 = new AlertDialog.Builder(
									Cart.this);
							builder3.setMessage(
									"Available balance in MyWallet is insufficient to process your order")
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// FIRE ZE MISSILES!

													dialog.cancel();
												}
											});
							builder3.create().show();
						}

					}
				});
			} else {

				new Utilities().AuthenticationPopup(Cart.this, null);
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	class PostPaymentAsync extends AsyncTask<String, Void, String> {
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
						"http://" + Constants.IP + "/webapi/order/mobile/")
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

				JSONObject orderJson = new JSONObject();
				orderJson.put("userid", Constants.getCurrentUser().getId());
				orderJson.put("timeStamp", strDate);
				orderJson.put("auth_token", Constants.getCurrentUser()
						.getAuth_Token());
				JSONArray cartitemArray = new JSONArray();
				for (CartItem cartitem : Constants.getCart()) {
					JSONObject cartitemJson = new JSONObject();
					cartitemJson.put("itemid", cartitem.getCartItem().getId());
					cartitemJson.put("count", cartitem.getCount());
					cartitemArray.put(cartitemJson);
				}
				orderJson.put("itemcount", cartitemArray);

				writer.write(orderJson.toString());
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
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONObject resultjson = new JSONObject(result);

				if (resultjson.optString("result").equals("OK")) {
					Toast.makeText(Cart.this, "Payment processed successfully",
							Toast.LENGTH_LONG).show();

					if (Constants.getCurrentUser().getId() == resultjson
							.optInt("UserId")) {
						Constants.getCurrentUser().setAvail_Amt(
								resultjson.optDouble("AvailBalance"));
					} else {
						Toast.makeText(Cart.this, "User Validation failed",
								Toast.LENGTH_LONG).show();
						Constants.setCurrentUser(null);
					}
					// update current user object+

				} else if (resultjson.optString("result").equals(
						"Auth-token failed"))

				{
					Toast.makeText(Cart.this, "Error : Auth-token failed",
							Toast.LENGTH_LONG).show();
				} else if (resultjson.optString("result").startsWith(
						"Payment failed")) {
					Toast.makeText(Cart.this,
							"Error : " + resultjson.optString("result"),
							Toast.LENGTH_LONG).show();
				} else {

					Toast.makeText(Cart.this, "Error : Payment failed",
							Toast.LENGTH_LONG).show();
				}
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
			pd = new ProgressDialog(Cart.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
