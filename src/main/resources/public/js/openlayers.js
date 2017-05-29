/**
 * Created by keli on 2017.05.29..
 */


$(document).ready(function(){
    var map, draw, source, poiWms;
    function init() {

        source = new ol.source.Vector({wrapX: false});

        // poiWms= new ol.layer.Tile({
        //     title: 'WMS',
        //     source: new ol.source.TileWMS({
        //         url: 'http://192.168.1.129:8080/geoserver/poi/wms',
        //         params: {'LAYERS': 'poi:poi_3857', 'TILED': true}
        //     })
        //
        // });

        map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Group({
                    'title': "Base maps",
                    layers: [
                        new ol.layer.Tile({
                            title: 'OpenStreetMap',
                            type: 'base',
                            source: new ol.source.OSM()
                        })
                    ]
                }),
                // new ol.layer.Group({
                //     title: "Overlays",
                //     layers: [
                //         new ol.layer.Tile({
                //             title: 'WMS',
                //             source: new ol.source.TileWMS({
                //                 url: 'http://gis.teir.hu/arcgis/services/rendezes/bp_agglo/MapServer/WMSServer',
                //                 params: {'LAYERS': '1', 'TILED': true}
                //             })
                //         }),
                //         poiWms,
                //
                //         new ol.layer.Vector({
                //             title: 'Point',
                //             source: source
                //         })
                //     ]
                // })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([19.109278, 47.496398]),
                zoom: 10
            })
        });

        draw = new ol.interaction.Draw({
            source: source,
            type: "Point"
        });

        draw.on("drawend", function(event) {

            var feature = event.feature;
            coords=feature.getGeometry().getCoordinates();

            sendData(coords[0], coords[1]);

        });

        draw.on("drawstart", function(event) {

            source.clear();

        });

        var layerSwitcher = new ol.control.LayerSwitcher({});

        map.addControl(layerSwitcher);
    }
    init();
});
