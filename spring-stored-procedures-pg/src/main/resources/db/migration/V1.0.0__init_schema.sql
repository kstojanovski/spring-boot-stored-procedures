CREATE TABLE car
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    model VARCHAR(255),
    year  INTEGER,
    brand VARCHAR(255),
    CONSTRAINT pk_car PRIMARY KEY (id)
);

CREATE
OR REPLACE FUNCTION FIND_CARS_AFTER_YEAR(year_in INTEGER)
RETURNS SETOF car AS $$
BEGIN
RETURN QUERY SELECT * FROM car WHERE year >= year_in ORDER BY year;
END;
$$
LANGUAGE plpgsql;

CREATE PROCEDURE GET_TOTAL_CARS_BY_MODEL(IN model_in VARCHAR (50), OUT count_out INT)
    LANGUAGE plpgsql
AS $$
BEGIN
SELECT COUNT(*)
into count_out
from car
WHERE model = model_in;
END;
$$;
