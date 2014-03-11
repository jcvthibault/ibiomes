/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** 
 * Experimental. Java wrapper for the AMBER PTRAJ tool (JNI interface to native C code)
 * @author Julien Thibault
 *
 */
public class Ptraj 
{
	public final static String PTRAJ_ACCEPTOR 			= "acceptor"; 
	public final static String PTRAJ_ANALYZE 			= "analyze";
	public final static String PTRAJ_ANGLE 				= "angle";
	public final static String PTRAJ_ATOMICFLUCT3D 		= "atomicfluct3D";
	public final static String PTRAJ_ATOMICFLUCT 		= "atomicfluct";
	public final static String PTRAJ_AVERAGE 			= "average";
	public final static String PTRAJ_BOX 				= "box";
	public final static String PTRAJ_CENTER 			= "center";
	public final static String PTRAJ_CHECKOVERLAP 		= "checkoverlap";
	public final static String PTRAJ_CLOSESTWATERS 		= "closestwaters";
	public final static String PTRAJ_DIHEDRALCLUSTER 	= "clusterdihedr";
	public final static String PTRAJ_CLUSTERATTRIBUTE 	= "clusterattribu";
	public final static String PTRAJ_CLUSTER 			= "cluster";
	public final static String PTRAJ_CORRELATION 		= "correlation";
	public final static String PTRAJ_CONTACTS 			= "contacts";
	public final static String PTRAJ_DIFFUSION 			= "diffusion";
	public final static String PTRAJ_DIHEDRAL 			= "dihedral";
	public final static String PTRAJ_DIPOLE 			= "dipole";
	public final static String PTRAJ_DISTANCE 			= "distance";
	public final static String PTRAJ_DNAIONTRACKER 		= "dnaiontracker";
	public final static String PTRAJ_DONOR 				= "donor";
	public final static String PTRAJ_ECHO 				= "echo";
	public final static String PTRAJ_ENERGY 			= "energy";
	public final static String PTRAJ_GRID 				= "grid";
	public final static String PTRAJ_HBOND 				= "hbond";
	public final static String PTRAJ_IMAGE 				= "image";
	public final static String PTRAJ_MATRIX 			= "matrix";
	public final static String PTRAJ_PRINCIPAL 			= "principal";
	public final static String PTRAJ_PRNLEV 			= "prnlev";
	public final static String PTRAJ_PROJECTION 		= "projection";
	public final static String PTRAJ_PUCKER 			= "pucker";
	public final static String PTRAJ_RADIAL 			= "radial";
	public final static String PTRAJ_RADIUSOFGYRATION 	= "radgyr";
	public final static String PTRAJ_RANDOMIZEIONS 		= "randomizeions";
	public final static String PTRAJ_RMS 				= "rms";
	public final static String PTRAJ_RUNNINGAVERAGE 	= "runningaver";
	public final static String PTRAJ_SCALE 				= "scale";
	public final static String PTRAJ_SECONDARYSTRUCT	= "secstruct";
	public final static String PTRAJ_STRIP 				= "strip";
	public final static String PTRAJ_TRANSFORM 			= "transform";
	public final static String PTRAJ_TRANSLATE 			= "translate";
	public final static String PTRAJ_TRUNCOCT 			= "truncoct";
	public final static String PTRAJ_TEST 				= "test";
	public final static String PTRAJ_TORSION 			= "torsion";
	public final static String PTRAJ_VECTOR 			= "vector";
	public final static String PTRAJ_WATERSHELL 		= "watershell";
	public final static String PTRAJ_2DRMS 				= "2drms";
	public final static String PTRAJ_GO 				= "go";
	public final static String PTRAJ_REFERENCE 			= "reference";
	public final static String PTRAJ_SOLVENT 			= "solvent";
	public final static String PTRAJ_TRAJIN 			= "trajin";
	public final static String PTRAJ_TRAJOUT 			= "trajout";

	public final static String CLUSTER_LINKAGE_AVG 	= "averagelinkage";
	public final static String CLUSTER_LINKAGE 		= "linkage";
	public final static String CLUSTER_COMPLETE 	= "complete";
	public final static String CLUSTER_EDGE 		= "edge";
	public final static String CLUSTER_HIERARCHY 	= "hierarchical";
	
	private File _prmtop;
	
	static
	{
		try{
			System.loadLibrary("ptraj");
		}catch(UnsatisfiedLinkError e){
            System.out.println("Couldn't load ptraj C library");
            System.out.println(e.getMessage());
        }
	}

	/**
	 * Ptraj
	 * @param prmtopPath Path of prmtop file
	 * @throws IOException 
	 */
	public Ptraj(String prmtopPath) throws IOException
	{
		File prmtop = new File(prmtopPath);
		if (!prmtop.exists()){
			throw new IOException("No file was not found at " + prmtopPath);
		}
		_prmtop = prmtop;
	}
	
	/**
	 * Run script
	 * @param scriptLines
	 */
	public void run(ArrayList<String> scriptLines)
	{
		String[] lines = new String[scriptLines.size()];
		lines = scriptLines.toArray(lines);
		
		this.run(lines);
	}
	
	/**
	 * Run script
	 * @param scriptLines
	 */
	public void run(String[] scriptLines)
	{
		System.out.println("[ptraj] Running script...");
		this.ptraj_jni(_prmtop.getAbsolutePath(), scriptLines);
		System.out.println("[ptraj] Done!");
		
	}
	
	/**
	 * JNI interface to Ptraj (C code)
	 * @param prmtop
	 * @param scriptLines
	 */
	public native void ptraj_jni(String prmtop, String[] scriptLines);

	/**
	 * Convert topology/trajectory info to average PDB information
	 * @param trajectories Input trajectories
	 * @param pdbOutput PDB output path
	 */
	public void getAveragePDB(String[] trajectories, String pdbOutput)
	{
		// set input trajectories
		ArrayList<String> scriptLines = loadTrajectories(trajectories);
		// get average structure
		scriptLines.add(PTRAJ_AVERAGE + " " + pdbOutput + " " + "pdb");
		this.run(scriptLines);
	}
	
	/**
	 * Remove water molecule and store averaged structure to PDB file
	 * @param trajectories
	 * @param pdbOutput
	 */
	public void getAvgSubsetWithNoWater(String[] trajectories, String pdbOutput)
	{
		this.getAvgSubsetFromFilter(trajectories, ":WAT", pdbOutput);
	}
	
	/**
	 * Filter out atoms using a mask and store the averaged structure to PDB file
	 * @param trajectories
	 * @param mask Atom mask
	 * @param pdbOutput
	 */
	public void getAvgSubsetFromFilter(String[] trajectories, String mask, String pdbOutput)
	{
		// set input trajectories
		ArrayList<String> scriptLines = loadTrajectories(trajectories);
		// strip water molecules (if any)
		scriptLines.add(PTRAJ_STRIP + " " + mask + " ");
		// get average structure
		scriptLines.add(PTRAJ_AVERAGE + " " + pdbOutput + " " + "pdb") ;
		
		this.run(scriptLines);
	}
	
	/**
	 * Cluster trajectories and store average PDB representations of each cluster.
	 * @param trajectories Input trajectory files
	 * @param outputname Output filename
	 * @param algorithm Clustering algorithm
	 * @param nclusters Number of clusters to generate
	 * @param mask Atom mask
	 */
	public void cluster(String[] trajectories, String outputname, String algorithm, int nclusters, String mask)
	{
		// set input trajectories
		ArrayList<String> scriptLines = loadTrajectories(trajectories);
		
		// strip water molecules (if any)
		scriptLines.add(PTRAJ_STRIP + " " + ":WAT" + " ");
		
		// cluster trajectories
		scriptLines.add(PTRAJ_CLUSTER + " out " + outputname + " representative none average pdb "+ algorithm +" all none clusters "+ nclusters +" rms " + mask);
		
		this.run(scriptLines);
	}
	
	/**
	 * Set trajin information
	 * @param trajectories
	 * @return
	 */
	private ArrayList<String> loadTrajectories(String[] trajectories)
	{
		//set input trajectories
		ArrayList<String> scriptLines = new ArrayList<String>();
		for (int t=0; t<trajectories.length; t++)
		{
			scriptLines.add(PTRAJ_TRAJIN + " " + trajectories[t]);
		}
		return scriptLines;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Ptraj ptraj;
		
		String prmtop = "/home/juji/Documents/amber/tutorial3/NMA.prmtop";
		
		
		ptraj = new Ptraj(prmtop);
		String[] scriptLines = {Ptraj.PTRAJ_TRAJIN + " /home/juji/Documents/amber/tutorial3/production/equil1.mdcrd","average avg.pdb pdb"};
		ptraj.run(scriptLines);
		
		
		
		//TEST AVERAGING
		ptraj = new Ptraj(prmtop);
		String[] trajs = {"/home/juji/Documents/amber/tutorial3/production/equil1.mdcrd",
				"/home/juji/Documents/amber/tutorial3/production/equil2.mdcrd"};
		ptraj.getAveragePDB(trajs, "out/avg.pdb");
		//ptraj.getSubsetWithNoWater(trajs, "out/avg_nowater.pdb");*/
		
		
		//TEST CLUSTERING
		String prmtop2 = "/home/juji/Documents/amber/simulations/mal1-trna-complex/prmtop";
		ptraj = new Ptraj(prmtop2);
		String[] trajs2 = {
				"/home/juji/Documents/amber/simulations/mal1-trna-complex/md_gb7.traj.1.gz",
				"/home/juji/Documents/amber/simulations/mal1-trna-complex/md_gb7.traj.2.gz",
				"/home/juji/Documents/amber/simulations/mal1-trna-complex/md_gb7.traj.3.gz",
				"/home/juji/Documents/amber/simulations/mal1-trna-complex/md_gb7.traj.4.gz"};
		ptraj.cluster(trajs2, "out/ptraj_cluster.pdb", CLUSTER_LINKAGE_AVG, 5, "*");

	}
	

}
