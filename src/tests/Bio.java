package tests;

import facepamphlet.FacePamphletProfile;

/**
 * This ensures that we can set and get a profile's bio.
 */
public class Bio {
	public static void main(String[] args) {
		FacePamphletProfile me = new FacePamphletProfile("Me");
		
		me.setBio("I was born.");
		
		System.out.println(me.getBio());
	}
}
