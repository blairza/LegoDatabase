import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.JFrame;

public class Main {
	private static JFrame preLogin;
	private static JFrame newDBFrame;

	public static void main(String[] args) {
		preLogin = new JFrame();
		JPanel buttons = new JPanel();
		JButton newDB = new JButton("New Database");
		JButton oldDB = new JButton("Existing Database");
		buttons.add(newDB);
		buttons.add(oldDB);
		preLogin.add(buttons, BorderLayout.CENTER);
		preLogin.pack();
		preLogin.setSize(400, 100);
		preLogin.setTitle("Initialization Phase");
		preLogin.setVisible(true);
		newDB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createNewDatabase();
			}

		});

		oldDB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				preLogin.setVisible(false);
				oldConnect();
			}

		});

	}

	private static void createNewDatabase() {
		preLogin.setVisible(false);
		newDBFrame = new JFrame();
		JPanel buttons = new JPanel();
		JButton confirm = new JButton("Confirm");
		JButton back = new JButton("Back");
		buttons.add(confirm);
		buttons.add(back);
		JTextField database_name = new JTextField("Enter Database Name");
		newDBFrame.add(buttons, BorderLayout.SOUTH);
		newDBFrame.add(database_name, BorderLayout.NORTH);
		newDBFrame.pack();
		newDBFrame.setSize(400, 100);
		newDBFrame.setTitle("Create New Database");
		newDBFrame.setVisible(true);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				preLogin.setVisible(true);
				newDBFrame.setVisible(false);
			}

		});
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String db_name = database_name.getText();
				newConnect(db_name);
			}

		});

	}

	private static void oldConnect() {
		LegoDatabase ld = new LegoDatabase("golem.csse.rose-hulman.edu", "Lego_Database_Demo");
		String encodedPass = "cmtRTGpRMQ==";
		byte[] decodedBytes = Base64.getDecoder().decode(encodedPass);
		String pass = new String(decodedBytes);
		boolean connected = ld.connect("appUserLego", pass);
		if (!connected)
			return;
		Connection dbConnection = ld.getConnection();
		UserInterface UI = new UserInterface(ld);
		UI.RunApp();
	}

	private static void newConnect(String newName) {
		LegoDatabase ld = new LegoDatabase("golem.csse.rose-hulman.edu", newName);
		String encodedPass = "cmtRTGpRMQ==";
		byte[] decodedBytes = Base64.getDecoder().decode(encodedPass);
		String pass = new String(decodedBytes);
		ld.createNew("appUserLego", pass);
		boolean connected = ld.connect("appUserLego", pass);
		if (!connected)
			return;
		Connection dbConnection = ld.getConnection();
		UserInterface UI = new UserInterface(ld);
		UI.RunApp();
	}

}
