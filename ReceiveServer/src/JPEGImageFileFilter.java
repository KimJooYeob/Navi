import java.io.File;

import javax.swing.filechooser.FileFilter;


public class JPEGImageFileFilter extends FileFilter implements java.io.FileFilter{
	
	 public boolean accept(File f)
	 {
		 if(f.getName().toLowerCase().endsWith(".jpeg"))
			 return true;
		 else if(f.getName().toLowerCase().endsWith(".jpg"))
			 return true;
		 else
			 return false;
	 }
	 public String getDescription()
	 {
		 return "JPEG files";
	 }
	
}
