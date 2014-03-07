                                                                      
SET foreign_key_checks = 0;

-- ================================================================
-- EXPERIMENT SETS                                
DROP TABLE IF EXISTS `EXPERIMENT_SET`;
CREATE TABLE `EXPERIMENT_SET` (
  `ID` INT(11) NOT NULL auto_increment,
  `NAME` VARCHAR(30) NOT NULL,
  `TIMESTAMP` INT(11) NOT NULL,
  `DESCRIPTION` VARCHAR(200),
  `OWNER` VARCHAR(30) NOT NULL,
  `IS_PUBLIC` BOOLEAN NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ================================================================
-- EXPERIMENT SET METADATA                            
DROP TABLE IF EXISTS `EXPERIMENT_SET_METADATA`;
CREATE TABLE `EXPERIMENT_SET_METADATA` (
  `ID` INT(11) NOT NULL auto_increment,
  `SET_ID` INT(11) NOT NULL,
  `ATTRIBUTE` VARCHAR(100),
  `VALUE` VARCHAR(2700),
  `UNIT` VARCHAR(30),
  PRIMARY KEY  (`ID`),
  INDEX INDEX_EXP_SET_METADATA (SET_ID),
  FOREIGN KEY (SET_ID) REFERENCES EXPERIMENT_SET(ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ================================================================

-- EXPERIMENT SET PRESENTATION                            
DROP TABLE IF EXISTS `EXPERIMENT_SET_PRESENTATION`;
CREATE TABLE `EXPERIMENT_SET_PRESENTATION` (
  `ID` INT(11) NOT NULL auto_increment,
  `SET_ID` INT(11) NOT NULL,
  `FILE_ID` INT(11) NOT NULL,
  PRIMARY KEY  (`ID`),
  INDEX INDEX_EXP_SET_PRESENTATION (SET_ID),
  FOREIGN KEY (SET_ID) REFERENCES EXPERIMENT_SET(ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ================================================================

SET foreign_key_checks = 1;


-- INDEX


       
