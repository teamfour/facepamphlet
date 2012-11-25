package tests;

import java.util.Calendar;

import facepamphlet.FacePamphletDatabase;
import facepamphlet.FacePamphletProfile;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This just ensures that we can set birthdays and get them.
 * @author Christian Fiddick
 */
public class Birthday {
	@Test
	public void birthdayTest() {
		Calendar c = Calendar.getInstance();
		
		FacePamphletProfile me = new FacePamphletProfile("Me");
		FacePamphletProfile friend = new FacePamphletProfile("Friend");

		FacePamphletDatabase people = new FacePamphletDatabase();
		people.addProfile(me);
		people.addProfile(friend);
		
		me.addFriend(friend.getName());
				
		me.setBirthday(c.getTime());
		friend.setBirthday(c.getTime());

		assertEquals(me.getBirthday(), c.getTime());
				
		// this is how we would get a friend's birthday
		assertEquals(friend.getBirthday(), people.getProfile(me.getFriends().next()).getBirthday());
	}
}
