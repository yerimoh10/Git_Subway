import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class Parsing {
	JSONObject jsonObject;
	JSONParser jsonParser = new JSONParser();
	long total_count = 0;
	
	String stat_cd = "";	// 역코드 - int 
	String stat_nm = ""; // 역이름
	String line_num = "";  //호선
	String train_no = ""; // 기차번호
	String arrive_time = ""; // 도착시간
	String left_time = ""; // 출발시간
	String origin = ""; // 출발역 int
	String destination = ""; // 종착역 int 
	String week = "";  // 요일 (평일, 토요일, 공휴일) int 
	String inout = ""; // 상행, 하행 int
	String express = "" ; // 급행 int
	
	Parsing(){
		
	}
	Parsing(String jsonContext) throws ParseException{
		jsonObject = (JSONObject)jsonParser.parse(jsonContext);
		
	}
	int findingCount() {
		//JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonContext);
		JSONObject timeTable = (JSONObject)jsonObject.get("SearchSTNTimeTableByIDService");
		total_count = (long) timeTable.get("list_total_count");
		int count = (int) total_count;
		return count;
	}
	void listParsing(String json_contents) throws ParseException {
		JSONObject jsonObject1 = (JSONObject)jsonParser.parse(json_contents);
		JSONObject timeTable = (JSONObject)jsonObject1.get("SearchSTNTimeTableByIDService");
		//JSONObject total_count = (JSONObject)timeTable.get("list_total_count");
		total_count = (long) timeTable.get("list_total_count");
		System.out.println("totalcount: " + total_count);
		JSONArray rows = (JSONArray)timeTable.get("row");
		for(int i = 0; i< rows.size(); i++) {
			JSONObject row_contents = (JSONObject)rows.get(i);
			stat_cd =  row_contents.get("STATION_CD").toString();
			stat_nm = row_contents.get("STATION_NM").toString();
			line_num = row_contents.get("LINE_NUM").toString();
			line_num = line_num.substring(1,line_num.length()); //01호선이어서 1호선으로 바꿔줌
			train_no = row_contents.get("TRAIN_NO").toString();
			arrive_time = row_contents.get("ARRIVETIME").toString();
			left_time = row_contents.get("LEFTTIME").toString();
			origin = row_contents.get("ORIGINSTATION").toString();
			destination = row_contents.get("DESTSTATION").toString();
			week = row_contents.get("WEEK_TAG").toString();
			inout = row_contents.get("INOUT_TAG").toString();
			express = row_contents.get("EXPRESS_YN").toString();
			printing();
		}

	}
	
	
	void printing() {
		
		//TimeDB tdb = new TimeDB(); //
		String query = String.format("insert into FIXEDTIME_TBL values(%s,'%s', '%s', '%s', '%s','%s', %s, %s, %s, %s, '%s');", 
				stat_cd,stat_nm,line_num, train_no, arrive_time, left_time, origin, destination, week, inout, express);
		System.out.println(query);
		// db에 저장하면 됨!
		//tdb.update(query);
		//tdb.select("select * from student");
	}
	
}
