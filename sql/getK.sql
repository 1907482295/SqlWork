CREATE DEFINER=`root`@`localhost` FUNCTION `getK`(epsMax DOUBLE, epsMin DOUBLE, priceMax DOUBLE, priceMin DOUBLE) RETURNS double
BEGIN
RETURN    (epsMax - epsMin) / (priceMax - priceMin);
END