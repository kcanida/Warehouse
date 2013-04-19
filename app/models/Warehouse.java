package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.Required;
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
  private long primaryKey;
  @Required
  @Column(unique=true, nullable=false)
  private String warehouseId;
  @Required
  private String name;
  @OneToMany(mappedBy="warehouse", cascade=CascadeType.ALL)
  private List<StockItem> stockitems = new ArrayList<>();
  @OneToOne(mappedBy="warehouse", cascade=CascadeType.ALL)
  private Address address;
  
  /**
   * Constructor method for a warehouse.
   * @param name of warehouse.
   */
  public Warehouse(String warehouseId, String name) {
    this.warehouseId = warehouseId;
    this.name = name;
  }
  
  public long getPrimaryKey() {
    return this.primaryKey;
  }
  
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public String getWarehouseId() {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String warehouseId) {
    this.warehouseId = warehouseId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public List<StockItem> getStockItems() {
    return this.stockitems;
  }
  
  public void setStockItems(List<StockItem> stockItems) {
    this.stockitems = stockItems;
  }
  
  public Address getAddress() {
    return this.address;
  }
  
  public void setAddress(Address address) {
    this.address = address;
  }
  
  public static Finder<Long,Warehouse> find() {
    return  new Finder<Long,Warehouse>(Long.class,Warehouse.class);
  }
  
  public String toString() {
    return String.format("[Warehouse %s %s]", warehouseId, name);
  }
}
