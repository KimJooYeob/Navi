import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;

public class ReceiveServer {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
		MainThread MT = new MainThread();
		ParentThread PT = new ParentThread();
		PT.start();
		MT.start();

	}
}
