import java.sql.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.sql.ResultSet;
//import oracle.jdbc.driver.OracleDriver;
import org.sqlite;
// import java.
/**
 * This Class represents the entry point for a user authentication system.
 * It provides methods to initialize a database connection, handle user login, sign up,
 * and change views after successful authentication. It also includes utility methods
 * for password hashing and searching for organizations.
 */
public class Start{
  private Connection connection;
  
  /**
   * Constructs a new Start object.
   */
  public Start (){}

  public void initialize() {
    initializeDBConnection();
  }

  /**
   * initiliazeDBConnection
   * Initializes the database connection
   */
  private void initializeDBConnection() {
    //assuming the SQLite JDBC driver is added to the project (cough daniel)
    // yes yes it's in JDBCdriver
    System.out.println("connecting to the SQLite database...");
    try {
      Class.forName("org.sqlite.JDBC");
      // Class.forName("oracle.jdbc.driver.OracleDriver");
      // Driver myDriver = new OracleDriver();
      // DriverManager.registerDriver( myDriver );
      String url = "jdbc:sqlite:Database.db";
      connection = DriverManager.getConnection(url);
      System.out.println("connected!");
      
    } catch (SQLException e) {
      System.out.println("Error connecting to SQLite database:\n" + e.getMessage());
    // } catch (ClassNotFoundException e) {
    //   System.out.println("Error trying to dynamically load the drivers for SQLite:\n" + e.getMessage());
    }
  }

  /**
   * login
   * Attempts to log in a user with the provided username and password
   * @param userName The username of the user trying to log in
   * @param password The password of the user trying to log in
   * @return {@code true} if authentication is successful, {@code false} otherwise
   */
  public boolean login (String userName, String password) {
    //pseudocode (what I need done for the frontend)
    //ping backend (SQLite) to see if the user exists, if so then hash the password
    // see if the passwords match
    // if it all looks good, then the authenticate is successful
    // go into a different view and you can see the accounts and all the organizations that you are apart of
    //able to search for a organization so you can join (search ) <-- another functions not in login
    // load the backend data (ping to retrieve)
    try {
      String query = "SELECT password FROM users WHERE username = ?";
      PreparedStatement pstatement = connection.prepareStatement(query);
      pstatement.setString(1, userName);
      ResultSet resultSet = pstatement.executeQuery();

      if (resultSet.next()) {
        String storedPasswordHash = resultSet.getString("password");
        String hashedPassword = SecurityUtils.hashPassword(password, SecurityUtils.generateSalt());
        if (hashedPassword.equals(storedPasswordHash)) {
          //then the authentication is successful
          return true;
        }
      }
    } catch (SQLException e) {
      System.out.println("Login error: " + e.getMessage());
    }
    return false; //authentication is not successful
  }

  /**
   * signUp
   * Signs up a new user with the provided real name, username, and password
   * @param realName The real name of the user
   * @param userName The username for the new account
   * @param password The password for the new account
   */
  public void signUp(String realName, String userName, String password) {
    //front end code to prompt the user 
    //two text boxes to type in username and password and a button to sign up
    //get the strings from the text field
    //ping backend (if there is a user already with this username, if there isn't, then hash the password and create this user and save it to the backend)
    try {
      String query = "INSERT INTO users (realname, username, password)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, realName);
      statement.setString(2, userName);
      statement.setString(3, SecurityUtils.hashPassword(password, SecurityUtils.generateSalt()));
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Sign up error: " + e.getMessage());
    }
    User user = new User(realName, userName); // add this somewhere when the user creates the account
  }

  // hashPassword(String password) is now stored in the SecuirtyUtils.java file

  /**
   * changeView
   * Changes the view to display user's organizations and handle organization search
   * @param user The user whose view is to be changed
   */
  public void changeView(User user) {
    System.out.println("Welcome, " + user.getUserName() + "!"); //make sure to replace the System.out.printlns with the frontend display (when andrew is done)

    //display users organizations here
    System.out.println("Your organizations:");
    for (Organization org : user.getOrgList()) {
      System.out.println(org.getName());
    }

    //prompt for organization search
    System.out.println("Enter the name of an organization to search for:");
    Scanner scanner = new Scanner(System.in);
    String orgName = scanner.nextLine();
    Organization org = Organization.searchOrganization(orgName, connection);

    if (org != null) {
      System.out.println("Found organization: " + org.getName());
      System.out.println("Would you like to join this organization? (yes/no)");
      String response = scanner.nextLine();
      if (response.equalsIgnoreCase("yes")) {
        user.joinOrganization(org);
        System.out.println("You have joined " + org.getName() + ".");
      } else {
        System.out.println("You have not joined " + org.getName() + ".");
      }
    } else {
      System.out.println("There is no organization found with the name " + orgName + ".");
    }
  }
}