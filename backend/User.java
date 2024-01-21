import java.util.HashSet;
import java.util.Scanner;

/**
 * Class represents a user with the ability to manage and interact with organizations.
 * The user can create, join, invite, and connect with organizations.
 */
public class User {
  private HashSet<Organization> orgList= new HashSet<Organization>();
  private int ID;
  private String realName; // remind! - realname is currently not used
  private String userName;

  /**
   * Constructs a new User with the specified real name and username
   * @param realName The real name of the user
   * @param userName The username for the user
   */
  public User(String realName, String userName) {
    this.realName = realName; 
    this.userName = userName;
  }

  /**
   * createOrganization
   * Creates a new organization of the specified type and adds it to the user's list of organizations
   * @param type The type of organization to create (e.g., School or Partner)
   */
  public void createOrganization(Organization.Type type) {
    //pronmpt user or smth
    int id = 0;
    String name = "";
    String resources = "";
    String contactInfo = "";
    
    Organization org;
    if (type == Organization.Type.School) {
      String schoolDistrict = "";
      String principalName = "";
      org = new SchoolOrganization(id, name, "School", resources, contactInfo, schoolDistrict, principalName);
      orgList.add(org);
      // add things later (CUZ THIS THING HAS TO CONNECT TO THE DATABASE)
    } else if (type == Organization.Type.Partner) {
      String phoneNumber = "";
      String companyEmail = "";
      org = new PartnerOrganization(id, name, "Partner", resources, contactInfo, phoneNumber, companyEmail);
      // add things later
      orgList.add(org);
    }
    
  }

  /**
   * joinOrganization
   * Adds an existing organization to the user's list of organizations
   * @param org The organization to join
   */
  public void joinOrganization(Organization org) {
    orgList.add(org);
  }

  /**
   * inviteOrganization
   * Invites another user to join an organization
   * @param user The user to invite
   * @param org  The organization to which the user is invited
   */
  // public void inviteOrganization() {
  //   joinOrganization(org);
  //   // what does this do? its supposed to be org.inviteUser(user or username) isnt it
  // }

  /**
   * connectWithOtherOrganizations
   * Connects the user with all organizations from another user's list
   * @param user The user whose organizations to connect with
   */
  public void connectWithOtherOrganizations() {
    for (Organization org : orgList) {
      orgList.add(org);
    }
    
  }

  /**
   * getID
   * Retrieves the user's ID
   * @return The ID of the user
   */
  public int getID() {
    return ID;
  }

  /**
   * setID
   * Sets the user's ID
   * @param ID The new ID of the user
   */
  public void setID(int ID) {
    this.ID = ID;
  }

  /**
   * getName
   * Retrieves the user's real name
   * @return The real name of the user
   */
  public String getRealName() {
    return realName;
  }

  /**
   * setName
   * Sets the user's real name
   * @param name The new real name of the user
   */
  public void setRealName(String newRealName) {
    this.realName = newRealName;
  }

  /**
   * getUserName
   * Retrieves the user's username
   * @return The username of the user
   */
  public String getUserName() {
    return userName;
  }

  /**
   * setUserName
   * Sets the user's username
   * @param userName The new username of the user
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public HashSet<Organization> getOrgList() {
    return orgList;
  }
}