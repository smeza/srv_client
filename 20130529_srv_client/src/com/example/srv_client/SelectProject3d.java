package com.example.srv_client;

import manipulate_data.ReadXML;
import manipulate_data.get_content;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectProject3d extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Izberi projekt 3D");		

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		Intent i = getIntent();
		// Receiving the Data
		String name = i.getStringExtra("inputName");
		String password = i.getStringExtra("inputPASS");
		String request = i.getStringExtra("request1");
		String URL = "193.2.92.184";
		int port = 8080;
		String scheme = "http";
		String response_XML = "";
		boolean responseOK;
//		name = "sebastjan.meza@gmail.com";
//		password = "admin";		
		responseOK = true;
		String[] error = {"Sorry something went wrong, connect BIM server administrator."};

		
		try {
			response_XML = get_content.response_text(URL, port, scheme, name,
					password, request);

			responseOK = true;
			

		} catch (Exception e) {
			responseOK = false;
			e.printStackTrace();
		}

		if (responseOK) {		
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item3d, 	ReadXML.get_two_elementXML(response_XML, "sProject","name","oid")));

        ListView lv = getListView();
        
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
 
              // selected item
              String product = ((TextView) view).getText().toString();
              String[] elementi = product.split(" ");
              int projekt = Integer.valueOf(elementi[elementi.length-1]);
     
            		  
              if (projekt ==66){
  				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("layar://bim3d"));
  				startActivity(browserIntent);
              }
              else if (projekt ==62){
    				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("layar://eko"));
      				startActivity(browserIntent);              }
              else{
//            	  Toast.makeText(getApplicationContext(),
//            			  product.split(" ")[0]+ "Augmented view for this project is not available.", Toast.LENGTH_SHORT)
//							.show();
    				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("layar://bim3d"));
      				startActivity(browserIntent);
              }
 
//              // Launching new Activity on selecting single List Item
//              Intent i = new Intent(getApplicationContext(), SingleListItem3D.class);
//              // sending data to new activity
//              i.putExtra("projektName", product);
//              startActivity(i);
 
          }
        });		
		
				
		}
		else{
	        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item3d, 	error));
		}

	}
}
