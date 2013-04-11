package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
  public Long id;
  public String name;
  @ManyToMany(mappedBy="tags", cascade=CascadeType.ALL)
  public List<Product> products = new ArrayList<>();
  
  /**
   * Constructor method for a tag.
   * @param name of tag.
   */
  public Tag(String name) {
    this.name = name;
  }
  
  public static Finder<Long, Tag> find() {
    return new Finder<Long, Tag>(Long.class, Tag.class);
  }
}
