package tests;

import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Test;

import facepamphlet.FacePamphletDatabase;
import facepamphlet.FacePamphletProfile;
import facepamphlet.FacePamphletSubProfile;
import facepamphlet.FacePamphletDatabasePersistor;

/**
 * This demonstrates the persistence module; a database is created, stored, 
 * and then initialized.
 * @author Christian Fiddick
 */
public class Persistence {
	@Test
	public void persistenceTest() {
		// TODO SET THIS PATH!!
		String databasePath = "/home/chris/workspace/FacePamphlet/src/tests/database.txt";
		
		FacePamphletProfile me = new FacePamphletProfile("Me");
		FacePamphletProfile friend = new FacePamphletProfile("Friend");

		FacePamphletDatabase people = new FacePamphletDatabase();
		people.addProfile(me);
		people.addProfile(friend);
		
		me.addFriend(friend.getName());
		friend.addFriend(me.getName());
		
		me.setBirthday(new Date());
		me.setStatus("Fine");
		me.setBio("I was born.");
		
		me.setCategory(friend.getName(), "Best friends");
		me.setCategory(friend.getName(), "School friends");
		
		FacePamphletSubProfile myCat = new FacePamphletSubProfile("myCat", me.getName());
		me.addSubProfile(myCat.getName());
		
		people.addProfile(myCat);
		
		me.receivePrivateMessage(friend.getName(), "Hey");
		me.receivePublicMessage(myCat.getName(), "Meeeoow");
		
		FacePamphletDatabasePersistor persistor = new FacePamphletDatabasePersistor(people);
		persistor.writeDatabaseToFile(databasePath);
		
		FacePamphletDatabase samePeople = new FacePamphletDatabase();
		persistor = new FacePamphletDatabasePersistor(samePeople);
		try {
			persistor.readFromFile(databasePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(people.getProfile("Me"));
		System.out.println(samePeople.getProfile("Me"));
		
		System.out.println();
		
		System.out.println(people.getProfile("Friend"));
		System.out.println(samePeople.getProfile("Friend"));
		
		System.out.println();
		
		System.out.println(people.getProfile("myCat"));
		System.out.println(samePeople.getProfile("myCat"));

	}
}
