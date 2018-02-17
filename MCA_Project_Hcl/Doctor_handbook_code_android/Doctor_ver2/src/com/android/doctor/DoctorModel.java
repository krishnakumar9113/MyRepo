package com.android.doctor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DoctorModel extends SQLiteOpenHelper  {

	private static final String DATABASE_NAME = "PatientDB1.db";
	private static final int DATABASE_VERSION = 1;	
	public static final String TABLE1 = "Patient";// Table name	
	public static final String TABLE2 = "Prescription";// Table name
	public static final String PATIENTID= "patient_id";// Columns	
	public static final String PATIENTNAME = "paitientname";
	public static final String DISEASE = "diseasename";
	public static final String PRESCRIPTION = "prescription";
	public static final String DATE = "date";
	public DoctorModel(Context context) {	
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}	
	@Override	
	public void onCreate(SQLiteDatabase db) {
		String paitentlist = "create table " + TABLE1 + "( " + BaseColumns._ID			
		+ " integer primary key autoincrement, " +PATIENTID+ " integer, "		
		+ PATIENTNAME+ " text not null);";
		String Prescriptiontable = "create table " + TABLE2 + "( " + BaseColumns._ID			
		+ " integer primary key autoincrement, " +PATIENTID+ " integer, "		
		+ PATIENTNAME+ " text not null," +DISEASE+ " text not null ," +PRESCRIPTION+ " text not null ," +DATE+ " text not null);";
	/*	String prescriptionlist = "create table " + TABLE + "( " + BaseColumns._ID			
		+ " integer primary key autoincrement, " + TIME + " integer, "		
		+ TITLE + " text not null);";*/
		//Log.d("EventsData", "onCreate: " + sql);
		try{
			db.execSQL(paitentlist);
		   db.execSQL(Prescriptiontable);
		}
		catch(SQLiteException e)
		{
			Log.i("DATABASE","ERROR");
		}
		Log.i("doc","dbcreated");
		}	
	@Override	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)	
			return;	
		String sql = null;
		String sql2 = null;
		if (oldVersion == 1) 	
			sql = "alter table " + TABLE1 + " add note text;";
		sql2 = "alter table " + TABLE2 + " add note text;";
		if (oldVersion == 2)
		{
			sql = "";	
			sql2 = "";	
		Log.d("doctors", "onUpgrade	: " + sql + sql2);		
		}
		if (sql != null||sql2!=null)		
		{
			db.execSQL(sql);
		
		db.execSQL(sql2);
		}
		}
	}


