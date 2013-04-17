package com.trial.newattempt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TableActivity extends Activity {

	private int rowCount = 5;
	private int columnCount = 5;
	private int level = 3;
	private Random r = new Random();
	
	public int levelChange = 0;
	private TableLayout tableLayout;
	private int token;
	private int score = 0;
	
	private List<Integer> visButts = new ArrayList<Integer>();
	private List<Integer> availTokens = new ArrayList<Integer>();
	private List<Integer> gotToken = new ArrayList<Integer>();
	private List<Integer> clickedButts = new ArrayList<Integer>();
	
	
	private void getVisible() {
		
		List<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < (rowCount * columnCount); i++)
			temp.add(i);
		for(int i = 0; i < level; i++) {
			int index = r.nextInt(temp.size ());
			Integer atindex = temp.get(index);
			temp.remove(atindex);
			visButts.add(atindex);
			
			ImageButton b =  (ImageButton) findViewById(atindex);
			b.setVisibility(View.VISIBLE);
		}
		availTokens.addAll(visButts);
		levelChange = 0;
		setToken();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);
		
		tableLayout = (TableLayout) findViewById(R.id.table);
		Intent in = getIntent();
		level = Integer.parseInt(in.getStringExtra("level"));
		level += 2;
				
		Log.e("Second Screen: ", level + "");

		for(int i = 0; i < rowCount; i++) {
			TableRow tr = new TableRow(this);
			tr.setPadding(10, 10, 10, 10);
			for(int j = 0; j < columnCount; j++) {
				ImageButton imageButton = new ImageButton(this);
				imageButton.setImageResource(R.drawable.box_grey);
				imageButton.setPadding(0, 0, 0, 0);
				imageButton.setId((i * rowCount) + j);
				imageButton.setVisibility(View.INVISIBLE);
				imageButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						checkValidity(v);
					}
				}); 
				tr.addView(imageButton);
			}
			tableLayout.addView(tr);
		}
		getVisible();
	}

	protected void popupWindow(String title) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage("Game Over\n" + "You Collected " + score + " tokens.");
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
	
	protected void checkValidity(View v) {

		final ImageButton temp = (ImageButton) v;
		int id = v.getId();
		
		if(id == token) {
			score++;
			gotToken.add(id);
			if(setToken()) {
				levelChange = 1;
				gotToken.clear();
				visButts.clear();
				level++;
			}
			clickedButts.clear();
			temp.setImageResource(R.drawable.yellow_button);
		}
		else if(gotToken.contains(id) || clickedButts.contains(id)) {
			temp.setImageResource(R.drawable.cross);
			popupWindow("Game Over");
		}
		else {
			clickedButts.add(id);
			temp.setImageResource(R.drawable.white_dot);
		}
		new CountDownTimer(400, 400) {
			
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				temp.setImageResource(R.drawable.box_grey);
				if(levelChange == 1) {
					for(int i = 0; i < (rowCount * columnCount); i++)
						((ImageButton) findViewById(i)).setVisibility(View.INVISIBLE);
					getVisible();
				}
			}
		}.start();
	}

	protected boolean setToken() {
		try {
			token = availTokens.remove(0);
			return false;
		} catch(IndexOutOfBoundsException e) {
			return true;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.table, menu);
		return true;
	}

}