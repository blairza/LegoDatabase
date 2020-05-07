import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class UserInterface {
	private LegoDatabase ld;
	private JFrame loginScreen;
	private JFrame mainScreen;
	private JFrame setScreen;
	private JFrame partScreen;
	private JFrame addPartScreen;
	private JFrame myPartScreen;
	private JFrame mySetScreen;
	private JFrame wishlistScreen;
	private String user;
	
	public UserInterface(LegoDatabase ld) {
		this.ld = ld;
		setUpLogin();
	}
	
	private void SetUpScreens() {
		setUpMainScreen();
		setUpSetScreen();
		setUpPartsScreen();
		setUpAddPartScreen("");
		setUpMyPartsScreen();
		setUpMySetsScreen();
		setUpWishListScreen();
		setScreen.setVisible(false);
		partScreen.setVisible(false);
		addPartScreen.setVisible(false);
		myPartScreen.setVisible(false);
		mySetScreen.setVisible(false);
		wishlistScreen.setVisible(false);
	}
	private void setUpMySetsScreen() {
		mySetScreen = new JFrame();
		mySetScreen.setTitle("My Sets");
		String[][] data = moveToArr(ld.getOwnedSets(user));
		String[] columns = {"Set Number","Set Name"};
		JTable mySets = new JTable(data,columns);
		JScrollPane scroll = new JScrollPane(mySets);
		JPanel top = new JPanel();
		top.add(new JLabel("My Sets"));
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		top.add(back);
		mySetScreen.add(top,BorderLayout.NORTH);
		mySetScreen.add(scroll);
		mySetScreen.pack();
		mySetScreen.setVisible(true);
	}

	private void setUpMyPartsScreen() {
		myPartScreen = new JFrame();
		myPartScreen.setTitle("My Parts");
		String[] columns = {"Part Number","Quantity","Part Name","Color"};
		String[][] data = moveToArr(ld.getOwnedParts(user));
		JTable myParts = new JTable(data,columns);
		JScrollPane scroll = new JScrollPane(myParts);
		JPanel top = new JPanel();
		top.add(new JLabel("My Parts"));
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		top.add(back);
		myPartScreen.add(top,BorderLayout.NORTH);
		myPartScreen.add(scroll);
		myPartScreen.pack();
		myPartScreen.setVisible(true);
	}

	private void setUpAddPartScreen(String partName) {
		addPartScreen = new JFrame();
		JPanel top = new JPanel();
		top.add(new JLabel("Add Part: "+partName));
		addPartScreen.add(top,BorderLayout.NORTH);
		JPanel addBoxes = new JPanel();
		JTextField color = new JTextField("Color");
		JTextField number = new JTextField("Quantity");
		addBoxes.add(new JLabel("Enter Values"));
		addBoxes.add(color);
		addBoxes.add(number);
		addPartScreen.add(addBoxes);
		JPanel bottom = new JPanel();
		JButton addPart = new JButton("Add to collection");
		bottom.add(addPart);
		addPart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ld.addPartToCollection(user, partName, color.getText(), number.getText())) {
					openErrorMessage("Part Added");
					addPartScreen.setVisible(false);
				} else {
					openErrorMessage("Unable to add part");
				}
			}
			
		});
		addPartScreen.add(bottom,BorderLayout.SOUTH);
		addPartScreen.pack();
		addPartScreen.setVisible(true);
	}

	private void setUpPartsScreen() {
		partScreen = new JFrame();
		String[] columns = {"Part Number","Part Name"};
		String[][] data = moveToArr(ld.getParts());
		JTable parts = new JTable(data,columns);
		JScrollPane scroll = new JScrollPane(parts);
		partScreen.add(scroll);
		JPanel top = new JPanel();
		top.add(new JLabel("Browse Parts"));
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		top.add(back);
		partScreen.add(top,BorderLayout.NORTH);
		JPanel bottom = new JPanel();
		JButton addPart = new JButton("Add to Collection");
		bottom.add(addPart);
		partScreen.add(bottom,BorderLayout.SOUTH);
		addPart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String partNum = (String) parts.getValueAt(parts.getSelectedRow(), 0);
				setUpAddPartScreen(partNum);
			}
			
		});
		partScreen.pack();
		partScreen.setVisible(true);
	}

	private void setUpSetScreen() {
		setScreen = new JFrame();
		setScreen.setTitle("Browse Sets");
		String[] columns = {"Set Number", "Set Name"};
		String[][] data = moveToArr(ld.getSets());
		JTable sets = new JTable(data,columns);
		JScrollPane scroll = new JScrollPane(sets);
		setScreen.add(scroll);
		JPanel top = new JPanel();
		top.add(new JLabel("Browse Sets"));
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		top.add(back);
		setScreen.add(top,BorderLayout.NORTH);
		JPanel bottom = new JPanel();
		JButton addToCollection = new JButton("Add to Collection");
		JButton addToWishList = new JButton("Add to Wishlist");
		bottom.add(addToCollection);
		bottom.add(addToWishList);
		setScreen.add(bottom,BorderLayout.SOUTH);
		addToCollection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(sets.getSelectedRow()==-1) {
					openErrorMessage("No Set Selected");
					return;
				}
				System.out.println(sets.getValueAt(sets.getSelectedRow(), 1));
				ld.addSetToCollection(user, (String)sets.getValueAt(sets.getSelectedRow(), 0));
			}
			
		});
		setScreen.pack();
		setScreen.setVisible(true);
	}
	
	private void setUpWishListScreen() {
		wishlistScreen = new JFrame();
		JPanel top = new JPanel();
		top.add(new JLabel("My Wishlist"));
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		top.add(back);
		wishlistScreen.add(top,BorderLayout.NORTH);
		JPanel mid = new JPanel();
		mid.add(new JLabel("Wishlist not Implemented"));
		wishlistScreen.add(mid);
		wishlistScreen.pack();
		wishlistScreen.setVisible(true);
	}

	private void setUpMainScreen() {
		mainScreen = new JFrame();
		JPanel main = new JPanel();
		JButton browseParts = new JButton("Browse Parts");
		JButton browseSets = new JButton("Browse Sets");
		JButton myParts = new JButton("My Parts");
		JButton mySets = new JButton("My Sets");
		JButton wishList = new JButton("My Wishlist");
		main.add(browseParts);
		main.add(browseSets);
		main.add(myParts);
		main.add(mySets);
		main.add(wishList);
		mainScreen.add(main,BorderLayout.CENTER);
		mainScreen.setTitle("LEGO Database");
		mainScreen.add(new JLabel("   "),BorderLayout.NORTH);
		mainScreen.pack();
		mainScreen.setSize(mainScreen.getWidth(),150);
		browseParts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpPartsScreen();
				mainScreen.setVisible(false);
			}
			
		});
		browseSets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpSetScreen();
				mainScreen.setVisible(false);
			}
			
		});
		myParts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpMyPartsScreen();
				mainScreen.setVisible(false);
			}
			
		});
		mySets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpMySetsScreen();
				mainScreen.setVisible(false);
			}
			
		});
		wishList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpWishListScreen();
				mainScreen.setVisible(false);
			}
			
		});
	}

	private void setUpLogin() {
		loginScreen = new JFrame();
		JPanel bottomButtons = new JPanel();
		JButton login = new JButton("Login");
		JButton register = new JButton("Register");
		bottomButtons.add(login);
		bottomButtons.add(register);
		loginScreen.add(bottomButtons,BorderLayout.SOUTH);
		JPanel textInputs = new JPanel();
		JTextField username = new JTextField("Username");
		JTextField password = new JTextField("Password");
		textInputs.add(username,BorderLayout.NORTH);
		textInputs.add(password,BorderLayout.SOUTH);
		loginScreen.add(textInputs,BorderLayout.CENTER);
		JPanel textTitles = new JPanel();
		textTitles.add(new JLabel("Username"),BorderLayout.WEST);
		textTitles.add(new JLabel("Password"),BorderLayout.EAST); 
		loginScreen.add(textTitles,BorderLayout.NORTH);
		loginScreen.pack();
		loginScreen.setSize(500,200);
		loginScreen.setTitle("Lego Database Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(ld.login(username.getText(), password.getText())) {
					System.out.println("Login success");
					user = username.getText();
					System.out.println(user);
					SetUpScreens();
					loginScreen.setVisible(false);
					mainScreen.setVisible(true);
					
				} else {
					openErrorMessage("Login Failed");
				}
			}
		});
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ld.register(username.getText(), password.getText())) {
					System.out.println("Register success");
					user = username.getText();
					SetUpScreens();
					loginScreen.setVisible(false);
					mainScreen.setVisible(true);
					
				} else {
					openErrorMessage("Register failed");
				}
			}
			
		});
	}
	
	public void RunApp() {
		loginScreen.setVisible(true);
	}
	
	private void openErrorMessage(String messageText) {
		JFrame error = new JFrame();
		error.add(new JLabel(messageText));
		error.setSize(100,100);
		error.setVisible(true);
	}
	
	private String[][] moveToArr(ArrayList<String[]> toChange) {
		if(toChange.size()==0) {
			return new String[0][0];
		}
		String[][] rtn = new String[toChange.size()][toChange.get(0).length];
		for(int i = 0; i<toChange.size();i++) {
			rtn[i] = toChange.get(i);
		}
		return rtn;
	}
	
	private class BackButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mainScreen.setVisible(true);
			setScreen.setVisible(false);
			partScreen.setVisible(false);
			addPartScreen.setVisible(false);
			myPartScreen.setVisible(false);
			mySetScreen.setVisible(false);
			wishlistScreen.setVisible(false);
		}
		
	}
}