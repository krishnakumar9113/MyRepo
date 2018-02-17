package com.android.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	String defaultuser=new String("doc");  
    String password1=new String("pass");
	 String doctorpass=new String();
	 String doctorname=new String();
	DoctorModel Model;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        Model=new DoctorModel(this);
	        Button okButton = (Button)this.findViewById(R.id.Button01);
	        okButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	EditText username=(EditText)findViewById(R.id.EditText01);
	     	       doctorname=(String)username.getText().toString();
	     	        EditText password=(EditText)findViewById(R.id.EditText02);
	     	        doctorpass=(String)password.getText().toString();
	                
	            	if (doctorname.equals(defaultuser)&& doctorpass.equals(password1))
	    	        {
	    	        	Intent i =new Intent(Login.this,Entrypage.class);
	    	        	startActivity(i);
	    	        }
	            	 
	            	else
	            	{
	            		 AlertDialog.Builder dialog=new AlertDialog.Builder(Login.this);
	            	        dialog.setMessage("Invalid Username and Password");
	            	        dialog.setPositiveButton("OK", null);
	            	       
	            	        dialog.create();
	            	        dialog.show();
	            	}
	            }
	          });
	        //cancel
	        Button cancelButton = (Button)findViewById(R.id.Button02);
	        cancelButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	             finish();
	            	
	            }
	          });

	        
	 }
	 @Override 
     public void onDestroy() {
		 super.onDestroy();
     	Model.close();  
     	}

}
