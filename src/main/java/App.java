import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("accountingdepartment", AccountingDepartment.get())
      model.put("marketingdepartment", MarketingDepartment.get())
      model.put("developingdepartment", DevelopingDepartment.get())
      model.put("template", "templates/administrator.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());







  }
}
