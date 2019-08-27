package Myclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {
	public static void main(String[] args)
	{
		try {
			Socket s=new Socket("write ip address of connecting server",44444);
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			dout.writeUTF("Hello Server");
			dout.flush();
			dout.close();
			s.close();
		} 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
