-- https://postgis.net/docs/AddGeometryColumn.html

-- create table searched_point (id serial);
--SELECT AddGeometryColumn ('public',
--'searched_point','geom_3857',
-- 3857,'POINT',2);

SELECT AddGeometryColumn ('public',
'searched_point','geom_eov',
23700,'POINT',2);
