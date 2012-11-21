package tests;

import facepamphlet.FacePamphletProfile;

public class Bio {
	public static void main(String[] args) {
		FacePamphletProfile me = new FacePamphletProfile("Me");
		me.setBio("I was born.");
		System.out.println(me.getBio());
	}
}
