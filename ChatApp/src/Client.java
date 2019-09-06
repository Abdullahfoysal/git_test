import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	final static int ServerPort=1234;
	
	public static void main(String args[]) throws IOException {
		Scanner scn=new Scanner(System.in);
		
		InetAddress ip=InetAddress.getByName("localhost");
		Socket s=new Socket(ip,ServerPort);
		
		DataInputStream dis=new DataInputStream(s.getInputStream());
		DataOutputStream dos=new DataOutputStream(s.getOutputStream());
		
		//send message t
		Thread sendMessage =new Thread(new Runnable () {
			public void run() {
				while(true) {
				String msg=scn.nextLine();
				try {
					dos.writeUTF(msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			}
		});
		
		//readMessage thread
		
		Thread readMessage=new Thread(new Runnable() {
			public void run() {
				while(true) {
				try {
					String msg=dis.readUTF();
					System.out.println(msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			
		});
		
		sendMessage.start();
		readMessage.start();
	}

}
