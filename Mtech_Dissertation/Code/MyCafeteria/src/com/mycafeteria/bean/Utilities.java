package com.mycafeteria.bean;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.cts.mycafeteria.Index;
import com.bits.cts.mycafeteria.R;

public class Utilities {
	ProgressDialog pd;
	AlertDialog.Builder builder = null;
	TabHost tabHost;
	Context CurrentContext;
	Intent LaunchingActivityIntent;
	String regmailid;
	TextView usernameTextView, passwordTextView;
	TextView registerMailIDTextView, registerUserNameTextView,
			registerPasswordTextView;

	public void AuthenticationPopup(Context a, Intent i) {

		this.CurrentContext = a;
		if (i != null)
			this.LaunchingActivityIntent = i;
		builder = new AlertDialog.Builder(a);
		LayoutInflater inflator = LayoutInflater.from(a);
		View view = inflator.inflate(R.layout.customtabdialog, null);

		tabHost = (TabHost) view.findViewById(R.id.tabhost);
		tabHost.setup();
		TabHost.TabSpec signinTabSpec = tabHost.newTabSpec("tag1");
		signinTabSpec.setContent(R.id.tab1);
		signinTabSpec.setIndicator("Sign-In");
		TextView forgotpasswordView = (TextView) view
				.findViewById(R.id.textView1);

		forgotpasswordView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder2 = new AlertDialog.Builder(
						CurrentContext);
				// you should edit this to fit your needs
				builder2.setTitle("Forgot Password");

				final EditText forgotPasswordEdittext = new EditText(
						CurrentContext);

				forgotPasswordEdittext.setHint("Enter registered mailID");// optional

				LinearLayout forgotPasswordLayout = new LinearLayout(
						CurrentContext);
				forgotPasswordLayout.setOrientation(LinearLayout.VERTICAL);
				forgotPasswordLayout.addView(forgotPasswordEdittext);

				builder2.setView(forgotPasswordLayout);

				// Set up the buttons
				builder2.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// get the two inputs
								regmailid = forgotPasswordEdittext.getText()
										.toString();
								if (regmailid.equals("") || regmailid.isEmpty()) {
									alert("MailID is empty", CurrentContext);
									return;
								}
								final ConnectivityManager connMgr = (ConnectivityManager) CurrentContext
										.getSystemService(Context.CONNECTIVITY_SERVICE);
								if (Utilities.isNetworkConnected(connMgr)) {
									new AuthenticationAsyncTask("fgpwd").execute("");
								} else {
									AlertDialog.Builder builder = new AlertDialog.Builder(
											CurrentContext);
									// you should edit this to fit your needs
									builder.setMessage("No Data Connection.Kindly check your network connection");
									builder.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// get the two inputs
													dialog.cancel();
													Intent intent = new Intent(
															CurrentContext,
															Index.class);
													intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
													intent.putExtra("EXIT",
															true);
													CurrentContext
															.startActivity(intent);
												}
											});
									builder.create().show();
								}

							}
						});

				builder2.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				builder2.create().show();

			}

		});
		tabHost.addTab(signinTabSpec);

		TabHost.TabSpec regtabSpec = tabHost.newTabSpec("tag2");
		regtabSpec.setContent(R.id.tab2);
		regtabSpec.setIndicator("Register");
		tabHost.addTab(regtabSpec);

		builder.setPositiveButton("Submit",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						final ConnectivityManager connMgr = (ConnectivityManager) CurrentContext
								.getSystemService(Context.CONNECTIVITY_SERVICE);
						if (Utilities.isNetworkConnected(connMgr)) {
							if (tabHost.getCurrentTab() == 0) {
								// Signin
								usernameTextView = (TextView) tabHost
										.findViewById(R.id.username);
								if (usernameTextView.getText().toString()
										.equals("")
										|| usernameTextView.getText()
												.toString().isEmpty()) {
									alert("UserName is empty", CurrentContext);
									return;
								}
								passwordTextView = (TextView) tabHost
										.findViewById(R.id.password);
								if (passwordTextView.getText().toString()
										.equals("")
										|| passwordTextView.getText()
												.toString().isEmpty()) {
									alert("Password is empty", CurrentContext);
									return;
								}
								new AuthenticationAsyncTask("signin").execute("");
							} else {
								// Register
								registerMailIDTextView = (TextView) tabHost
										.findViewById(R.id.name_reg);
								String mailid = registerMailIDTextView
										.getText().toString();
								if (mailid.equals("") || mailid.isEmpty()) {
									alert("MailID is Empty", CurrentContext);
									return;
								}
								if (!isvalidMailID(mailid)) {
									alert("Please enter a valid MailID",
											CurrentContext);
									return;
								}
								registerUserNameTextView = (TextView) tabHost
										.findViewById(R.id.username_reg);
								if (registerUserNameTextView.getText()
										.toString().equals("")
										|| registerUserNameTextView.getText()
												.toString().isEmpty()) {
									alert("UserName is empty", CurrentContext);
									return;
								}
								registerPasswordTextView = (TextView) tabHost
										.findViewById(R.id.password_reg);
								if (registerPasswordTextView.getText()
										.toString().equals("")
										|| registerPasswordTextView.getText()
												.toString().isEmpty()) {
									alert("Password is empty", CurrentContext);
									return;
								}
								new AuthenticationAsyncTask("reg").execute("");
							}
						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									CurrentContext);
							// you should edit this to fit your needs
							builder.setMessage("No Data Connection.Kindly check your network connection");
							builder.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											// get the two inputs
											dialog.cancel();
											Intent intent = new Intent(
													CurrentContext, Index.class);
											intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											intent.putExtra("EXIT", true);
											CurrentContext
													.startActivity(intent);
										}
									});
							builder.create().show();
						}

						// sign in the user ...

					}
				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.setView(view);

		builder.create().show();
	}

	class AuthenticationAsyncTask extends AsyncTask<String, Void, String> {
		String URL;
		HttpURLConnection urlConnection;
		String url;
		String type;
		String data;// json
		String result = null;

		AuthenticationAsyncTask(String type) {
			this.type = type;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				// Connect
				if (type.equals("signin")) {
					urlConnection = (HttpURLConnection) ((new java.net.URL(
							"http://" + Constants.IP
									+ "/webapi/user/mobile/Auth/")
							.openConnection()));
				} else if (type.equals("reg")) {
					urlConnection = (HttpURLConnection) ((new java.net.URL(
							"http://" + Constants.IP
									+ "/webapi/user/mobile/Reg")
							.openConnection()));
				} else if (type.equals("fgpwd")) {
					urlConnection = (HttpURLConnection) ((new java.net.URL(
							"http://" + Constants.IP
									+ "/webapi/user/mobile/forgotpwd")
							.openConnection()));

				}

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
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(calendar.getTime());
				JSONObject obj = new JSONObject();
				if (type.equals("signin")) {

					// TextView tv = (TextView)
					// tabHost.findViewById(R.id.username);
					obj.put("username", usernameTextView.getText().toString());
					// TextView tv2 = (TextView)
					// tabHost.findViewById(R.id.password);
					obj.put("password", passwordTextView.getText().toString());
				} else if (type.equals("reg")) {

					obj.put("mailid", registerMailIDTextView.getText()
							.toString());

					obj.put("username", registerUserNameTextView.getText()
							.toString());

					obj.put("password", registerPasswordTextView.getText()
							.toString());
					// reg
				} else if (type.equals("fgpwd")) {

					obj.put("mailid", regmailid);
				}

				writer.write(obj.toString());
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
				if (type.equals("signin")) {

					if (result.contains("UserName not found")) {
						Constants.setLoggedIn(false);
						alert("UserName not found", CurrentContext);
						return;
					} else if (result.contains("Invalid UserName and Password")) {
						Constants.setLoggedIn(false);
						alert("Invalid UserName and Password", CurrentContext);
						return;
					} else {
						JSONObject obj = new JSONObject(result);
						JSONObject user = obj.getJSONObject("user");
						JSONArray favs = obj.getJSONArray("favs");
						Constants.setCurrentUser(new User(
								user.getInt("userid"), user
										.getString("userName"), user
										.getString("mailid"), user
										.getString("password"), user
										.getDouble("declaredAmount"), user
										.getDouble("availableBalance"), user
										.getString("auth_token")));
						Constants.getFavourites().clear();

						if (favs != null) {
							for (int i = 0; i < favs.length(); i++) {
								JSONObject jsonObject = favs.getJSONObject(i);
								Item item = new Item(
										jsonObject.optInt("id"),
										jsonObject.optString("name").toString(),
										jsonObject.optDouble("price"), false,
										jsonObject.optInt("count"), true,
										jsonObject.optString("vendorName")
												.toString());

								Constants.getFavourites().add(item);
							}
						}
						Constants.setLoggedIn(true);
						Toast.makeText(CurrentContext,
								"Logged In successfully", Toast.LENGTH_LONG)
								.show();
						if (LaunchingActivityIntent != null) {
							CurrentContext
									.startActivity(LaunchingActivityIntent);
						}
					}

				} else if (type.equals("reg")) {
					if (result.equals("OK")) {
						alert("Registered successfully.Kindly login with your credentials",
								CurrentContext);

					} else {
						alert(result, CurrentContext);
					}

				} else if (type.equals("fgpwd")) {
					if (result.equals("Ok")) {
						alert("New password has been sent to your registered mailid",
								CurrentContext);

					} else if (result.equals("MailId not found")) {
						alert("MailId not found", CurrentContext);
					} else {
						alert("Password reset failed", CurrentContext);

					}
				}
			} catch (Exception e) {
				Constants.setLoggedIn(false);
				alert("Server Error", CurrentContext);

				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				pd.dismiss();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(CurrentContext);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

	public static boolean isNetworkConnected(
			final ConnectivityManager connectivityManager) {

		boolean value = false;

		Log.d("Network", "Checking for Mobile Internet Network");
		final android.net.NetworkInfo mobile = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobile.isAvailable() && mobile.isConnected()) {
			Log.i("Network", "Found Mobile Internet Network");
			value = true;
		} else {
			Log.e("Network", "Mobile Internet Network not Found");
		}

		Log.d("Network", "Checking for WI-FI Network");
		final android.net.NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi.isAvailable() && wifi.isConnected()) {
			Log.i("Network", "Found WI-FI Network");
			value = true;
		} else {
			Log.e("Network", "WI-FI Network not Found");
		}

		return value;
	}

	public static void alert(String Message, Context CurrentContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(CurrentContext);
		// you should edit this to fit your needs
		builder.setMessage(Message);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// get the two inputs
				dialog.cancel();

			}
		});
		builder.create().show();
	}

	public static boolean isvalidMailID(String mailid) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return mailid.matches(EMAIL_REGEX);
	}

	public static boolean isNumber(String mailid) {
		return mailid.matches("\\d+");
	}
}
