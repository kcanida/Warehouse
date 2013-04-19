package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * 
 * @author Kellie Canida
 * 
 * A stock item is held at a warehouse and is a type of product. A warehouse
 * has a certain quantity of stock items.
 *
 */
@Entity
public class StockItem extends Model {
  private static final long serialVersionUID = -7681398919929300442L;
  @Id
  private long primaryKey;
  @Required
  @Column(unique=true, nullable=false)
  private String stockItemId;
  @Required
  @ManyToOne(cascade=CascadeType.ALL)
  private Warehouse warehouse;
  @Required
  @ManyToOne(cascade=CascadeType.ALL)
  private Product product;
  @Required
  private long quantity;
  
  /**
   * Constructor method for a stock item.
   * @param warehouse stock item is held at.
   * @param product the stock item belongs to.
   * @param quantity of the stock item at the warehouse.
   */
  public StockItem(String stockItemId, Warehouse warehouse, Product product, long quantity) {
    this.stockItemId = stockItemId;
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }
  
  public String validate() {
    if(Warehouse.find().findList().contains(warehouse.getWarehouseId())==false || 
       Product.find().findList().contains(product.getProductId())==false) {
      return ("A stockItem must be associated with a valid warehouse and product.");
    }
    return null;
  }
  
  public long getPrimaryKey() {
    return this.primaryKey;
  }
  
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public String getStockItemId() {
    return this.stockItemId;
  }
  
  public void setStockItemId(String stockItemId) {
    this.stockItemId = stockItemId;
  }
  
  public Warehouse getWarehouse() {
    return this.warehouse;
  }
  
  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }
  
  public Product getProduct() {
    return this.product;
  }
  
  public void setProduct(Product product) {
    this.product = product;
  }
  
  public long getQuantity() {
    return this.quantity;
  }
  
  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }
  
  public static Finder<Long, StockItem> find() {
    return new Finder<Long, StockItem>(Long.class,StockItem.class);
  }
  
  public String toString() {
    return String.format("[StockItem %s %s %s %d]", stockItemId, warehouse.getWarehouseId(),
                           product.getProductId(), quantity);
  }
}
