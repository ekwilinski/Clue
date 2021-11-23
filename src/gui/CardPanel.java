package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Map.Entry;
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
import clueGame.Player;

@SuppressWarnings("serial")
public class CardPanel extends JPanel{
	private JPanel peoplePanel, roomPanel, weaponPanel, peopleCardPanel, weaponCardPanel, roomCardPanel, seenPeopleCardPanel, seenWeaponCardPanel, seenRoomCardPanel;
	private JPanel handLabel, seenLabel;
	private static HumanPlayer jim = new HumanPlayer("Jim Halpert", "blue", 9, 6, 5);
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public CardPanel()  {
		createBlankPanel();
	}
	
	public void refresh() {
		removeAll();
		setLayout(new GridLayout(1,3));
	}
	
	public void createBlankPanel() {
		HumanPlayer blank = new HumanPlayer("steve", "blank", 1, 1, 2);
		createCardPanel(blank);
	}

	public void createCardPanel(Player humanPlayer) {
		refresh();
		
		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards", TitledBorder.CENTER, TitledBorder.TOP));

		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0, 1));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));

		handLabel = new JPanel();
		handLabel.add(new JLabel("In Hand:"));
		peoplePanel.add(handLabel, BorderLayout.NORTH);

		add(peoplePanel);

		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0, 1));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));

		handLabel = new JPanel();
		handLabel.add(new JLabel("In Hand:"));
		roomPanel.add(handLabel, BorderLayout.NORTH);

		add(roomPanel);

		weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(0, 1));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));

		handLabel = new JPanel();
		handLabel.add(new JLabel("In Hand:"));
		weaponPanel.add(handLabel, BorderLayout.NORTH);
		
		add(weaponPanel);
		
		setInHand(humanPlayer);
		
		seenLabel = new JPanel();
		seenLabel.add(new JLabel("Seen:"));
		peoplePanel.add(seenLabel, BorderLayout.SOUTH);
		
		seenLabel = new JPanel();
		seenLabel.add(new JLabel("Seen:"));
		roomPanel.add(seenLabel, BorderLayout.SOUTH);
		
		seenLabel = new JPanel();
		seenLabel.add(new JLabel("Seen:"));
		weaponPanel.add(seenLabel, BorderLayout.SOUTH);
		
		setSeen(humanPlayer);
		
	}

	private void setInHand(Player humanPlayer) {
		Set<Card> humanHand = humanPlayer.getHand();

		Color humanColor = convertColor(humanPlayer.getColor());
		for(Card card : humanHand) {
			if(card.getType().equals(CardType.PERSON))
			{
				JTextField human = new JTextField(13);
				human.setForeground(Color.GRAY);
				human.setText(card.getName());
				human.setBackground(humanColor);
				peoplePanel.add(human, BorderLayout.NORTH);
			}
			else if(card.getType().equals(CardType.ROOM)) {
				JTextField room = new JTextField(13);
				room.setForeground(Color.GRAY);
				room.setText(card.getName());
				room.setBackground(humanColor);
				roomPanel.add(room, BorderLayout.NORTH);
			}
			else {
				JTextField weapon = new JTextField(13);
				weapon.setForeground(Color.GRAY);
				weapon.setText(card.getName());
				weapon.setBackground(humanColor);
				weaponPanel.add(weapon, BorderLayout.NORTH);
			}
		}
	}

	private void setSeen(Player humanPlayer) {
		Map<Card, String> humanSeenAndColors = humanPlayer.getSeenAndColorCards();

		for(Entry<Card, String> card : humanSeenAndColors.entrySet()) {
			if(card.getKey().getType().equals(CardType.PERSON))
			{
				JTextField human = new JTextField(13);
				human.setForeground(Color.GRAY);
				human.setText(card.getKey().getName());
				human.setBackground(convertColor(card.getValue()));
				peoplePanel.add(human, BorderLayout.SOUTH);
			}
			else if(card.getKey().getType().equals(CardType.ROOM)) {
				JTextField room = new JTextField(13);
				room.setForeground(Color.GRAY);
				room.setText(card.getKey().getName());
				room.setBackground(convertColor(card.getValue()));
				roomPanel.add(room, BorderLayout.SOUTH);
			}
			else {
				JTextField weapon = new JTextField(13);
				weapon.setForeground(Color.GRAY);
				weapon.setText(card.getKey().getName());
				weapon.setBackground(convertColor(card.getValue()));
				weaponPanel.add(weapon, BorderLayout.SOUTH);
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

		// test filling in the data
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
		Card sword = new Card("Sword", CardType.WEAPON);
		Card ak47 = new Card("ak47", CardType.WEAPON);
		Card hq = new Card("Headquarters", CardType.ROOM);

		jim.updateSeen(steve, "black");
		jim.updateSeen(tony, "red");
		jim.updateSeen(shield, "orange");
		jim.updateSeen(sword, "orange");
		jim.updateSeen(ak47, "tan");
		jim.updateSeen(hq, "yellow");

		panel.createCardPanel(jim);
		frame.setVisible(true); // make it visible
	}
}
