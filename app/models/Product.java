package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * 
 * @author Kellie Canida
 *
 * A product describes the type of object. Many stock items can 
 * belong to a product and a product can have multiple tags.
 * 
 */
@Entity
public class Product extends Model {
  private static final long serialVersionUID = 5888606313787772233L;
  @Id
  private long primaryKey;
  @Required
  @Column(unique=true, nullable=false)
  private String productId;
  @Required
  private String name;
  private String description;
  @ManyToMany(cascade=CascadeType.ALL)
  private List<Tag> tags = new ArrayList<>();
  @OneToMany(mappedBy="product",cascade=CascadeType.ALL)
  private List<StockItem> stockitems = new ArrayList<>();
  
  /**
   * Constructor method of a Product.
   * @param name of type of product.
   * @param description of product.
   */
  public Product(String productId, String name, String description) {
    this.productId = productId;
    this.name = name;
    this.description = description;
  }

  public long getPrimaryKey() {
    return this.primaryKey;
  }
  
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public String getProductId() {
    return this.productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public List<Tag> getTags() {
    return this.tags;
  }
  
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }
  
  public List<StockItem> getStockItems() {
    return this.stockitems;
  }
  
  public void setStockItems(List<StockItem> stockItems) {
    this.stockitems = stockItems;
  }
  
  public static Finder<Long, Product> find() {
    return new Finder<Long, Product>(Long.class, Product.class);
  }
  
  public String toString() {
    return String.format("[Product %s %s %s]", productId, name, description);
  }
}
