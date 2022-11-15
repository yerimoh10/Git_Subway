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

	public static void main(String[] args) throws IOException{
/*
		String subway_num = "1713";
		String day = "1";	
		String dest = "1";	
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		urlBuilder.append("/" + URLEncoder.encode("696c47547274686532354b4e617755","UTF-8") );
		urlBuilder.append("/" + URLEncoder.encode("JSON","UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("SearchSTNTimeTableByIDService","UTF-8")); 
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); 
		urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); 
		urlBuilder.append("/" + URLEncoder.encode(subway_num,"UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(day,"UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(dest,"UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		System.out.println("Response code: " + conn.getResponseCode()); 
		BufferedReader rd;
		
		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		
		json parsing 
		total_context.append(ssbb);   // 전체 내용 넣기 
		 */
		
		
		StringBuilder ssbb = callingApi("1", "5", "1713", "1", "1");
		StringBuilder total_context = new StringBuilder();
		
		String result = ssbb.toString();
		JSONParser jsonParser = new JSONParser();
		long total_count = 0;
		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
			JSONObject timeTable = (JSONObject)jsonObject.get("SearchSTNTimeTableByIDService");
			//JSONObject total_count = (JSONObject)timeTable.get("list_total_count");
			total_count = (long) timeTable.get("list_total_count");
			System.out.println("totalcount: " + total_count);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("JSON 에러");
		}
		int count = (int) total_count;
		System.out.println("count : " + count);
		
		for(int i = 6; i<=count; i+= 5) {   // 5단위로 호출해도 정상 출력됨
			String start, end = "";
			start = Integer.toString(i);
			end = Integer.toString(i+4);
			ssbb.append(callingApi(start, end, "1713", "1", "1"));
			
		}
		
		
		System.out.println(ssbb.toString());
		try {
			File fout = new File("C:\\Users\\oyeri\\Desktop\\Java_Project_TeamH\\context.txt");
			FileWriter fileWriter = new FileWriter(fout);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(ssbb.toString());
			printWriter.close();
		}catch(FileNotFoundException e) {
			System.out.println("해당 파일 없음");
		}
		
		TimeDB tdb = new TimeDB();
		tdb.update("insert into student......");
		tdb.select("select * from student");
		
		
	}
	
	
	private static StringBuilder callingApi(String start, String end, String subway_num, String day, String dest) throws IOException{
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		urlBuilder.append("/" + URLEncoder.encode("696c47547274686532354b4e617755","UTF-8") );
		urlBuilder.append("/" + URLEncoder.encode("JSON","UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("SearchSTNTimeTableByIDService","UTF-8")); 
		urlBuilder.append("/" + URLEncoder.encode(start,"UTF-8")); 
		urlBuilder.append("/" + URLEncoder.encode(end,"UTF-8")); 
		urlBuilder.append("/" + URLEncoder.encode(subway_num,"UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(day,"UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(dest,"UTF-8"));
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		System.out.println("Response code: " + conn.getResponseCode()); 
		BufferedReader rd;
		
		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();   // ???
		conn.disconnect();   //??
		return sb;
	}

}
