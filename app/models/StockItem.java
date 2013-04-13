package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
  public Long id;
  @ManyToOne(cascade=CascadeType.ALL)
  public Warehouse warehouse;
  @ManyToOne(cascade=CascadeType.ALL)
  public Product product;
  public long quantity;
  
  /**
   * Constructor method for a stock item.
   * @param warehouse stock item is held at.
   * @param product the stock item belongs to.
   * @param quantity of the stock item at the warehouse.
   */
  public StockItem(Warehouse warehouse, Product product, long quantity) {
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }
  
  public long getQuantity() {
    return this.quantity;
  }
  
  public static Finder<Long, StockItem> find() {
    return new Finder<Long, StockItem>(Long.class,StockItem.class);
  }
}
