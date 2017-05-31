package dao;

import jdbc.ConnectionGetter;
import jdbc.JdbcDao;
import model.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keli on 2017.05.31..
 */
public class PointDaoImpl extends JdbcDao implements PointDao {

    @Override
    public void addPoint(Point point) {
//        String query = "INSERT INTO public.searched_point (geom_3857) VALUES(" +
//                "ST_SetSRID(ST_MakePoint (" + point.getX() + ", " + point.getY() + "), 3857))";
//            executeQuery(query);
//            System.out.println(query);

        String query = "INSERT INTO public.searched_point (geom_3857) VALUES(" +
                "ST_SetSRID(ST_MakePoint (?, ?), 3857))";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, point.getX());
            stmt.setDouble(2, point.getY());
            stmt.executeQuery();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Point getNearestPoint() {
        Point nearestPoint;
        String query = " SELECT * FROM bp_poi_restaurant_3857_point " +
                "WHERE ST_Distance( (SELECT ST_Transform(geom_3857, 23700) from searched_point " +
                "WHERE id = (SELECT MAX(id) from searched_point) ), ST_Transform(geom, 23700)) = " +
                "(SELECT MIN(ST_Distance((SELECT ST_Transform(geom_3857, 23700) FROM searched_point " +
                "WHERE id = (SELECT MAX(id) from searched_point)), ST_Transform(geom, 23700))) FROM bp_poi_restaurant_3857_point);";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                nearestPoint = new Point(resultSet.getString("name"),
                        resultSet.getString("fclass"),
                        resultSet.getString("osm_id"),
                        resultSet.getDouble("xcoord"),
                        resultSet.getDouble("ycoord"));
            conn.close();
            return nearestPoint;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
