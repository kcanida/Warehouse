package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

/**
 * 
 * @author Kellie Canida
 *
 * A Warehouse has a single address and can hold multiple stock items.
 * 
 */
@Entity
public class Warehouse extends Model{
  private static final long serialVersionUID = 1597818328908114668L;
  @Id
  public long id;
  public String name;
  @OneToMany(mappedBy="warehouse", cascade=CascadeType.ALL)
  public List<StockItem> stockitems = new ArrayList<>();
  @OneToOne(mappedBy="warehouse", cascade=CascadeType.ALL)
  public Address address;
  
  /**
   * Constructor method for a warehouse.
   * @param name of warehouse.
   */
  public Warehouse(String name) {
    this.name = name;
  }
  
  public static Finder<Long,Warehouse> find() {
    return  new Finder<Long,Warehouse>(Long.class,Warehouse.class);
  }
}
