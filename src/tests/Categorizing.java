package tests;

import java.util.ArrayList;
import java.util.Iterator;

import facepamphlet.FacePamphletProfile;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This tests the categorization of friends.
 * @author Christian Fiddick
 */
public class Categorizing {
	@Test
	public void categorizingTest() {
		FacePamphletProfile me = new FacePamphletProfile("Me");
		
		FacePamphletProfile friend1 = new FacePamphletProfile("Friend1");
		FacePamphletProfile friend2 = new FacePamphletProfile("Friend2");

		me.addFriend(friend1.getName());
		me.addFriend(friend2.getName());
				
		me.setCategory(friend1.getName(), "Male");

		assertEquals(me.getCategories(friend1.getName()).next(), "Male");
		
		me.setCategory(friend2.getName(), "Female");
		me.setCategory(friend2.getName(), "Family");
		
		ArrayList<String> friend2Categories = new ArrayList<String>();
		for(Iterator<String> i = me.getCategories(friend2.getName()); i.hasNext(); ) {
			friend2Categories.add(i.next());
		}
		
		assertEquals(friend2Categories.get(0), "Female");
		assertEquals(friend2Categories.get(1), "Family");

		me.setCategory(friend1.getName(), null);
		
		assertEquals(me.getCategories(friend1.getName()), null);
		
		assertEquals(me.getFriends("Family").next(), friend2.getName());
	}
}
