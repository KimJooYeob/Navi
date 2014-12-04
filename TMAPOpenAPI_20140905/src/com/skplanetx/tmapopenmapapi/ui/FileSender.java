package com.skplanetx.tmapopenmapapi.ui;


import java.net.*;
import java.io.*;

import android.os.Environment;

public class FileSender extends Thread {
    Socket socket;
    String serverIP;
    DataOutputStream dos;
    FileInputStream fis;
    BufferedInputStream bis;
    double north;
    double east;
    File f;
    byte[] data;
    public FileSender(String serverIP,double north,double east,byte[] data){
        this.serverIP = serverIP;
        this.north=north;
        this.east=east;
        this.data=data;
        System.out.println("make FileSender : " + data.length);
    }
    
 
    @Override
    public void run() {
        try {
        	//this.sleep(10000);
        	this.socket=new Socket(serverIP,5000);
        	 // ������ ���ۿ� ��Ʈ�� ����
        	System.out.println("On SendFile");
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Socket Connect completes");
 
            // ���� �̸� ����
            // String fName = "�۾���a.txt";
            // String fName = "��Ƽa.ppt";
            // String fName = "�۾���a.jpg";
            String sdcard = Environment.getExternalStorageDirectory().toString()+"/Graduate/";
            String fName = "1.jpg";
            String path=sdcard+fName;
            dos.writeUTF(fName);
            System.out.printf("���� �̸�(%s)�� �����Ͽ����ϴ�.\n", fName);
            Double nGPS = new Double(north);
            Double eGPS = new Double(east);
            dos.writeUTF(nGPS.toString());
            dos.writeUTF(eGPS.toString());
            System.out.println("Location�� ���� �Ͽ����ϴ�.");
            // ���� ������ �����鼭 ����
            int temp = data.length;
            dos.writeUTF(Integer.toString(temp));
            System.out.println("����ũ�⸦ �����Ͽ����ϴ�..");
            dos.write(data, 0, data.length);
            System.out.println("������ �����Ͽ����ϴ�..");
           
            dos.flush();
            dos.close();
           // bis.close();
            // fis.close();
            System.out.println("���� ���� �۾��� �Ϸ��Ͽ����ϴ�.");
            System.out.println("���� ������ ������ : " + data.length);
           // f.delete();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}