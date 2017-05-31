package dao;

import jdbc.ConnectionGetter;
import jdbc.JdbcDao;
import model.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by keli on 2017.05.31..
 */
public class PointDaoImpl extends JdbcDao implements PointDao {
    // add hozzá a pontot a searched_pointhoz
    // vedd ki a legmagasabb id-jút a searched_point-ból
    // számold ki a legközelebbi pontot

    @Override
    public void addPoint(Point point) {
        String query = "INSERT INTO public.searched_point (geom_3857) VALUES(" +
                "ST_SetSRID(ST_MakePoint (" + point.getX() + ", " + point.getY() + "), 3857))";

//        String query = "INSERT INTO public.searched_point (geom_3857, geom_eov) VALUES(" +
//                "ST_SetSRID(ST_MakePoint (?, ?), 3857))";

            executeQuery(query);
            System.out.println(query);
    }

    @Override
    public void getNearestPoint(Point inputPoint) {

    }

}
