                                 
SET foreign_key_checks = 0;

-- ================================================================================================
-- RESIDUES
-- ================================================================================================
                        
DROP TABLE IF EXISTS `RESIDUE`;
CREATE TABLE `RESIDUE` (
  `ID` INT(11) NOT NULL auto_increment,
  `CODE` VARCHAR(30) NOT NULL,
  `TERM` VARCHAR(100) NOT NULL,
  `DEFINITION` VARCHAR(200),
  `UNIT` VARCHAR(20),
  `TERM_ID` VARCHAR(30),
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- =================================================================

insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('MOL', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('BC', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ACE', 'acyl', 'acyl, used for N-terminus');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HISB', 'histidine', 'histidine, N-delta protonated');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('LYSH', 'lysine', 'lysine, protonated');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HISA', 'histidine', 'histidine, N-epsilon protonated');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('POPC', 'palmitoyloleoylphosphatidylcholine', 'palmitoyloleoylphosphatidylcholine, lipid');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('SOL', 'SOLVENT molecule', 'SOLVENT molecule');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('TER', 'terminus', 'the end of a list of ATOM/HETATM records for a chain; http://www.rcsb.org/pdb/docs/format/pdbguide2.2/part_66.html');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HISH', 'histidine', 'histidine, both Ns protonated');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HDS', 'hexadecanesulfonyl serine', 'product of hexadecanesulfonylfluoride (HSF) and serine; http://dx.doi.org/10.1016/S0022-2836(03)00718-6');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('TRP', 'tryptophan', 'tryptophan');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('CYX', 'cysteine', 'cysteine (cross-linked); half of cystine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HIE', 'histidine', 'histidine, protonated at epsilon-position');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HCODE', 'histidine', 'histidine, protonated at delta-position');
-- amino-acids
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ALA', 'alanine', 'alanine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('VAL', 'valine', 'valine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('PHE', 'phenylalanine', 'phenylalanine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('PRO', 'proline', 'proline');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('MET', 'methionine', 'methionine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ILE', 'isoleucine', 'isoleucine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('LEU', 'leucine', 'leucine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ASP', 'aspartic acid', 'aspartic acid');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('GLU', 'glutamic acid', 'glutamic acid');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('LYS', 'lysine', 'lysine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ARG', 'arginine', 'arginine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('SER', 'serine', 'serine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('THR', 'threonine', 'threonine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('TYR', 'tyrosine', 'tyrosine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HIS', 'histidine', 'histidine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('CYS', 'cysteine', 'cysteine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ASN', 'asparagine', 'asparagine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('GLN', 'glutamine', 'glutamine');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('GLY', 'glycine', 'glycine');
-- DNA
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DA', 'A', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DA3', 'A', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DA5', 'A', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DC', 'C', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DC3', 'C', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DC5', 'C', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DT', 'T', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DT3', 'T', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DT5', 'T', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DG', 'G', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DG3', 'G', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DG5', 'G', NULL);
-- RNA
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RA', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RA3', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RA5', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RC', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RC3', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RC5', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RU', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RU3', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RU5', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RG', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RG3', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('RG5', 'unknown', NULL);
-- ions
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('IP', 'positive ion', 'ion, with positive electric charge');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('Ca', 'calcium', 'calcium or derivative');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('Na', 'sodium', 'sodium or derivative');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('NA+', 'sodium', 'sodium or derivative');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('Cl', 'chlorine', 'chlorine or derivative');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('Cl-', 'chlorine', 'chlorine or derivative');
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('K', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('K+', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('Zn', 'unknown', NULL);
-- solvent
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('WAT', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('TIP', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('TIP3', 'unknown', NULL);
-- other
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('POP', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DMP', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HSP', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HOH', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('POG', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('POE', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('SET', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HWD', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('ADC', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('FG', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('CY1', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DPPC', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('PIND', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('PASH', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('NH2', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('CC1', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('CC2', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DPP', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HEM', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('SIBU', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('GLT', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('KAI', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('MFQ', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HSE', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('HSD', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('SOD', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('NC', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('OG2', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('DPC', 'unknown', NULL);
insert into RESIDUE (CODE, TERM, DEFINITION) VALUES ('NEVI', 'unknown', NULL);

-- ================================================================

SET foreign_key_checks = 1;


-- INDEX
CREATE UNIQUE INDEX INDEX_RESIDUE_VAL ON RESIDUE (CODE);

