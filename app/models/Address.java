package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

@Entity
public class Address extends Model{
  private static final long serialVersionUID = -5999834051115543069L;
  @Id
  public long id;
  public String name;
  @OneToOne(cascade=CascadeType.ALL)
  public Warehouse warehouse;
  
  public Address(String name, Warehouse warehouse) {
    this.name = name;
    this.warehouse = warehouse;
  }
  
  public static Finder<Long,Address> find() {
    return new Finder<Long,Address>(Long.class,Address.class);
  }
}
