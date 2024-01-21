import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents an organization with a unique ID, name, type, resources, and contact information.
 */
public class Organization {
  private int id;
  private String name;
  private String type;
  private String resources;
  private String contactInfo;

  /**
   * Constructs an Organization object with the given parameters.
   *
   * @param id the unique ID of the organization
   * @param name the name of the organization
   * @param type the type of the organization
   * @param resources the resources of the organization
   * @param contactInfo the contact information of the organization
   */
  public Organization(int id, String name, String type, String resources, String contactInfo) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.resources = resources;
    this.contactInfo = contactInfo;
  }

  /**
   * Creates a new Organization object and inserts it into the database.
   *
   * @param name the name of the organization
   * @param type the type of the organization
   * @param resources the resources of the organization
   * @param contactInfo the contact information of the organization
   * @return the newly created Organization object
   */
  public static Organization createOrganization(String name, String type, String resources, String contactInfo) {
    Organization newOrg = null;
    //insert organization into the database and return the organization object
    return newOrg;
  }

  /**
   * Invites a user to the organization.
   *
   * @param user the user to be invited
   */
  public static void inviteUser(User user) {
    // logic to invite user to this organization
  }

  /**
   * Searches for an organization in the database by its name.
   *
   * @param name the name of the organization
   * @param connection the database connection object
   * @return the Organization object if found, null otherwise
   */
  public static Organization searchOrganization(String name, Connection connection) {
    //this would typically interact with the database to find the organization for now imma just return it
    try {
      String query = "FROM organizations WHERE name = ?";
      PreparedStatement pstatement = connection.prepareStatement(query);
      pstatement.setString(1, name);
      ResultSet resultSet = pstatement.executeQuery();

      if (resultSet.next()) {
        String orgName = resultSet.getString("name");
        // get org's pass hash
        String orgPassHash = "";
        String storedPasswordHash = "";
        // get stored pass hash (what does this even do)
        if (orgPassHash.equals(storedPasswordHash)) {
          //then the authentication is successful
          // bro what authentication you're literally just looking to see if the organization exists
          // i think just return an org object here?
          return null;
        }
      }
    } catch (SQLException e) {
      System.out.println("Login error: " + e.getMessage());
    }
    return null;
  }

  /**
   * Enum representing the type of the organization.
   */
  public enum Type { 
    School,
    Partner
  }

  /**
   * Returns the name of the organization.
   *
   * @return the name of the organization
   */
  public String getName() {
    return name;
  }
}