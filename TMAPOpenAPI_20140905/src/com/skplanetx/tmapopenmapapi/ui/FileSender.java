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
        	 // 데이터 전송용 스트림 생성
        	System.out.println("On SendFile");
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Socket Connect completes");
 
            // 파일 이름 전송
            // String fName = "작업용a.txt";
            // String fName = "피티a.ppt";
            // String fName = "작업용a.jpg";
            String sdcard = Environment.getExternalStorageDirectory().toString()+"/Graduate/";
            String fName = "1.jpg";
            String path=sdcard+fName;
            dos.writeUTF(fName);
            System.out.printf("파일 이름(%s)을 전송하였습니다.\n", fName);
            Double nGPS = new Double(north);
            Double eGPS = new Double(east);
            dos.writeUTF(nGPS.toString());
            dos.writeUTF(eGPS.toString());
            System.out.println("Location을 전송 하였습니다.");
            // 파일 내용을 읽으면서 전송
            int temp = data.length;
            dos.writeUTF(Integer.toString(temp));
            System.out.println("파일크기를 전송하였습니다..");
            dos.write(data, 0, data.length);
            System.out.println("파일을 전송하였습니다..");
           
            dos.flush();
            dos.close();
           // bis.close();
            // fis.close();
            System.out.println("파일 전송 작업을 완료하였습니다.");
            System.out.println("보낸 파일의 사이즈 : " + data.length);
           // f.delete();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}