package dao;

import jdbc.JdbcDao;
import model.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@link PointDaoImpl} class responsible to execute the queries with {@link PreparedStatement}.
 * <p>
 * This class implements the PointDao interface and extends from the JdbcDao.
 *
 * @author      Kelemen Gergo
 * @version     1.8
 */
public class PointDaoImpl extends JdbcDao implements PointDao {

    /**
     * Overrided method, from the PointDao interface.
     * Add the input argument ({@link Point}) as a new row to the searched_point table.
     *
     * @param point {@link Point} the added point instance
     */
    @Override
    public void addPoint(Point point) {
        String query = "INSERT INTO public.searched_point (geom_3857) VALUES(" +
                "ST_SetSRID(ST_MakePoint (?, ?), 3857))";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, point.getX());
            stmt.setDouble(2, point.getY());
            stmt.executeQuery();
            conn.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Overrided method, from the PointDao interface.
     * Select the nearest point from the db, based on meter, use the searched_point table last row for it.
     * Swallow exception, and log that.
     *
     * @return the nearest {@link Point} object
     */
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

    /**
     * Overrided method, from the PointDao interface.
     * Select the minimum distance between searched and founded point (based on meter).
     * Use the searched_point table last row for it.
     * Swallow exception, and log that.
     *
     * @return the minimum distance between the searched and founded point
     */
    @Override
    public double getNearestDistance(){
        String query = "SELECT MIN(ST_Distance((SELECT ST_Transform(geom_3857, 23700) FROM searched_point " +
                "WHERE id = (SELECT MAX(id) from searched_point)), ST_Transform(geom, 23700))) " +
                "FROM bp_poi_restaurant_3857_point;";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                double minDistance = resultSet.getDouble("min");
                conn.close();
                return minDistance;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
