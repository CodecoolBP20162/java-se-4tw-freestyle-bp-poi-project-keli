import controller.Controller;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;


/**
 * Created by keli on 2017.05.29..
 */
public class Main {

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(9999);

        get("/home", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(Controller.renderIndex(req, res));
        });

        post("/get-point/:x/:y/:name", (Request req, Response res) -> {
            double xCoord = Double.parseDouble(req.params(":x"));
            double yCoord = Double.parseDouble(req.params(":y"));
            String name = req.params(":name");
            System.out.println(yCoord);
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });
    }
}
