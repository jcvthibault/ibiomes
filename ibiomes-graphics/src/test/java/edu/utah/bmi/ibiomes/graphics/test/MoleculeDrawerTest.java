package edu.utah.bmi.ibiomes.graphics.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;

import edu.utah.bmi.ibiomes.graphics.MoleculeDrawer;


public class MoleculeDrawerTest {

	private final static String gaussianSrcFilePath = "test/gaussian/acac_sp.gjf";
	private final static String pdbSrcFilePath = "test/pdb/1BIV.pdb";
	private final static String imgOutFilePath = "test/img/1BIV.pdb.png";
	//private final static String pdbSrcFilePath = "test/pdb/Complex407.pdb";
	//private final static String imgOutFilePath = "test/img/Complex407.pdb.png";

	private final Logger logger = Logger.getLogger(MoleculeDrawerTest.class);
	
	public void test() throws Exception
	{
		try{
			MoleculeDrawer drawer = new MoleculeDrawer(gaussianSrcFilePath);
			IMolecule molecule = drawer.getMolecule();
			drawer.drawMolecule(imgOutFilePath, 300, 300);
			
			drawer = new MoleculeDrawer(molecule);
			drawer.drawMolecule(imgOutFilePath, 300, 300);
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
