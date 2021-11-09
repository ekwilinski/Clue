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
	private JPanel cardPanel,peoplePanel, roomPanel, weaponPanel;
	private JLabel peopleLabel, roomLabel, weaponLabel, seenPLabel, seenRLabel, seenWLabel;
	private JTextField peopleField, seenPField, roomField, seenRField, weaponField, seenWField;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public CardPanel()  {
		setLayout(new GridLayout(1,3));
		createCardPanel();
	}

	public void createCardPanel() {
		cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(3, 1));
		cardPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards", TitledBorder.CENTER, TitledBorder.TOP));

		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(4, 1));
		peopleLabel = new JLabel("In Hand:");
		seenPLabel = new JLabel("Seen:");
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		peopleField = new JTextField(13);
		seenPField = new JTextField(13);
		peoplePanel.add(peopleLabel, BorderLayout.NORTH);
		peoplePanel.add(peopleField, BorderLayout.NORTH);
		peoplePanel.add(seenPLabel, BorderLayout.SOUTH);
		peoplePanel.add(seenPField, BorderLayout.SOUTH);
		cardPanel.add(peoplePanel, BorderLayout.NORTH);

		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(4, 1));
		roomLabel = new JLabel("In Hand:");
		seenRLabel = new JLabel("Seen:");
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		roomField = new JTextField(13);
		seenRField = new JTextField(13);
		roomPanel.add(roomLabel, BorderLayout.NORTH);
		roomPanel.add(roomField, BorderLayout.NORTH);
		roomPanel.add(seenRLabel, BorderLayout.SOUTH);
		roomPanel.add(seenRField, BorderLayout.SOUTH);
		cardPanel.add(roomPanel, BorderLayout.CENTER);

		weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(4, 1));
		weaponLabel = new JLabel("In Hand:");
		seenWLabel = new JLabel("Seen:");
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weaponField = new JTextField(13);
		seenWField = new JTextField(13);
		weaponPanel.add(weaponLabel, BorderLayout.NORTH);
		weaponPanel.add(weaponField, BorderLayout.NORTH);
		weaponPanel.add(seenWLabel, BorderLayout.SOUTH);
		weaponPanel.add(seenWField, BorderLayout.SOUTH);
		cardPanel.add(weaponPanel, BorderLayout.SOUTH);

		add(cardPanel);
	}

	private void setInHand(HumanPlayer humanPlayer) {
		Set<Card> humanHand = humanPlayer.getHand();
		String cards = "";
		
		for(Card card : humanHand) {
			if(card.getType().equals(CardType.PERSON))
			{
				cards+= card;
			}
			
			else if(card.getType().equals(CardType.ROOM)) {
				roomField.setText(String.valueOf(card));
				roomPanel.add(roomField, BorderLayout.NORTH);
				cardPanel.add(roomPanel);
			}
			else {
				weaponField.setText(String.valueOf(card));
				weaponPanel.add(weaponField, BorderLayout.NORTH);
				cardPanel.add(weaponPanel);
			}
			
			peopleField.setText(cards);
			peoplePanel.add(peopleField, BorderLayout.NORTH);
			cardPanel.add(peoplePanel);
		}
		add(cardPanel);
	}
	private void setSeen(HumanPlayer humanPlayer) {
		//ComputerPlayer cardHolder = new ComputerPlayer();
		for(Card card : humanPlayer.getSeenCards())
		{
			if(card.getType().equals(CardType.PERSON))
			{
				seenPField.setText(String.valueOf(card));
				//seenPField.setBackground(convertColor(cardHolder.getColor()));
				peoplePanel.add(seenPField, BorderLayout.SOUTH);
				cardPanel.add(peoplePanel);
			}
			else if(card.getType().equals(CardType.ROOM)) {
				seenRField.setText(String.valueOf(card));
				//seenRField.setBackground(convertColor(cardHolder.getColor()));
				roomPanel.add(seenRField, BorderLayout.SOUTH);
				cardPanel.add(roomPanel);
			}
			else {
				seenWField.setText(String.valueOf(card));
				//seenWField.setBackground(convertColor(cardHolder.getColor()));
				weaponPanel.add(seenWField, BorderLayout.SOUTH);
				cardPanel.add(weaponPanel);
			}
		}
		add(cardPanel);
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
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(false); // make it visible

		// test filling in the data
		HumanPlayer jim = new HumanPlayer( "Jim Halpert", "blue", 9, 6, 5);
		panel.setInHand(jim);
		panel.setSeen(jim);
		frame.setVisible(true); // make it visible
	}
}
