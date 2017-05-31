import controller.Controller;
import dao.PointDao;
import dao.PointDaoImpl;
import model.Point;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;


public class Main {

    private static PointDao pointDao = new PointDaoImpl();

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(9999);

        get("/home", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(Controller.renderIndex(req, res));
        });

        post("/get-point/:x/:y", (Request req, Response res) -> {
            double xCoord = Double.parseDouble(req.params(":x"));
            double yCoord = Double.parseDouble(req.params(":y"));
            Controller.calculateNearestPoint(xCoord, yCoord);

            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });
    }
}
