/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
<<<<<<< HEAD
import java.util.Vector;
=======
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author foysal
 */
public class Client extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
     ///initialize all 
    //client code
    static int ServerPort=1234;
    static InetAddress ip;
    static Socket s;
    static DataInputStream dis;
    static DataOutputStream dos;
    static String name="foysal";
    static String ServerAddress;

   
    
    public Client(String name,String ServerAddress,int ServerPort ){
       
        this.name=name;
        this.ServerAddress=ServerAddress;
        this.ServerPort=ServerPort;
//        System.out.println("client asse");
    }
    public Client() {
        initComponents();
    }
    
<<<<<<< HEAD
//    public static void Refresh(Vector<ClientHandler> ActiveClient){
//        for(ClientHandler mc:ActiveClient){
//           Active.setText(mc.name+"\n");
//        }
//        
//    }
    
=======
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        msg_area = new javax.swing.JTextField();
        msg_text = new javax.swing.JTextField();
        ClientSentButton = new javax.swing.JButton();
        SentToClient = new javax.swing.JTextField();
<<<<<<< HEAD
        status = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Active = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        Activity = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

=======
        jScrollPane1 = new javax.swing.JScrollPane();
        ActiveList = new javax.swing.JList<>();
        status = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_area.setText("Client");
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
        msg_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_areaActionPerformed(evt);
            }
        });

        msg_text.setText("message text");
        msg_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_textActionPerformed(evt);
            }
        });

        ClientSentButton.setText("send");
        ClientSentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientSentButtonActionPerformed(evt);
            }
        });

        SentToClient.setText("To");
        SentToClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SentToClientActionPerformed(evt);
            }
        });

<<<<<<< HEAD
=======
        jScrollPane1.setViewportView(ActiveList);

>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
        status.setText("Active");
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });

<<<<<<< HEAD
        Active.setColumns(20);
        Active.setRows(5);
        jScrollPane2.setViewportView(Active);

        Activity.setColumns(20);
        Activity.setRows(5);
        jScrollPane3.setViewportView(Activity);

=======
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
<<<<<<< HEAD
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ClientSentButton)
                .addGap(170, 170, 170))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(msg_area)
                .addGap(210, 210, 210)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SentToClient, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
=======
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SentToClient, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 181, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ClientSentButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(msg_area)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))))
                .addContainerGap())
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
<<<<<<< HEAD
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_area, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
=======
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                    .addComponent(msg_area))
                .addGap(18, 18, 18)
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(msg_text, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(SentToClient))
                .addGap(3, 3, 3)
                .addComponent(ClientSentButton))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_textActionPerformed
      
        
    }//GEN-LAST:event_msg_textActionPerformed

    private void SentToClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SentToClientActionPerformed
        
    }//GEN-LAST:event_SentToClientActionPerformed

    private void ClientSentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientSentButtonActionPerformed

        try {
            String msout="";
            msout=msg_text.getText().trim();
            msout+="#"+SentToClient.getText().trim();
            dos.writeUTF(msout);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ClientSentButtonActionPerformed

    private void msg_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_areaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msg_areaActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    /**
     * @param args the command line arguments
     */
     
 
    
	
	public static void main(String args[]) throws IOException {
            
            /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
        
<<<<<<< HEAD
      
=======
       
            
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
            
		Scanner scn=new Scanner(System.in);
		
		// ip=InetAddress.getByName("localhost");
		// s=new Socket(ip,ServerPort);
                    s=new Socket(ServerAddress,ServerPort);
                 
		
		 dis=new DataInputStream(s.getInputStream());
		 dos=new DataOutputStream(s.getOutputStream());
<<<<<<< HEAD
                 
//		  msg_area.setText(Client.name);
		//send message t
               // System.out.println(name);
=======
		
		//send message t
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
		Thread sendMessage =new Thread(new Runnable () {
			public void run() {
				//while(true) {
				//String msg=scn.nextLine();
				try {
                                        
					dos.writeUTF(name);
<<<<<<< HEAD
//                                        Active.setText(name);
=======
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			//}
			}
		});
		
		//readMessage thread
<<<<<<< HEAD
                int client=0;
=======
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
		
		Thread readMessage;
        readMessage = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        String msg=dis.readUTF();
<<<<<<< HEAD
                        msg_area.setText(name);
                        
                        
                       Activity.setText(Activity.getText().trim()+"\n"+msg);
=======
//                        msg_area.setText("k");
                            msg_area.setText(msg_area.getText().trim()+" "+msg);
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
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

<<<<<<< HEAD
=======

>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
    
    //client code end
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Client().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
<<<<<<< HEAD
    private static javax.swing.JTextArea Active;
    private static javax.swing.JTextArea Activity;
    private javax.swing.JButton ClientSentButton;
    private javax.swing.JTextField SentToClient;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
=======
    private static javax.swing.JList<String> ActiveList;
    private javax.swing.JButton ClientSentButton;
    private javax.swing.JTextField SentToClient;
    private javax.swing.JScrollPane jScrollPane1;
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
    private static javax.swing.JTextField msg_area;
    private javax.swing.JTextField msg_text;
    private javax.swing.JTextField status;
    // End of variables declaration//GEN-END:variables
<<<<<<< HEAD

    


=======
>>>>>>> 15ab5f76f7b86e84c76a59ddc836fa4658ef686e
}
