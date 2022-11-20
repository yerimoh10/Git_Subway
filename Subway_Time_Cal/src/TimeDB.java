
import java.sql.DriverManager;

public class TimeDB {
	java.sql.Connection conn;
	java.sql.Statement stmt;
	java.sql.ResultSet rs;
	
	TimeDB(){//생성자
		connect();
	}
	
	void connect() {
		String url = "jdbc:mysql://localhost:3306/javadb"; 
		String user = "root";
		String password = "6574";
		
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			this.conn = java.sql.DriverManager.getConnection(url, user, password);
			System.out.println("db 연결!");
		} catch (Exception e) {
			System.out.println("connection error: " + e);
		}
	}
	//업데이트 쿼리문 실행
	void update(String dbCommand) {
		try {
			this.stmt.executeUpdate(dbCommand);
		} catch (Exception e) {
			System.out.println("update error : " + e);
		}
	}
	
	// 실행결과
	void select(String dbSelect) {
		try {
			this.rs = this.stmt.executeQuery(dbSelect);
		} catch (Exception e) {
			System.out.println("select error : " + e);
		}
	}
	
	void close() {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("close error : " + e);
		}
	}
	
	
}