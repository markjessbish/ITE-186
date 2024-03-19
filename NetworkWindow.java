

package ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NetworkWindow extends JFrame {

	private static final long serialVersionUID = -3680869784531557351L;
	
	
	public static final int DEFAULT_WIDTH = 400;
	
	
	public static final int DEFAULT_HEIGHT = 140;
	
	
	public static final String DEFAULT_TITLE = "Configure Network";

	
	public static final int CONNECT_BUTTON = 0;
	

	public static final int LISTEN_BUTTON = 1;
	

	private JTextField srcPort;
	
	
	private JTextField destHost;
	

	private JTextField destPort;
	

	private JButton listen;
	
	
	private JButton connect;
	
	
	private JPanel src;
	
	
	private JPanel dest;
	
	
	private JLabel msg;
	
	
	private ActionListener actionListener;

	
	public NetworkWindow() {
		super(DEFAULT_TITLE);
		super.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		super.setLocationByPlatform(true);
		init();
	}
	
	
	public NetworkWindow(ActionListener actionListener) {
		this();
		this.actionListener = actionListener;
	}
	
	
	public NetworkWindow(ActionListener actionListener, int srcPort,
			String destHost, int destPort) {
		this();
		this.actionListener = actionListener;
		setSourcePort(srcPort);
		setDestinationHost(destHost);
		setDestinationPort(destPort);
	}
	
	
	private void init() {
		
		
		this.getContentPane().setLayout(new GridLayout(3, 1));
		this.srcPort = new JTextField(4);
		this.destHost = new JTextField(11);
		this.destHost.setText("127.0.0.1");
		this.destPort = new JTextField(4);
		this.listen = new JButton("Listen");
		this.listen.addActionListener(new ButtonListener());
		this.connect = new JButton("Connect");
		this.connect.addActionListener(new ButtonListener());
		this.src = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.dest = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.msg = new JLabel();
		this.src.add(new JLabel("Source port:"));
		this.src.add(srcPort);
		this.src.add(listen);
		this.dest.add(new JLabel("Destination host/port:"));
		this.dest.add(destHost);
		this.dest.add(destPort);
		this.dest.add(connect);
		setCanUpdateConnect(false);
		
		
		this.srcPort.setToolTipText("Source port to listen for "
				+ "updates (1025 - 65535)");
		this.destPort.setToolTipText("Destination port to listen for "
				+ "updates (1025 - 65535)");
		this.destHost.setToolTipText("The destination host to send "
				+ "updates to (e.g. localhost)");

		createLayout(null);
	}
	
	
	private void createLayout(String msg) {
		
		this.getContentPane().removeAll();
		
		
		this.getContentPane().add(src);
		this.getContentPane().add(dest);
		this.msg.setText(msg);
		this.getContentPane().add(this.msg);
		this.msg.setVisible(false);
		this.msg.setVisible(true);
	}
	
	
	public void setCanUpdateListen(boolean canUpdate) {
		this.srcPort.setEnabled(canUpdate);
		this.listen.setEnabled(canUpdate);
	}
	
	
	public void setCanUpdateConnect(boolean canUpdate) {
		this.destHost.setEnabled(canUpdate);
		this.destPort.setEnabled(canUpdate);
		this.connect.setEnabled(canUpdate);
	}
	
	
	public ActionListener getActionListener() {
		return actionListener;
	}

	
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	
	public int getSourcePort() {
		return parseField(srcPort);
	}
	
	
	public void setSourcePort(int port) {
		this.srcPort.setText("" + port);
	}
	
	
	public String getDestinationHost() {
		return destHost.getText();
	}
	
	
	public void setDestinationHost(String host) {
		this.destHost.setText(host);
	}
	
	
	public int getDestinationPort() {
		return parseField(destPort);
	}
	
	
	public void setDestinationPort(int port) {
		this.destPort.setText("" + port);
	}
	
	/**
	 * Gets the message text being displayed on the window.
	 * 
	 * @return the message being displayed.
	 * @see {@link #setMessage(String)}
	 */
	public String getMessage() {
		return msg.getText();
	}
	
	/**
	 * Sets the message to display on the window and updates the user
	 * interface.
	 * 
	 * @param message	the message to display.
	 * @see {@link #getMessage()}
	 */
	public void setMessage(String message) {
		createLayout(message);
	}
	
	/**
	 * Attempts to parse the specified text field value to an integer.
	 * 
	 * @param tf	the text field to parse.
	 * @return the integer value parsed from the text field or 0 if an error
	 * occurred.
	 */
	private static int parseField(JTextField tf) {
		
		if (tf == null) {
			return 0;
		}
		
		// Try to parse the text input
		int val = 0;
		try {
			val = Integer.parseInt(tf.getText());
		} catch (NumberFormatException e) {}
		
		return val;
	}
	
	/**
	 * The {@code ButtonListener} class listens for button click events from
	 * any button in the window.
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (actionListener != null) {
				JButton src = (JButton) e.getSource();
				ActionEvent event = null;
				if (src == listen) {
					event = new ActionEvent(NetworkWindow.this,
							LISTEN_BUTTON, null);
				} else {
					event = new ActionEvent(NetworkWindow.this,
							CONNECT_BUTTON, null);
				}
				actionListener.actionPerformed(event);
			}
		}
	}
}
