package com.example.srv_client;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends Activity {

	private RadioGroup radioGroup;
	private RadioButton radioButton;
	boolean haveNetworkConnection = true;

	EditText inputName;
	EditText inputPASS;
	String userName;
	String pass;

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		setTitle("BIM server AR client");

		inputName = (EditText) findViewById(R.id.editText1);
		inputPASS = (EditText) findViewById(R.id.editText2);

		Button btnNextScreen = (Button) findViewById(R.id.button1);

		// Listening to button event
		btnNextScreen.setOnClickListener(new View.OnClickListener() {
			//
			public void onClick(View arg0) {

				radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

				// get selected radio button from radioGroup
				int selectedId = radioGroup.getCheckedRadioButtonId();

				// find the radio button by returned id
				radioButton = (RadioButton) findViewById(selectedId);

				int idx = radioGroup.indexOfChild(radioButton);

				if (isOnline() && idx == 0) {
					Toast.makeText(getApplicationContext(),
							"Connecting please wait...", Toast.LENGTH_SHORT)
							.show();

					Intent nextScreen = new Intent(getApplicationContext(),
							SelectProject3d.class);

					// Sending data to another Activity
					nextScreen.putExtra("inputName", inputName.getText()
							.toString());
					nextScreen.putExtra("inputPASS", inputPASS.getText()
							.toString());
					nextScreen.putExtra("request1", "getAllProjects");

					Log.e("n", inputName.getText() + "." + inputName.getText());
					startActivity(nextScreen);

				} else if (isOnline() && idx == 1) {
					Toast.makeText(getApplicationContext(),
							"Connecting please wait...", Toast.LENGTH_SHORT)
							.show();

					Intent nextScreen = new Intent(getApplicationContext(),
							SelectProject.class);

					// Sending data to another Activity
					nextScreen.putExtra("inputName", inputName.getText()
							.toString());
					nextScreen.putExtra("inputPASS", inputPASS.getText()
							.toString());
					nextScreen.putExtra("request1", "getAllProjects");

					startActivity(nextScreen);
				}

				else if (isOnline() && idx == 2) {
					Toast.makeText(getApplicationContext(),
							"THIS OPTION IS NOT AVAILABLE", Toast.LENGTH_SHORT)
							.show();

				}

				else if (!isOnline()) {

					Toast.makeText(getApplicationContext(),
							"Connect to internet", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Check BIM server status", Toast.LENGTH_LONG)
							.show();
				}

			}

		});

	}

}
