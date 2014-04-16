package com.example.srv_client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.net.ftp.*;

import com.example.srv_client.R.drawable;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleListItem extends Activity {

	private int mYear;
	private int mMonth;
	private int mDay;
	private String text;

	private TextView mDateDisplay;
	private TextView txtDetails;
	private Button mPickDate;

	static final int DATE_DIALOG_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.single_list_item_view);

		Intent i = getIntent();
		// getting attached intent data
		String intentGet = i.getStringExtra("projektName");// .split(" ");
		String[] elementi = intentGet.split(" ");
		setTitle(elementi[0]);
		final int projekt = Integer.valueOf(elementi[elementi.length - 1]);
		String projektDetails = "details.txt";

		Button btnAugment = (Button) findViewById(R.id.Augment4d);
		ImageView immage = (ImageView) findViewById(R.id.imageView1);
		// TextView txtDetails = (TextView) findViewById(R.id.fileDetails);
		txtDetails = (TextView) findViewById(R.id.fileDetails);
		txtDetails.setMovementMethod(new ScrollingMovementMethod());

		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
		mPickDate = (Button) findViewById(R.id.pickDate);

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		mDateDisplay.setText(dateFormat.format(date));

		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		if (projekt == 62) {
			immage.setImageResource(drawable.eko);
			projektDetails = "ekoDetails.txt";

		} else if (projekt == 66) {
			immage.setImageResource(drawable.pavilion);
			projektDetails = "pavilionDetails.txt";
		} else {
			immage.setImageResource(drawable.drugo);
		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		//
		FTPClient mFTP = new FTPClient();
		File root = android.os.Environment.getExternalStorageDirectory();

		// Download info;
		String folder = "/AAmodels";
		String destpath = root.getAbsolutePath() + folder;
		String srcFileSpec = projektDetails;
		String destname = srcFileSpec;

		String fileName = root.getAbsolutePath() + folder + "/" + destname;
		try {

			// Connect to FTP Server
			mFTP.connect("193.2.92.184");
			mFTP.login("smeza", "lenovot60");
			mFTP.setFileType(FTP.BINARY_FILE_TYPE);
			mFTP.enterLocalPassiveMode();
			mFTP.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

			BufferedOutputStream fos = null;

			File pathSpec = new File(destpath);
			fos = new BufferedOutputStream(new FileOutputStream(
					pathSpec.toString() + "/" + destname));
			mFTP.retrieveFile(srcFileSpec, fos);

			fos.close();

			mFTP.disconnect();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		text = "Details";

		try {
			text = readFile(fileName);
		} catch (IOException e) {

			e.printStackTrace();
		}
		txtDetails.setText(Html.fromHtml("Select a date!"));

		btnAugment.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				if (projekt == 62) {
					// eko
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("layar://eko4d"));
					startActivity(browserIntent);
				} else if (projekt == 66) {
					// pavilion
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("layar://pavilion4d"));
					startActivity(browserIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							"This layar is not available.", Toast.LENGTH_SHORT)
							.show();

				}

			}
		});

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private void updateDisplay() {

		StringBuilder sb01 = new StringBuilder();
		sb01.append(mDay).append(".").append(mMonth + 1).append(".")
				.append(mYear).append(" ");
		String izbraniDatum = sb01.toString();
		this.mDateDisplay.setText(izbraniDatum);

		StringBuilder sb = new StringBuilder();
		String[] temp;
		temp = text.split("\n");

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.ENGLISH);

		String poudarjeno = null;

		try {
			Date startDate = sdf.parse(izbraniDatum);

			Date date = sdf.parse(izbraniDatum);
			if (startDate.after(sdf.parse("7.11.2012"))
					&& startDate.before(sdf.parse("30.10.2013"))) {

				for (String s : temp) {
					Date date2 = sdf.parse(s.split("\t")[1]);
					Date date3 = sdf.parse(s.split("\t")[2]);
					if (date.before(date3) && date.after(date2)
							|| date.equals(date2) || date.equals(date3)) {
						sb.append("<b>" + s + "</b>" + "<br>");
					} else {
						sb.append("<i>" + s + "</i>" + "<br>");
					}
					poudarjeno = sb.toString();
				}

			}else{
				poudarjeno = "<b>Picka a date in range 7.11.2012 - 30.10.2013</b>";
				
			}

		} catch (ParseException ex) {
			ex.printStackTrace();
		}


		this.txtDetails.setText(Html.fromHtml(poudarjeno));

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

}