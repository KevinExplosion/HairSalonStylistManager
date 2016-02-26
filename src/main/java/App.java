import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import spark.Request.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String stylistLastName = request.queryParams("newStylistLastName");
      String stylistFirstName = request.queryParams("newStylistFirstName");
      Stylist myStylist = new Stylist(stylistLastName, stylistFirstName);
      myStylist.save();
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("stylist", Stylist.find(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/stylistClients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int stylistId = Integer.parseInt(request.params(":id"));
      String clientLastName = request.queryParams("newClientLastName");
      String clientFirstName = request.queryParams("newClientFirstName");
      Client newClient = new Client(clientLastName, clientFirstName, stylistId);
      newClient.save();
      model.put("stylist", Stylist.find(stylistId));
      model.put("template", "templates/stylistClients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
