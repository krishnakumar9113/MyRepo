package com.android.doctor;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PrescriptionList extends ListActivity{
	 DoctorModel Model;
	 String Patient_global;
	 String PatientName;
	 @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescriptionlist);
        Model=new DoctorModel(this);
        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("patientid");
        String name=bundle.getString("paitientname");
        Patient_global=id;
        PatientName=name;
       // Log.i("Prescripotion","idgot"+id);
        Cursor ncursor=getpaitientinfo(id);
		ListView_2(ncursor);
	}
	private void ListView_2(Cursor ncursor)
    {
    	ListView nv;
    	String prescription[]={DoctorModel.DATE};
        int view[]={R.id.name_entry};
        SimpleCursorAdapter nAdapter = new SimpleCursorAdapter(this, R.layout.list_example_entry, ncursor, prescription, view);
         this.setListAdapter(nAdapter);
         nv=getListView();
         nv.setOnItemClickListener(new OnItemClickListener() {  
         	public void onItemClick(AdapterView<?> mAdapter, View view,int position, long id) 
         	{
         		SQLiteDatabase db = Model.getReadableDatabase();
         		 
         		//Toast.makeText(getApplicationContext(), "click event", Toast.LENGTH_SHORT).show(); 
         		String id_str=Long.toString(id);
         		Log.i("itemclick",id_str);
         		
         		Cursor cursor = db.query(DoctorModel.TABLE2,new String[] {"patient_id","paitientname","diseasename","prescription","date"},"_id="+id_str,null , null,null,null);
         	//	Cursor cursor = db.query(DoctorModel.TABLE2, null,"_id="+id_str, null, null,null,null);  
         		
         		startManagingCursor(cursor);
         		//long patientid=cursor.getLong(1);
         		//String patientname=cursor.getString(2);
         	//	String Disease=cursor.getString();
         	//	String Prescription=cursor.getString();
         		if(cursor.moveToFirst()) { 
         		Intent i= new Intent(PrescriptionList.this,Show.class);
         		Bundle bundle = new Bundle();
         		bundle.putLong("patientid",cursor.getLong(0));
         		PatientName=cursor.getString(1);
         		bundle.putString("patientname",PatientName);
         		
         		bundle.putString("Disease",cursor.getString(2));
         		bundle.putString("Prescription",cursor.getString(3));
         		bundle.putString("Date",cursor.getString(4));
         		i.putExtras(bundle);
         		  startActivity(i);   
         		      
         		}
    		}  });
    }
   
public Cursor getpaitientinfo(String id)
    {
    	
    	SQLiteDatabase db = Model.getReadableDatabase();
    
    	Cursor cursor = db.query(DoctorModel.TABLE2,new String[] {"_id", "date"},"patient_id="+id,null , null,null,null); 
    	startManagingCursor(cursor);   
    	return cursor;  
    	}
@Override
public boolean onCreateOptionsMenu(Menu menu) {  
	MenuInflater inflater = getMenuInflater(); 
	inflater.inflate(R.menu.option_menu, menu); 
	return true;
	}
@Override
public boolean onOptionsItemSelected(MenuItem item)
{    // Handle item selection   
	switch (item.getItemId())
	{   
	case R.id.new_pres:   
		Intent i= new Intent(PrescriptionList.this,NewPrescription.class);
		Bundle bundle=new Bundle();
		bundle.putString("patientid_g",Patient_global);
		bundle.putString("patientname_g",PatientName);
		i.putExtras(bundle);
		 startActivity(i);
		return true;  

      default:      
	return super.onOptionsItemSelected(item);  
	}
	}
}
