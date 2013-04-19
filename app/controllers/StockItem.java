package controllers;

import static play.data.Form.form;
import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class StockItem extends Controller{

  public static Result index() {
    List<models.StockItem> stockItems= models.StockItem.find().findList();
    return ok(stockItems.isEmpty() ? "No stockItems" : stockItems.toString());
  }
  
  public static Result details(String stockItemId) {
    models.StockItem stockItem = models.StockItem.find().where().eq("stockItemId",stockItemId).findUnique();
    return (stockItem==null) ? notFound("No stock item found") : ok(stockItem.toString());
  }

  public static Result newStockItem() {
    //Create a stockItem form and bind the request variables to it.
    Form<models.StockItem> stockItemForm = form(models.StockItem.class).bindFromRequest();
    //Validate the form values.
    if(stockItemForm.hasErrors()) {
      return badRequest("Stock Item ID, name, warehouse, product, and quantity are required");
    }
    //form is OK, so  make a stockItem and save it
    models.StockItem stockItem = stockItemForm.get();
    stockItem.save();
    return ok(stockItem.toString());
  }

  public static Result delete(String stockItemId) {
    models.StockItem stockItem = models.StockItem.find().where().eq("stockItemId", stockItemId).findUnique();
    if(stockItem != null) {
      stockItem.delete();
    }
    return ok();
  }
}
