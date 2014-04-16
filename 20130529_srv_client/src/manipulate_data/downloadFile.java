package manipulate_data;


import manipulate_data.ReadXML;

public class downloadFile {
	
	
	public static String download(int project_number){
		
		String URL = "193.2.92.184";
		int port = 8080;
		String scheme = "http";
		String userName = "sebastjan.meza@gmail.com";
		String password = "admin";
		String lastRevision;
		String getDownloadData;
		String roID; //Number which project/revision to download
		String nothing = "";
		
		lastRevision = get_content.response_text(URL, port, scheme, userName, password, "checkoutLastRevision?poid="+project_number+"&serializerName=Ifc2x3&sync=true");
		roID = lastRevision;
		getDownloadData = get_content.response_text(URL, port, scheme, userName, password, "getDownloadData?actionId="+roID);
		return (nothing + base64.decode(ReadXML.get_element(getDownloadData, "sCheckoutResult", "file")));

		
		
	}

}
