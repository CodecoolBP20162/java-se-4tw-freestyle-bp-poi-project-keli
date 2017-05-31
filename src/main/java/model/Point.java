package model;

/**
 * Created by keli on 2017.05.29..
 */
public class Point {

    private String name;
    private String type;
    private int osmCode;
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(String name, String type, int osmCode) {
        this.name = name;
        this.type = type;
        this.osmCode = osmCode;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getOsmCode() {
        return osmCode;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
