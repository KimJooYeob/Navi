import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;

public class MainThread extends Thread {
	String startLatitude;
	String startLongitude;
	
	String endLongitude; 
	String endLatitude;
	public void SendLocation() {
		ServerSocket locationSocket = null;
		Socket socket = null;
		try {
			locationSocket = new ServerSocket(5050);
			System.out.println("Location : server is Start");
			socket = locationSocket.accept();
			System.out.println("Location : Connect to Android");

			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());

			dos.writeUTF(startLatitude);
			dos.writeUTF(startLongitude);
			dos.writeUTF(endLatitude);
			dos.writeUTF(endLongitude);

			
			dos.close();
			locationSocket.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Error in SendLocation");
			e.printStackTrace();
		}

	}
	
	private void SendEndLocation(String ID) throws ClassNotFoundException, SQLException{
		Statement stmt = DBConnect("jdbc:mysql://localhost:3306/test", "root", "92k01i28m").createStatement();
		String SQL = "SELECT latitude, longitude from PARENT WHERE ID = "+ ID;
		System.out.println(SQL);
		ResultSet RS= stmt.executeQuery(SQL);
		while(RS.next()){
			endLatitude =  RS.getString(1);
			endLongitude = RS.getString(2);
		
			System.out.println("endLatitude : " + endLatitude+"   endLongitude : "+ endLongitude);
			break;
		}
	}
	
	public static Connection DBConnect(String url, String ID, String PW)
			throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, ID, PW);
		return conn;
	}

	public void run() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		while (true) {
			try {
				// 파일수신
				serverSocket = new ServerSocket(5000);
				System.out.println("Main : 서버가 시작되었습니다.");
				socket = serverSocket.accept();
				System.out.println("Main : 클라이언트와 연결되었습니다.");
				FileReceiver fr = new FileReceiver(socket);
				fr.start();
				
				while (!fr.getState().equals(Thread.State.TERMINATED))
					;
				
				// 이미지 비교
				NaiveSimilarityFinder SimilarityFinder = new NaiveSimilarityFinder();
				/*
				 * String basePath ="RoadView"; JFileChooser fc = new
				 * JFileChooser(basePath); fc.setFileFilter(new
				 * JPEGImageFileFilter()); File file = new
				 * File("CameraImage/1.jpg"); String GPS =
				 * SimilarityFinder.compare(file);
				 * System.out.println("GPS Location : "+GPS);
				 */
			
				String basePath = "RoadView";
				System.out.println("이미지 비교시작");
				JFileChooser fc = new JFileChooser(basePath);
				fc.setFileFilter(new JPEGImageFileFilter());
				System.out.println("파일읽기");
				File file = new File("RoadView/1.jpg");
				System.out.println("Image Comparing....");
				String GPS = SimilarityFinder.compare(file);
				System.out.println("이미지 비교완료");
				//file.delete();
				System.out.println("GPS Location : " + GPS);
				GPS = GPS.replaceAll(".jpg", "");
				String Location[] = GPS.split(",");
				startLatitude = Location[0];
				startLongitude = Location[1];
				System.out.println("Latitude : " + startLatitude + "  Longitude : "
						+ startLongitude);
				
				SendEndLocation("1");
				SendLocation();
				
				serverSocket.close();
				socket.close();
				//this.sleep(5000);
				

			} catch (Exception e) {
				System.out.println("Error in MainThread");
				System.out.println(e.getMessage());
				//continue;
			}
		}
	}

}
