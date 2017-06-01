package controller;

import dao.PointDao;
import dao.PointDaoImpl;
import model.Point;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.sql.SQLException;
import java.util.*;

public class Controller {
    private PointDao pointDao = new PointDaoImpl();
    private Deque<Point> searchedPoints = new LinkedList<>();
    private Stack<Point> foundedPoints = new Stack();

    public ModelAndView renderIndex(Request req, Response res) throws SQLException{
        Map<String, List> params = new HashMap();
        return new ModelAndView(params, "index");
    }

    public Point calculateNearestPoint(double xCoord, double yCoord) throws SQLException {
        Point searchedPoint = new Point(xCoord, yCoord);
        searchedPoints.push(searchedPoint);     // last-in-first-out, store all searched points
        pointDao.addPoint(searchedPoint);

        Point foundedPoint = pointDao.getNearestPoint();
        foundedPoints.add(foundedPoint);        // store the founded points

        double minDistance = pointDao.getNearestDistance();
        System.out.println("Distance: " + minDistance + " rest: " + foundedPoint.getName());
        return foundedPoint;
    }
}
