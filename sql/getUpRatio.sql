CREATE DEFINER=`root`@`localhost` FUNCTION `getUpRatio`(priceFuture DOUBLE, nowPrice DOUBLE) RETURNS double
BEGIN

RETURN (priceFuture - nowPrice)/nowPrice;
END