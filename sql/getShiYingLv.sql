CREATE DEFINER=`root`@`localhost` FUNCTION `getShiYingLv`(eps double, price double) RETURNS int(11)
BEGIN

RETURN price/eps;
END