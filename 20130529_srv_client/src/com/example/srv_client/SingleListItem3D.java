package com.example.srv_client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SingleListItem3D extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_list_item_3d);

		Intent i = getIntent();
		// getting attached intent data
		setTitle(i.getStringExtra("projektName").split(" ")[0]);


		Button btnAugment = (Button) findViewById(R.id.Augment4d);

		
		 btnAugment.setOnClickListener(new View.OnClickListener() {
		
		 public void onClick(View arg0) {
		
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("layar://eko"));
				startActivity(browserIntent);
		
		 }
		 });

	}
}
