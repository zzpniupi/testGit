package testjdk;

import java.io.File;
import java.io.IOException;

public class test723 {
	public static void main(String args[]) {
		createtxt();
	}
	
	public static void createtxt() {
		File txt=new File("e:\\test723"+"/test");
		System.out.println(txt);
		if(!txt.exists()) {
			txt.mkdirs();
		}
		String filename="testdata.txt";
		File file=new File(txt,filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch(IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			}
		}
	}
}
