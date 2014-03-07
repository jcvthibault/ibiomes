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

package edu.utah.bmi.ibiomes.topo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * 3D coordinates
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="coordinates")
public class Coordinate3D {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Get X coordinate
	 * @return X coordinate
	 */
	@XmlAttribute(name="x")
	public double getX() {
		return x;
	}
	
	/**
	 * Get Y coordinate
	 * @return Y coordinate
	 */
	@XmlAttribute(name="y")
	public double getY() {
		return y;
	}
	
	/**
	 * Get Z coordinate
	 * @return Z coordinate
	 */
	@XmlAttribute(name="z")
	public double getZ() {
		return z;
	}
	
	/**
	 * private no-arg constructor for JAXB
	 */
	@SuppressWarnings("unused")
	private Coordinate3D(){
	}
	
	/**
	 * Construct 3D coordinate object.
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public Coordinate3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Load Cartesian coordinates using a Z-matrix (internal coordinates).
	 * Original code: org.openscience.cdk.geometry.zmatrixToCartesian() 
	 * (Copyright (C) 2004-2007  The Chemistry Development Kit (CDK) project).
	 * 
	 * @param distances Distances
	 * @param firstAtoms First atoms
	 * @param angles Angles
	 * @param secondAtoms Second atoms
	 * @param dihedrals Dihedrals
	 * @param thirdAtoms Third atoms
	 * @return List of cartesian coordinates
	 */
	public static Coordinate3D[] loadFromZMatrix(
			double[] distances, int[] firstAtoms,
            double[] angles,    int[] secondAtoms,
            double[] dihedrals, int[] thirdAtoms)
	{
		Point3d[] tmpCoords = new Point3d[distances.length];
		for (int index=0; index<distances.length; index++)
		{
			if (index==0) {
				tmpCoords[index] = new Point3d(0d, 0d, 0d);
			} else if (index==1) {
				tmpCoords[index] = new Point3d(distances[1], 0d, 0d);
			} else if (index==2) {
				tmpCoords[index] = new Point3d(
									-Math.cos((angles[2]/180)*Math.PI)*distances[2]+distances[1],
					                Math.sin((angles[2]/180)*Math.PI)*distances[2],
					                0);
				if (firstAtoms[index] == 0)
					tmpCoords[index].x = (tmpCoords[index].x - distances[1]) * -1;
			} else {
				Vector3d cd = new Vector3d();
				cd.sub(tmpCoords[thirdAtoms[index]], tmpCoords[secondAtoms[index]]);
				
				Vector3d bc = new Vector3d();
				bc.sub(tmpCoords[secondAtoms[index]], tmpCoords[firstAtoms[index]]);
				
				Vector3d n1 = new Vector3d();
				n1.cross(cd, bc);
				
				Vector3d n2 = rotate(n1,bc,-dihedrals[index]);
				Vector3d ba = rotate(bc,n2,-angles[index]);
				
				ba.normalize();
				ba.scale(distances[index]);
				
				Point3d result = new Point3d();
				result.add(tmpCoords[firstAtoms[index]], ba);
				tmpCoords[index] = result;
			}
		}
		Coordinate3D[] coords = new Coordinate3D[distances.length];
		for (int index=0; index<distances.length; index++){
			coords[index] = new Coordinate3D(tmpCoords[index].x, tmpCoords[index].y, tmpCoords[index].z);
		}
		return coords;
	}
	
	private static Vector3d rotate(Vector3d vector, Vector3d axis, double angle) {
        Matrix3d rotate = new Matrix3d();
        rotate.set(new AxisAngle4d(axis, Math.toRadians(angle)));
        Vector3d result = new Vector3d();
        rotate.transform(vector, result);
        return result;
    }
}
