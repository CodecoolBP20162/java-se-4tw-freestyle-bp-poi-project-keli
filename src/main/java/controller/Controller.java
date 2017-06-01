package controller;

import dao.PointDao;
import dao.PointDaoImpl;
import model.Point;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private static PointDao pointDao = new PointDaoImpl();

    public static ModelAndView renderIndex(Request req, Response res) throws SQLException{
        Map<String, List> params = new HashMap();
        return new ModelAndView(params, "index");
    }

    public static Point calculateNearestPoint(double xCoord, double yCoord){
        Point searchedPoint = new Point(xCoord, yCoord);
        pointDao.addPoint(searchedPoint);
        Point foundedPoint = pointDao.getNearestPoint();
        double minDistance = pointDao.getNearestDistance();
        System.out.println("Distance: " + minDistance + " rest: " + foundedPoint.getName());
        return foundedPoint;
    }
}
