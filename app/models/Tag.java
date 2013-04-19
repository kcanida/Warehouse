package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * 
 * @author Kellie Canida
 * 
 * A tag can belong to multiple products.
 *
 */
@Entity
public class Tag extends Model {
  private static final long serialVersionUID = -8417172117934560476L;
  @Id
  private long primaryKey;
  @Required
  @Column(unique=true, nullable=false)
  private String tagId;
  @ManyToMany(mappedBy="tags", cascade=CascadeType.ALL)
  private List<Product> products = new ArrayList<>();
  
  /**
   * Constructor method for a tag.
   * @param name of tag.
   */
  public Tag(String tagId) {
    this.tagId = tagId;
  }
  
  /**
   * NO tag can be named "Tag".
   * Note: illustrates use of validate() method.
   * @return null if OK, error string if not OK.
   */
  public String validate() {
    return ("Tag".equals(this.tagId)) ? "Invalid tag name" : null;
  }
  
  
  public long getPrimaryKey() {
    return this.primaryKey;
  }
  
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public String getTagId() {
    return this.tagId;
  }
  
  public void setTagId(String tagId) {
    this.tagId = tagId;
  }
  
  public List<Product> getProducts() {
    return this.products;
  }
  
  public void setProducts(List<Product> products) {
    this.products = products;
  }
  
  public static Finder<Long, Tag> find() {
    return new Finder<Long, Tag>(Long.class, Tag.class);
  }
  
  public String toString() {
    return String.format("[Tag %s]", tagId);
  }
}
