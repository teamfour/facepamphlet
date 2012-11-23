package facepamphlet;

/**
 * File: FacePamphletSubProfile.java
 * ------------------------------
 * This is very much like a FacePamphletProfile except we extend to 
 * indicate a sub-profile. I opted for this since it means that a 
 * profile can have sub-profiles, but that's it (sub-profiles can't 
 * have sub-profiles; it just isn't necessary).
 */
public class FacePamphletSubProfile extends FacePamphletProfile {
	/** The name of the parent profile */
	private String parentProfileName;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the sub-profile.
	 */
	public FacePamphletSubProfile(String name, String parentProfileName) {
		super(name);
		this.parentProfileName = parentProfileName;
	}

	/** Get the parent profile name */
	public String getParentProfile() {
		return parentProfileName;
	}
	
	/** Set the parent profile name */
	public void setParentProfile(String parentProfileName) {
		this.parentProfileName = parentProfileName;
	}

	@Override
	public String toString() {
		return "Parent Profile: " + parentProfileName
				+ ", " + super.toString();
	}
}
