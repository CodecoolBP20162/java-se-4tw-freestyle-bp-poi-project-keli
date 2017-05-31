package dao;

import model.Point;

import java.util.List;


public interface PointDao {

    void addPoint(Point point);

    void getNearestPoint(Point inputPoint);

}
