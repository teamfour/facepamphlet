package tests;

import facepamphlet.FacePamphletDatabase;
import facepamphlet.FacePamphletProfile;
import facepamphlet.FacePamphletSubProfile;

/**
 * This tests the sub-profile functionality of a profile. This is very simple
 * since all we need to test is the association of the profiles.
 */
public class SubProfile {
	public static void main(String[] args) {		
		FacePamphletDatabase people = new FacePamphletDatabase();
		
		FacePamphletProfile me = new FacePamphletProfile("Me");
		FacePamphletSubProfile myCat = new FacePamphletSubProfile("Cat", me.getName());
				
		people.addProfile(me);
		people.addProfile(myCat);
		
		me.addSubProfile("Cat");
		
		System.out.println(me.toString());
		System.out.println(myCat.toString());
	}
}
