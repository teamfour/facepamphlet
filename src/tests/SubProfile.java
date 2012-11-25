package tests;

import facepamphlet.FacePamphletDatabase;
import facepamphlet.FacePamphletProfile;
import facepamphlet.FacePamphletSubProfile;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This tests the sub-profile functionality of a profile. This is very simple
 * since all we need to test is the association of the profiles.
 * @author Christian Fiddick
 */
public class SubProfile {
	@Test
	public void subProfileTest() {		
		FacePamphletDatabase people = new FacePamphletDatabase();
		
		FacePamphletProfile me = new FacePamphletProfile("Me");
		FacePamphletSubProfile myCat = new FacePamphletSubProfile("Cat", me.getName());
				
		people.addProfile(me);
		people.addProfile(myCat);
		
		me.addSubProfile("Cat");
		
		assertEquals(me.getSubProfiles().next(), "Cat");
		assertEquals(myCat.getParentProfile(), "Me");
		
		System.out.println(me.toString());
		System.out.println(myCat.toString());
	}
}
