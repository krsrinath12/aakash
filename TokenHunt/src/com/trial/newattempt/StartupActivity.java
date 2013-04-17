package com.trial.newattempt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartupActivity extends Activity {
	
	EditText inputName;
    EditText level;
    Button btnNextScreen;
	 
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.startup);
	        
	        level = (EditText) findViewById(R.id.level);
	        btnNextScreen = (Button) findViewById(R.id.btnNextScreen);
	    	                
	        //Listening to button event
	        btnNextScreen.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int temp = 0;
					try {
						temp = Integer.parseInt(level.getText().toString());
					} catch(NumberFormatException e) {
						popupWindow("Invalid Level");
					}
					
					if (temp < 1 || temp > 23)
						popupWindow("Invalid Level");
					
					else
					{
						Intent nextScreen = new Intent(getApplicationContext(), TableActivity.class);
						 
				         //Sending data to another Activity
				         nextScreen.putExtra("level", level.getText().toString());
				
				         startActivity(nextScreen);
				         finish ();
					}
				}
			});
	    }
	 
	 protected void popupWindow(String title) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(title);
			builder.setMessage("Level must be between 1 and 23");
			builder.setCancelable(false);
			builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			
		}

}