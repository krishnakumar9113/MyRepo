package com.android.doctor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Show extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlayout);
       /* AlertDialog.Builder dialog=new AlertDialog.Builder(Show.this);
        dialog.setMessage("hurray");
        dialog.setPositiveButton("OK", null);
        dialog.create();
        dialog.show();*/
        Bundle bundle = getIntent().getExtras();
        long patientid=bundle.getLong("patientid");
 		String patientname=bundle.getString("patientname");
 		String Disease=bundle.getString("Disease");
       String Prescription=bundle.getString("Prescription");
       String Date=bundle.getString("Date");
        TextView patientid1=(TextView) findViewById(R.id.patientid);
        TextView patientname1=(TextView) findViewById(R.id.patientname);
        TextView disease1=(TextView) findViewById(R.id.disease);
        TextView prescription1=(TextView) findViewById(R.id.prescription);
        TextView Date1=(TextView) findViewById(R.id.date);
      patientid1.setText("PATIENT ID : "+Long.toString(patientid));
      patientname1.setText("PATIENT NAME :\n "+patientname);
        disease1.setText("DISEASE : "+Disease);
        prescription1.setText("PRESCRIPTION : \n"+Prescription);
        Date1.setText("DATE : "+Date);
	}
	
	}

