package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import facepamphlet.FacePamphletProfile;

public class Messaging {
	FacePamphletProfile testProfile;
	@Before
	public void setUp() throws Exception {
		testProfile = new FacePamphletProfile("Me");
	}
	/**
	 * Some tests for the messaging system.
	 * @author Cameron Ross
	 */
	@Test
	public void test() {
		try {
			testProfile.receivePrivateMessage("Crazy Bees", "Buzzzzzzzzzzz");
		} catch (Exception e) {
			fail("Private messaging failed: " + e.getMessage());
		}

		testProfile.receivePublicMessage("Robot", "BOOP");
		assertArrayEquals(testProfile.getPublicMessages().get("Robot").toArray(), new String[] { "BOOP" });
		testProfile.receivePublicMessage("Robot", "BOP");
		assertArrayEquals(testProfile.getPublicMessages().get("Robot").toArray(), new String[] { "BOOP", "BOP" });

		assertArrayEquals(testProfile.getPublicMessages("Robot").toArray(), new String[] { "BOOP", "BOP" });
		
	}

}
