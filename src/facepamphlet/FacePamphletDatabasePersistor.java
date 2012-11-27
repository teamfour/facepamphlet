package facepamphlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * This is a plain text persistence class.
 * @author Christian Fiddick
 */
public class FacePamphletDatabasePersistor {
	
	/** Storage String constants (reserved keywords)*/
	public static final String ITEM_SEPARATOR = ":::";
	public static final String NONE = "_NONE_";
	public static final String SUBPROFILE = "SUBPROFILE";
	public static final String PROFILE = "PROFILE";
	
	/** The database we are storing */
	private FacePamphletDatabase profiles = null;
	
	/**
	 * @param profiles Initialize with this FacePamphletDatabase
	 * @author Christian Fiddick
	 */
	public FacePamphletDatabasePersistor(FacePamphletDatabase profiles) {
		this.profiles = profiles;
	}
	
	/**
	 * Write a FacePamphletDatabase to a file.
	 * @param fileName Path to file
	 * @author Christian Fiddick
	 */
	public void writeDatabaseToFile(String fileName) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			
			for(Iterator<FacePamphletProfile> profileIterator = profiles.getFacePamphletProfiles(); profileIterator.hasNext(); ) {
				FacePamphletProfile profile = profileIterator.next();
				writer.write(profilePersistenceString(profile));
				// separate profiles with blank lines
				writer.write(System.lineSeparator());
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) {
					writer.close( );
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param fileName Parse a FacePamphletDatabase from this file
	 * @throws FileNotFoundException
	 * @author Christian Fiddick
	 */
	public void readFromFile(String fileName) throws FileNotFoundException {
		Scanner s = null;
		
		try {
			s = new Scanner(new File(fileName));
			
			LinkedList<String> persistenceString = new LinkedList<String>();
			
			String line = s.nextLine();	
			
			while(true) {
				// reached the end of a profile
				if(line == null || line.length() < 1) {
					FacePamphletProfile profile = persistenceStringToProfile(persistenceString);
					if(profile != null) {
						profiles.addProfile(profile);
					}
					
					// reset for next profile
					persistenceString.clear();
					
					// but two blank lines will indicate end of file
					if(s.hasNextLine()) {
						line = s.nextLine();
						if(line == null || line.length() < 1) {
							break;
						}
					}
					else {
						break;
					}
				}
				else {
					// still reading profile lines
					persistenceString.add(line);
					line = s.nextLine();
				}			
			}
		} finally {
			if(s != null) {
				s.close();
			}
		}
	}
	
	/**
	 * Parse a persistence String (in our predetermined format) into a 
	 * FacePamphletProfile.
	 * @param lines Lines to parse from (entirely dependent on order!)
	 * @return Parsed FacePamphletProfile
	 * @author Christian Fiddick
	 */
	public FacePamphletProfile persistenceStringToProfile(LinkedList<String> lines) {
		FacePamphletProfile profile = null;
		
		if(lines == null || lines.size() < 1) {
			return null;
		}
				
		String profileType = cleanIncomingString(lines.pop());
		String profileName = cleanIncomingString(lines.pop());
		
		// identify subprofile vs. profile
		if(profileType.startsWith(SUBPROFILE)) {
			// need to know the parent profile name, e.g. from "SUBPROFILE:::christian"
			profile = new FacePamphletSubProfile(profileName, splitMultipleItemString(profileType)[1]);
		}
		else {
			profile = new FacePamphletProfile(profileName);
		}
		
		// profile bio
		profile.setBio(cleanIncomingString(lines.pop()));
		
		// profile status
		profile.setStatus(cleanIncomingString(lines.pop()));
		
		// profile birthday
		String date = cleanIncomingString(lines.pop());
		if(date != null) {
			profile.setBirthday(new Date(Long.parseLong(date)));
		}
		
		// profile friends and categories
		int numFriends = Integer.parseInt(lines.pop());
		for(int i = 0; i < numFriends; i++) {
			String[] friend = splitMultipleItemString(lines.pop());
			if(friend[0] != null) {
				profile.addFriend(friend[0]);
				for(int j = 1; j < friend.length; j++) {
					if(friend[j] == null) continue;
					profile.setCategory(friend[0], friend[j]);
				}
			}
		}
		
		// owned subprofiles
		String[] subProfiles = splitMultipleItemString(lines.pop());
		if(subProfiles != null) {
			for(String subProfile:subProfiles) {
				if(subProfile != null) {
					profile.addSubProfile(subProfile);
				}
			}
		}
		
		// profile notifications
		String[] notifications = splitMultipleItemString(lines.pop());
		if(notifications != null) {
			for(String notification:notifications) {
				if(notification != null) {
					profile.addNotification(notification);
				}
			}
		}
		
		// profile public messages
		int numPublicMessages = Integer.parseInt(lines.pop());
		for(int i = 0; i < numPublicMessages; i++) {
			String[] publicMessage = splitMultipleItemString(lines.pop());
			if(publicMessage[0] != null) {
				for(int j = 1; j < publicMessage.length; j++) {
					if(publicMessage[j] == null) continue;
					profile.receivePublicMessage(publicMessage[0], publicMessage[j]);
				}
			}
		}
		
		// profile private messages
		int numPrivateMessages = Integer.parseInt(lines.pop());
		for(int i = 0; i < numPrivateMessages; i++) {
			String[] privateMessage = splitMultipleItemString(lines.pop());
			if(privateMessage[0] != null) {
				for(int j = 1; j < privateMessage.length; j++) {
					if(privateMessage[j] == null) continue;
					profile.receivePrivateMessage(privateMessage[0], privateMessage[j]);
				}
			}
		}
		
		return profile;
	}
	
	/**
	 * Create a persistence String from a FacePamphletProfile in our
	 * predetermined format.
	 * @param profile FacePamphletProfile to make String from
	 * @return String from FacePamphletProfile
	 * @author Christian Fiddick
	 */
	public String profilePersistenceString(FacePamphletProfile profile) {		
		if(profiles == null) {
			return null;
		}

		String stringProfile = "";
		
		// identify subprofile vs. profile
		if(profile instanceof FacePamphletSubProfile) {
			stringProfile += SUBPROFILE;
			stringProfile += ITEM_SEPARATOR;
			stringProfile += cleanOutGoingString(((FacePamphletSubProfile) profile).getParentProfile());
		}
		else {
			stringProfile += PROFILE;
		}
		stringProfile += System.lineSeparator();
		
		// profile name
		stringProfile += cleanOutGoingString(profile.getName());
		stringProfile += System.lineSeparator();
		
		// profile bio
		stringProfile += cleanOutGoingString(profile.getBio());
		stringProfile += System.lineSeparator();
		
		// profile status
		stringProfile += cleanOutGoingString(profile.getStatus());
		stringProfile += System.lineSeparator();
		
		// profile birthday
		if(profile.getBirthday() != null) {
			stringProfile += cleanOutGoingString(profile.getBirthday().getTime());
		}
		else {
			stringProfile += cleanOutGoingString(null);
		}
		stringProfile += System.lineSeparator();
		
		// profile friends and categories
		stringProfile += iteratorSize(profile.getFriends());
		stringProfile += System.lineSeparator();
		Iterator<String> friends = profile.getFriends();
		while(friends.hasNext()) {
			// e.g. "christian::family::bermuda::male"
			String friend = friends.next();
			stringProfile += cleanOutGoingString(friend);
			stringProfile += ITEM_SEPARATOR;
			Iterator<String> categories = profile.getCategories(friend);
			stringProfile += cleanOutGoingString(multipleItemString(categories));
			stringProfile += System.lineSeparator();
		}
		
		// owned subprofiles
		stringProfile += cleanOutGoingString(multipleItemString(profile.getSubProfiles()));
		stringProfile += System.lineSeparator();
		
		// profile notifications
		stringProfile += cleanOutGoingString(multipleItemString(profile.getNotifications().iterator()));
		stringProfile += System.lineSeparator();

		// profile public messages
		Map<String,ArrayList<String>> publicMessages = profile.getPublicMessages();
		stringProfile += publicMessages.keySet().size();
		stringProfile += System.lineSeparator();
		for(String from:publicMessages.keySet()) {
			// e.g. "someFriend::message1::message2::message3"
			stringProfile += cleanOutGoingString(from);
			stringProfile += ITEM_SEPARATOR;
			stringProfile += cleanOutGoingString(multipleItemString(publicMessages.get(from).iterator()));
			stringProfile += System.lineSeparator();
		}
		
		// profile private messages
		Map<String,ArrayList<String>> privateMessages = profile.getPrivateMessages();
		stringProfile += privateMessages.keySet().size();
		stringProfile += System.lineSeparator();
		for(String from:privateMessages.keySet()) {
			// e.g. "someFriend::message1::message2::message3"
			stringProfile += cleanOutGoingString(from);
			stringProfile += ITEM_SEPARATOR;
			stringProfile += cleanOutGoingString(multipleItemString(privateMessages.get(from).iterator()));
			stringProfile += System.lineSeparator();
		}
				
		return stringProfile;
	}
	
	/**
	 * @param s Input String to print
	 * @return The String split around ITEM_SEPARATOR and made clean for input
	 * @author Christian Fiddick
	 */
	public static String[] splitMultipleItemString(String s) {
		if(s == null) {
			return null;
		}
		
		String[] sArr = s.split(ITEM_SEPARATOR);
		
		for(int i = 0; i < sArr.length; i++) {
			sArr[i] = cleanIncomingString(sArr[i]);
		}
		
		return sArr;
	}
	
	/**
	 * This is mainly used to convert our NONE flag or empty Strings to null
	 * for parsing simplicity.
	 * @author Christian Fiddick
	 */
	public static String cleanIncomingString(String s) {
		if(NONE.equals(s) || s == "") {
			return null;
		}
		else {
			return s;
		}
	}
	
	/**
	 * This is mainly used to convert null or empty Strings to NONE
	 * for parsing simplicity.	
	 * @author Christian Fiddick
	 */
	public static String cleanOutGoingString(Object o) {
		if(o == null || o.toString().length() < 1) {
			return NONE;
		}
		else {
			return o.toString();
		}
	}
	
	/**
	 * Create a String for representing a group of items, e.g.
	 * item1:::item2:::item3
	 * @author Christian Fiddick
	 */
	public static String multipleItemString(Iterator<?> iterator) {
		String s = "";
		
		if(iterator == null) {
			return null;
		}
		
		if(iterator.hasNext()) {
			s += cleanOutGoingString(iterator.next());
		}
		else {
			return "";
		}
		
		while(iterator.hasNext()) {
			s += ITEM_SEPARATOR + cleanOutGoingString(iterator.next());
		}
		
		return s;
	}
	
	/**
	 * Utility method to figure out how many items are in an iterator.
	 * @author Christian Fiddick
	 */
	public static int iteratorSize(Iterator<?> iterator) {
		if(iterator == null) {
			return 0;
		}
		
		int i = 0;
		while(iterator.hasNext()) {
			i++;
			iterator.next();
		}
		
		return i;
	}
}
