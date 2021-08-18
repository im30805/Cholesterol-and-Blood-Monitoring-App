package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.hl7.fhir.r4.model.Practitioner;

import Controller.AppController;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import task2.application.ServerBase;


/**
 * The AppLogin class is the class where the application will be run
 * Here the login screen is first initialized
 */

public class AppLogin {

	private JFrame frame;
	private JTextField idInput;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppLogin window = new AppLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

   /**
    * Constructor
    * Used to initialize the application the moment it is instantiated
    */
	public AppLogin() {
		initialize();
	}

   /**
    * Initializes the contents of the frame used in the application window
    *
    */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your Practitioner ID:");
		lblPleaseEnterYour.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPleaseEnterYour.setBounds(25, 91, 212, 44);
		frame.getContentPane().add(lblPleaseEnterYour);
		
		idInput = new JTextField();
		idInput.setBounds(250, 105, 144, 19);
		frame.getContentPane().add(idInput);
		idInput.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		AppController appController = new AppController(idInput,frame);
		btnOk.addActionListener(appController);

		btnOk.setBounds(155, 155, 104, 34);
		
		frame.getContentPane().add(btnOk);
		
	}
	
}
