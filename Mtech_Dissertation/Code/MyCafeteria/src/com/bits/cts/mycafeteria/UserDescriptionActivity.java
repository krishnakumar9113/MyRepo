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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mycafeteria.bean.Constants;
import com.mycafeteria.bean.Utilities;

public class UserDescriptionActivity extends ActionBarActivity {
	AlertDialog.Builder builder;
	ProgressDialog pd;
	String currentpassword, newpassword, repeatnewpwd;

	Double declaredamount;
	TextView declaredAmountTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_description);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setTitle(
				(Html.fromHtml("<font color=\"#FF4444\">" + "User Description"
						+ "</font>")));
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (Constants.getCurrentUser() == null) {
			finish();
		}

		TextView mailidTextView = (TextView) findViewById(R.id.textView1);
		mailidTextView.setText(Constants.getCurrentUser().getmailid());

		TextView userNameTextView = (TextView) findViewById(R.id.textView4);
		userNameTextView.setText(Constants.getCurrentUser().getUserName());

		declaredAmountTextView = (TextView) findViewById(R.id.textView6);
		declaredAmountTextView.setText(Constants.df.format(Constants
				.getCurrentUser().getDecl_Amt()));
		declaredAmountTextView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						UserDescriptionActivity.this);
				// you should edit this to fit your needs
				builder.setTitle("Declare Amount");

				final EditText declaredAmountTextView = new EditText(
						UserDescriptionActivity.this);

				declaredAmountTextView.setHint("Enter new amount < Rs.3000");// optional

				LinearLayout layoutdeclamt = new LinearLayout(
						UserDescriptionActivity.this);
				layoutdeclamt.setOrientation(LinearLayout.VERTICAL);
				layoutdeclamt.addView(declaredAmountTextView);

				builder.setView(layoutdeclamt);

				// Set up the buttons
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// get the two inputs
								String declaredAmount = declaredAmountTextView
										.getText().toString();
								if (declaredAmount.equals("")
										|| declaredAmount.isEmpty()) {
									AlertDialog.Builder builder = new AlertDialog.Builder(
											UserDescriptionActivity.this);
									// you should edit this to fit your needs
									builder.setMessage("Declaration amount cannot be empty");
									builder.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// get the two inputs
													dialog.cancel();

												}
											});
									builder.create().show();
									return;
								}
								declaredamount = Double
										.parseDouble(declaredAmount);
								if (declaredamount > 3000.00) {
									Toast.makeText(
											UserDescriptionActivity.this,
											"Entered amount should be less than Rs.3000",
											Toast.LENGTH_LONG).show();
									declaredAmountTextView.setText("");
								} else {
									if (Utilities.isNetworkConnected(connMgr)) {
										new PostUserDetailsAsync(2).execute("");

									} else {
										AlertDialog.Builder builder = new AlertDialog.Builder(
												UserDescriptionActivity.this);
										// you should edit this to fit your
										// needs
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
																getApplicationContext(),
																Index.class);
														intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
														intent.putExtra("EXIT",
																true);
														startActivity(intent);
													}
												});
										builder.create().show();
									}

								}

							}
						});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				builder.create().show();

			}
		});
		TextView availableAmountTextView = (TextView) findViewById(R.id.textView10);
		availableAmountTextView.setText(Constants.df.format(Constants
				.getCurrentUser().getAvail_Amt()));
		Button signout = (Button) findViewById(R.id.signout);

		builder = new AlertDialog.Builder(this);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		signout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Constants.setLoggedIn(false);
				Constants.getFavourites().clear();
				Intent i = new Intent(UserDescriptionActivity.this, Index.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				// Perform action on click
			}
		});
		TextView ChangePassword = (TextView) findViewById(R.id.changepass);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

		ChangePassword.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Start

				AlertDialog.Builder builder = new AlertDialog.Builder(
						UserDescriptionActivity.this);
				// you should edit this to fit your needs
				builder.setTitle("Change Password");

				final EditText currentPasswordEdittext = new EditText(
						UserDescriptionActivity.this);
				final EditText newpasswordEditText = new EditText(
						UserDescriptionActivity.this);
				final EditText repeatPasswordEdittext = new EditText(
						UserDescriptionActivity.this);
				currentPasswordEdittext.setHint("Current Password");// optional
				currentPasswordEdittext.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				newpasswordEditText.setHint("New Password");// optional
				newpasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				repeatPasswordEdittext.setHint("Retype New Password");// optional
				repeatPasswordEdittext.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				// in my example i use TYPE_CLASS_NUMBER for input only numbers
				// from.setInputType(InputType.TYPE_CLASS_NUMBER);
				// to.setInputType(InputType.TYPE_CLASS_NUMBER);

				LinearLayout changepasswordlayout = new LinearLayout(
						UserDescriptionActivity.this);
				changepasswordlayout.setOrientation(LinearLayout.VERTICAL);
				changepasswordlayout.addView(currentPasswordEdittext);
				changepasswordlayout.addView(newpasswordEditText);
				changepasswordlayout.addView(repeatPasswordEdittext);
				builder.setView(changepasswordlayout);

				// Set up the buttons
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// get the two inputs
								currentpassword = currentPasswordEdittext
										.getText().toString();
								newpassword = newpasswordEditText.getText()
										.toString();
								repeatnewpwd = repeatPasswordEdittext.getText()
										.toString();
								if (currentpassword.equals("")
										|| currentpassword.isEmpty()) {
									Toast.makeText(
											UserDescriptionActivity.this,
											"Current Password cannot be empty",
											Toast.LENGTH_LONG).show();
									return;
								}
								if (newpassword.equals("")
										|| newpassword.isEmpty()) {
									Toast.makeText(
											UserDescriptionActivity.this,
											"New Password cannot be empty",
											Toast.LENGTH_LONG).show();
									return;
								}
								if (repeatnewpwd.equals("")
										|| repeatnewpwd.isEmpty()) {
									Toast.makeText(
											UserDescriptionActivity.this,
											"Re -Enter Password cannot be empty",
											Toast.LENGTH_LONG).show();
									return;
								}
								if (!newpassword.equals(repeatnewpwd)) {
									Toast.makeText(
											UserDescriptionActivity.this,
											"New Password and Re-Type password are not same",
											Toast.LENGTH_LONG).show();
									return;
								}
								if (Utilities.isNetworkConnected(connMgr)) {

									new PostUserDetailsAsync(1).execute("");

								} else {
									AlertDialog.Builder builder = new AlertDialog.Builder(
											UserDescriptionActivity.this);
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
															getApplicationContext(),
															Index.class);
													intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
													intent.putExtra("EXIT",
															true);
													startActivity(intent);
												}
											});
									builder.create().show();
								}

							}
						});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				builder.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.signout) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class PostUserDetailsAsync extends AsyncTask<String, Void, String> {
		String URL;
		HttpURLConnection urlConnection;
		String url;
		String data;// json
		String result = null;
		int type;

		PostUserDetailsAsync(int type) {
			this.type = type;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				// Connect
				if (type == 1) {
					// pwd change

					urlConnection = (HttpURLConnection) ((new java.net.URL(
							"http://" + Constants.IP + "/webapi/user/changepwd")
							.openConnection()));
				} else {
					// decl amt change
					urlConnection = (HttpURLConnection) ((new java.net.URL(
							"http://" + Constants.IP + "/webapi/user/declamt")
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
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String strDate = sdf.format(c.getTime());

				JSONObject resultJson = new JSONObject();

				resultJson.put("userid", Constants.getCurrentUser().getId());
				if (type == 1) {
					resultJson.put("currentpwd", currentpassword);
					resultJson.put("newpwd", newpassword);
				} else {
					resultJson.put("declamt", declaredamount);
				}

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
				Toast.makeText(UserDescriptionActivity.this, "Update failed",
						Toast.LENGTH_LONG).show();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result.equals("OK")) {
				Toast.makeText(UserDescriptionActivity.this,
						"Updated successfully", Toast.LENGTH_LONG).show();
				if (type != 1) {
					Constants.getCurrentUser().setDecl_Amt(declaredamount);
					declaredAmountTextView.setText(Constants.df
							.format(Constants.getCurrentUser().getDecl_Amt()));
				}
			} else {
				Toast.makeText(UserDescriptionActivity.this, "Update failed",
						Toast.LENGTH_LONG).show();
			}
			pd.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(UserDescriptionActivity.this);
			pd.setMessage("Loading... ");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(false);
			pd.show();

		}

	}

}
