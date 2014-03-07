
-- ===========================================================================
-- GET LIST OF AVAILABLE ATTRIBUTES
-- ===========================================================================

DELIMITER //
DROP PROCEDURE IF EXISTS get_attributes;
CREATE PROCEDURE get_attributes()
BEGIN
    SELECT * 
    FROM METADATA_ATTRIBUTE 
    ORDER BY TERM;
END //
DELIMITER ;

-- ===========================================================================
-- GET ATTRIBUTE BY CODE
-- ===========================================================================

DELIMITER //
DROP PROCEDURE IF EXISTS get_attribute_bycode;
CREATE PROCEDURE get_attribute_bycode(IN attrCode VARCHAR(30))
BEGIN
    SELECT *
    FROM METADATA_ATTRIBUTE
    WHERE CODE like attrCode
    ORDER BY TERM;
END //
DELIMITER ;

-- ===========================================================================
-- GET POSSIBLE VALUES FOR GIVEN ATTRIBUTE
-- ===========================================================================

DELIMITER //
DROP PROCEDURE IF EXISTS get_attribute_values;
CREATE PROCEDURE get_attribute_values(IN attrCode VARCHAR(30))
BEGIN
    SELECT DICTIONARY INTO @dict 
    FROM METADATA_ATTRIBUTE 
    WHERE CODE like attrCode 
    ORDER BY TERM;
    
    IF LENGTH(@dict) > 0
    THEN
        SET @sqltext = concat('SELECT * FROM ', @dict ,' ORDER BY TERM');
    
        PREPARE stmt1 FROM @sqltext;
        EXECUTE stmt1;
        DEALLOCATE PREPARE stmt1;
    END IF;
 
END //
DELIMITER ;

-- ===========================================================================
-- GET POSSIBLE VALUES FOR GIVEN ATTRIBUTE WITH FILTER ON VALUES
-- ===========================================================================

DELIMITER //
DROP PROCEDURE IF EXISTS get_attribute_values_filter;
CREATE PROCEDURE get_attribute_values_filter(IN attrCode VARCHAR(30), IN partialTerm VARCHAR(60))
BEGIN
    SELECT DICTIONARY INTO @dict
    FROM METADATA_ATTRIBUTE
    WHERE CODE like attrCode
    ORDER BY TERM;

    IF LENGTH(@dict) > 0
    THEN
        SET @sqltext = concat('SELECT * FROM ', @dict ,' WHERE TERM LIKE \'%', partialTerm ,'%\' ORDER BY TERM');

        PREPARE stmt1 FROM @sqltext;
        EXECUTE stmt1;
        DEALLOCATE PREPARE stmt1;
    END IF;

END //
DELIMITER ;


