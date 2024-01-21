import java.sql.*;

/**
 * Represents a connector to the organization database.
 */
public class OrganizationConnector {
  private Connection connection;

  /**
   * Constructs an OrganizationConnector object and establishes a connection to the database.
   *
   * @param dbPath the path to the database
   * @throws SQLException if a database access error occurs
   */
  public OrganizationConnector(String dbPath) throws SQLException {
    //establish a connection to the database
    this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
  }

  /**
   * Registers a new organization in the database.
   *
   * @param name the name of the organization
   * @param type the type of the organization
   * @param resources the resources of the organization
   * @param contactInfo the contact information of the organization
   * @return the ID of the newly created organization
   * @throws SQLException if a database access error occurs
   */
  public int registerOrganization(String name, String type, String resources, String contactInfo) throws SQLException {
    String insertQuery = "INSERT INTO organizations (name, type, resources, contact_info) VALUES (?, ?, ?, ?);";
    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, type);
      preparedStatement.setString(3, resources);
      preparedStatement.setString(4, contactInfo);
      int affectedRows = preparedStatement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating organization failed, no rows affected.");
      }
      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        } else {
          throw new SQLException("Creating organization failed, no ID obtained.");
        }
      }
    }
  }

  /**
   * Connects a school with a partner in the database.
   *
   * @param schoolId the ID of the school
   * @param partnerId the ID of the partner
   * @throws SQLException if a database access error occurs
   */
  public void connectSchoolWithPartner(int schoolId, int partnerId) throws SQLException {
    String insertQuery = "INSERT INTO organization_partners (school_id, partner_id) VALUES (?, ?);";
    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
      preparedStatement.setInt(1, schoolId);
      preparedStatement.setInt(2, partnerId);
      preparedStatement.executeUpdate();
    }
    // has to interact with the backend
  }

  /**
   * Disconnects a school from a partner in the database.
   *
   * @param schoolId the ID of the school
   * @param partnerId the ID of the partner
   * @throws SQLException if a database access error occurs
   */
  public void disconnectSchoolWithPartner(int schoolId, int partnerId) throws SQLException {
    String deleteQuery = "DELETE FROM organization_partners (school_id, partner_id) VALUES (?, ?);";
    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
      preparedStatement.setInt(1, schoolId);
      preparedStatement.setInt(2, partnerId);
      preparedStatement.executeUpdate();
    }
    // has to interact with the backend
  }

  /**
   * Closes the connection to the database.
   */
  public void closeConnection() {
    if (this.connection != null) {
      try {
        this.connection.close();
      } catch (SQLException e) {
        System.err.println("Failed to close the database connection: " + e.getMessage());
      }
    }
  }
}