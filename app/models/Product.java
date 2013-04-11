package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
  public Long id;
  public String name;
  public String description;
  @ManyToMany(cascade=CascadeType.ALL)
  public List<Tag> tags = new ArrayList<>();
  @OneToMany(mappedBy="product",cascade=CascadeType.ALL)
  public List<StockItem> stockitems = new ArrayList<>();
  
  /**
   * Constructor method of a product.
   * @param name of type of product.
   * @param description of product.
   */
  public Product(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public static Finder<Long, Product> find() {
    return new Finder<Long, Product>(Long.class, Product.class);
  }
}
