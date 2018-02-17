package com.android.doctor;



//import java.util.Date;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Entrypage extends ListActivity  {
    /** Called when the activity is first created. */
	 DoctorModel Model;
	 ListView lv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
       Model=new DoctorModel(this);
       add_paitient_name();
       Cursor cursor=this.getcursor();
       //showinfo(cursor);
       String patients[]={DoctorModel.PATIENTID,DoctorModel.PATIENTNAME};
       int view[]={R.id.id_entry,R.id.name_entry};
       SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_example_entrypage, cursor, patients, view);
        this.setListAdapter(mAdapter);
        lv=getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {  
    	public void onItemClick(AdapterView<?> mAdapter, View view,int position, long id) 
    	{
    		
    		String basecolumnid=Long.toString(id);
    		Log.i("id",Long.toString(id));
    		SQLiteDatabase db = Model.getReadableDatabase();
        	Cursor cursor = db.query(DoctorModel.TABLE1, new String[]{"patient_id","paitientname"},"_id="+basecolumnid, null, null,null,null);  
        	startManagingCursor(cursor); 
        	if(cursor.moveToFirst()) { 
        	Long patientid=cursor.getLong(0);
        	
    		Log.i("ITem click patientid ",patientid.toString());
    		
    		Intent i= new Intent(Entrypage.this,PrescriptionList.class);
    		Bundle bundle = new Bundle();
    		bundle.putString("patientid",Long.toString(patientid));
    		bundle.putString("paitientname",cursor.getString(1));
    		i.putExtras(bundle);
   		     startActivity(i); 
        	}
    		//Log.i("ITem click", a.toString());
    		//Cursor ncursor=getpaitientinfo(id);
    		//ListView_2(ncursor);
    		//'Toast.makeText(getApplicationContext(), "hai", Toast.LENGTH_SHORT).show();   
    		// When clicked, show a toast with the TextView text 
    	//	Toast.makeText(getApplicationContext(), ((TextView) view.getRootView()).getText(),Toast.LENGTH_SHORT).show();   
    		}  });
    	}

   
    
  
    //for testing 
    public void add_paitient_name()
    {
    	  //table1	
    	 SQLiteDatabase db =Model.getWritableDatabase();
    	
    	 ContentValues values = new ContentValues();
    	 for (int i=1;i<=3;i++)
    	 {
    		
    	 String paitientname="PATIENT"+Integer.toString(i);
    	 values.put(DoctorModel.PATIENTID,i);
    	 values.put(DoctorModel.PATIENTNAME,paitientname);
    	 db.insert(DoctorModel.TABLE1, null, values);
    	 }
    	 
    	 //table2
      	 SQLiteDatabase db2 =Model.getWritableDatabase();	
    	 ContentValues values2 = new ContentValues();
    	 for (int i=1;i<=4;i++)
    	 {
    		if(i<=3)
    		{
         String paitientname="PATIENT"+Integer.toString(i);
    	 values2.put(DoctorModel.PATIENTID,i);
    	 values2.put(DoctorModel.PATIENTNAME,paitientname);
    		}
    	 if(i==1)
    		 { 
    		 values2.put(DoctorModel.DISEASE,"Cold");
    		 values2.put(DoctorModel.PRESCRIPTION,"Crocin");
    		// Date date1=new Date(111,06,19);
    	 values2.put(DoctorModel.DATE,"02/07/2011");
    		 }
    	 if(i==2)
		 {
    		 values2.put(DoctorModel.DISEASE,"Fracture");
    		 values2.put(DoctorModel.PRESCRIPTION,"Bandage");
    		// Date date1=new Date(111,06,20);
	        values2.put(DoctorModel.DATE,"15/05/2011");
		 }
    	 if(i==3)
		 {
    		 values2.put(DoctorModel.DISEASE,"Stomach Ache");
    		 values2.put(DoctorModel.PRESCRIPTION,"Gelusil");
    		// Date date1=new Date(111,06,21);
	        values2.put(DoctorModel.DATE,"27/06/2011");
		 }
    	 if(i==4)
		 {
			 values2.put(DoctorModel.PATIENTID,2);
	    	 values2.put(DoctorModel.PATIENTNAME,"PATIENT2");
	    	 values2.put(DoctorModel.DISEASE,"High fever");
    		 values2.put(DoctorModel.PRESCRIPTION,"Garamycin");
    		// Date date1=new Date(111,06,22);
    	     values2.put(DoctorModel.DATE,"22/06/2011");
	    	
		 }
    	 db2.insert(DoctorModel.TABLE2, null, values2);
    	 }
    	 
    }
    private Cursor getcursor()
    {
    	SQLiteDatabase db = Model.getReadableDatabase();
    	Cursor cursor = db.query(DoctorModel.TABLE1, null, null, null, null,null,null);  
    	startManagingCursor(cursor);   
    	return cursor;  
    	}
  /* private void showinfo(Cursor cursor) { 
    	StringBuilder ret = new StringBuilder("Patients List:\n\n");
    	
    	while (cursor.moveToNext()) { 
    		long id = cursor.getLong(0);   
    		long patientid = cursor.getLong(1);   
    		String patientname = cursor.getString(2); 
    				ret.append(patientid+":"+ id+":"+patientname + "\n");
    			
    			   }
    		    		}*/
    
    }
