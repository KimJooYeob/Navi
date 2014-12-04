package com.skplanetx.tmapopenmapapi.ui;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.Environment;

public class GPSReciever extends Thread {
	Socket socket;
    String serverIP;
    DataInputStream dis;
    
    double fromLatitude;
    double fromLongitude;
    
    double toLatitude;
    double toLongitude;
    
    public GPSReciever(String serverIP){
        this.serverIP = serverIP;
    }
 


	@Override
    public void run() {
        try {
        	System.out.println("GPS Receiver has been started");
        	this.socket=new Socket(serverIP,5050);
        	  dis = new DataInputStream(socket.getInputStream());
              // 파일명을 전송 받고 파일명 수정.
              String latitude = dis.readUTF();
              String longitude = dis.readUTF();
              
              fromLatitude=Double.parseDouble(latitude);
              fromLongitude=Double.parseDouble(longitude);
              
              latitude = dis.readUTF();
              longitude = dis.readUTF();
              
              toLatitude=Double.parseDouble(latitude);
              toLongitude=Double.parseDouble(longitude);
              
              
              System.out.println("Start Location : "+fromLatitude+","+fromLongitude);
              System.out.println("End Location : "+toLatitude+","+toLongitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
    public double getFromLatitude() {
		return fromLatitude;
	}

	public void setFromLatitude(double fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public double getFromLongitude() {
		return fromLongitude;
	}

	public void setFromLongitude(double fromLongitude) {
		this.fromLongitude = fromLongitude;
	}

	public double getToLatitude() {
		return toLatitude;
	}

	public void setToLatitude(double toLatitude) {
		this.toLatitude = toLatitude;
	}

	public double getToLongitude() {
		return toLongitude;
	}

	public void setToLongitude(double toLongitude) {
		this.toLongitude = toLongitude;
	}
}
