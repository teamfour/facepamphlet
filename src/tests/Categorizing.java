package tests;

import java.util.Iterator;

import facepamphlet.FacePamphletProfile;

/**
 * This tests the categorization of friends. Note that this is done on a name 
 * basis, so we don't need a profile of actual profiles, we assume they exist somewhere.
 */
public class Categorizing {
	public static void main(String[] args) {
		FacePamphletProfile me = new FacePamphletProfile("Me");
		
		// we are just gonna name friends with numbers and categories with letters
		// for convenience
		
		char category = 'a';
		for(int friend = 0; friend < 15; friend++) {
			me.addFriend(""+friend);
			me.setCategory(""+friend, ""+category);
			if(friend % 3 == 0) {
				category++;
			}
		}
		
		System.out.println();
		System.out.println("Checking individual categories: ");
		for(int friend = 0; friend < 15; friend++) {
			System.out.println(friend+" ("+me.getCategory(""+friend)+")");
		}
		
		System.out.println();
		System.out.println("All categories: ");
		for(Iterator<String> c = me.getCategories(); c.hasNext(); ) {
			System.out.println(c.next());
		}
		
		System.out.println();
		System.out.println("Friends grouped by categories: ");
		for(Iterator<String> c = me.getCategories(); c.hasNext(); ) {
			String cat = c.next();
			System.out.println(cat+": ");
			for(Iterator<String> f = me.getFriends(cat); f.hasNext(); ) {
				System.out.println(" "+f.next());
			}
		}
		
		System.out.println();
		System.out.println(me.toString());
	}
}
