/**
 * Created by keli on 2017.05.29..
 */


$(document).ready(function(){
    var map, draw, source, poiWms, polygonWMS, baseMap;

    function init() {
        // create objects
        source = new ol.source.Vector({wrapX: false});

        poiWms = new ol.layer.Tile({
            title: 'Restaurant',
            source: new ol.source.TileWMS({
                url: 'http://localhost:8080/geoserver/wms',
                params: {'LAYERS': 'bp:bp_poi_restaurant_3857', 'TILED': true}
            })
        });

        polygonWMS = new ol.layer.Tile({
            title: 'Budapest border',
            source: new ol.source.TileWMS({
                url: 'http://localhost:8080/geoserver/wms',
                params: {'LAYERS': 'bp:bp_hatar_3857', 'TILED': true}
            })
        });

        baseMap = new ol.layer.Tile({
            title: 'OpenStreetMap',
            type: 'base',
            source: new ol.source.OSM()
        });

        // extent map object with poi and polygon layers
        map = new ol.Map({
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
            view: new ol.View({
                center: ol.proj.fromLonLat([19.110288, 47.496398]),
                zoom: 11
            })
        });

        draw = new ol.interaction.Draw({
            source: source,
            type: "Point"
        });

        draw.on("drawend", function(event) {
            var feature = event.feature;
            coords = feature.getGeometry().getCoordinates();
            sendData(coords[0], coords[1]);
        });

        draw.on("drawstart", function(event) {
            source.clear();
        });

        // layerSwitcher
        var layerSwitcher = new ol.control.LayerSwitcher({});
        map.addControl(layerSwitcher);
    }

    var drawPoint = function() {
        map.addInteraction(draw);
    };

    var updateModalWindow = function(data){
        $("#modal-body-to-insert").empty();
        $("#modal-header").text(data["name"]);
        var $type = $("<p>").text("Type: " + data["fclass"]);
        var $distance = $("<p>").text("Distance: " + data["distance"] + " meter");
        var $xCoord = $("<p>").text("X coord: " + data["x"]);
        var $yCoord = $("<p>").text("Y coord: " + data["y"]);
        $("#modal-body-to-insert").append($distance).append($type).append($xCoord).append($yCoord);
    };

    var getData = function(){
        $.ajax({
            url: "/get-nearest",
            method: "GET",
            dataType: "json",
            success: function(data){
                updateModalWindow(data);
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
            // data:
            //     {
            //         "id": "data",
            //     },
            //  {
            //     x: "sad",
            // y: y
            // type: document.getElementById("type").value},
            success: function (data) {
                console.log("elment");
                getData();
            },
            error: function(){
                alert("no-no");
            }
        });
    };

    // var cleanUp = function() {
    //     // document.getElementById("userName").value="";
    //     // document.getElementById("date").value="";
    //     // document.getElementById("note").value="";
    //     source.clear();
    //
    //     map.removeInteraction(draw);
    //     params=poiWms.getSource().getParams();
    //     params.t= new Date().getMilliseconds();
    //     poiWms.getSource().updateParams(params);
    // };

    init();

    $("#addPoint").click(function(){
        drawPoint();
    })
});
