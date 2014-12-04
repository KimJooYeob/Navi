import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

class GPSReceiver extends Thread {
    Socket socket;
    DataInputStream dis;
    FileOutputStream fos;
    BufferedOutputStream bos;
    double latitude;
    double longitude;
    public GPSReceiver(Socket socket) {
        this.socket = socket;
    }
 
    @Override
    public void run() {
        try {
            System.out.println("���� ���� �۾��� �����մϴ�.");
            dis = new DataInputStream(socket.getInputStream());
            String nGPS = dis.readUTF();
            System.out.println("North : "+nGPS);
            String eGPS = dis.readUTF();
            System.out.println("East : "+eGPS);
            // ������ �����ϰ� ���Ͽ� ���� ��� ��Ʈ�� ����
            latitude=Double.parseDouble(nGPS);
            longitude=Double.parseDouble(eGPS);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}