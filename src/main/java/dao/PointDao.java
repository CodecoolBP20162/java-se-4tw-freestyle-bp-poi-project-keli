package dao;

import model.Point;


public interface PointDao {

    void addPoint(Point point);

    void getNearestPoint(Point inputPoint);



}
