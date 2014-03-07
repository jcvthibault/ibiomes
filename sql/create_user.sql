

-- =============================================================
-- iBIOMES user (READ only)
-- =============================================================

-- delete user if exists already
DROP USER 'ibiomes'@'%';
FLUSH PRIVILEGES;
-- create user
CREATE USER 'ibiomes' IDENTIFIED BY 'ibiomes';

-- grant SELECT
GRANT SELECT ON * TO 'ibiomes'@'%';

-- grant stored procedure calls
GRANT EXECUTE ON PROCEDURE get_attribute_bycode TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_attributes TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_attribute_values TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_attribute_values_filter TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_list_structure_db TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_literature_db TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_list_software TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_file_format TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_os TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_hw_make TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_cpu_arch TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_list_force_field_type TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_force_field TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_electrostatics TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_md_constraint TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_unit_shape TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_md_integrator TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_ensemble TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_barostat TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_thermostat TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_sampling_method TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_list_bound_cond TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_method TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_solvent TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_list_molecule_type TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_list_CALCULATION_type TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_basis_set_type TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_basis_set TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_qm_method_family TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_qm_method TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_qm_method_by_family TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_qm_pseudo_potential TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_list_qm_exchange_corr TO 'ibiomes'@'%';

GRANT EXECUTE ON PROCEDURE get_experiment_set TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_experiment_sets_read TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_experiment_sets_owned TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE create_experiment_set TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE update_experiment_set TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE delete_experiment_set TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_experiment_set_metadata TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE add_experiment_set_avu TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE delete_experiment_set_avu TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE clear_experiment_set_metadata TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE get_experiment_set_analysis_data TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE add_experiment_set_analysis_data TO 'ibiomes'@'%';
GRANT EXECUTE ON PROCEDURE remove_experiment_set_analysis_data TO 'ibiomes'@'%';

