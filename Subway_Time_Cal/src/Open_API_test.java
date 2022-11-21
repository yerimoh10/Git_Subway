import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.IOException;

import java.io.File;
//import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class Open_API_test {

	public static void main(String[] args) throws IOException, ParseException {


		StringBuilder ssbb = callingApi("1", "5", "1728", "1", "1"); // 1715 ~ 1728까지 수동으로 작업.

		String result = ssbb.toString();

		Parsing p = new Parsing(result); // json 파싱
		int count = p.findingCount();

		System.out.println("count: " + count);
		p.listParsing(result);

		
		for(int i = 6; i<=count; i+= 5) { // 5단위로 호출해도 정상 출력됨 
			String start, end = "";
			StringBuilder sb;
			start = Integer.toString(i); 
			end = Integer.toString(i+4);
			sb = callingApi(start, end, "1728", "1", "1"); // 1715 ~ 1728까지 수동으로 작업.
			p.listParsing(sb.toString());
		}
		 

		System.out.println(ssbb.toString());

	}

	private static StringBuilder callingApi(String start, String end, String subway_num, String day, String dest)
			throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		urlBuilder.append("/" + URLEncoder.encode("696c47547274686532354b4e617755", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("SearchSTNTimeTableByIDService", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(start, "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(end, "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(subway_num, "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(day, "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(dest, "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;

		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close(); // ???
		conn.disconnect(); // ??
		return sb;
	}

}
