package tests;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import facepamphlet.FacePamphletDatabase;
import facepamphlet.FacePamphletProfile;

/**
 * This just ensures that we can set birthdays and get them.
 */
public class Birthday {
	public static void main(String[] args) {
		Random r = new Random();
		Calendar c = Calendar.getInstance();		
		
		FacePamphletDatabase people = new FacePamphletDatabase();
		
		FacePamphletProfile me = new FacePamphletProfile("Me");

		c.set(Calendar.DAY_OF_YEAR, r.nextInt(365));
		me.setBirthday(c.getTime());
		
		people.addProfile(me);
		
		for(int i = 0; i < 10; i++) {
			FacePamphletProfile friend = new FacePamphletProfile("friend"+i);
			
			c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_YEAR, r.nextInt(365));
			
			friend.setBirthday(c.getTime());
			
			me.addFriend(friend.getName());
			
			people.addProfile(friend);
		}
		
		System.out.println("My birthday: "+me.getBirthday());
		
		System.out.println();
		
		System.out.println("Friend birthdays: ");
		for(Iterator<String> f = me.getFriends(); f.hasNext(); ) {
			String friend = f.next();
			
			System.out.println(friend+": "+people.getProfile(friend).getBirthday());
		}
	}
}
