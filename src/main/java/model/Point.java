package model;

/**
 * Created by keli on 2017.05.29..
 */
public class Point {

    private String name;
    private String type;
    private String osmCode;
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(String name, String type, String osmCode) {
        this.name = name;
        this.type = type;
        this.osmCode = osmCode;
    }

    public Point(String name, String type, String osmCode, double x, double y) {
        this.name = name;
        this.type = type;
        this.osmCode = osmCode;
        this.x = x;
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getOsmCode() {
        return osmCode;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
