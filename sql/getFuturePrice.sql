CREATE DEFINER=`root`@`localhost` FUNCTION `getFuturePrice`(eps2018 DOUBLE, epsMin DOUBLE, k DOUBLE, priceMin DOUBLE) RETURNS double
BEGIN
RETURN (eps2018-epsMin)/k + priceMin;
END