/**
 * Created by keli on 2017.05.29..
 */


$(document).ready(function(){
    var map, draw, source, poiWms, polygonWMS, baseMap, vectorSource;

    var initMap = function () {
        // create objects
        source = new ol.source.Vector({wrapX: false});

        poiWms = new ol.layer.Tile({        // restaurant poi layer
            title: 'Restaurant',
            source: new ol.source.TileWMS({
                url: 'http://localhost:8080/geoserver/wms',
                params: {'LAYERS': 'bp:bp_poi_restaurant_3857', 'TILED': true}
            })
        });

        polygonWMS = new ol.layer.Tile({    // Budapest border layer
            title: 'Budapest border',
            source: new ol.source.TileWMS({
                url: 'http://localhost:8080/geoserver/wms',
                params: {'LAYERS': 'bp:bp_hatar_3857', 'TILED': true}
            })
        });

        baseMap = new ol.layer.Tile({       // Base map layer (OSM)
            title: 'OpenStreetMap',
            type: 'base',
            source: new ol.source.OSM()
        });

        map = new ol.Map({                  // Add above object to map object
            target: 'map',
            layers: [
                new ol.layer.Group({
                    'title': "Base maps",
                    layers: [
                        baseMap
                    ]
                }),
                new ol.layer.Group({
                    'title' : 'Layers',
                    layers : [
                        poiWms,
                        polygonWMS
                    ]
                })
            ],
            view: new ol.View({             // Add view to map
                center: ol.proj.fromLonLat([19.110288, 47.496398]),
                zoom: 11
            })
        });

        // Draw interaction
        draw = new ol.interaction.Draw({
            source: source,
            type: "Point"
        });

        draw.on("drawend", function(event) {
            var feature = event.feature;
            var coords = feature.getGeometry().getCoordinates();
            sendData(coords[0], coords[1]);
        });

        draw.on("drawstart", function(event) {
            source.clear();
        });

        // create layerSwitcher and add as control
        var layerSwitcher = new ol.control.LayerSwitcher({});
        map.addControl(layerSwitcher);
    };

    var setMapView = function (xCoord, yCoord){
        map.getView().setCenter([xCoord, yCoord]);
        map.getView().setZoom(18);
    };

    var drawPoint = function() {
        map.addInteraction(draw);
    };

    var updateModalWindow = function(data){
        var $modalBody = $("#modal-body-to-insert");
        $modalBody.empty();
        $("#modal-header").text(data["name"]);
        var $type = $("<p>").text("Type: " + data["fclass"]);
        var $distance = $("<p>").text("Distance: " + data["distance"] + " meter");
        var $xCoord = $("<p>").text("X coord: " + data["x"]);
        var $yCoord = $("<p>").text("Y coord: " + data["y"]);
        $modalBody.append($distance).append($type).append($xCoord).append($yCoord);
    };

    // ajax
    var getData = function(){
        $.ajax({
            url: "/get-nearest",
            method: "GET",
            dataType: "json",
            success: function(data){
                updateModalWindow(data);
                setMapView(data["y"], data["x"]);
                // map.getView().setCenter(parseInt(data["x"], parseInt(data["y"])))
            },
            error: function(){
                alert("baj van");
            }

        })
    };

    var sendData = function(x,y) {
        $.ajax({
            url:"/get-point/" + x + "/" + y,
            method: "POST",
            dataType: "json",
            success: function (data) {
                console.log("elment");
                getData();
            },
            error: function(){
                alert("no-no");
            }
        });
    };

    initMap();

    $("#addPoint").click(function(){
        drawPoint();
    })
});
