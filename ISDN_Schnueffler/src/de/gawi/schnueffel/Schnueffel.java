package de.gawi.schnueffel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class Schnueffel extends JFrame 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3819884599311221086L;

	JLabel statusbar;
    
    JLabel l_serverAdress;
    JLabel l_source;
    JLabel l_destination;
    
    JTextField tf_srverAdress;
    JTextField tf_source;
    JTextField tf_destination;

    public Schnueffel() 
    {

        initUI();
    }

    public final void initUI() 
    {

        JPanel panel = new JPanel();
        statusbar = new JLabel(" ZetCode");

        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));

        panel.setLayout(null);
        
        l_serverAdress = new JLabel("Server Adresse");
        l_serverAdress.setBounds(40, 40, 100, 25);
        tf_srverAdress = new JTextField();
        tf_srverAdress.setBounds(160, 40, 150, 25);
        
        l_source = new JLabel("Quelle");
        l_source.setBounds(40, 80, 80, 25);
        tf_source = new JTextField();
        tf_source.setBounds(160, 80, 150, 25);
        
        l_destination = new JLabel("Ziel");
        l_destination.setBounds(40, 120, 80, 25);
        tf_destination = new JTextField();
        tf_destination.setBounds(160, 120, 150, 25);

        
        JButton save = new JButton("send");
        save.setBounds(40, 180, 80, 25);
        save.addActionListener(new ButtonSendListener());


        panel.add(save);

        panel.add(l_serverAdress);
        panel.add(tf_srverAdress);
        panel.add(l_source);
        panel.add(tf_source);
        panel.add(l_destination);
        panel.add(tf_destination);
        panel.add(statusbar, BorderLayout.SOUTH);

        add(panel);
        
        setTitle("ISDN_SChnueffler");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    class ButtonSendListener implements ActionListener 
    {

        public void actionPerformed(ActionEvent e) 
        {
            System.out.println("senden geklickt");
            
            String serverAdressString = tf_srverAdress.getText();
            String src = tf_source.getText();
            String dest = tf_destination.getText();
            
            String charset = "UTF-8";            

            try {
            	String query = String.format("src=%s&dest=%s", 
                URLEncoder.encode(src, charset), 
                URLEncoder.encode(dest, charset));
            	String end = serverAdressString + "/ISDN_Vaadin_Server/InjectServlet" + "?" + query;
            	System.out.println("end:  " +end);
//				URL url = new URL(end);
//				URLConnection uc = url.openConnection();
//				uc.setRequestProperty("Accept-Charset", charset);
//				uc.connect();
//				
//				InputStream is = uc.getInputStream();
//				is.read();
//				is.close();
            	
				URL url = new URL(end);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.out.println(conn.getResponseMessage());
				} else {
					// Verarbeitung des Ergebnisses
				}
            	
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }

    public static void main(String[] args) 
    {

        SwingUtilities.invokeLater(new Runnable() 
        {

            public void run() {
            	Schnueffel ms = new Schnueffel();
                ms.setVisible(true);
            }
        });
    }
}
