package edu.utah.bmi.ibiomes.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openscience.cdk.ChemFile;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.*;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.layout.*;
import org.openscience.cdk.renderer.*;
import org.openscience.cdk.renderer.font.*;
import org.openscience.cdk.renderer.generators.*;
import org.openscience.cdk.renderer.visitor.*;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

/**
 * Molecule drawer. Based on the tutorial code provided by Egon Willighagen.
 * @author Julien Thibault, University of Utah
 *
 */
public class MoleculeDrawer {
	
	private IMolecule molecule;
	
	/**
	 * Get molecule to draw
	 * @return Molecule
	 */
	public IMolecule getMolecule() {
		return molecule;
	}

	/**
	 * Set molecule to draw
	 * @param molecule Molecule
	 */
	public void setMolecule(IMolecule molecule) {
		this.molecule = molecule;
	}

	/**
	 * Load molecule from structure file (e.g. PDB).
	 * @param moleculeFilePath Path to file defining the molecule
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws CDKException 
	 */
	public MoleculeDrawer(String moleculeFilePath) throws FileNotFoundException, IOException, CDKException{
		
		File input = new File(moleculeFilePath);
		
		//DefaultChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		
        BufferedReader br = new BufferedReader(new FileReader(input));
        ReaderFactory readerFactory = new ReaderFactory();
        ISimpleChemObjectReader reader = readerFactory.createReader(br);
        IChemFile content = (IChemFile) reader.read(new ChemFile());
        /*List<IChemModel> models = ChemFileManipulator.getAllChemModels(content);
        if (models.size()>0){
        	System.out.println(models.size());
        	IMoleculeSet setOfMolecules = models.get(0).getMoleculeSet();
        	System.out.println(setOfMolecules.getMoleculeCount());
        	if (setOfMolecules.getMoleculeCount()>0){
        		this.molecule = setOfMolecules.getMolecule(0);
        	}
        }*/
        List<IAtomContainer> atomGroups = ChemFileManipulator.getAllAtomContainers(content);
        for (IAtomContainer container : atomGroups){
        	this.molecule = new Molecule(container);
        	System.out.println("Molecule created for "+moleculeFilePath+": " + molecule.getAtomCount());
        }
        br.close();
	}
	
	/**
	 * Constructor
	 * @param molecule Molecule
	 */
	public MoleculeDrawer(IMolecule molecule){
		this.molecule = molecule;
	}
	
	/**
	 * Draw molecule and save as PNG file
	 * @param outputPath Path to output file
	 * @param width Width
	 * @param height Height
	 * @throws Exception
	 */
	public void drawMolecule(String outputPath, int width, int height) throws Exception {

		// the draw area and the image should be the same size
		Rectangle drawArea = new Rectangle(width, height);
		Image image = new BufferedImage(
				width, height, BufferedImage.TYPE_INT_RGB
		);

		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(molecule);
		sdg.generateCoordinates();
		molecule = sdg.getMolecule();

		// generators make the image elements
		List<IAtomContainerGenerator> generators = new ArrayList<IAtomContainerGenerator>();
		generators.add(new BasicSceneGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());

		// the renderer needs to have a toolkit-specific font manager
		AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());

		// the call to 'setup' only needs to be done on the first paint
		renderer.setup(molecule, drawArea);

		// paint the background
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);

		// the paint method also needs a toolkit-specific renderer
		renderer.paint(molecule, new AWTDrawVisitor(g2));

		ImageIO.write((RenderedImage)image, "PNG", new File(outputPath));
    }
}
