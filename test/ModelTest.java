import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import models.Address;
import models.Product;
import models.StockItem;
import models.Tag;
import models.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

/**
 * 
 * @author Kellie Canida
 * 
 * Model test for the Warehouse data model.
 *
 */
public class ModelTest {
  private FakeApplication application;
  
  @Before
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }

  @After
  public void stopApp() {
    stop(application);
  }
  
  @Test
  public void testModel() {
    //Create 1 tag that's associated with 1 product.
    Tag tag = new Tag("Tag");
    Product product = new Product("1", "Product", "Description");
    product.getTags().add(tag);
    tag.getProducts().add(product);
    
    //Create 1 warehouse that's associated with 1 StockItem for 1 Product.
    Warehouse warehouse = new Warehouse("1", "Warehouse");
    StockItem stockitem = new StockItem("1", warehouse, product, 100);
    warehouse.getStockItems().add(stockitem);
    //stockitem.warehouse = warehouse;  REDUNDANT STATEMENT IS UNNECESSARY
    
    //Create 1 address that's associated with 1 warehouse
    Address address = new Address("1", "street","city","state","zip",warehouse);
    warehouse.setAddress(address);
    
    
    //Persist the sample model by saving all entities and relationships.
    warehouse.save();
    tag.save();
    product.save();
    stockitem.save();
    address.save();
    
    //Retrieve the entire model from the database.
    List<Warehouse> warehouses = Warehouse.find().findList();
    List<Tag> tags = Tag.find().findList();
    List<Product> products = Product.find().findList();
    List<StockItem> stockitems = StockItem.find().findList();
    List<Address> addresses = Address.find().findList();
    
    //Check that we've recovered all our entities
    assertEquals("Checking warehouse", warehouses.size(), 1);
    assertEquals("Checking tags", tags.size(), 1);
    assertEquals("Checking products", products.size(), 1);
    assertEquals("Checking stockitems", stockitems.size(), 1);
    assertEquals("Checking address", addresses.size(), 1);
    
    //Check that we've recovered all relationships
    assertEquals("Warehouse-StockItem", warehouses.get(0).getStockItems().get(0), stockitems.get(0));
    assertEquals("StockItem-Warehouse", stockitems.get(0).getWarehouse(), warehouses.get(0));
    assertEquals("Product-StockItem", products.get(0).getStockItems().get(0), stockitems.get(0));
    assertEquals("StockItem-Product", stockitems.get(0).getProduct(), products.get(0));
    assertEquals("Product-Tag", products.get(0).getTags().get(0), tags.get(0));
    assertEquals("Tag-Product", tags.get(0).getProducts().get(0), products.get(0));
    assertEquals("Warehouse-Address", warehouses.get(0).getAddress(), addresses.get(0));
    assertEquals("Address-Warehouse", addresses.get(0).getWarehouse(), warehouses.get(0));
    
    //Some code to illustrate model manipulation with ORM
    //Start in Java. Delete the tag from the (original) product instance.
    product.getTags().clear();
    //Persist the revised product instance.
    product.save();
    //FYI: this does not change our previously retrieved instance from the database.
    assertTrue("Previously retrieved product still has tag", !products.get(0).getTags().isEmpty());
    //But of course it does change a freshly retrieved product instance.
    assertTrue("Fresh Product has not tag", Product.find().findList().get(0).getTags().isEmpty());
    //Note: Freshly retrieved Tag does not point to any Product.
    assertTrue("Fresh Tag has no Products", Tag.find().findList().get(0).getProducts().isEmpty());
    //We can now delete this Tag from the database if we want.
    tag.delete();
    assertTrue("No more tags in database", Tag.find().findList().isEmpty());
  }
}
