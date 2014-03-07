

-- ===================================================================
-- GET EXPERIMENT SET
DELIMITER //
DROP PROCEDURE IF EXISTS get_experiment_set;
CREATE PROCEDURE get_experiment_set(IN experimentSetId INT(11), IN username VARCHAR(30))
BEGIN
    SELECT *
    FROM EXPERIMENT_SET
    WHERE ((IS_PUBLIC is TRUE) OR (OWNER LIKE username)) AND ID=experimentSetId;
END //
DELIMITER ;

-- ===================================================================
-- GET EXPERIMENT SETS OWNED BY USER
DELIMITER //
DROP PROCEDURE IF EXISTS get_experiment_sets_owned;
CREATE PROCEDURE get_experiment_sets_owned(IN username VARCHAR(30))
BEGIN
    SELECT *
    FROM EXPERIMENT_SET
    WHERE OWNER LIKE username;
END //
DELIMITER ;

-- ===================================================================
-- GET EXPERIMENT SETS READABLE BY USER
DELIMITER //
DROP PROCEDURE IF EXISTS get_experiment_sets_read;
CREATE PROCEDURE get_experiment_sets_read(IN username VARCHAR(30))
BEGIN
    SELECT *
    FROM EXPERIMENT_SET
    WHERE ((IS_PUBLIC is TRUE) OR (OWNER LIKE username));
END //
DELIMITER ;

-- ===================================================================
-- CREATE EXPERIMENT
DELIMITER //
DROP PROCEDURE IF EXISTS create_experiment_set;
CREATE PROCEDURE create_experiment_set(
                              IN username VARCHAR(30),
                              IN expName VARCHAR(30),
                              IN expDesc VARCHAR(200),
                              IN isPublic BOOLEAN,
                              OUT expId INT(11)
                          )
BEGIN
    SELECT UNIX_TIMESTAMP() INTO @ts;

    INSERT INTO EXPERIMENT_SET
    (OWNER,NAME,DESCRIPTION,IS_PUBLIC,TIMESTAMP)
    VALUES
    (username,expName,expDesc,isPublic,@ts);
    
    SELECT LAST_INSERT_ID() INTO expId;
END //
DELIMITER ;

-- ===================================================================
-- UPDATE EXPERIMENT SET
DELIMITER //
DROP PROCEDURE IF EXISTS update_experiment_set;
CREATE PROCEDURE update_experiment_set(
                              IN expId INT(11), 
                              IN username VARCHAR(30),
                              IN expName VARCHAR(30),
                              IN expDesc VARCHAR(200),
                              IN isPublic BOOLEAN
                          )
BEGIN
    UPDATE EXPERIMENT_SET
    SET NAME=expName, 
        DESCRIPTION=expDesc,
        IS_PUBLIC=isPublic
    WHERE ID=expId and OWNER=username;
END //
DELIMITER ;

-- ===================================================================
-- DELETE EXPERIMENT SET
DELIMITER //
DROP PROCEDURE IF EXISTS delete_experiment_set;
CREATE PROCEDURE delete_experiment_set(IN expId INT(11), IN username VARCHAR(30))
BEGIN
    DELETE FROM EXPERIMENT_SET
    WHERE ID=expId and OWNER=username;
END //
DELIMITER ;

-- ===================================================================
-- GET EXPERIMENT SET METADATA
DELIMITER //
DROP PROCEDURE IF EXISTS get_experiment_set_metadata;
CREATE PROCEDURE get_experiment_set_metadata(IN experimentSetId INT(11), IN username VARCHAR(30))
BEGIN
    SELECT M.ATTRIBUTE, M.VALUE, M.UNIT
    FROM EXPERIMENT_SET S, EXPERIMENT_SET_METADATA M
    WHERE M.SET_ID=experimentSetId and S.ID=experimentSetId
          and ((S.IS_PUBLIC is TRUE) or (S.OWNER like username));
END //
DELIMITER ;

-- ===================================================================
-- DELETE EXPERIMENT SET AVU
DELIMITER //
DROP PROCEDURE IF EXISTS delete_experiment_set_avu;
CREATE PROCEDURE delete_experiment_set_avu(IN expId INT(11), IN username VARCHAR(30), IN avuAttribute VARCHAR(50), IN avuValue VARCHAR(50), IN avuUnit VARCHAR(30))
BEGIN
    DELETE FROM EXPERIMENT_SET_METADATA
    WHERE SET_ID=expId and ATTRIBUTE=avuAttribute and VALUE=avuValue and UNIT=avuUnit;
END //
DELIMITER ;

-- ===================================================================
-- DELETE EXPERIMENT SET METADATA (ALL AVU)
DELIMITER //
DROP PROCEDURE IF EXISTS clear_experiment_set_metadata;
CREATE PROCEDURE clear_experiment_set_metadata(IN expId INT(11), IN username VARCHAR(50))
BEGIN
    DELETE FROM EXPERIMENT_SET_METADATA
    WHERE SET_ID=expId;
END //
DELIMITER ;

-- ===================================================================
-- ADD AVU TO EXPERIMENT SET
DELIMITER //
DROP PROCEDURE IF EXISTS add_experiment_set_avu;
CREATE PROCEDURE add_experiment_set_avu(IN expId INT(11), IN username VARCHAR(30), IN avuAttribute VARCHAR(50), IN avuValue VARCHAR(50), IN avuUnit VARCHAR(30))
BEGIN
    INSERT INTO EXPERIMENT_SET_METADATA
    (SET_ID, ATTRIBUTE, VALUE, UNIT)
    VALUES 
    (expId, avuAttribute, avuValue, avuUnit);
END //
DELIMITER ;

-- ===================================================================
-- GET EXPERIMENT SET PRESENTATION DATA FILES
DELIMITER //
DROP PROCEDURE IF EXISTS get_experiment_set_analysis_data;
CREATE PROCEDURE get_experiment_set_analysis_data(IN experimentSetId INT(11), IN username VARCHAR(30))
BEGIN
    SELECT P.FILE_ID
    FROM EXPERIMENT_SET S, EXPERIMENT_SET_PRESENTATION P
    WHERE P.SET_ID=experimentSetId and S.ID=experimentSetId
          and ((S.IS_PUBLIC is TRUE) or (S.OWNER like username));
END //
DELIMITER ;


-- ===================================================================
-- DELETE EXPERIMENT SET PRESENTATION DATA FILES
DELIMITER //
DROP PROCEDURE IF EXISTS remove_experiment_set_analysis_data;
CREATE PROCEDURE remove_experiment_set_analysis_data(IN expId INT(11), IN username VARCHAR(30), IN fileId INT(11))
BEGIN
    DELETE FROM EXPERIMENT_SET_PRESENTATION
    WHERE SET_ID=expId and FILE_ID=fileId;
END //
DELIMITER ;


-- ===================================================================
-- ADD PRESENTATION DATA FILE TO EXPERIMENT SET
DELIMITER //
DROP PROCEDURE IF EXISTS add_experiment_set_analysis_data;
CREATE PROCEDURE add_experiment_set_analysis_data(IN expId INT(11), IN username VARCHAR(30), IN fileId INT(11))
BEGIN
    INSERT INTO EXPERIMENT_SET_PRESENTATION
    (SET_ID, FILE_ID)
    VALUES 
    (expId, fileId);
END //
DELIMITER ;


