import java.sql.*;

/**
 * Manages operations related to organizations using a database connection.
 */
public class OrganizationManager {
  private Connection connection;

  /**
   * Constructs an OrganizationManager object with the given database connection.
   *
   * @param connection the database connection
   */
  public OrganizationManager(Connection connection) {
    this.connection = connection;
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

    try (PreparedStatement pStatement = connection.prepareStatement(insertQuery)) {
      pStatement.setInt(1, schoolId);
      pStatement.setInt(2, partnerId);
      pStatement.executeUpdate();
    } catch (SQLException e) {
      //andle the exception properly - could be a duplicate entry, connection issue, etc.
      throw e;
    }
  }
}