package tests;

import facepamphlet.FacePamphletProfile;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This ensures that we can set and get a profile's bio.
 */
public class Bio {
	@Test
	public  void bioTest() {
		FacePamphletProfile me = new FacePamphletProfile("Me");
		
		me.setBio("I was born.");
		
		assertEquals(me.getBio(), "I was born.");
	}
}
