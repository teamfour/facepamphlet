package facepamphlet;

/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import java.util.*;

import acm.graphics.GImage;

public class FacePamphletProfile {
	private GImage image;
	private String bio;
	private String status;
	private final String name;
	private HashMap<String, String> friendCategories;
	private ArrayList<String> friends;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 */
	public FacePamphletProfile(String name) {
		 friendCategories = new HashMap<String, String>();
		 friends = new ArrayList<String>();
		 this.name = name;
	}

	/** This method returns the name associated with the profile. */ 
	public String getName() {
		return name;
	}
	
	/** Return all categories this user is using */
	public Iterator<String> getCategories() {
		HashSet<String> categories = new HashSet<String>(); 
		for(String key:friendCategories.keySet()) {
			if(!categories.contains(friendCategories.get(key))) {
				categories.add(friendCategories.get(key));
			}
		}
		return categories.iterator();
	}
	
	/**
	 * Assign a friend to a category, return true if friend exists 
	 * (and changes were made).
	 */
	public boolean setCategory(String friend, String category) {
		if(friends.contains(friend)) {
			friendCategories.put(friend, category);
			return true;
		}
		return false;
	}

	/** Get a friend's category */
	public String getCategory(String friend) {
		return friendCategories.get(friend);
	}
	
	/** Get all the friends of a particular category */
	public Iterator<String> getFriends(String category) {
		Collection<String> categoryFriends = new ArrayList<String>();
		for(String friend:friendCategories.keySet()) {
			if(friendCategories.get(friend).equals(category)) {
				categoryFriends.add(friend);
			}
		}
		return categoryFriends.iterator();
	}
	
	/** 
	 * This method returns the image associated with the profile.  
	 * If there is no image associated with the profile, the method
	 * returns null. 
	 */ 
	public GImage getImage() {
		return image;
	}

	/** This method sets the image associated with the profile. */ 
	public void setImage(GImage image) {
		this.image = image;
	}
	
	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getStatus() {
		return status == null ? "" : status;
	}
	
	/** This method sets the status associated with the profile. */ 
	public void setStatus(String status) {
		this.status = status;
	}
	
	/** 
	 * This method returns the bio associated with the profile.
	 * If there is no bio associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getBio() {
		return bio == null ? "" : bio;
	}

	/** This method sets the bio associated with the profile. */ 
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 */
	public boolean addFriend(String friend) {
		if(!friends.contains(friend)) {
			friends.add(friend);
			return true;
		}
		return false;
	}

	/** 
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the 
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name 
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if(friends.remove(friend) != false) {
			friendCategories.remove(friend);
			return true;
		}
		return false;
	}

	/** 
	 * This method returns an iterator over the list of friends 
	 * associated with the profile.
	 */ 
	public Iterator<String> getFriends() {
		return friends.iterator();
	}
	
	/** 
	 * This method returns a string representation of the profile.  
	 * This string is of the form: "name (status): list of friends", 
	 * where name and status are set accordingly and the list of 
	 * friends is a comma separated list of the names of all of the 
	 * friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is 
	 * "coding" and who has friends Don, Chelsea, and Bob, this method 
	 * would return the string: "Alice (coding): Don, Chelsea, Bob"
	 */ 
	public String toString() {
		String toString = name;
		
		toString += " (" + getStatus();
		
		toString += ", " + getBio() +"): ";
		
		for(int i = 0; i < friends.size(); i++) {
			String friend = friends.get(i);
			toString += friend;
			toString += friendCategories.containsKey(friend) ? " (" + friendCategories.get(friend) + ")" : "";
			toString += i == friends.size() - 1 ? "" : ", ";
		}
		
		return toString;
	}
	
}
