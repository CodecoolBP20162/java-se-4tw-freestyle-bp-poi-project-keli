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
    // add hozzá a pontot a searched_pointhoz
    // vedd ki a legmagasabb id-jút a searched_point-ból
    // számold ki a legközelebbi pontot

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
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getNearestPoint(Point inputPoint) {
        String query = " SELECT * FROM bp_poi_restaurant_3857_point " +
                "WHERE ST_Distance( (SELECT ST_Transform(geom_3857, 23700) from searched_point " +
                "WHERE id = (SELECT MAX(id) from searched_point) ), ST_Transform(geom, 23700)) = " +
                "(SELECT MIN(ST_Distance((SELECT ST_Transform(geom_3857, 23700) FROM searched_point " +
                "WHERE id = (SELECT MAX(id) from searched_point)), ST_Transform(geom, 23700))) FROM bp_poi_restaurant_3857_point);";


    }

    @Override
    public List<Point> getRestaurants(){
        List<Point> restaurants = new ArrayList<>();
        String query = "SELECT * FROM bp_poi_restaurant_3857_point";

        try {
            Connection connection = ConnectionGetter.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Point point = new Point(resultSet.getString("name"),
                        resultSet.getString("fclass"),
                        resultSet.getInt("code"));
                restaurants.add(point);
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        return null;
    }

}
