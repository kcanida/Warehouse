import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.stop;
import java.util.HashMap;
import java.util.Map;
import models.Product;
import models.Tag;
import models.StockItem;
import models.Warehouse;
import models.Address;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

/**
 * 
 * @author Kellie Canida
 *
 */
public class ControllerTest {
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
  public void testProductController() {
    //Test GET /product on an empty database.
    Result result = callAction(controllers.routes.ref.Product.index());
    assertTrue("Empty products", contentAsString(result).contains("No products"));
  
    //Test GET /product on a database containing a single product.
    String productId = "Product-01";
    Product product = new Product(productId, "French Press", "Coffee Maker");
    product.save();
    result = callAction(controllers.routes.ref.Product.index());
    assertTrue("One product", contentAsString(result).contains(productId));
    
    //Test GET /product/Product-01
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertTrue("Product detail", contentAsString(result).contains(productId));
    
    //Test GET /product/BadProductId and make sure we get a 404
    result = callAction(controllers.routes.ref.Product.details("BadProductId"));
    assertEquals("Product detail (bad)", NOT_FOUND, status(result));
    
    //Test POST /products (with simulated, valid form data).
    Map<String, String> productData = new HashMap<String, String>();
    productData.put("productId", "Product-02");
    productData.put("name", "Baby Gaggia");
    productData.put("description", "Espresso machine");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(productData);
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create a new product", OK, status(result));
  
    //Test POST /products (with simulated, invalid form data).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create bad product fails", BAD_REQUEST, status(result));
    
    //Test DELETE /products/Product-01 (a valid ProductId).
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete current product OK", OK, status(result));
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertEquals("Deleted product gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete missing product also OK", OK, status(result));
  }

  @Test
  public void testTagController() {
    //Test GET /tags on an empty database.
    Result result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("Empty Tags", contentAsString(result).contains("No tags"));
    
    //Test GET /tags on a database containing a single Tag.
    String tagId = "Tag-01";
    Tag tag = new Tag(tagId);
    tag.save();
    result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("One tag", contentAsString(result).contains(tagId));
    
    //Test GET /tags/Tag-01
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertTrue("Tag detail", contentAsString(result).contains(tagId));
    
    //Test GET /tags/BadTagId and make sure we get a 404.
    result = callAction(controllers.routes.ref.Tag.details("BadTagId"));
    assertEquals("Tag details (bad)", NOT_FOUND, status(result));
    
    //Test POST /tags (with simulated, valid form data).
    Map<String, String> tagData = new HashMap<String, String>();
    tagData.put("tagId", "Tag-02");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create a new tag", OK, status(result));
    
    //Test POST /tags (with invalid tag: tags cannot be named "Tag").
    //Illustrates use of validate() method in models.Tag.
    request = fakeRequest();
    tagData.put("tagId", "Tag");
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create bad tag fails", BAD_REQUEST, status(result));
    
    //Test DELETE /tags/Tag-01(a valid TagId).
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete current tag OK", OK, status(result));
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertEquals("Deleted tag gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete missing tag also OK", OK, status(result));
  }
  
  @Test
  public void testStockItemController() {
    //Test GET /stockItem on an empty database.
    Result result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("Empty stockItems", contentAsString(result).contains("No stockItems"));
    
    //Test GET /stockItem on a database containing a single stockItem.
    String stockItemId = "StockItem-01";
    Warehouse warehouse = new Warehouse("Warehouse-01", "warehouse");
    Product product = new Product("Product-01", "French Press", "Coffee Maker");
    warehouse.save();
    product.save();
    StockItem stockItem = new StockItem(stockItemId, warehouse, product, 1);
    stockItem.save();
    result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("One stockItem", contentAsString(result).contains(stockItemId));
    
    //Test GET /stockItem/StockItem-01
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertTrue("stockItem detail", contentAsString(result).contains(stockItemId));
    
    //Test GET /stockItem/BadStockItemId and make sure we get a 404
    result = callAction(controllers.routes.ref.StockItem.details("BadStockItemId"));
    assertEquals("StockItem detail (bad)", NOT_FOUND, status(result));
    
    //Test POST /stockItems (with simulated, valid form data).
    Map<String, String> stockItemData = new HashMap<String, String>();
    stockItemData.put("stockItemId", "StockItem-02");
    stockItemData.put("product.productId", "Product-02");
    stockItemData.put("product.name", "coffee maker");
    stockItemData.put("warehouse.warehouseId", "Warehouse-02");
    stockItemData.put("warehouse.name", "warehouse");
    stockItemData.put("quantity", "1" );
    
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(stockItemData);
    result = callAction(controllers.routes.ref.StockItem.newStockItem(), request);
    assertEquals("Create a new stockItem", OK, status(result));
    
    //Test POST /stockItems (with simulated, invalid form data).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.StockItem.newStockItem(), request);
    assertEquals("Create bad stockItem fails", BAD_REQUEST, status(result));
    
    //Test DELETE /stockItems/StockItem-01(a valid StockItemId).
    result = callAction(controllers.routes.ref.StockItem.delete(stockItemId));
    assertEquals("Delete current stockItem OK", OK, status(result));
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertEquals("Deleted stockItem gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.StockItem.delete(stockItemId));
    assertEquals("Delete missing stockItem also OK", OK, status(result));
  }
  
  @Test public void testWarehouseController() {
    //Test GET /warehouse on an empty database.
    Result result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("Empty warehouses", contentAsString(result).contains("No warehouses"));
    
    //Test GET /warehouse on a database containing a single warehouse.
    String warehouseId = "Warehouse-01";
    Warehouse warehouse = new Warehouse(warehouseId, "warehouse");
    warehouse.save();
    result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("One warehouse", contentAsString(result).contains(warehouseId));
    
    //Test GET /warehouse/Warehouse-01
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertTrue("Warehouse detail", contentAsString(result).contains(warehouseId));
    
    //Test GET /warehouse/BadWarehouseId and make sure we get a 404
    result = callAction(controllers.routes.ref.Warehouse.details("BadWarehouseId"));
    assertEquals("Warehouse detail (bad)", NOT_FOUND, status(result));
    
    //Test POST /warehouses (with simulated, valid form data).
    Map<String, String> warehouseData = new HashMap<String, String>();
    warehouseData.put("warehouseId", "Warehouse-02");
    warehouseData.put("name", "Amazon warehouse");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(warehouseData);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create a new warehouse", OK, status(result));
    
    //Test POST /warehouses (with simulated, invalid form data).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create bad warehouse fails", BAD_REQUEST, status(result));
    
    //Test DELETE /warehouse/Warehouse-01(a valid WarehouseId).
    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
    assertEquals("Delete current warehouse OK", OK, status(result));
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertEquals("Deleted warehouse gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
    assertEquals("Delete missing warehouse also OK", OK, status(result));
  }
  
  @Test public void testAddress() {
    //Test GET /address on an empty database.
    Result result = callAction(controllers.routes.ref.Address.index());
    assertTrue("Empty addresses", contentAsString(result).contains("No addresses"));
    
    //Test GET /address on a database containing a single address.
    Warehouse warehouse = new Warehouse("Warehouse-01", "warehouse");
    String addressId = "Address-01";
    Address address = new Address(addressId, "street","city","state","zip", warehouse);
    address.save();
    result = callAction(controllers.routes.ref.Address.index());
    assertTrue("One address", contentAsString(result).contains(addressId));
    
    //Test GET /addresses/Address-01
    result = callAction(controllers.routes.ref.Address.details(addressId));
    assertTrue("Address detail", contentAsString(result).contains(addressId));
    
    //Test GET /addresses/BadAddressId and make sure we get a 404.
    result = callAction(controllers.routes.ref.Address.details("BadAddressId"));
    assertEquals("Address details (bad)", NOT_FOUND, status(result));
  }
}
