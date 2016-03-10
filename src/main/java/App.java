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

    get("/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String lastName = request.queryParams("stylistLastName");
      String firstName = request.queryParams("stylistFirstName");
      Stylist myStylist = new Stylist(lastName, firstName);
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
      String lastName = request.queryParams("newClientLastName");
      String firstName = request.queryParams("newClientFirstName");
      Client newClient = new Client(lastName, firstName, stylistId);
      newClient.save();
      model.put("stylist", Stylist.find(stylistId));
      model.put("template", "templates/stylistClients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/delete/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int stylistId = Integer.parseInt(request.params(":id"));

      String[] deletedClientIds =
      request.queryParamsValues("deleteClient");
      for (String clientId : deletedClientIds) {
        Client foundClient = Client.find(Integer.parseInt(clientId));
        foundClient.delete();
      }
      model.put("stylist", Stylist.find(stylistId));
      model.put("template", "templates/stylistClients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/delete/stylist/:id", (request, response) -> {
      HashMap model = new HashMap();
      int id = Integer.parseInt(request.queryParams("stylistId"));
      Stylist stylist = Stylist.find(id);
      stylist.delete();
      response.redirect("/");
      return null;
    });
  }
}
