package sb;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class FlightBooker extends JFrame {

	JFrame window;
	static final long serialVersionUID=0;
	JTextField passengers = new JTextField();
	JCheckBox comfort = new JCheckBox();
	JTextField result = new JTextField("xxxxxxxxxxxxxxxx");
	JLabel config = new JLabel("<html><body>Seats: 120<br>Seats (with extra comfort): 80</body></html>"), passengerP, comfortP;
	JButton checkButton, continueButton, infoButton, exitButton;
	
	FlightBooker() {
		window = this;
		setTitle("Flight Booker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		passengerP = new JLabel("Passengers");
		getContentPane().add( passengerP );
		passengerP.setBounds( 25, 25, 100, passengerP.getPreferredSize().height );
		passengers.setName("passengers");
		getContentPane().add( passengers );
		passengers.setBounds( 150, 25, 100, passengers.getPreferredSize().height );
		comfortP = new JLabel("Extra Comfort");
		getContentPane().add( comfortP );
		comfortP.setBounds( 25, 55, 100, comfortP.getPreferredSize().height );
		comfort.setName("comfort");
		comfort.setBackground(Color.LIGHT_GRAY);
		getContentPane().add( comfort );
		comfort.setBounds( 147, 52, comfort.getPreferredSize().width, comfort.getPreferredSize().height );
		checkButton = new JButton("Check");
		checkButton.setName("checkbutton");
		checkButton.setFocusPainted(false);
		getContentPane().add( checkButton );
		
		CharterFlight c3 = new CharterFlight();//--
		
		
		checkButton.setBounds( 60, 90, checkButton.getPreferredSize().width, checkButton.getPreferredSize().height);
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int p=-1;
            	try { p = Integer.parseInt(passengers.getText().trim()); }
            	catch (Exception ex) {}
            	boolean cf=comfort.isSelected();
				c3.booking(cf);//--
            	result.setText(CharterFlight.fits(p,cf).name());
                continueButton.setVisible(true);
                result.setVisible(true);
                checkButton.setVisible(false);
                exitButton.setVisible(false);
        		window.setTitle("Flight Check Results");
            }
        }); 
		infoButton = new JButton("Info");
		infoButton.setName("infobutton");
		infoButton.setFocusPainted(false);
		getContentPane().add( infoButton );
		infoButton.setBounds( 175, 90, infoButton.getPreferredSize().width, infoButton.getPreferredSize().height);
		infoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                config.setVisible(true);
                continueButton.setVisible(true);
                checkButton.setVisible(false);
                comfort.setVisible(false);
                exitButton.setVisible(false);
                infoButton.setVisible(false);
                comfortP.setVisible(false);
                passengerP.setVisible(false);
                passengers.setVisible(false);
                result.setVisible(false);
        		window.setTitle("Flight Information");
            }
        }); 
		result.setName("result");		
		getContentPane().add( result );
		result.setBounds( 25, 130, result.getPreferredSize().width, result.getPreferredSize().height );
        result.setVisible(false);
        getContentPane().add( config );
        config.setName("configInfo");
        config.setBounds( 25, 25, config.getPreferredSize().width, config.getPreferredSize().height );
        config.setVisible(false);
		continueButton = new JButton("Continue");
		continueButton.setName("continuebutton");
		continueButton.setFocusPainted(false);
		getContentPane().add( continueButton );
		continueButton.setBounds( 60, 90, continueButton.getPreferredSize().width, continueButton.getPreferredSize().height);
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continueButton.setVisible(false);
                config.setVisible(false);
                exitButton.setVisible(true);
                result.setVisible(false);  
                checkButton.setVisible(true);
                comfort.setVisible(true);
                comfortP.setVisible(true);
                infoButton.setVisible(true);
                passengerP.setVisible(true);
                passengers.setVisible(true);
        		window.setTitle("Flight Booker");
            }
        }); 
        continueButton.setVisible(false);
		exitButton = new JButton("Exit");
		exitButton.setName("exitbutton");
		exitButton.setFocusPainted(false);
		getContentPane().add( exitButton );
		exitButton.setBounds( 65, 160, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
		exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                	System.exit(0);
            }
        }); 
		setSize(350,230);
		setVisible(true);
	}
	
	public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FlightBooker().setVisible(true);
            }
        });		
	}
}
