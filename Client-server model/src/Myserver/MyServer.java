package Myserver;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer{
	public static void main(String[] args)
	{
		try {
			ServerSocket ss=new ServerSocket(44444);
			Socket s=ss.accept();
			DataInputStream dis=new DataInputStream(s.getInputStream());
			String str=dis.readUTF();
			System.out.println("Client says = "+str);
			ss.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
