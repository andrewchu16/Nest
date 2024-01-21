/**
 * Represents a partner organization, which is a specific type of organization.
 * It extends the Organization class and adds additional properties such as phone number and company email.
 */
public class PartnerOrganization extends Organization {
  private String phoneNumber;
  private String companyEmail;

  /**
   * Constructs a PartnerOrganization object with the given parameters.
   *
   * @param id the unique ID of the organization
   * @param name the name of the organization
   * @param type the type of the organization
   * @param resources the resources of the organization
   * @param contactInfo the contact information of the organization
   * @param phoneNumber the phone number of the partner organization
   * @param companyEmail the company email of the partner organization
   */
  public PartnerOrganization(int id, String name, String type, String resources, String contactInfo, String phoneNumber, String companyEmail) {
    super(id, name, type, resources, contactInfo);
    this.phoneNumber = phoneNumber;
    this.companyEmail = companyEmail;
  }

  /**
   * Returns the phone number of the partner organization.
   *
   * @return the phone number of the partner organization
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the phone number of the partner organization.
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns the company email of the partner organization.
   *
   * @return the company email of the partner organization
   */
  public String getCompanyEmail() {
    return companyEmail;
  }

  /**
   * Sets the company email of the partner organization.
   *
   * @param companyEmail the new company email
   */
  public void setCompanyEmail(String companyEmail) {
    this.companyEmail = companyEmail;
  }

  //additional methods specific to PartnerOrganization
}