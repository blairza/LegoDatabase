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
import javax.swing.JOptionPane;
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
	private JFrame importScreen;
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
		setUpImportScreen();
		setScreen.setVisible(false);
		partScreen.setVisible(false);
		addPartScreen.setVisible(false);
		myPartScreen.setVisible(false);
		mySetScreen.setVisible(false);
		wishlistScreen.setVisible(false);
		importScreen.setVisible(false);
	}
	private void setUpMySetsScreen() {
		mySetScreen = new JFrame();
		mySetScreen.setTitle("My Sets");
		String[][] data = moveToArr(ld.getOwnedSets(user));
		String[] columns = {"Set Number","Set Name","Quantity"};
		JTable mySets = new JTable(data,columns);
		JScrollPane scroll = new JScrollPane(mySets);
		JPanel top = new JPanel();
		top.add(new JLabel("My Sets"));
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		top.add(back);
		mySetScreen.add(top,BorderLayout.NORTH);
		mySetScreen.add(scroll);
		JButton removeSetFromCollection = new JButton("Remove 1");
		JPanel bottom = new JPanel();
		bottom.add(removeSetFromCollection);
		removeSetFromCollection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ld.removeSetFromCollection(user,(String)mySets.getValueAt(mySets.getSelectedRow(), 0));
				mySetScreen.setVisible(false);
				setUpMySetsScreen();
				openMessage("1 of Set "+(String)mySets.getValueAt(mySets.getSelectedRow(),0)+" Removed From collection");
			}
			
		});
		bottom.add(removeSetFromCollection);
		mySetScreen.add(bottom,BorderLayout.SOUTH);
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
				boolean isValidNum = true;
				try {
					Integer.parseInt(number.getText());
				} catch(Exception exception) {
					openMessage("Invalid Number selection");
					isValidNum=false;
				}
				if(isValidNum) {
					int errorCode = ld.addPartToCollection(user, partName, color.getText(), number.getText());
					if(errorCode==0) {
						openMessage("Part Added");
						addPartScreen.setVisible(false);
					} else if(errorCode==3) {
						openMessage("Quantity cannot be negative");
					}else if(errorCode==4) {
						openMessage("Color does not exist");
					} else {
						openMessage("Unable to add part");
					}
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
					openMessage("No Set Selected");
					return;
				}
				ld.addSetToCollection(user, (String)sets.getValueAt(sets.getSelectedRow(), 0));
				openMessage("Set Added To Collection");
			}
			
		});
		addToWishList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sets.getSelectedRow()==-1) {
					openMessage("No Set Selected");
					return;
				}
				int errCode = ld.addToWishList(user, (String)sets.getValueAt(sets.getSelectedRow(), 0));
				if(errCode==0) {
					openMessage("Set Added to Wishlist");
				} else if(errCode==3) {
					openMessage("Set already in wishlist");
				}
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
		String[] columns = {"Set Number","Set Name"};
		String[][] data = moveToArr(ld.getWishlistedSets(user));
		JTable sets = new JTable(data,columns);
		JScrollPane scroll = new JScrollPane(sets);
		mid.add(scroll);
		wishlistScreen.add(mid);
		JButton removeFromWishlist = new JButton("Remove");
		JPanel bottom = new JPanel();
		bottom.add(removeFromWishlist);
		removeFromWishlist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sets.getSelectedRow()==-1) {
					openMessage("No Set Selected");
				} else {
					ld.removeFromWishlist(user,(String)sets.getValueAt(sets.getSelectedRow(),0));
					wishlistScreen.setVisible(false);
					setUpWishListScreen();
					openMessage("Set Removed From Wishlist");
				}
			}
			
		});
		JButton addSetFromWishList = new JButton("Add to Collection");
		bottom.add(addSetFromWishList);
		addSetFromWishList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sets.getSelectedRow()==-1) {
					openMessage("No Set Selected");
				} else {
					String setNum = (String)sets.getValueAt(sets.getSelectedRow(),0);
					ld.removeFromWishlist(user, setNum);
					ld.addSetToCollection(user, setNum);
					wishlistScreen.setVisible(false);
					setUpWishListScreen();
					openMessage("Set Added to Collection");
				}
			}
		});
		wishlistScreen.add(bottom,BorderLayout.SOUTH);
		wishlistScreen.pack();
		wishlistScreen.setVisible(true);
	}
    
	private void setUpImportScreen() {
		importScreen = new JFrame();
		JPanel filebar = new JPanel();
		JTextField fileName = new JTextField("Insert File Name");
		JPanel buttons = new JPanel();
		JButton importFile = new JButton("Import File");
		JButton back = new JButton("Back");
		back.addActionListener(new BackButton());
		buttons.add(back);
		filebar.add(fileName);
		buttons.add(importFile);
		
		importScreen.add(filebar,BorderLayout.NORTH);
		importScreen.add(buttons,BorderLayout.SOUTH);
		importScreen.setTitle("Import Sets");
		importScreen.setSize(300, 150);
		importScreen.setVisible(true);
		
	}
	
	private void setUpMainScreen() {
		mainScreen = new JFrame();
		JPanel main = new JPanel();
		JButton browseParts = new JButton("Browse Parts");
		JButton browseSets = new JButton("Browse Sets");
		JButton importData = new JButton("Import Data");
		JButton myParts = new JButton("My Parts");
		JButton mySets = new JButton("My Sets");
		JButton wishList = new JButton("My Wishlist");
		main.add(browseParts);
		main.add(browseSets);
		main.add(importData);
		main.add(myParts);
		main.add(mySets);
		main.add(wishList);
		mainScreen.add(main,BorderLayout.CENTER);
		mainScreen.setTitle("LEGO Database");
		JPanel top = new JPanel();
		top.add(new JLabel("Welcome to LEGO Database"),BorderLayout.NORTH);
		mainScreen.add(top,BorderLayout.NORTH);
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
		importData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent argo0) {
				setUpImportScreen();
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
					user = username.getText();
					SetUpScreens();
					loginScreen.setVisible(false);
					mainScreen.setVisible(true);
					
				} else {
					openMessage("Login Failed");
				}
			}
		});
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ld.register(username.getText(), password.getText())) {
					user = username.getText();
					SetUpScreens();
					loginScreen.setVisible(false);
					mainScreen.setVisible(true);
					
				} else {
					openMessage("Register failed");
				}
			}
			
		});
	}
	
	public void RunApp() {
		loginScreen.setVisible(true);
	}
	
	private void openMessage(String messageText) {
		JOptionPane.showMessageDialog(null, messageText);
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
			importScreen.setVisible(false);
		}
		
	}
}