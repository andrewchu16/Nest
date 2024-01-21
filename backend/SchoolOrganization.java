/**
 * Represents a school organization, which is a specific type of organization.
 * It extends the Organization class and adds additional properties such as school district and principal name.
 */
public class SchoolOrganization extends Organization {
  private String schoolDistrict;
  private String principalName;

  /**
   * Constructs a SchoolOrganization object with the given parameters.
   *
   * @param id the unique ID of the organization
   * @param name the name of the organization
   * @param type the type of the organization
   * @param resources the resources of the organization
   * @param contactInfo the contact information of the organization
   * @param schoolDistrict the school district of the school organization
   * @param principalName the principal's name of the school organization
   */
  public SchoolOrganization(int id, String name, String type, String resources, String contactInfo, String schoolDistrict, String principalName) {
    super(id, name, type, resources, contactInfo);
    this.schoolDistrict = schoolDistrict;
    this.principalName = principalName;
  }

  /**
   * Returns the school district of the school organization.
   *
   * @return the school district of the school organization
   */
  public String getSchoolDistrict() {
    return schoolDistrict;
  }

  /**
   * Sets the school district of the school organization.
   *
   * @param schoolDistrict the new school district
   */
  public void setSchoolDistrict(String schoolDistrict) {
    this.schoolDistrict = schoolDistrict;
  }

  /**
   * Returns the principal's name of the school organization.
   *
   * @return the principal's name of the school organization
   */
  public String getPrincipalName() {
    return principalName;
  }

  /**
   * Sets the principal's name of the school organization.
   *
   * @param principalName the new principal's name
   */
  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }

  //additional methods specific to SchoolOrganization
}