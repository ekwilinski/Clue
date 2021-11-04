package clueGame;

public class Solution {

	private Card room;
	private Card person;
	private Card weapon;
	public void addRoom(Card card) {
		room = card;
	}
	public void addWeapon(Card card) {
		weapon = card;
	}
	public void addPlayer(Card card) {
		person = card;
	}
	
	public Card getRoom() {
		return room;
	}
	public Card getWeapon() {
		return weapon;
	}
	public Card getPlayer() {
		return person;
	}
	
}
