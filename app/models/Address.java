package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * 
 * @author Kellie Canida
 *
 * An address identifies the location of a single warehouse.
 */
@Entity
public class Address extends Model{
  private static final long serialVersionUID = -5999834051115543069L;
  @Id
  private long primaryKey;
  @Required
  private String addressId;
  @Required
  private String street;
  @Required
  private String city;
  @Required
  private String state;
  @Required
  private String zipCode;
  @Required
  @OneToOne(cascade=CascadeType.ALL)
  private Warehouse warehouse;
  
  /**
   * Constructor method of an address.
   * @param name of address.
   * @param warehouse that belongs at that address.
   */
  public Address(String addressId, String street, String city, String state, String zipCode, Warehouse warehouse) {
    this.addressId = addressId;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.warehouse = warehouse;
  }
  
  public long getPrimaryKey() {
    return this.primaryKey;
  }
  
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public String getAddressId() {
    return this.addressId;
  }
  
  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }
  
  public String getStreet() {
    return this.street;
  }
  
  public void setStreet(String street) {
    this.street = street;
  }
  
  public String getCity() {
    return this.city;
  }
  
  public void setCity(String city) {
    this.city =  city;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  public String getZipCode() {
    return this.zipCode;
  }
  
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
  
  public Warehouse getWarehouse() {
    return this.warehouse;
  }
  
  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }
  
  public static Finder<Long,Address> find() {
    return new Finder<Long,Address>(Long.class,Address.class);
  }
  
  public String toString() {
    return String.format("[Address %s %s %s %s %s]", addressId, street, city, state, zipCode);
  }
}
