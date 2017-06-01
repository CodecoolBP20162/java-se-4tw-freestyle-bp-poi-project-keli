package model;

import dao.PointDaoImpl;

import java.sql.PreparedStatement;

/**
 * The {@link Point} class responsible for instantiate the points from db and web.
 *
 * @author      Kelemen Gergo
 * @version     1.8
 */
public class Point {

    private String name;
    private String type;
    private String osmCode;
    private double x;
    private double y;

    /**
     * Class constructor, which use the x and y coordinate to create instances.
     *
     * @param x coordinate
     * @param y coordinate
     */
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Class constructor, which use name, type and osmcode to create instances.
     *
     * @param name {@link String} the point's name
     * @param type {@link String} the point's type
     * @param osmCode {@link String} the point's osm code
     */
    public Point(String name, String type, String osmCode) {
        this.name = name;
        this.type = type;
        this.osmCode = osmCode;
    }

    /**
     * Class constructor, which use name, type, osmcode and x-y coordinates to create instances.
     *
     * @param name {@link String} the point's name
     * @param type {@link String} the point's type
     * @param osmCode {@link String} the point's osm code
     * @param x coordinate
     * @param y coordinate
     */
    public Point(String name, String type, String osmCode, double x, double y) {
        this.name = name;
        this.type = type;
        this.osmCode = osmCode;
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the {@link Point} instances.
     *
     * @return the point's name {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the {@link Point} instances.
     *
     * @return the point's type {@link String}
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for the {@link Point} instances.
     *
     * @return the point's osm code {@link String}
     */
    public String getOsmCode() {
        return osmCode;
    }

    /**
     * Getter for the {@link Point} instances.
     *
     * @return the point's x coordinate {@link Double}
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the {@link Point} instances.
     *
     * @return the point's y coordinate {@link Double}
     */
    public double getY() {
        return y;
    }
}
