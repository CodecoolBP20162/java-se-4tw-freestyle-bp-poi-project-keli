package dao;

import model.Point;

import java.util.List;


public interface PointDao {

    void addPoint(Point point);

    Point getNearestPoint();

    double getNearestDistance();

}
