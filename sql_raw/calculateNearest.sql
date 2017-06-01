-- INSERT INTO public.searched_point (geom_3857, geom_eov) VALUES(ST_SetSRID(ST_MakePoint
-- (2111176.242944597, 6026153.920894134), 3857));

-- SELECT ST_TRANSFORM(geom_3857, 23700) from searched_point where id = 2;
-- SELECT * from bp_poi_restaurant_3857 where id = 3;

-- SELECT ST_Distance ((SELECT ST_Transform(geom_3857, 23700) from searched_point where id = 2), (SELECT ST_Transform(geom, 23700) from bp_poi_restaurant_3857 where id = 3));
-- SELECT MAX(id) from searched_point;

-- megadja a legkisebb távolságot
-- SELECT MIN(ST_Distance((SELECT ST_Transform(geom_3857, 23700) from searched_point where id = (SELECT MAX(id) from searched_point)), ST_Transform(geom, 23700))) FROM bp_poi_restaurant_3857;


-- MIN(ST_Distance( (SELECT ST_Transform(geom_3857, 23700) from searched_point where 
-- id = (SELECT MAX(id) from searched_point) ), ST_Transform(geom, 23700)));

--MIN(ST_Distance( (SELECT ST_Transform(geom_3857, 23700) from searched_point where 
--id = (SELECT MAX(id) from searched_point) ), ST_Transform(geom, 23700))) FROM bp_poi_restaurant_3857;

-- final ->
SELECT * FROM bp_poi_restaurant_3857_point 
WHERE ST_Distance( (SELECT ST_Transform(geom_3857, 23700) from searched_point 
WHERE id = (SELECT MAX(id) from searched_point) ), ST_Transform(geom, 23700)) = 
(SELECT MIN(ST_Distance((SELECT ST_Transform(geom_3857, 23700) FROM searched_point 
WHERE id = (SELECT MAX(id) from searched_point)), ST_Transform(geom, 23700))) FROM bp_poi_restaurant_3857_point);