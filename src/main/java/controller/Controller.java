package controller;

import jdbc.ConnectionGetter;
import model.Point;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by keli on 2017.05.29..
 */
public class Controller {

    public static ModelAndView renderIndex(Request req, Response res) throws SQLException{
        Map<String, List> params = new HashMap();
        params.put("points", getAllPoints());
        return new ModelAndView(params, "index");
    }


    public static List<Point> getAllPoints() throws SQLException {
        List<Point> pointList = new ArrayList<>();
        String query = "SELECT * from bp_poi_eov WHERE fclass='restaurant';";
        Connection connection = ConnectionGetter.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();

        while(resultSet.next()){
            Point point = new Point(resultSet.getString("name"),
                    resultSet.getString("fclass"),
                    resultSet.getInt("code"));
            pointList.add(point);
        }

        return pointList;
    }
}
