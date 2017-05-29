import controller.Controller;
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
    }
}
