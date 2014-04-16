package manipulate_data;

import java.io.UnsupportedEncodingException;

import android.util.Base64;



public class base64 {

	public static String decode(String s) {
		
		byte[] data = Base64.decode(s, Base64.DEFAULT);
		String text = null;
		try {
			text = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	
}