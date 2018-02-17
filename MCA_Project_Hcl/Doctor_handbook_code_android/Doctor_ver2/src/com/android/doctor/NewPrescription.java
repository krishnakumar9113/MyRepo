package com.android.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.DatePicker;
import android.widget.EditText;

public class NewPrescription extends Activity{
	DoctorModel Model;
	 String  patientid;
	 String  patientname;
	 String  datefull;
	 String ptdisease;
	 String ptprescription;
	 int parsedid;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newprescription);
        Model =new DoctorModel(this);
       // EditText ptid=(EditText)findViewById(R.id.EditTextptid);
	   // patientid=(String)ptid.getText().toString();
        Bundle bundle1 = getIntent().getExtras();
        patientid=bundle1.getString("patientid_g");
        patientname=bundle1.getString("patientname_g");;
       parsedid= (int)Integer.parseInt(patientid);
        
	     EditText ptname=(EditText)findViewById(R.id.EditTextptname);
	      ptname.setText( patientid+"-"+patientname);
	     
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {  
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.option_menu_submit, menu); 
		return true;
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{    // Handle item selection   
		switch (item.getItemId())
		{   
		case R.id.submit:  
			fillingtext();
			 SQLiteDatabase db =Model.getWritableDatabase();
		    	 ContentValues values = new ContentValues();
		    	 values.put(DoctorModel.PATIENTID,parsedid);
		    	 values.put(DoctorModel.PATIENTNAME, patientname);
		    	 values.put(DoctorModel.DATE,datefull);
		    	 Log.i("dbinssert",datefull);
		    	 values.put(DoctorModel.DISEASE, ptdisease);
		    	 Log.i("dbinssert", ptdisease);
	    		 values.put(DoctorModel.PRESCRIPTION, ptprescription);
	    		 Log.i("dbinssert", ptprescription);
	    		 if(db.insert(DoctorModel.TABLE2, null, values)!=-1)
	    		 {
	    			 AlertDialog.Builder dialog=new AlertDialog.Builder(NewPrescription.this);
	    		        dialog.setMessage("Prescription Details updated Successfully");
	    		        dialog.setPositiveButton("OK", null);
	    		        dialog.create();
	    		        dialog.show();
	    		 }else
	    		 {
	    			 AlertDialog.Builder dialog=new AlertDialog.Builder(NewPrescription.this);
	    		        dialog.setMessage("Prescription Details Not updated ");
	    		        dialog.setPositiveButton("OK", null);
	    		        dialog.create();
	    		        dialog.show();
	    		 }
	    		 
			return true;  

	      default:      
		     return super.onOptionsItemSelected(item);  
		}
		}
	private void fillingtext()
	{
		DatePicker dae= (DatePicker) findViewById(R.id.DatePicker01);
		String date=Integer.toString(dae.getDayOfMonth()) ;
		String month=Integer.toString(dae.getMonth()+1);
		String year=Integer.toString(dae.getYear());
		datefull=date+"/"+month+"/"+year;
		Log.i("datedisplayed",datefull);
		//EditText ptdt=(EditText)findViewById(R.id.EditTextdt);
		
	   // date=(String)ptdt.getText().toString();
	     EditText ptdis=(EditText)findViewById(R.id.EditTextdis);
	   ptdisease=(String)ptdis.getText().toString();
	     EditText ptpres=(EditText)findViewById(R.id.EditTextpres);
	    ptprescription=(String)ptpres.getText().toString();
	}
}
