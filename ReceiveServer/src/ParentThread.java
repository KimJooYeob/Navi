import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ParentThread extends Thread {
	
	// DB���� �Լ�
	public static Connection DBConnect(String url, String ID, String PW) throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");		
		Connection conn = DriverManager.getConnection(url, ID, PW);
		return conn;
	}
	public void run(){
	
	
		Statement stmt=null;
		try {
			stmt = DBConnect("jdbc:mysql://localhost:3306/test", "root", "92k01i28m").createStatement();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ServerSocket serverSocket = null;
		Socket socket = null;
		while(true){
			//���ϼ���
			try{
				serverSocket = new ServerSocket(5010);
				System.out.println("Parent : ������ ���۵Ǿ����ϴ�.");
				socket = serverSocket.accept();
				System.out.println("Parent : Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");
				GPSReceiver GR = new GPSReceiver(socket);
				GR.start();
				while(!GR.getState().equals(Thread.State.TERMINATED));
				
				double latitude=GR.getLatitude();
				double longitude=GR.getLongitude();
				System.out.println(latitude+" , "+longitude +"  has been stored to Database");
				String SQL ="INSERT INTO PARENT(LATITUDE,LONGITUDE) VALUE("+
							latitude+","+longitude+")";
			
				System.out.println(SQL);
				stmt.executeQuery(SQL);
				socket.close();
				serverSocket.close();
			
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				continue;
			}
		}
		
	}
	
	
}
