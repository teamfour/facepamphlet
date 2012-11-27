package facepamphlet;

import java.util.*;

import acm.graphics.GImage;

/**
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */
public class FacePamphletProfile {
	private GImage image;
	private String bio;
	private String status;
	private Date birthday;
	
	private final String name; // Can't change (for consistency)
	
	private HashMap<String, ArrayList<String>> friendCategories;
	private ArrayList<String> friends;
	
	private ArrayList<String> subProfiles;
	
	private HashMap<String,ArrayList<String>> publicMessages;
	
	private HashMap<String,ArrayList<String>> privateMessages;
	
	private ArrayList<String> notifications;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 * @param name The name of this user
	 * @author Christian Fiddick, Cameron Ross
	 */
	public FacePamphletProfile(String name) {
		 friendCategories = new HashMap<String, ArrayList<String>>();
		 friends = new ArrayList<String>();
		 subProfiles = new ArrayList<String>();
		 
		 privateMessages = new HashMap<String,ArrayList<String>>();
		 publicMessages = new HashMap<String,ArrayList<String>>();
		 notifications = new ArrayList<String>();
		 
		 this.name = name;
	}

	/** 
	 * This method returns the name associated with the profile. 
	 * @author Christian Fiddick
	 */ 
	public String getName() {
		return name;
	}
	
	/** 
	 * Return all categories this user is using.
	 * @author Christian Fiddick
	 */
	public Iterator<String> getAllCategories() {
		HashSet<String> categories = new HashSet<String>(); 
		for(String key:friendCategories.keySet()) {
			if(!categories.contains(friendCategories.get(key))) {
				ArrayList<String> cats = friendCategories.get(key);
				for(String c:cats) {
					categories.add(c);
				}
			}
		}
		return categories.iterator();
	}
	
	/**
	 * Assign a friend to a category, return true if friend exists 
	 * (and changes were made). Return false if friend was already in 
	 * the category! If null is passed as the category, friend is 
	 * removed from all categories.
	 * @author Christian Fiddick
	 */
	public boolean setCategory(String friend, String category) {
		if(friends.contains(friend)) {
			// null removes user from all categories
			if(category == null) {
				friendCategories.remove(friend);
				return true;
			}
			
			if(friendCategories.get(friend) != null) {
				if(!friendCategories.get(friend).contains(category)) {
					friendCategories.get(friend).add(category);
					return true;
				}
			}
			else {
				ArrayList<String> cats = new ArrayList<String>();
				cats.add(category);
				friendCategories.put(friend, cats);
			}
		}
		return false;
	}

	/** 
	 * Get a friend's categories. 
	 * @author Christian Fiddick
	 */
	public Iterator<String> getCategories(String friend) {
		ArrayList<String> cats = friendCategories.get(friend);
		if(cats == null) {
			return null;
		}
		else {
			return cats.iterator();
		}
	}
	
	/** 
	 * Get all the friends of a particular category.
	 * @author Christian Fiddick 
	 */
	public Iterator<String> getFriends(String category) {
		Collection<String> categoryFriends = new ArrayList<String>();
		for(String friend:friendCategories.keySet()) {
			if(friendCategories.get(friend) != null && friendCategories.get(friend).contains(category)) {
				categoryFriends.add(friend);
			}
		}
		return categoryFriends.iterator();
	}
	
	/** 
	 * This method returns the image associated with the profile.  
	 * If there is no image associated with the profile, the method
	 * returns null. 
	 * @author Christian Fiddick
	 */ 
	public GImage getImage() {
		return image;
	}

	/** 
	 * This method sets the image associated with the profile. 
	 * @author Christian Fiddick
	 */ 
	public void setImage(GImage image) {
		this.image = image;
	}
	
	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 * @author Christian Fiddick
	 */ 
	public String getStatus() {
		return status == null ? "" : status;
	}
	
	/** 
	 * This method sets the status associated with the profile. 
	 * @author Christian Fiddick
	 */ 
	public void setStatus(String status) {
		this.status = status;
	}
	
	/** 
	 * This method returns the bio associated with the profile.
	 * If there is no bio associated with the profile, the method
	 * returns the empty string ("").
	 * @author Christian Fiddick
	 */ 
	public String getBio() {
		return bio == null ? "" : bio;
	}

	/** 
	 * This method sets the bio associated with the profile. 
	 * @author Christian Fiddick
	 */ 
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	/** 
	 * This method returns the bio associated with the profile.
	 * If there is no bio associated with the profile, the method
	 * returns null.
	 * @author Christian Fiddick
	 */
	public Date getBirthday() {
		return birthday;
	}

	/** 
	 * This method sets the birthday associated with the profile.
	 * @author Christian Fiddick
	 */ 
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 * @author Christian Fiddick
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
	 * @author Christian Fiddick
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
	 * @author Christian Fiddick
	 */ 
	public Iterator<String> getFriends() {
		return friends.iterator();
	}
	
	/** 
	 * This method adds the named sub-profile to this profile's list of 
	 * sub-profiles.  It returns true if the name was not already
	 * in the list for this profile (and the name is added 
	 * to the list).  The method returns false if the given name
	 * was already in the list of sub-profiles for this profile (in which 
	 * case, the given name is not added to the list a second time.)
	 * @author Christian Fiddick
	 */
	public boolean addSubProfile(String subProfileName) {
		if(!subProfiles.contains(subProfileName)) {
			subProfiles.add(subProfileName);
			return true;
		}
		return false;
	}

	/** 
	 * This method removes the named sub-profile from this profile's list
	 * of sub-profiles.  It returns true if the name was in the 
	 * list for this profile (and the name was removed from
	 * the list).  The method returns false if the given name 
	 * was not in the list of sub-profiles for this profile (in which case,
	 * the given name could not be removed.)
	 * @author Christian Fiddick
	 */
	public boolean removeSubProfile(String subProfileName) {
		if(subProfiles.remove(subProfileName) != false) {
			friendCategories.remove(subProfileName);
			return true;
		}
		return false;
	}
	
	/** 
	 * This method returns an iterator over the list of sub-profiles 
	 * associated with the profile.
	 * @author Christian Fiddick
	 */ 
	public Iterator<String> getSubProfiles() {
		return subProfiles.iterator();
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
	 * @author Christian Fiddick
	 */ 
	public String toString() {
		String toString = name;
		
		toString += " (" + getStatus();
		
		toString += ", " + getBio() +", sub accounts: ";
		
		for(int i = 0; i < subProfiles.size(); i++) {
			String subProfile = subProfiles.get(i);
			toString += subProfile;
			toString += i == friends.size() - 1 ? "" : ", ";
		}
				
		toString += "): ";
		
		for(int i = 0; i < friends.size(); i++) {
			String friend = friends.get(i);
			toString += friend;
			toString += friendCategories.containsKey(friend) ? " (" + friendCategories.get(friend) + ")" : "";
			toString += i == friends.size() - 1 ? "" : ", ";
		}
		
		return toString;
	}
	
	/**
	 * Receive a private message from another user.
	 * @param from The user sending the message.
	 * @param message The message this user should receive
	 * @Author Cameron Ross
	 */
	public void receivePrivateMessage(String from, String message) {
		if (privateMessages.get(from) == null)
			privateMessages.put(from, new ArrayList<String>());
		addNotification("You have a new private message!");
		privateMessages.get(from).add(message);
	}
	/**
	 * Receive a public message from another user.
	 * @param from The user sending the message.
	 * @param message The message this user should receive
	 * @author Cameron Ross
	 */
	public void receivePublicMessage(String from, String message) {
		if (publicMessages.get(from) == null)
			publicMessages.put(from, new ArrayList<String>());
		addNotification("You have a new public message!");
		publicMessages.get(from).add(message);
	}
	/**
	 * Get a list of public messages sent to this user.
	 * @return A map of the public messages sent to this user.
	 * @author Cameron Ross
	 */
	@SuppressWarnings("unchecked")
	public Map<String,ArrayList<String>> getPublicMessages() {
		return (Map<String, ArrayList<String>>) publicMessages.clone();
	}

	/**
	 * Get a list of public messages sent to this user from a particular user.
	 * @param from The user to filter the list by.
	 * @return A list of the messages sent to this user by a particular user.
	 * @author Cameron Ross
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getPublicMessages(String from) {
		return (ArrayList<String>) publicMessages.get(from).clone();
	}
	/**
	 * Get a list of private messages sent to this user.
	 * @return A map of the private messages sent to this user.
	 * @author Cameron Ross
	 */
	@SuppressWarnings("unchecked")
	public Map<String,ArrayList<String>> getPrivateMessages() {
		return (Map<String, ArrayList<String>>) privateMessages.clone();
	}

	/**
	 * Get a list of private messages sent to this user from a particular user.
	 * @param from The user to filter the list by.
	 * @return A list of the messages sent to this user by a particular user.
	 * @author Cameron Ross
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getPrivateMessages(String from) {
		return (ArrayList<String>) privateMessages.get(from).clone();
	}
	/** 
	 * Get a list of unread notifications for this user.
	 * @return List of unread notifications
	 * @author Cameron Ross
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getNotifications() {
		return (ArrayList<String>) notifications.clone();
	}
	/**
	 * Clears all notifications for this user.
	 * @author Cameron Ross
	 */
	public void clearNotifications() {
		notifications.clear();
	}
	/**
	 * Clear a specific notification for this user.
	 * @param id The ID of the notification to clear.
	 * @author Cameron Ross
	 */
	public void clearNotification(int id) {
		notifications.remove(id);
	}
	/**
	 * Add a notification for this user.
	 * @param message Message for the user to read
	 * @author Cameron Ross
	 */
	public void addNotification(String message) {
		notifications.add(message);
	}
	
}
