                                                                      
SET foreign_key_checks = 0;


-- METADATA ATTRIBUTES                                  
DROP TABLE IF EXISTS `METADATA_ATTRIBUTE`;
CREATE TABLE `METADATA_ATTRIBUTE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(50) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  `TYPE` VARCHAR(10) NOT NULL,
  `DICTIONARY` VARCHAR(100),
  `TERM_ID` VARCHAR(30),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Populates the dictionary tables

-- ================================================================================================
-- METADATA ATTRIBUTES WITH FREE TEXT/INTEGER/BOOLEAN VALUES
-- ================================================================================================
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('TITLE'                 , 'Title'                  , 'Experiment title'       , 'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('EXPERIMENT_DESCRIPTION', 'Experiment description' , 'Experiment description' , 'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('TASK_DESCRIPTION'      , 'Task description'       , 'Task description'       , 'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('FILE_DESCRIPTION'      , 'File description'       , 'File description'       , 'STRING');

-- ================================================================================================
-- AUTHORSHIP
-- ================================================================================================

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('PDB_ID'   , 'PDB ID'   , 'Protein Data Bank (PDB) identifier','STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('PUBMED_ID', 'PubMed ID', 'PubMed article identifier'         ,'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('STRUCTURE_REF_ID', 'Structure ID', 'ID of the structure in a specified database','STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('PUBLICATION_REF_ID', 'Citation ID', 'ID of the citation in a specified database', 'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('STRUCTURE_REF_DB', 'Structure database', 'Database that contains molecular structures','STRING', 'STRUCTURE_DB');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('PUBLICATION_DB', 'Citation database', 'Citation database', 'STRING', 'LITERATURE_DB');

-- ================================================================================================
-- MOLECULAR SYSTEM / TOPOLOGY
-- ================================================================================================
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('MOLECULE_TYPE'    , 'Molecule type', 'Molecule type (e.g. RNA, DNA, Protein)','STRING','MOLECULE_TYPE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('ION_TYPE'         , 'Ion type', 'Ion type (e.g. Cl-, K+)' ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('SOLVENT_MOLECULE' , 'Solvent molecule', 'Solvent molecule (e.g. water)' ,'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('RESIDUE_CHAIN'     , 'Residue chain'           , 'Residue chain (software-specific)','STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('RESIDUE_CHAIN_NORM', 'Normalized residue chain', 'Normalized residue chain'         ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('RESIDUE_NON_STD', 'Non-standard residue', 'Non-standard residue','STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('COUNT_ATOM'   , 'Atom count'            , 'Atom count'            , 'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('COUNT_ION'    , 'Ion count'             , 'Ion count'             , 'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('COUNT_SOLVENT', 'Solvent molecule count', 'Solvent molecule count', 'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('COUNT_RESIDUE', 'Residue count'         , 'Residue count'         , 'INTEGER');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('MOLECULE_ATOMIC_WEIGHT'     , 'Atomic weight'     , 'Atomic weight of the molecule', 'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('MOLECULE_ATOMIC_COMPOSITION', 'Atomic composition (compact)', 'Atomic composition of the molecule. Format is ''el1:n1 el2:n2 ...''  (e.g. C:26 N:1 O:2 H:29)', 'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('MOLECULE_ATOMIC_COMPOSITION2', 'Atomic composition', 'Atomic composition of the molecule. Format is ''el1[n1] el2[n2] ...''  (e.g. C[26] N[1] O[2] H[29])', 'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('TOTAL_MOLECULE_CHARGE'      , 'Total molecule charge' , 'Total molecule charge (formal charge)', 'FLOAT');

-- ================================================================================================
-- METHODS (COMMON)
-- ================================================================================================

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('METHOD'                , 'Method'                , 'Simulation/calculation method' ,'STRING' ,'METHOD');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('SOLVENT'               , 'Solvent'               , 'Solvent'                       ,'STRING' ,'SOLVENT_MODEL');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('IMPLICIT_SOLVENT_MODEL', 'Implicit solvent model', 'Implicit solvent model'        ,'STRING' ,'IMPLICIT_SOLVENT_MODEL');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('BOUNDARY_CONDITIONS'   , 'Boundary conditions'   , 'Boundary conditions'           ,'STRING' ,'BOUNDARY_COND');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('TIME_LENGTH'     , 'Simulated time'   , 'Simulated time'  , 'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('TIME_STEP_LENGTH', 'Time step length' , 'Time step length', 'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('TIME_STEP_COUNT' , 'Time step count'  , 'Time step count' , 'INTEGER');

-- ================================================================================================
-- MOLECULAR DYNAMICS
-- ================================================================================================

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('FORCE_FIELD'              , 'Force field'             , 'Force-field used for Molecular Dynamics simulation.','STRING','MD_FORCE_FIELD');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('ENSEMBLE'                 , 'Ensemble modeling'       , 'Ensemble modeling'    ,'STRING','ENSEMBLE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('UNIT_SHAPE'               , 'Unit shape'              , 'Unit shape'           ,'STRING','MD_UNIT_SHAPE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('MM_INTEGRATOR'            , 'MM integrator'           , 'MM integrator'        ,'STRING','MD_INTEGRATOR');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('CUTOFF_VAN_DER_WAALS' , 'Van der Waals cutoff', 'Cutoff for Van der Waals calculations (in Angstroms)', 'FLOAT');
-- TEMPERATURE CONTROL
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('REFERENCE_TEMPERATURE'    , 'Reference temperature'   , 'Reference temperature (in Kelvins) at which the system is maintained','FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('INITIAL_TEMPERATURE'      , 'Initial temperature'     , 'Initial temperature (in Kelvins) of the system before heating'       ,'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('THERMOSTAT_ALGORITHM'     , 'Thermostat'              , 'Thermostat','STRING','THERMOSTAT_ALGORITHM');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('THERMOSTAT_TIME_CONSTANT' , 'Thermostat time constant', 'Thermostat time constant (in ps)','STRING');
-- PRESSURE CONTROL
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('REFERENCE_PRESSURE'       , 'Reference pressure'      , 'Reference pressure (in bars) at which the system is maintained','FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('INITIAL_PRESSURE'         , 'Initial pressure'        , 'Initial pressure (in bars) of the system'                      ,'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('BAROSTAT_ALGORITHM'       , 'Barostat implementation' , 'Barostat implementation'                                       ,'STRING' , 'BAROSTAT_ALGORITHM');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('BAROSTAT_TYPE'            , 'Barostat type'           , 'Barostat type'                                                 ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('BAROSTAT_TIME_CONSTANT'   , 'Barostat time constant'  , 'Barostat time constant (in ps)'                                ,'STRING');
-- ENHANCED SAMPLING / REMD
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('ENHANCED_SAMPLING_METHOD_NAME'          , 'Enhanced sampling method' , 'Enhanced sampling method, typically used in MD',               'STRING', 'MD_SAMPLING_METHOD');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('ENHANCED_SAMPLING_METHOD_REPLICA_COUNT' , 'Number of replicas'       , 'Number of replicas/ensembles simulated for enhanced sampling', 'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('REMD_EXCHANGE_COUNT'                    , 'Number of exchanges'      , 'Number of exchanges in REMD run'                             , 'INTEGER');
-- RESTRAINTS / CONSTRAINTS
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('RESTRAINT'           , 'Restraint'           , 'Restraint'                                  ,'STRING' , 'RESTRAINT_TYPE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('RESTRAINT_TARGET'    , 'Restraint target'    , 'Restraint target'                           ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('HAS_RESTRAINTS'      , 'Has restraints'      , 'Whethere the system is restrained or not'   ,'BOOLEAN');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('CONSTRAINT_ALGORITHM', 'Constraint algorithm', 'Constraint algorithm'                       ,'STRING'    ,'MD_CONSTRAINT_ALGORITHM');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('CONSTRAINT_TARGET'   , 'Constraint target'   , 'Constraint target'                          ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('HAS_CONSTRAINTS'     , 'Has constraints'     , 'Whethere the system has constraints or not' ,'BOOLEAN');
-- METHOD-SPECIFIC 
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('LANGEVIN_COLLISION_FREQUENCY'    , 'Collision frequency'   , 'Langevin collision frequency (ps-1)'      ,'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('LANGEVIN_RANDOM_SEED'            , 'Langevin random seed'  , 'Random seed used for Langevin thermostat' ,'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('STOCHASTICS_NOISE_TERM_AMPLITUDE', 'Stochastics noise term', 'Stochastics noise term amplitude'         ,'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('MD_MINIMIZATION', 'Minimizations', 'Minimizations performed before a production MD run'                 ,'STRING');
-- ELECTROSTATICS
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('ELECTROSTATICS'        , 'Electrostatics'         , 'Electrostatics modeling'                              , 'STRING','MD_ELECTROSTATICS');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('CUTOFF_ELECTROSTATICS' , 'Electrostatics cutoff'  , 'Cutoff for electrostatics calculations (in Angstroms)', 'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('PME_EWALD_COEFFICIENT' , 'Ewald coefficient'      , 'Ewald coefficient'                                    , 'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('PME_INTERPOLATION'     , 'PME interpolation order', 'PME interpolation order'                              , 'INTEGER');

-- ================================================================================================
-- QUANTUM MECHANICS CALCULATIONS
-- ================================================================================================
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QM_SPIN_MULTIPLICITY'   , 'Spin multiplicity', 'Spin multiplicity (spin multiplicity is given by 2S+1, where S is the total electron spin for the molecule).','INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QM_TOTAL_MOLECULE_CHARGE', 'Total charge'              , 'Total molecule charge for QM calculations.'            ,'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QM_BASIS_SET_FAMILY'    , 'Basis set family', 'Basis set family.'                             ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('QM_BASIS_SET'           , 'Basis set'      , 'Basis sets for Quantum Mechanics calculations.','STRING', 'QM_BASIS_SET');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('QM_LEVEL_OF_THEORY'     , 'QM method family'   , 'Family of method used for Quantum calculations','STRING', 'QM_LEVEL_OF_THEORY');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('QM_METHOD_NAME'         , 'QM method'   , 'Specific name of the method used for Quantum calculations','STRING', 'QM_METHOD_NAME');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('CALCULATION'            , 'Calculation type', 'Type of calculation performed','STRING', 'CALCULATION');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QM_EXCHANGE_CORRELATION', 'Exchange-correlation functional', 'Exchange-correlation functional for QM calculations (e.g. DFT).'  ,'STRING');

-- ================================================================================================
-- QM/MM CALCULATIONS
-- ================================================================================================
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QM_REGION_DEFINITION'   	   , 'QM region definition'    , 'Definition of the QM region for QM/MM calculations (e.g. atom mask)','STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QM_ELECTROSTATICS_MODELING' , 'QM electrostatics'       , 'Definition of the electrostatics interactions in QM region for QM/MM calculations','STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('QMMM_BOUNDARY_TREATMENT'    , 'QM/MM boundary treatment', 'Treatment of the QM/MM boundary','STRING');

-- ================================================================================================
-- QUANTUM MD
-- ================================================================================================
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('QMD_METHOD', 'Quantum MD method' , 'Name of quantum MD method',  'STRING',  'QMD_METHOD');

-- ================================================================================================
-- HARDWARE / SOFTWARE / EXECUTION
-- ================================================================================================

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('HARDWARE_MAKE', 'Hardware make', 'Hardware make (e.g. Intel, Apple, IBM, Sun).','STRING', 'HW_MAKE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('OP_SYS', 'OS', 'Operating System','STRING', 'OPERATING_SYSTEM');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('SOFTWARE_NAME'          , 'Software package name'             , 'Simulation software package name (without version)', 'STRING', 'SOFTWARE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('SOFTWARE_NAME_W_VERSION', 'Software package name and version' , 'Simulation software package name with version'     , 'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('SOFTWARE_EXEC_NAME'     , 'Software executable'               , 'Simulation software package executable name'       , 'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('SOFTWARE_VERSION'       , 'Software version'                  , 'Simulation software package version'               , 'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('EXECUTION_TIME'     , 'Execution time'     , 'Execution time'                            ,'FLOAT');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('PROGRAM_TERMINATION', 'Program termination', 'Program termination (e.g. normal, errors)' ,'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('NUMBER_CPUS'         , 'Number of CPUs'      , 'Number of CPUs'                                        ,'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('NUMBER_GPUS'         , 'Number of GPUs'      , 'Number of GPUs'                                        ,'INTEGER');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('CPU_ARCHITECTURE'    , 'CPU architecture'    , 'CPU architecture (e.g. Intel x86, PowerPC).'           ,'STRING'  , 'CPU_ARCHITECTURE');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('GPU_ARCHITECTURE'    , 'GPU architecture'    , 'GPU architecture'                                      ,'STRING');
insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE)             VALUES ('MACHINE_ARCHITECTURE', 'Machine architecture', 'Machine architecture (e.g. Cray XC30, IBM Blue Gene/L).', 'STRING');

insert into METADATA_ATTRIBUTE (CODE, TERM, DEFINITION, TYPE, DICTIONARY) VALUES ('FILE_FORMAT'   , 'File format', 'Format of the file', 'STRING', 'FILE_FORMAT');


SET foreign_key_checks = 1;


-- INDEX

CREATE UNIQUE INDEX INDEX_META_ATTR ON METADATA_ATTRIBUTE (CODE);


       
