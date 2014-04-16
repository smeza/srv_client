package manipulate_data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ReadXML {

	public static String get_element(String xml_content, String baseNodle, String query) {
		String XML_content = xml_content;
		String base_xml_nodle = baseNodle;
		String search = query;
		String returncontent= "";
		try {
			
			Document doc = Util.stringToDom(XML_content);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(base_xml_nodle);
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					returncontent += (getTagValue(search, eElement))+"\n";

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (returncontent);
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	public static String[] get_two_elementXML(String response_XML,
			String baseNodle, String query, String oid) {
		String XML_content = response_XML;
		String base_xml_nodle = baseNodle;
		String search = query;
		List<String> content = new ArrayList<String>();
		String[] returncontent;

		try {
			
			Document doc = Util.stringToDom(XML_content);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(base_xml_nodle);
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					content.add(getTagValue(search, eElement)+" "
							+getTagValue(oid, eElement));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		returncontent = content.toArray(new String[content.size()]);
		return (returncontent);
	}

}
