

-- ==========================================================================================
-- AUTHORSHIP
-- ==========================================================================================

-- STRUCTURE DB
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_structure_db;
CREATE PROCEDURE get_list_structure_db()
BEGIN
    SELECT *
    FROM STRUCTURE_DB
    ORDER BY	TERM;
END //
DELIMITER ;

-- CITATION DB
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_literature_db;
CREATE PROCEDURE get_list_literature_db()
BEGIN
    SELECT *
    FROM LITERATURE_DB
    ORDER BY TERM;
END //
DELIMITER ;

-- ==========================================================================================
-- MOLECULAR SYSTEM
-- ==========================================================================================

-- MOLECULE TYPE
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_molecule_type;
CREATE PROCEDURE get_list_molecule_type()
BEGIN
    SELECT *
    FROM MOLECULE_TYPE
    ORDER BY TERM;
END //
DELIMITER ;

-- ==========================================================================================
-- FOR ALL METHODS
-- ==========================================================================================

-- COMPUTATIONAL METHODS
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_method;
CREATE PROCEDURE get_list_method()
BEGIN
    SELECT *
    FROM METHOD
    ORDER BY TERM;
END //
DELIMITER ;

-- BOUNDARY CONDITIONS
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_bound_cond;
CREATE PROCEDURE get_list_bound_cond()

BEGIN
    SELECT *
    FROM BOUNDARY_COND
    ORDER BY TERM;
END //
DELIMITER ;

-- SOLVENT TYPES
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_solvent;
CREATE PROCEDURE get_list_solvent()
BEGIN
    SELECT *
    FROM SOLVENT_MODEL
    ORDER BY TERM;
END //
DELIMITER ;

-- ==========================================================================================
-- QUANTUM MECHANICS
-- ==========================================================================================

-- QM BASIS SET TYPES
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_basis_set_type;
CREATE PROCEDURE get_list_basis_set_type()

BEGIN
    SELECT *
    FROM QM_BASIS_SET_TYPE
    ORDER BY TERM;
END //
DELIMITER ;

-- QM - BASIS SETS 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_basis_set;
CREATE PROCEDURE get_list_basis_set()
BEGIN
    SELECT *
    FROM QM_BASIS_SET
    ORDER BY TERM;
END //
DELIMITER ;

-- QM - FAMILIES OF LEVELS OF THEORY 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_qm_method_family;
CREATE PROCEDURE get_list_qm_method_family()
BEGIN
    SELECT *
    FROM QM_METHOD_FAMILY
    ORDER BY TERM;
END //
DELIMITER ;

-- QM - LEVELS OF THEORY 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_qm_method;
CREATE PROCEDURE get_list_qm_method()
BEGIN
    SELECT *
    FROM QM_LEVEL_OF_THEORY
    ORDER BY TERM;
END //
DELIMITER ;

-- QM - LEVELS OF THEORY BY FAMILY 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_qm_method_by_family;
CREATE PROCEDURE get_list_qm_method_by_family(IN code_family VARCHAR(30))
BEGIN
    SELECT *
    FROM QM_LEVEL_OF_THEORY
    WHERE FAMILY like code_family
    ORDER BY TERM;
END //
DELIMITER ;

-- QM - PSEUDO POTENTIAL SCHEMES 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_qm_pseudo_potential;
CREATE PROCEDURE get_list_qm_pseudo_potential()
BEGIN
    SELECT *
    FROM QM_PSEUDO_POTENTIAL_SCHEME
    ORDER BY TERM;
END //
DELIMITER ;

-- QM - EXCHANGE CORRELATION FUNCTIONS 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_qm_exchange_corr;
CREATE PROCEDURE get_list_qm_exchange_corr()
BEGIN
    SELECT *
    FROM QM_EXCHANGE_CORRELATION_FN
    ORDER BY TERM;
END //
DELIMITER ;


-- QM CALCULATION TYPES
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_CALCULATION_type;
CREATE PROCEDURE get_list_CALCULATION_type()
BEGIN
    SELECT *
    FROM CALCULATION
    ORDER BY TERM;
END //
DELIMITER ;


-- ==========================================================================================
-- MOLECULAR DYNAMICS
-- ==========================================================================================

-- MD FORCE FIELD TYPE
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_force_field_type;
CREATE PROCEDURE get_list_force_field_type()
BEGIN
    SELECT *
    FROM MD_FORCE_FIELD_TYPE
    ORDER BY TERM;
END //
DELIMITER ;

-- MD FORCE FIELDS 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_force_field;
CREATE PROCEDURE get_list_force_field()
BEGIN
    SELECT *
    FROM MD_FORCE_FIELD
    ORDER BY TERM;
END //
DELIMITER ;

-- MD ELECTROSTATICS 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_electrostatics;
CREATE PROCEDURE get_list_electrostatics()
BEGIN
    SELECT *
    FROM MD_ELECTROSTATICS
    ORDER BY TERM;
END //
DELIMITER ;

-- MD CONSTRAINT ALGORITHM
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_md_constraint;
CREATE PROCEDURE get_list_md_constraint()
BEGIN
    SELECT *
    FROM MD_CONSTRAINT_ALGORITHM
    ORDER BY TERM;
END //
DELIMITER ;

-- MD UNIT SHAPE
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_unit_shape;
CREATE PROCEDURE get_list_unit_shape()

BEGIN
    SELECT *
    FROM MD_UNIT_SHAPE
    ORDER BY TERM;
END //
DELIMITER ;

-- MD INTEGRATOR
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_md_integrator;
CREATE PROCEDURE get_list_md_integrator()
BEGIN
    SELECT *
    FROM MD_INTEGRATOR
    ORDER BY TERM;
END //
DELIMITER ;

-- ENSEMBLES
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_ensemble;
CREATE PROCEDURE get_list_ensemble()
BEGIN
    SELECT *
    FROM ENSEMBLE
    ORDER BY TERM;
END //
DELIMITER ;

-- THERMOSTAT
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_thermostat;
CREATE PROCEDURE get_list_thermostat()
BEGIN
    SELECT *
    FROM THERMOSTAT_ALGORITHM
    ORDER BY TERM;
END //
DELIMITER ;

-- BAROSTAT
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_barostat;
CREATE PROCEDURE get_list_barostat()
BEGIN
    SELECT *
    FROM BAROSTAT_ALGORITHM
    ORDER BY TERM;
END //
DELIMITER ;

-- MD SAMPLING METHODS
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_sampling_method;
CREATE PROCEDURE get_list_sampling_method()
BEGIN
    SELECT *
    FROM MD_SAMPLING_METHOD
    ORDER BY TERM;
END //
DELIMITER ;

-- ==========================================================================================
-- SOFTWARE / HARDWARE
-- ==========================================================================================

-- SOFTWARE 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_software;
CREATE PROCEDURE get_list_software()
BEGIN
    SELECT *
    FROM SOFTWARE
    ORDER BY TERM;
END //
DELIMITER ;

-- FILE FORMATS
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_file_format;
CREATE PROCEDURE get_list_file_format()
BEGIN
    SELECT *
    FROM FILE_FORMAT
    ORDER BY TERM;
END //
DELIMITER ;

-- CPU ARCHITECTURE
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_cpu_arch;
CREATE PROCEDURE get_list_cpu_arch()
BEGIN
    SELECT *
    FROM CPU_ARCHITECTURE
    ORDER BY TERM;
END //
DELIMITER ;

-- HARDWARE MAKE 
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_hw_make;
CREATE PROCEDURE get_list_hw_make()
BEGIN
    SELECT *
    FROM HW_MAKE
    ORDER BY TERM;
END //
DELIMITER ;

-- OPERATING SYSTEM
DELIMITER //
DROP PROCEDURE IF EXISTS get_list_os;
CREATE PROCEDURE get_list_os()
BEGIN
    SELECT *
    FROM OPERATING_SYSTEM
    ORDER BY TERM;
END //
DELIMITER ;





