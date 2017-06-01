import controller.Controller;
import dao.PointDao;
import dao.PointDaoImpl;
import jdbc.JdbcDao;
import model.Point;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;

import static spark.Spark.*;


public class Main {

    private static PointDao pointDao = new PointDaoImpl();
    private static Point nearestPoint;
    private static Controller controller = new Controller();

    public static void main(String[] args) {
        JdbcDao.setupUserAndPasswordFromFile("connection.properties");
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(9999);

        get("/home", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(controller.renderIndex(req, res));
        });

        post("/get-point/:x/:y", (Request req, Response res) -> {
            double xCoord = Double.parseDouble(req.params(":x"));
            double yCoord = Double.parseDouble(req.params(":y"));
            System.out.println(xCoord);
            nearestPoint = controller.calculateNearestPoint(xCoord, yCoord);
            System.out.println(xCoord + " --> " +  yCoord);
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

        get("/get-nearest", (Request req, Response res) -> {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name", nearestPoint.getName());
            jsonObj.put("distance", (int) pointDao.getNearestDistance());
            jsonObj.put("fclass", nearestPoint.getType());
            jsonObj.put("x", nearestPoint.getY());
            jsonObj.put("y", nearestPoint.getX());
            return jsonObj;
        });
    }
}
