package dao;

import model.Point;

import java.sql.SQLException;
import java.util.List;


public interface PointDao {

    void addPoint(Point point) ;

    Point getNearestPoint();

    double getNearestDistance();

}
