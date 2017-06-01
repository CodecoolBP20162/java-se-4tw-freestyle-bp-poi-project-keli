package controller;

import dao.PointDao;
import dao.PointDaoImpl;
import jdbc.JdbcDao;
import model.Point;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * The {@link Controller} class responsible to control the app process.
 * <p>
 * This class render the index.html page and calculate the nearest point.
 *
 * @author      Kelemen Gergo
 * @version     1.8
 */
public class Controller {
    private PointDao pointDao = new PointDaoImpl();
    private Deque<Point> searchedPoints = new LinkedList<>();
    private Stack<Point> foundedPoints = new Stack();

    /**
     * Render to the index page.
     *
     * @param req {@link Request} object from the request
     * @param res {@link Response} object from the response
     * @return a new {@link ModelAndView} instance, with index viewName
     */
    public ModelAndView renderIndex(Request req, Response res) {
        return new ModelAndView(new HashMap(), "index");
    }

    /**
     * Calculate the nearest point and based on the x and y coordinates.
     *
     * @param xCoord {@link Double} the point's x coordinate
     * @param yCoord {@link Double} the point's y coordinate
     * @return with the nearest point ({@link Point} instance)
     */
    public Point calculateNearestPoint(double xCoord, double yCoord) {
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
