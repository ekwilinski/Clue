package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

@SuppressWarnings("serial")
public class CardPanel extends JPanel{
	private JPanel peoplePanel, roomPanel, weaponPanel, peopleCardPanel, weaponCardPanel, roomCardPanel, seenPeopleCardPanel, seenWeaponCardPanel, seenRoomCardPanel;
	private JLabel handLabel, roomLabel, weaponLabel, seenLabel, seenRLabel, seenWLabel;
	private JTextField peopleField, seenPField, roomField, seenRField, weaponField, seenWField;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public CardPanel()  {
		setLayout(new GridLayout(1,3));
		createCardPanel();
	}

	public void createCardPanel() {
		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards", TitledBorder.CENTER, TitledBorder.TOP));

		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(4, 1));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		handLabel = new JLabel("In Hand:");
		peoplePanel.add(handLabel);
	
		peopleCardPanel = new JPanel();
		peopleCardPanel.setLayout(new GridLayout(0, 1));
		peoplePanel.add(peopleCardPanel, BorderLayout.CENTER);
		
		seenLabel = new JLabel("Seen:");
		peoplePanel.add(seenLabel);

		seenPeopleCardPanel = new JPanel();
		seenPeopleCardPanel.setLayout(new GridLayout(0, 1));
		peoplePanel.add(seenPeopleCardPanel, BorderLayout.CENTER);
		
		add(peoplePanel);
		
		
		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(4, 1));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		handLabel = new JLabel("In Hand:");
		roomPanel.add(handLabel);
	
		roomCardPanel = new JPanel();
		roomCardPanel.setLayout(new GridLayout(0, 1));
		roomPanel.add(roomCardPanel);
		
		seenLabel = new JLabel("Seen:");
		roomPanel.add(seenLabel);

		seenRoomCardPanel = new JPanel();
		seenRoomCardPanel.setLayout(new GridLayout(0, 1));
		roomPanel.add(seenRoomCardPanel);
		
		add(roomPanel);
		
		
		weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(4, 1));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		handLabel = new JLabel("In Hand:");
		weaponPanel.add(handLabel);
	
		weaponCardPanel = new JPanel();
		weaponCardPanel.setLayout(new GridLayout(0, 1));
		weaponPanel.add(weaponCardPanel);
		
		seenLabel = new JLabel("Seen:");
		weaponPanel.add(seenLabel);

		seenWeaponCardPanel = new JPanel();
		seenWeaponCardPanel.setLayout(new GridLayout(0, 1));
		weaponPanel.add(seenWeaponCardPanel);
		
		add(weaponPanel);

	}

	private void setInHand(HumanPlayer humanPlayer) {
		Set<Card> humanHand = humanPlayer.getHand();
		
		Color humanColor = convertColor(humanPlayer.getColor());
		for(Card card : humanHand) {
			if(card.getType().equals(CardType.PERSON))
			{
				JTextField human = new JTextField(13);
				human.setText(card.getName());
				human.setBackground(humanColor);
				peopleCardPanel.add(human);
			}
			else if(card.getType().equals(CardType.ROOM)) {
				JTextField room = new JTextField(13);
				room.setText(card.getName());
				room.setBackground(humanColor);
				roomCardPanel.add(room);
			}
			else {
				JTextField weapon = new JTextField(13);
				weapon.setText(card.getName());
				weapon.setBackground(humanColor);
				weaponCardPanel.add(weapon);
			}
		}
	}
	
	private void setSeen(HumanPlayer humanPlayer) {
		//ComputerPlayer cardHolder = new ComputerPlayer();
		Set<Card> humanSeen = humanPlayer.getSeenCards();
		
		for(Card card : humanSeen) {
			if(card.getType().equals(CardType.PERSON))
			{
				JTextField human = new JTextField(13);
				human.setText(card.getName());
				seenPeopleCardPanel.add(human);
			}
			else if(card.getType().equals(CardType.ROOM)) {
				JTextField room = new JTextField(13);
				room.setText(card.getName());
				seenRoomCardPanel.add(room);
			}
			else {
				JTextField weapon = new JTextField(13);
				weapon.setText(card.getName());
				seenWeaponCardPanel.add(weapon);
			}
		}
	}
	
	private Color convertColor(String color) {
		Color convertedColor = new Color(0,0,0);
		switch(color.toLowerCase()) {
		case "blue" :
			convertedColor = Color.BLUE;
			break;
		case "tan" :
			convertedColor = new Color(210, 180, 140);
			break;
		case "white":
			convertedColor = Color.WHITE;
			break;
		case "yellow" :
			convertedColor = Color.YELLOW;
			break;
		case "gray" :
			convertedColor = Color.GRAY;
			break;
		case "purple" :
			convertedColor = new Color(148, 0, 211);
		}

		return convertedColor;
	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 600);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(false); // make it invisible

		// test filling in the data
		HumanPlayer jim = new HumanPlayer( "Jim Halpert", "blue", 9, 6, 5);
		
		Card bob = new Card("Bob Smalls", CardType.PERSON);
		Card knife = new Card("Knife", CardType.WEAPON);
		Card gun = new Card("Gun", CardType.WEAPON);
		Card bathroom = new Card("Bathroom", CardType.ROOM);
		
		jim.updateHand(bob);
		jim.updateHand(knife);
		jim.updateHand(gun);
		jim.updateHand(bathroom);
		
		Card steve = new Card("Steve Rogers", CardType.PERSON);
		Card tony = new Card("Tony Stark", CardType.PERSON);
		Card shield = new Card("Shield", CardType.WEAPON);
		Card hq = new Card("Headquarters", CardType.ROOM);
		
		jim.updateSeen(steve);
		jim.updateSeen(tony);
		jim.updateSeen(shield);
		jim.updateSeen(hq);
		
		panel.setInHand(jim);
		panel.setSeen(jim);
		frame.setVisible(true); // make it visible
	}
}
