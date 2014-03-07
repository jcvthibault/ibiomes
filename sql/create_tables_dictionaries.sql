                                                                      
SET foreign_key_checks = 0;

-- =================================================================
-- MOLECULAR SYSTEM
-- =================================================================

-- MOLECULE TYPES                                
DROP TABLE IF EXISTS `MOLECULE_TYPE`;
CREATE TABLE `MOLECULE_TYPE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- MOLECULE TYPES                             
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('Protein'          , 'Protein'          , 'Protein');
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('Nucleic acid'     , 'Nucleic acid'     , 'Nucleic acid');
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('DNA'              , 'DNA'              , 'DNA');
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('RNA'              , 'RNA'              , 'RNA');
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('Lipid'            , 'Lipid'            , 'Lipid');
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('Carbohydrate'     , 'Carbohydrate'     , 'Carbohydrate');
insert into MOLECULE_TYPE (CODE, TERM, DEFINITION) VALUES ('Chemical compound', 'Chemical compound', 'Chemical compound');

-- =================================================================
-- CITATION AND STRUCTURE DATABASES
-- =================================================================

-- CITATION DATABASES                                
DROP TABLE IF EXISTS `LITERATURE_DB`;
CREATE TABLE `LITERATURE_DB` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into LITERATURE_DB (CODE, TERM, DEFINITION) VALUES ('PUBMED'  , 'PubMed' , 'PubMed');


-- STRUCTURE DB                                
DROP TABLE IF EXISTS `STRUCTURE_DB`;
CREATE TABLE `STRUCTURE_DB` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into STRUCTURE_DB (CODE, TERM, DEFINITION) VALUES ('PUBCHEM'  , 'PubChem' , 'PubChem');
insert into STRUCTURE_DB (CODE, TERM, DEFINITION) VALUES ('PDB'      , 'PDB'     , 'Protein Data Bank');
insert into STRUCTURE_DB (CODE, TERM, DEFINITION) VALUES ('CSD'      , 'CSD'     , 'Cambridge Structural Database');

-- =================================================================
-- QUANTUM MECHANICS METHOD DICTIONARIES
-- =================================================================

-- CALCULATION TYPES                                
DROP TABLE IF EXISTS `CALCULATION`;
CREATE TABLE `CALCULATION` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_SCAN'        , 'Scan'                        , 'Scan');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_FREQUENCY'   , 'Frequency calculations'      , 'Frequency calculations');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_NMR'         , 'NMR calculations'            , 'Nuclear Magnetic Resonance calculations');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_IRC'         , 'IRC calculations'            , 'IRC (Intrasic Reaction Coordinates) calculations');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_GEOMETRY_OPT', 'Geometry optimization'       , 'Geometry optimization');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_STABILITY'   , 'Stability calculations'      , 'Stability calculations');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_SPE'         , 'SPE calculations'            , 'Singe Point Energy calculations');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_SADDLE_POINT', 'Saddle point'                , 'Saddle point');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_RAMAN'       , 'Raman intensity calculations', 'Raman intensity calculations');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_HESSIAN'     , 'Hessian'                     , 'Hessian calculation');
insert into CALCULATION (CODE, TERM, DEFINITION) VALUES ('QM_ENERGY_GRAD' , 'Energy gradient calculations', 'Energy gradient calculations');

-- =================================================================
-- BASIS SETS                                
DROP TABLE IF EXISTS `QM_BASIS_SET`;
CREATE TABLE `QM_BASIS_SET` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  `FAMILY` VARCHAR(50),
  `BS_TYPE` VARCHAR(50),
  `IS_POLARIZED` VARCHAR(50),
  `HAS_DIFFUSE_FN` BOOLEAN,
  `HAS_DFT_DATA_FIT` BOOLEAN,
  `HAS_EFFECTIVE_POTENTIAL_DATA` BOOLEAN,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('STO-3G'        , 'STO-3G'               , 'STO-3G minimal basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('STO-3G_P'      , 'STO-3G*'              , 'Polarized STO-3G minimal basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('STO-4G'        , 'STO-4G'               , 'STO-4G minimal basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('STO-6G'        , 'STO-6G'               , 'STO-6G minimal basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('3_21G'         , '3-21G'                , '3-21G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('3_21G_P'       , '3-21G*'               , 'Polarized 3-21G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('3_21G_D'       , '3-21+G'               , '3-21G with diffuse function (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('3_21G_PD'      , '3-21+G*'              , '3-21G with polarization and diffuse functions (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_21G'         , '4-21G'                , '3-21G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_21G_P'       , '4-21G*'               , 'Polarized 4-21G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_21G_D'       , '4-21+G'               , '4-21G with diffuse function (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_21G_PD'      , '4-21+G*'              , '4-21G with polarization and diffuse functions (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_31G'         , '4-31G'                , '4-31G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_31G_P'       , '4-31G*'               , 'Polarized 4-31G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_31G_D'       , '4-31+G'               , '4-31G with diffuse function (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('4_31G_PD'      , '4-31+G*'              , '4-31G with polarization and diffuse functions (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_21G'         , '6-21G'                , '6-21G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_21G_P'       , '6-21G*'               , 'Polarized 6-21G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_21G_D'       , '6-21+G'               , '6-21G with diffuse function (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_21G_PD'      , '6-21+G*'              , '6-21G with polarization and diffuse functions (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_31G'         , '6-31G'                , '6-31G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_31G_P'       , '6-31G*'               , 'Polarized 6-31G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_31G_D'       , '6-31+G'               , '6-31G with diffuse function (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_31G_PD'      , '6-31+G*'              , '6-31G with polarization and diffuse functions (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_311G'        , '6-311G'               , '6-311G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_311G_P'      , '6-311G*'              , 'Polarized 6-311G (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_311G_D'      , '6-311+G'              , '6-311G with diffuse function (Pople basis set)');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('6_311G_PD'     , '6-311+G*'             , '6-311G with polarization and diffuse functions (Pople basis set)');

-- LANL

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL08'           , 'LANL08'                     , 'Uncontracted basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL08_F'         , 'LANL08(f)'                  , 'Uncontracted basis set + f polarization');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL08_P'         , 'LANL08+'                    , 'Uncontracted basis set + diffuse d function');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2_6S4P4D2F'   , 'LANL2-[6s4p4d2f]'           , 'Lanl2-[6s4p4d2f]');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2DZ_ECP'      , 'LANL2DZ ECP'                , 'DZ Double Zeta Basis Set designed for an ECP');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2DZ_1D1F'     , 'LANL2DZ+1d1f'               , 'LANL2DZ+1d1f ');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2DZ_2S2P2D2F' , 'LANL2DZ+2s2p2d2f'           , 'Lanl2DZ+2s2p2d2f');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2DZ_DP_ECP'   , 'LANL2DZdp ECP'              , 'DZP Double Zeta + Polarization + Diffuse ECP');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2DZ_DP_ECP_P' , 'LANL2DZdp ECP Polarization' , '1P Polarization Functions');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2TZ'          , 'LANL2TZ'                    , 'LANL2TZ');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2TZ_F'        , 'LANL2TZ(f)'                 , 'TZ triple zeta basis set designed for an ECP + f polarization');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('LANL2TZ_P'        , 'LANL2TZ+'                   , 'TZ triple zeta basis set designed for an ECP + diffuse d function');

-- CC

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PVDZ'      , 'cc-pVDZ'             , 'cc-pVDZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PVTZ'      , 'cc-pVTZ'             , 'cc-pVTZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PVQZ'      , 'cc-pVQZ'             , 'cc-pVQZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV5Z'      , 'cc-pV5Z'             , 'cc-pV5Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV6Z'      , 'cc-pV6Z'             , 'cc-pV6Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV8Z'      , 'cc-pV8Z'             , 'cc-pV8Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV9Z'      , 'cc-pV9Z'             , 'cc-pV9Z correlation-consistent polarized basis set');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PVDZ_RI'      , 'cc-pVDZ-RI'       , 'cc-pVDZ-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PVTZ_RI'      , 'cc-pVTZ-RI'       , 'cc-pVTZ-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PVQZ_RI'      , 'cc-pVQZ-RI'       , 'cc-pVQZ-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV5Z_RI'      , 'cc-pV5Z-RI'       , 'cc-pV5Z-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV6Z_RI'      , 'cc-pV6Z-RI'       , 'cc-pV6Z-RI correlation-consistent polarized basis set');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PCVDZ'      , 'cc-pCVDZ'             , 'cc-pCVDZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PCVTZ'      , 'cc-pCVTZ'             , 'cc-pCVTZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PCVQZ'      , 'cc-pCVQZ'             , 'cc-pCVQZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PCV5Z'      , 'cc-pCV5Z'             , 'cc-pCV5Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PCV6Z'      , 'cc-pCV6Z'             , 'cc-pCV6Z correlation-consistent polarized basis set');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV_DZ_PLUS_D_Z'   , 'cc-pV(D+d)Z'    , 'cc-pV(D+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV_TZ_PLUS_D_Z'   , 'cc-pV(T+d)Z'    , 'cc-pV(T+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV_QZ_PLUS_D_Z'   , 'cc-pV(Q+d)Z'    , 'cc-pV(Q+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV_5Z_PLUS_D_Z'   , 'cc-pV(5+d)Z'    , 'cc-pV(5+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('CC_PV_6Z_PLUS_D_Z'   , 'cc-pV(6+d)Z'    , 'cc-pV(6+d)Z correlation-consistent polarized basis set');

-- aug-CC

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PVDZ'      , 'aug-cc-pVDZ'             , 'aug-cc-pVDZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PVTZ'      , 'aug-cc-pVTZ'             , 'aug-cc-pVTZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PVQZ'      , 'aug-cc-pVQZ'             , 'aug-cc-pVQZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV5Z'      , 'aug-cc-pV5Z'             , 'aug-cc-pV5Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV6Z'      , 'aug-cc-pV6Z'             , 'aug-cc-pV6Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV8Z'      , 'aug-cc-pV8Z'             , 'aug-cc-pV8Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV9Z'      , 'aug-cc-pV9Z'             , 'aug-cc-pV9Z correlation-consistent polarized basis set');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PVDZ_RI'      , 'aug-cc-pVDZ-RI'       , 'aug-cc-pVDZ-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PVTZ_RI'      , 'aug-cc-pVTZ-RI'       , 'aug-cc-pVTZ-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PVQZ_RI'      , 'aug-cc-pVQZ-RI'       , 'aug-cc-pVQZ-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV5Z_RI'      , 'aug-cc-pV5Z-RI'       , 'aug-cc-pV5Z-RI correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV6Z_RI'      , 'aug-cc-pV6Z-RI'       , 'aug-cc-pV6Z-RI correlation-consistent polarized basis set');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PCVDZ'      , 'aug-cc-pCVDZ'             , 'aug-cc-pCVDZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PCVTZ'      , 'aug-cc-pCVTZ'             , 'aug-cc-pCVTZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PCVQZ'      , 'aug-cc-pCVQZ'             , 'aug-cc-pCVQZ correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PCV5Z'      , 'aug-cc-pCV5Z'             , 'aug-cc-pCV5Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PCV6Z'      , 'aug-cc-pCV6Z'             , 'aug-cc-pCV6Z correlation-consistent polarized basis set');

insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV_DZ_PLUS_D_Z'   , 'aug-cc-pV(D+d)Z'    , 'aug-cc-pV(D+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV_TZ_PLUS_D_Z'   , 'aug-cc-pV(T+d)Z'    , 'aug-cc-pV(T+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV_QZ_PLUS_D_Z'   , 'aug-cc-pV(Q+d)Z'    , 'aug-cc-pV(Q+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV_5Z_PLUS_D_Z'   , 'aug-cc-pV(5+d)Z'    , 'aug-cc-pV(5+d)Z correlation-consistent polarized basis set');
insert into QM_BASIS_SET (CODE, TERM, DEFINITION) VALUES ('AUG_CC_PV_6Z_PLUS_D_Z'   , 'aug-cc-pV(6+d)Z'    , 'aug-cc-pV(6+d)Z correlation-consistent polarized basis set');




-- =================================================================
-- BASIS SET TYPES                                
DROP TABLE IF EXISTS `QM_BASIS_SET_TYPE`;
CREATE TABLE `QM_BASIS_SET_TYPE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('POPLE'         , 'Pople basis set'                 , 'Pople basis set family (e.g. 3-21G, 6-31G)');
insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('MINIMAL'       , 'Minimal basis set'               , 'Minimal basis set');
insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('CORR_CONSIST'  , 'Correlation-consistent basis set', 'Correlation-consistent basis set (e.g. cc-pVDZ, cc-pVTZ, aug-cc-pVDZ).');
insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('SPLIT_VALENCE' , 'Split-valence basis set'         , 'Other split-valence basis sets (e.g. SVP, DZV, TZV) ');
insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('REAL_SPACE'    , 'Real-space basis set'            , 'Real-space basis set.');
insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('PLANE_WAVE'    , 'Plane-wave basis set'            , 'Plane-wave basis set.');
insert into QM_BASIS_SET_TYPE (CODE, TERM, DEFINITION) VALUES ('OTHER'         , 'Other'                           , 'Other types of basis sets.');

-- =================================================================
-- QM METHODS FAMILIES (e.g. Hartree-Fock, Moeller-Plesset, Configuration Interaction)                              
DROP TABLE IF EXISTS `QM_LEVEL_OF_THEORY`;
CREATE TABLE `QM_LEVEL_OF_THEORY` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('HARTREE_FOCK'    , 'Hartree-Fock'                    , 'Hartree-Fock');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('CI'              , 'Configuration Interaction'       , 'Configuration Interaction');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('MRCI'            , 'Multi-reference CI'              , 'Multi-reference Configuration Interaction');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('QCI'             , 'Quadratic CI'                    , 'Quadratic Configuration Interaction');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('CC'              , 'Coupled-Cluster'                 , 'Coupled-cluster method');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('MP'              , 'Moeller-Plesset'                 , 'Moeller-Plesset perturbation theory');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('DFT'             , 'DFT'                             , 'Density Functional Theory');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('Q_MC'            , 'Quantum Monte Carlo'             , 'Quantum Monte Carlo');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('PATH_INTEGRAL'   , 'Path Integral formulation'       , 'Path Integral formulation');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('DMRG'            , 'DMRG'                            , 'Density Matrix renormalization group');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('SCATTERING'      , 'Scattering theory'               , 'Scattering theory');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('GREENS'          , 'Greens function'                 , 'Greens funtion');
insert into QM_LEVEL_OF_THEORY (CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL'  , 'Semi-empirical'                  , 'Semi-empirical method');

-- QUANTUM MD METHODS
DROP TABLE IF EXISTS `QMD_METHOD`;
CREATE TABLE `QMD_METHOD` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into QMD_METHOD (CODE, TERM, DEFINITION) VALUES ('BORN_OPPENHEIMER_MD' , 'Born-Oppenheimer MD' , 'Born-Oppenheimer MD (BOMD)');
insert into QMD_METHOD (CODE, TERM, DEFINITION) VALUES ('CAR_PARINELLO_MD'    , 'Car-Parrinello MD'   , 'Car-Parrinello MD (CPMD)');
insert into QMD_METHOD (CODE, TERM, DEFINITION) VALUES ('PATH_INTEGRAL_MD'    , 'Path integral MD'    , 'Path integral MD');

-- =================================================================
-- LEVELS OF THEORY (e.g. SCF, MP2, CCSD)                               
DROP TABLE IF EXISTS `QM_METHOD_NAME`;
CREATE TABLE `QM_METHOD_NAME` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  `FAMILY` VARCHAR(50),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('HF'       ,'HARTREE_FOCK'  , 'Hartree-Fock'        , 'Hartree-Fock (any implementation)');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('SCF'      ,'HARTREE_FOCK'  , 'SCF'                 , 'Self-consistent field');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MC_SCF'   ,'HARTREE_FOCK'  , 'MC-SCF'              , 'Multiconfiguration SCF (MC-SCF)');

insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CISD'     ,'CI'      , 'CISD'                      , 'Configuration interaction singles and doubles');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CISDT'    ,'CI'      , 'CISDT'                     , 'Configuration interaction singles, doubles, and triples');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CISDTQ'   ,'CI'      , 'CISDTQ'                    , 'Configuration interaction singles, doubles, triples, and quadruples');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('QCISD'    ,'QCI'     , 'QCISD'                     , 'Quadratic configuration interaction singles & doubles');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MRCISD'   ,'MRCI'    , 'MR-CI(SD)'                 , 'Configuration Interaction');

insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CCD'       ,'CC'      , 'CCD'                      , 'Coupled-cluster doubles');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CCSD'      ,'CC'      , 'CCSD'                     , 'Coupled-cluster singles and doubles');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('LCCD'      ,'CC'      , 'LCCD'                     , 'Linearized coupled-cluster doubles');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('LCCSD'     ,'CC'      , 'LCCSD'                    , 'Linearized coupled-cluster singles and doubles');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CCSDT'     ,'CC'      , 'CCSDT'                    , 'Coupled-cluster singles, doubles, and triples');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CCSDTQ'    ,'CC'      , 'CCSDTQ'                   , 'Coupled-cluster singles, doubles, triples, and quadruples');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CCSD_T'    ,'CC'      , 'CCSD(T)'                  , 'CCSD and perturbative connected triples');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('CR_CCSD_T' ,'CC'      , 'CR-CCSD(T)'               , 'Completely renormalized CCSD(T) method');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MRCCSD'    ,'CC'      , 'MR-CCSD'                  , 'Coupled-cluster method');

insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MP2'       ,'MP'      , 'MP2'                      , 'Moeller-Plesset perturbation theory (second-order)');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MP3'       ,'MP'      , 'MP3'                      , 'Moeller-Plesset perturbation theory (third-order)');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MP4'       ,'MP'      , 'MP4'                      , 'Moeller-Plesset perturbation theory (fourth-order)');
insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('MP5'       ,'MP'      , 'MP5'                      , 'Moeller-Plesset perturbation theory (fifth-order)');

insert into QM_METHOD_NAME (CODE, FAMILY, TERM, DEFINITION) VALUES ('DFT_ANY'    ,'DFT'     , 'DFT'       , 'Density Functional Theory (any implementation)');


insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'PPP','PPP','Pariser-Parr-Pople');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'CNDO/2','CNDO/2','CNDO/2');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'INDO','INDO','INDO semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'NDDO','NDDO','NDDO semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'MINDO','MINDO','MINDO semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'MNDO','MNDO','MNDO semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'AM1','AM1','AM1 semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'PM3','PM3','PM3 semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'PM6','PM6','PM6 semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'RM1','RM1','RM1 semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'SAM1','SAM1','SAM1 semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'Sparkle/AM1','Sparkle/AM1','Sparkle/AM1 semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'ZINDO','ZINDO','ZINDO semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'SINDO','SINDO','SINDO semi-empirical method');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'PM3MM','PM3MM','PM3MM semi-empirical method (variation of PM3)');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('SEMI_EMPIRICAL', 'PDDG','PDDG','PDDG semi-empirical method (variation of PM6)');

insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'APBE','APBE','DFT with APBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B1LYP','B1LYP','DFT with B1LYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B1B95','B1B95','DFT with B1B95 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B2PLYP','B2PLYP','DFT with B2PLYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B3LYP','B3LYP','DFT with B3LYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B3LYP*','B3LYP*','DFT with B3LYP* exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B3LYP-D','B3LYP-D','DFT with B3LYP-D exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B3P86','B3P86','DFT with B3P86 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B3PW91','B3PW91','DFT with B3PW91 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B98','B98','DFT with B98 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B97-D','B97-D','DFT with B97-D pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B971','B971','DFT with B971 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'B972','B972','DFT with B972 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'BHandH','BHandH','DFT with BHandH exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'BHandHLYP','BHandHLYP','DFT with BHandHLYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'BLYP','BLYP','DFT with BLYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'BMK','BMK','DFT with BMK exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'BP86','BP86','DFT with BP86 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'CAM-B3LYP','CAM-B3LYP','DFT with CAM-B3LYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'DCD-BLYP','DCD-BLYP','DFT with DCD-BLYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'DSD-PBEBP86','DSD-PBEBP86','DFT with DSD-PBEBP86 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HCTH','HCTH','DFT with HCTH pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HCTH93','HCTH93','DFT with HCTH93 pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HCTH147','HCTH147','DFT with HCTH147 pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HCTH407','HCTH407','DFT with HCTH407 pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HFB','HFB','DFT with HFB exchange functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HFS','HFS','DFT with HFS exchange functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HSE','HSE','DFT with HSE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HSE2PBE','HSE2PBE','DFT with HSE2PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HSEh1PBE','HSEh1PBE','DFT with HSEh1PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'HSEhPBE','HSEhPBE','DFT with HSEhPBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'KT1','KT1','DFT with KT1 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'LB94','LB94','DFT with LB94 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'LC-wPBE','LC-wPBE','DFT with LC-wPBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'LC-PBE','LC-PBE','DFT with LC-PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'LDA','LDA','DFT with LDA exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'M05','M05','DFT with M05 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'M05-2X','M05-2X','DFT with M05-2X exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'M06','M06','DFT with M06 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'M06HF','M06HF','DFT with M06HF exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'M06-L','M06-L','DFT with M06L pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'M06-2X','M06-2X','DFT with M062X exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'mPW1k','mPW1k','DFT with mPW1k exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'mPW1LYP','mPW1LYP','DFT with mPW1LYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'mPW1PBE','mPW1PBE','DFT with mPW1PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'mPW1PW91','mPW1PW91','DFT with mPW1PW91 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'mPW3PBE','mPW3PBE','DFT with mPW3PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'O3LYP','O3LYP','DFT with O3LYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'OLYP','OLYP','DFT with OLYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'optB88-vdW','optB88-vdW','DFT with optB88-vdW exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'PBE','PBE','DFT with PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'PBE1PBE','PBE1PBE','DFT with PBE1PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'PBEh1PBE','PBEh1PBE','DFT with PBEh1PBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'PW91','PW91','DFT with PW91 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'PW6B95','PW6B95','DFT with PW6B95 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'PWPB95-D3','PWPB95-D3','DFT with PWPB95-D3 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'revPBE','revPBE','DFT with revPBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'revTPSS','revTPSS','DFT with revTPSS exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'revTPSS-D','revTPSS-D','DFT with revTPSS-D exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'RPA','RPA','DFT with RPA exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'RPBE','RPBE','DFT with RPBE exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'S12g','S12g','DFT with S12g exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'S12h','S12h','DFT with S12h exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'SAOP','SAOP','DFT with SAOP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'SSB-D','SSB-D','DFT with SSB-D exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'tau-HCTH','tau-HCTH','DFT with tau-HCTH pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'tHCTH','tHCTH','DFT with tHCTH pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'tHCTHhyb','tHCTHhyb','DFT with tHCTHhyb exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'TPSSh','TPSSh','DFT with TPSSh exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'VSXC','VSXC','DFT with VSXC pure functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'wB97','wB97','DFT with wB97 exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'wB97X','wB97X','DFT with wB97X exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'wB97XD','wB97XD','DFT with wB97XD exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'X3LYP','X3LYP','DFT with X3LYP exchange-correlation functional');
insert into QM_METHOD_NAME (FAMILY, CODE, TERM, DEFINITION) VALUES ('DFT', 'XAlpha','XAlpha','DFT with XAlpha exchange functional');


-- =================================================================
-- EXCHANGE CORRELATION FUNCTIONAL                  
DROP TABLE IF EXISTS `QM_EXCHANGE_CORRELATION_FN`;
CREATE TABLE `QM_EXCHANGE_CORRELATION_FN` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into QM_EXCHANGE_CORRELATION_FN (CODE, TERM, DEFINITION) VALUES ('LOCAL_DENSITY_APPROX' , 'Local density approximation'  , 'Local density approximation');
insert into QM_EXCHANGE_CORRELATION_FN (CODE, TERM, DEFINITION) VALUES ('DENSITY_GRAD_CORRECT' , 'Density gradient correction'  , 'Density gradient correction');
insert into QM_EXCHANGE_CORRELATION_FN (CODE, TERM, DEFINITION) VALUES ('ADIABETIC_CONNECT'    , 'Adiabatic connection'         , 'Adiabatic connection method');
insert into QM_EXCHANGE_CORRELATION_FN (CODE, TERM, DEFINITION) VALUES ('OTHER'                , 'Other'                        , 'Other exchange-correlation functional');

-- =================================================================
-- PSEUDO-POTENTIAL SCHEME                                
DROP TABLE IF EXISTS `QM_PSEUDO_POTENTIAL_SCHEME`;
CREATE TABLE `QM_PSEUDO_POTENTIAL_SCHEME` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into QM_PSEUDO_POTENTIAL_SCHEME (CODE, TERM, DEFINITION) VALUES ('HAMMAN_SCHLUTER'    , 'Hamman-Schluter-Chiang'   , 'Hamman-Schluter-Chiang pseudo-potential scheme');
insert into QM_PSEUDO_POTENTIAL_SCHEME (CODE, TERM, DEFINITION) VALUES ('BACHELET_HAMMAN'    , 'Bachelet-Hamman-Schluter' , 'Bachelet-Hamman-Schluter pseudo-potential scheme');
insert into QM_PSEUDO_POTENTIAL_SCHEME (CODE, TERM, DEFINITION) VALUES ('KERKER'             , 'Kerker'                   , 'Kerker pseudo-potential scheme');
insert into QM_PSEUDO_POTENTIAL_SCHEME (CODE, TERM, DEFINITION) VALUES ('TROUILLER_MARTINS'  , 'Trouiller-Martins'        , 'Trouiller-Martins pseudo-potential scheme');

-- =================================================================
-- MOLECULAR DYNAMICS METHOD DICTIONARIES
-- =================================================================

-- FORCE FIELDS                                
DROP TABLE IF EXISTS `MD_FORCE_FIELD`;
CREATE TABLE `MD_FORCE_FIELD` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  `FAMILY` VARCHAR(50),
  `FF_TYPE` VARCHAR(50),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_94'      , 'AMBER FF94'             , 'AMBER FF94 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_96'      , 'AMBER FF96'             , 'AMBER FF96 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_99'      , 'AMBER FF99'             , 'AMBER FF99 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_99SB'    , 'AMBER FF99SB'           , 'AMBER FF99SB force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_99BSC0'  , 'AMBER FF99bsc0'         , 'AMBER FF99bsc0 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_02'      , 'AMBER FF02'             , 'AMBER FF02 additive (non-polarizable) force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_03'      , 'AMBER FF03'             , 'AMBER FF03 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_10'      , 'AMBER FF10'             , 'AMBER FF10 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_12SB'    , 'AMBER FF12SB'           , 'AMBER FF12SB force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_GAFF'    , 'AMBER GAFF'             , 'General Amber Force Field for small molecules');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_GLYCAM06'  , 'AMBER GLYCAM 06'      , 'AMBER force field for carbohydrates and lipids');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_GLYCAM06EP', 'AMBER GLYCAM 06 EP'   , 'AMBER force field for carbohydrates and lipids using lone pairs (extra points)');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('AMBER_AMOEBA'  , 'AMBER AMOEBA'           , 'AMBER AMOEBA polarizable force field');

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('CHARMM_19'     , 'CHARMM 19'              , 'CHARMM 19 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('CHARMM_22'     , 'CHARMM 22'              , 'CHARMM 22 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('CHARMM_27'     , 'CHARMM 27'              , 'CHARMM 27 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('CHARMM_36'     , 'CHARMM 36'              , 'CHARMM 36 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('CHARMM_CGENFF' , 'CHARMM CGenFF'          , 'CHARMM CGenFF force field for drug-like molecules');

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('OPLS'          , 'OPLS'                   , 'OPLS (Optimized Potential for Liquid Simulations) force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('OPLS_AA'       , 'OPLS-AA'                , 'OPLS (Optimized Potential for Liquid Simulations) force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('OPLS_UA'       , 'OPLS-UA'                , 'OPLS (Optimized Potential for Liquid Simulations) force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('OPLS_2001'     , 'OPLS-2001'              , 'OPLS (Optimized Potential for Liquid Simulations) force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('OPLS_2005'     , 'OPLS-2005'              , 'OPLS (Optimized Potential for Liquid Simulations) force field');

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS'        , 'GROMOS'               , 'GROMOS force field (any version)');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_43A1'   , 'GROMOS 43A1'          , 'GROMOS 43a1 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_43B1'   , 'GROMOS 43B1'          , 'GROMOS 43b1 (vacuum) force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_43A2'   , 'GROMOS 43A2'          , 'GROMOS 43a2 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_45A3'   , 'GROMOS 45A3'          , 'GROMOS 45A3 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_45A4'   , 'GROMOS 45A4'          , 'GROMOS 45A4 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_53A5'   , 'GROMOS 53A5'          , 'GROMOS 53A5 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_53A6'   , 'GROMOS 53A6'          , 'GROMOS 53A6 force field');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('GROMOS_54A7'   , 'GROMOS 54A7'          , 'GROMOS 54A7 force field');

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('TIP3P'         , 'TIP3P'                  , 'TIP3P 3-site water model: each atom gets assigned a point charge.');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('TIP4P'         , 'TIP4P'                  , 'TIP4P 4-site water model: a negative charge is placed on a dummy atom.');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('TIP5P'         , 'TIP5P'                  , 'TIP5P 5-site water model: negative charge placed on 2 dummy atoms.');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('TIP6P'         , 'TIP6P'                  , 'TIP6P 6-site water model: combines TIP4P and TIP5P models.');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('SPC'           , 'SPC'                    , 'SPC water model (Single Point Charge)');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('SPC_FLEXIBLE'  , 'SPC flexible'           , 'Flexible SPC water model (Single Point Charge)');
insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('SPCE'          , 'SPC/E'                  , 'SPC water model with average polarization correction to the potential energy function');

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('REAX_FF'       , 'ReaxFF'                 , 'Reactive force field');

insert into MD_FORCE_FIELD (CODE, TERM, DEFINITION) VALUES ('NON_STD'       , 'Non-standard'           , 'Non-standard force field');

-- =================================================================
-- FORCE FIELD TYPE (classical, polarizable, reactive, etc)                               
DROP TABLE IF EXISTS `MD_FORCE_FIELD_TYPE`;
CREATE TABLE `MD_FORCE_FIELD_TYPE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into MD_FORCE_FIELD_TYPE (CODE, TERM, DEFINITION) VALUES ('CLASSICAL'    , 'Classical'    , 'Classical force-field');
insert into MD_FORCE_FIELD_TYPE (CODE, TERM, DEFINITION) VALUES ('POLARIZABLE'  , 'Polarizable'  , 'Polarizable force-field');
insert into MD_FORCE_FIELD_TYPE (CODE, TERM, DEFINITION) VALUES ('REACTIVE'     , 'Reactive'     , 'Reactive force-field');
insert into MD_FORCE_FIELD_TYPE (CODE, TERM, DEFINITION) VALUES ('OTHER'        , 'Other'        , 'Other types of force fields');

-- =================================================================
-- MM INTEGRATORS                                
DROP TABLE IF EXISTS `MD_INTEGRATOR`;
CREATE TABLE `MD_INTEGRATOR` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('EULER'           , 'Euler'           , 'Euler intrgator');
insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('RUNGE_KUTTA'     , 'Runge-Kutta'     , 'Runge-Kutta integrator');
insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('ADAMS_BASHFORTH' , 'Adams-Bashforth' , 'Adams-Bashforth-Moulton integrator');
insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('VERLET'          , 'Verlet'          , 'Verlet integrator');
insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('VERLET_VELOCITY' , 'Verlet velocity' , 'Verlet velocity');
insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('LEAPFROG'        , 'Leap-frog'       , 'Leap-frog');
insert into MD_INTEGRATOR (CODE, TERM, DEFINITION) VALUES ('BEEMAN'          , 'Beeman'          , 'Beeman integrator');

-- =================================================================
-- MD CONSTRAINT ALGORITHMS (e.g. LINCS, RATTLE)                                
DROP TABLE IF EXISTS `MD_CONSTRAINT_ALGORITHM`;
CREATE TABLE `MD_CONSTRAINT_ALGORITHM` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into MD_CONSTRAINT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('SHAKE',  'SHAKE',        'SHAKE');
insert into MD_CONSTRAINT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('RATTLE', 'RATTLE',       'RATTLE');
insert into MD_CONSTRAINT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('LINCS',  'LINCS',        'LINCS');

-- =================================================================
-- MD RESTRAINT TYPES                           
DROP TABLE IF EXISTS `RESTRAINT_TYPE`;
CREATE TABLE `RESTRAINT_TYPE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into RESTRAINT_TYPE (CODE, TERM, DEFINITION) VALUES ('RESTRAINT_POSITION' , 'Position'  , 'Position restraint');
insert into RESTRAINT_TYPE (CODE, TERM, DEFINITION) VALUES ('RESTRAINT_DIHEDRAL' , 'Dihedral'  , 'Dihedral restraint');
insert into RESTRAINT_TYPE (CODE, TERM, DEFINITION) VALUES ('RESTRAINT_DISTANCE' , 'Distance'  , 'Distance restraint');
insert into RESTRAINT_TYPE (CODE, TERM, DEFINITION) VALUES ('RESTRAINT_PLANEBASE', 'Plane-base', 'Plane-base restraint');

-- =================================================================
-- MD ELECTROSTATICS INTERACTION MODELING (e.g. cutoff, classic ewald, PME)                                
DROP TABLE IF EXISTS `MD_ELECTROSTATICS`;
CREATE TABLE `MD_ELECTROSTATICS` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('PME'           , 'PME'                       ,'Particle-mesh Ewald (PME)');
insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('CUTOFF'        , 'Cutoff'                    ,'cutoff');
insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('EWALD'         , 'Classic Ewald'             ,'classic Ewald');
insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('PPME'          , 'PPME'                      ,'particle-particle particle-mesh (PPME)');
insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('REAC_FIELD'    , 'Reaction field'            ,'reacfield');
insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('GEN_REAC_FIELD', 'Generalized reaction field','generalized reaction field');
insert into MD_ELECTROSTATICS (CODE, TERM, DEFINITION) VALUES ('SHIFT'         , 'Shift'                     ,'Shift (Gromacs)');

-- =================================================================
-- MD UNIT SHAPE                                
DROP TABLE IF EXISTS `MD_UNIT_SHAPE`;
CREATE TABLE `MD_UNIT_SHAPE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into MD_UNIT_SHAPE (CODE, TERM, DEFINITION) VALUES ('CAP'              , 'Cap'            , 'Cap');
insert into MD_UNIT_SHAPE (CODE, TERM, DEFINITION) VALUES ('CUBIC'            , 'Cubic'          , 'Cubic');
insert into MD_UNIT_SHAPE (CODE, TERM, DEFINITION) VALUES ('OCTAHEDRON'       , 'Octahedron'     , 'Octahedron');
insert into MD_UNIT_SHAPE (CODE, TERM, DEFINITION) VALUES ('ORTHORHOMBIC'     , 'Orthorhombic'   , 'Orthorhombic');
insert into MD_UNIT_SHAPE (CODE, TERM, DEFINITION) VALUES ('SHELL'            , 'Shell'          , 'Shell');

-- =================================================================
-- ENSEMBLES                                
DROP TABLE IF EXISTS `ENSEMBLE`;
CREATE TABLE `ENSEMBLE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into ENSEMBLE (CODE, TERM, DEFINITION) VALUES ('NVE'    , 'NVE' , 'Microcanonical ensemble (NVE)');
insert into ENSEMBLE (CODE, TERM, DEFINITION) VALUES ('NVT'    , 'NVT' , 'Canonical ensemble (NVT)');
insert into ENSEMBLE (CODE, TERM, DEFINITION) VALUES ('NPT'    , 'NPT' , 'Isothermal-isobaric ensemble (NPT)');
insert into ENSEMBLE (CODE, TERM, DEFINITION) VALUES ('GENERAL', 'Generalized' , 'Generalized ensemble');

-- =================================================================
-- THERMOSTAT_ALGORITHM                                
DROP TABLE IF EXISTS `THERMOSTAT_ALGORITHM`;
CREATE TABLE `THERMOSTAT_ALGORITHM` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into THERMOSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('THERM_ALG_BERENDSEN'     , 'Berendsen'    ,'Berendsen');
insert into THERMOSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('THERM_ALG_LANGEVIN'      , 'Langevin'     ,'Langevin');
insert into THERMOSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('THERM_ALG_NOSE'          , 'Nose'         ,'Nose');
insert into THERMOSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('THERM_ALG_NOSE_HOOVER'   , 'Nose-Hoover'  ,'Nose-Hoover');
insert into THERMOSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('THERM_ALG_NOSE_POINCARRE', 'Nose-Poincare','Nose-Poincare');
insert into THERMOSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('THERM_ALG_V_RESCALE'     , 'V-rescale'    ,'V-rescale');

-- =================================================================
-- BAROSTAT_ALGORITHM                                
DROP TABLE IF EXISTS `BAROSTAT_ALGORITHM`;
CREATE TABLE `BAROSTAT_ALGORITHM` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into BAROSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('BARO_ALG_BERENDSEN', 'Berendsen'        ,'Berendsen');
insert into BAROSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('BARO_ALG_ANDERSEN' , 'Andersen'         ,'Andersen');
insert into BAROSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('BARO_ALG_HOOVER'   , 'Hoover'           ,'Hoover');
insert into BAROSTAT_ALGORITHM (CODE, TERM, DEFINITION) VALUES ('BARO_ALG_PARINELLO', 'Parrinello-Rahman','Parrinello-Rahman');

-- =================================================================
-- MD SAMPLING_METHODS                                
DROP TABLE IF EXISTS `MD_SAMPLING_METHOD`;
CREATE TABLE `MD_SAMPLING_METHOD` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('ACCELERATED_MD'        , 'Accelerated MD'                          , 'Accelerated MD');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('ADAPTIVE_FORCE_BIAS'   , 'Adaptive Force Bias'                     , 'Adaptive Force Bias');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('CAFES'                 , 'Canonical adiabatic free energy sampling', 'Canonical adiabatic free energy sampling');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('FORWARD_FLUX'          , 'Forward flux simulation'                 , 'Forward flux simulation');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('LOCALLY_ENHANCED'      , 'Locally enhanced sampling'               , 'Locally enhanced sampling');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('MARKOV_CHAIN'          , 'Markov chain model'                      , 'Markov chain model');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('MAX_FLUX'              , 'Max-flux approach'                       , 'Max-flux approach');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('METADYNAMICS'          , 'Metadynamics'                            , 'Metadynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('MILESTONING'           , 'Milestoning'                             , 'Milestoning');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('MBAR'                  , 'Multi-state Bennett Acceptance Ratio'    , 'Multi-state Bennett Acceptance Ratio (MBAR)');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('NEURAL_NETWORKS'       , 'Neural networks'                         , 'Neural networks');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('NUDGE_ELASTIC_BAND'    , 'Nudged elastic band'                     , 'Nudged elastic band');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('REPSWA'                , 'Reference potential spatial warping'     , 'Reference potential spatial warping algorithm');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('REPLICA_EXCHANGE'      , 'Replica-Exchange'                        , 'Replica-Exchange molecular dynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('REPLICA_EXCHANGE_MULTI', 'Multi-dimension Replica-Exchange'        , 'Multi-dimension replica-exchange molecular dynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('REPLICA_EXCHANGE_TEMP' , 'Temperature Replica-Exchange'            , 'Temperature replica-exchange molecular dynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('REPLICA_EXCHANGE_H'    , 'Hamiltonian Replica-Exchange'            , 'Hamiltonian replica-exchange molecular dynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('STEERED_DYNAMICS'      , 'Steered dynamics'                        , 'Steered dynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('STRING_METHOD'         , 'String method'                           , 'String method');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('SWARM_OF_TRAJECTORIES' , 'Swarm-of-trajectories'                   , 'Swarm-of-trajectories');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('TARGETED_MD'           , 'Targeted MD'                             , 'Targeted molecular dynamics');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('TRANSITION_PATH'       , 'Transition path sampling'                , 'Transition path sampling');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('TRANSITION_STATE_ENS'  , 'Transition state ensembles'              , 'Transition state ensembles');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('TRANSITION_INTERFACE'  , 'Transition interface sampling'           , 'Transition interface sampling');
insert into MD_SAMPLING_METHOD (CODE, TERM, DEFINITION) VALUES ('UMBRELLA_SAMPLING'     , 'Umbrella sampling'                       , 'Umbrella sampling');

-- =================================================================
-- ALL METHOD DICTIONARIES
-- =================================================================

-- METHODS
DROP TABLE IF EXISTS `METHOD`;
CREATE TABLE `METHOD` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_MD'                , 'Molecular dynamics'               , 'Molecular dynamics (MD)');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_REMD'              , 'Replica-exchange MD'              , 'Replica-exchange molecular dynamics (REMD)');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_QM'                , 'Quantum mechanics'                , 'Quantum mechanics (QM)');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_QMMM'              , 'QM/MM'                            , 'Hybrid Quantum/Molecular mechanics');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_LANGEVIN_DYNAMICS' , 'Langevin dynamics'                , 'Langevin dynamics');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_COARSE_GRAIN'      , 'Coarse-grain dynamics'            , 'Coarse-grain dynamics');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_QUANTUM_MD'        , 'Quantum MD'                       , 'Quantum molecular dynamics.');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_SEMI_EMPIRICAL'    , 'Semi-empirical method'            , 'Semi-empirical method');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_SEMI_CLASSICAL'    , 'Semi-classical method'            , 'Semi-classical method');
insert into METHOD (CODE, TERM, DEFINITION) VALUES ('METHOD_DOCKING'           , 'Docking'                          , 'Molecular docking');

-- =================================================================
-- BOUNDARY CONDITIONS (e.g. periodic bounday conditions)                                
DROP TABLE IF EXISTS `BOUNDARY_COND`;
CREATE TABLE `BOUNDARY_COND` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into BOUNDARY_COND (CODE, TERM, DEFINITION) VALUES ('PERIODIC'       , 'Periodic'     , 'Periodic boundary conditions');
insert into BOUNDARY_COND (CODE, TERM, DEFINITION) VALUES ('NON_PERIODIC'   , 'Non-periodic' , 'Non periodic boundary conditions');

-- =================================================================
-- SOLVENT 
DROP TABLE IF EXISTS `SOLVENT_MODEL`;
CREATE TABLE `SOLVENT_MODEL` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('SOLVENT_IN_VACUO', 'In vacuo', 'In vacuo (no water)');
insert into SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('SOLVENT_EXPLICIT', 'Explicit', 'Explicit water');
insert into SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('SOLVENT_IMPLICIT', 'Implicit', 'Implicit water');

-- =================================================================
-- IMPLICIT SOLVENT 
DROP TABLE IF EXISTS `IMPLICIT_SOLVENT_MODEL`;
CREATE TABLE `IMPLICIT_SOLVENT_MODEL` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into IMPLICIT_SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('IMPLICIT_MODEL_GB_HCT', 'GB HCT'  , 'GB HCT');
insert into IMPLICIT_SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('IMPLICIT_MODEL_GB_OBC', 'GB OBC'  , 'GB OBC');
insert into IMPLICIT_SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('IMPLICIT_MODEL_GB_N'  , 'GBn'     , 'GBn');
insert into IMPLICIT_SOLVENT_MODEL (CODE, TERM, DEFINITION) VALUES ('IMPLICIT_MODEL_PB'    , 'PB'      , 'PB');

-- =================================================================
-- HARDWARE / SOFTWARE DICTIONARIES
-- =================================================================

-- OPERATING SYSTEM                                
DROP TABLE IF EXISTS `OPERATING_SYSTEM`;
CREATE TABLE `OPERATING_SYSTEM` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  `FAMILY` VARCHAR(20),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('LINUX'         , 'Linux'         , 'Linux');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('HPUX'          , 'HPUX'          , 'HPUX (Hewlett-Packard)');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('IRIX'          , 'IRIX'          , 'IRIX (Silicon Graphics, sgi)');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('SCO_UNIX'      , 'SCO Unix'      , 'SCO Unix');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('UNICOS'        , 'UNICOS/mk'     , 'UNICOS/mk (Cray)');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('FREEBSD'       , 'FreeBSD'       , 'FreeBSD');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('AIX'           , 'AIX'           , 'AIX (IBM)');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('WIN_NT'        , 'Windows NT'    , 'Microsoft Windows NT');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('WIN_2000'      , 'Windows 2000'  , 'Microsoft Windows 2000');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('WIN_2003'      , 'Windows 2003'  , 'Microsoft Windows 2003');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('WIN_XP'        , 'Windows XP'    , 'Microsoft Windows XP');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('WIN_VISTA'     , 'Windows Vista' , 'Microsoft Windows Vista');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('WIN_7'         , 'Windows 7'     , 'Microsoft Windows 7');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('MSDOS'         , 'MS-DOS'        , 'Microsoft DOS');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('VAX_VMS'       , 'VAX/VMS'       , 'VAX/VMS (Digital, Compaq, Hewlett-Packard)');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('MAC'           , 'Macintosh'     , 'Macintosh (Apple Computers)');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('NOVELL_NETWARE', 'Novell Netware', 'Novell Netware');
insert into OPERATING_SYSTEM (CODE, TERM, DEFINITION) VALUES ('SOLARIS'       , 'Solaris'       , 'Solaris (Sun Microsystems)');

-- =================================================================
-- CPU ARCHITECTURE                                
DROP TABLE IF EXISTS `CPU_ARCHITECTURE`;
CREATE TABLE `CPU_ARCHITECTURE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into CPU_ARCHITECTURE (CODE, TERM, DEFINITION) VALUES ('INTELX86'    , 'Intel (x86)'           , 'Intel (x86)');
insert into CPU_ARCHITECTURE (CODE, TERM, DEFINITION) VALUES ('POWERPC'     , 'PowerPC'               , 'PowerPC (except Scalable POWERparallel)');
insert into CPU_ARCHITECTURE (CODE, TERM, DEFINITION) VALUES ('SPARC'       , 'Sparc (Sun)'           , 'Sparc (Sun)');
insert into CPU_ARCHITECTURE (CODE, TERM, DEFINITION) VALUES ('SCAL_POW_PAR', 'Scalable POWERparallel', 'Scalable POWERparallel');
insert into CPU_ARCHITECTURE (CODE, TERM, DEFINITION) VALUES ('T3E'         , 'T3E (Cray)'            , 'T3E (Cray)');

-- =================================================================
-- HARDWARE MAKE                                
DROP TABLE IF EXISTS `HW_MAKE`;
CREATE TABLE `HW_MAKE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('INTEL'  , 'Intel'  ,'Intel');
insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('APPLE'  , 'Apple'  ,'Apple');
insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('DIGITAL', 'Digital','Digital');
insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('SGI'    , 'SGI'    ,'SGI');
insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('SUN'    , 'Sun'    ,'Sun');
insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('IBM'    , 'IBM'    ,'IBM');
insert into HW_MAKE (CODE, TERM, DEFINITION) VALUES ('CRAY'   , 'Cray'   ,'Cray');

-- =================================================================
-- SOFTWARE                                
DROP TABLE IF EXISTS `SOFTWARE`;
CREATE TABLE `SOFTWARE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('AMBER'   , 'AMBER'   , 'AMBER (Assisted Model Building and Energy Refinement) MD software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('NAMD'    , 'NAMD'    , 'NAMD MD software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('CHARMM'  , 'CHARMM'  , 'CHARMM (Chemistry at HARvard Molecular Mechanics) MD software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('TINKER'  , 'TINKER'  , 'TINKER software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('GROMACS' , 'GROMACS' , 'GROMACS MD software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('GROMOS'  , 'GROMOS'  , 'GROMOS (GROningen MOlecular Simulation package) MD software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('NWCHEM'  , 'NWChem'  , 'NWChem QM software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('GAMESS'  , 'GAMESS'  , 'GAMESS (General Atomic and Molecular Electronic Structure System) QM software package');
insert into SOFTWARE (CODE, TERM, DEFINITION) VALUES ('GAUSSIAN', 'Gaussian', 'Gaussian QM software package');

-- =================================================================
-- FILE FORMATS                                
DROP TABLE IF EXISTS `FILE_FORMAT`;
CREATE TABLE `FILE_FORMAT` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- FILE FORMATS
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('PDB'                        , 'PDB'                        , 'Protein Data Bank (PDB) file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('SDF'                        , 'SDF'                        , 'MDL Structure Data Format (SDF)');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('CML'                        , 'CML'                        , 'Chemical Markup Language file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('Mol2'                       , 'Mol2'                       , 'Mol2 file format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER parmtop'              , 'AMBER parmtop'              , 'AMBER parmtop format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER MD input'             , 'AMBER MD input'             , 'AMBER MD input format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER MD output'            , 'AMBER MD output'            , 'AMBER MD output format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER restart'              , 'AMBER restart'              , 'AMBER restart format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER library'              , 'AMBER library'              , 'AMBER OFF library format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER trajectory'           , 'AMBER trajectory'           , 'AMBER trajectory file format (ASCII)');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER trajectory (ASCII)'   , 'AMBER trajectory (ASCII)'   , 'AMBER trajectory (ASCII) format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER trajectory (binary)'  , 'AMBER trajectory (binary)'  , 'AMBER trajectory (binary) format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('AMBER trajectory (NetCDF)'  , 'AMBER trajectory (NetCDF)'  , 'AMBER trajectory (NetCDF) format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('Ptraj script'               , 'Ptraj script'               , 'Ptraj script');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS system topology'    , 'GROMACS system topology'    , 'GROMACS system topology');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS include topology'   , 'GROMACS include topology'   , 'GROMACS include topology');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS trajectory'         , 'GROMACS trajectory'         , 'GROMACS trajectory');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS MD input'           , 'GROMACS MD input'           , 'GROMACS MD input');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS MD output'          , 'GROMACS MD output'          , 'GROMACS MD output');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS restart'            , 'GROMACS restart'            , 'GROMACS restart');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS gro'                , 'GROMACS gro'                , 'GROMACS gro coordinate file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GROMACS index'              , 'GROMACS index'              , 'GROMACS index file format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('CHARMM coordinates'         , 'CHARMM coordinates'         , 'CHARMM coordinates file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('CHARMM input'               , 'CHARMM input'               , 'CHARMM input file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('CHARMM output'              , 'CHARMM output'              , 'CHARMM output file format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('DCD trajectory'             , 'DCD trajectory'             , 'DCD trajectory file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('PSF'                        , 'PSF'                        , 'Protein Structure File (PSF) format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('NAMD configuration'         , 'NAMD configuration'         , 'NAMD configuration');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('NAMD output'                , 'NAMD output'                , 'NAMD output');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAUSSIAN com'               , 'GAUSSIAN com'               , 'GAUSSIAN input file format (.com)');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAUSSIAN log'               , 'GAUSSIAN log'               , 'GAUSSIAN log/output file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAUSSIAN cube'              , 'GAUSSIAN cube'              , 'GAUSSIAN cube file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAUSSIAN chk'               , 'GAUSSIAN chk'               , 'GAUSSIAN chk file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAUSSIAN restart'           , 'GAUSSIAN restart'           , 'GAUSSIAN restart file format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('NWChem input'               , 'NWChem input'               , 'NWChem input file format (.nw)');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('NWChem output'              , 'NWChem output'              , 'NWChem log/output file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('NWChem topology'            , 'NWChem topology'            , 'NWChem topology file format (.top)');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('NWChem restart'             , 'NWChem restart'             , 'NWChem restart file format (.rst)');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAMESS input'               , 'GAMESS input'               , 'GAMESS input file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GAMESS output'              , 'GAMESS output'              , 'GAMESS log/output file format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('XML'                        , 'XML'                        , 'XML (eXtensible Markup Language) file');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('HTML'                       , 'HTML'                       , 'HTML (HyperText Markup Language) file');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('CSV'                        , 'CSV'                        , 'Comma-separated value (CSV) file format');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('Text'                       , 'Text'                       , 'Text file (ASCII)');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('PDF'                        , 'PDF'                        , 'Portable Document Format');

insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('JPEG'                       , 'JPEG'                       , 'JPEG image file');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('PNG'                        , 'PNG'                        , 'PNG image file');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('GIF'                        , 'GIF'                        , 'GIF image file');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('TIF'                        , 'TIF'                        , 'TIF image file');
insert into FILE_FORMAT (CODE, TERM, DEFINITION) VALUES ('BMP'                        , 'BMP'                        , 'Bitmap image file');

-- =================================================================
-- FILE/MEDIA TYPE                                
DROP TABLE IF EXISTS `MEDIA_TYPE`;
CREATE TABLE `MEDIA_TYPE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


SET foreign_key_checks = 1;



       
