import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.*;

public class Server {
	
	static Vector<ClientHandler>ar=new Vector<>();
	
	static int ClientCount=0;
	public static void main(String[] args) throws IOException {
		
		ServerSocket ss=new ServerSocket(1234);
		Socket s;
		
		while(true) {
			s=ss.accept();
			System.out.println("New client request received "+s );
			
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			String n=dis.readUTF();
			
			System.out.println("creating a new handler for this client..");
			
			ClientHandler mtch=new ClientHandler(s,n,dis,dos);
			
			Thread t=new Thread(mtch);
			
			System.out.println("Adding this client to active client list");
			
			ar.add(mtch);
			
			t.start();
			
			ClientCount++;
			
			
			
		
		}
	}
	

}

 class ClientHandler implements Runnable{
	 
	 Scanner scn=new Scanner(System.in);
	 private String name;
	 private DataInputStream dis;
	 private DataOutputStream dos;
	 Socket s;
	 boolean isloggedin;
	 
	 public ClientHandler(Socket s, String name,DataInputStream dis,DataOutputStream dos) {
		 
		 this.dis=dis;
		 this.name=name;
		 this.dos=dos;
		 this.isloggedin=true;
		 
	 }

	@Override
	public void run() {
		String received;
		while(true) {
			try {
				received=dis.readUTF();
				System.out.println(received+" server");
				if(received.contentEquals("logout")) {
					this.isloggedin=false;
					this.dis.close();
					break;
				}
				
				//break message to message vs recepient
				StringTokenizer st=new StringTokenizer(received,"#");
				String MsgToSend=st.nextToken();
				String recepient=st.nextToken();
				
				//search recepient in active list
				
				for(ClientHandler mc:Server.ar) {
					if(mc.name.contentEquals(recepient) && mc.isloggedin==true) {
						mc.dos.writeUTF(this.name+ " : " + MsgToSend);
						break;
					}
				}
				
				
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			this.dis.close();
			this.dos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
