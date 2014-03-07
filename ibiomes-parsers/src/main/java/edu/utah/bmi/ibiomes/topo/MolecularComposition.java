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

import java.util.List;

/**
 * Molecular composition utility class
 * @author Julien Thibault
 *
 */
public class MolecularComposition {

	private final static String[] elementOrder = {"C", "H", "Fe", "N", "O"};
	
	/**
	 * Get atomic composition in format 'E1:n1 E2:n2' ...
	 * @param elements Element occurrences
	 * @return Atomic composition
	 */
	public static String getAtomicComposition(List<AtomicElementOccurrence> elements) {
		String chain = "";
		for (int e=0;e<elementOrder.length;e++){
			int i=0;
			boolean found = false;
			while (i<elements.size() && !found){
				if (elements.get(i).getElement().getSymbol().equals(elementOrder[e])){
					chain +=  elements.get(i).getElement().getSymbol() + ":" + elements.get(i).getCount() + " ";
					elements.remove(i);
					found = true;
				}
				i++;
			}
		}
		for (AtomicElementOccurrence elementOccurence : elements){
			chain +=  elementOccurence.getElement().getSymbol() + ":" + elementOccurence.getCount() + " " ;
		}
		return chain.trim();
	}
	
	/**
	 * Get atomic composition in format 'E1[n1]E2[n2]' ...
	 * @param elements Element occurrences
	 * @return Atomic composition
	 */
	public static String getAtomicCompositionCompact(List<AtomicElementOccurrence> elements)
	{
		String chain = getAtomicComposition(elements);
		if (chain !=null){
			String[] elts = chain.split(" ");
			chain = "";
			for (int e=0; e<elts.length;e++)
			{
				if (elts[e].endsWith(":1"))
					chain +=  elts[e].substring(0, elts[e].length()-2);
				else
					chain += elts[e].replaceFirst("\\:", "[") + "]";
			}
			return chain;
		}
		else return null;
	}
	
	/**
	 * Merge 2 lists of element occurrences
	 * @param list1 List 1
	 * @param list2 List 2
	 * @return Merged list
	 */
	public static List<AtomicElementOccurrence> merge(List<AtomicElementOccurrence> list1, List<AtomicElementOccurrence> list2){
		for (AtomicElementOccurrence elt1 : list1){
			int e=0;
			boolean found = false;
			//merge lists for common elements
			while (e<list2.size() && !found){
				AtomicElementOccurrence elt2 = list2.get(e);
				if (elt1.getElement().getAtomicNumber() == elt2.getElement().getAtomicNumber()){
					found = true;
					elt1.setCount(elt1.getCount() + elt2.getCount());
					//System.out.println("\tmerge (+"+elt2.getCount()+")");
					list2.remove(e);
				}
				e++;
			}
		}
		//add extra elements
		for (AtomicElementOccurrence elt2 : list2){
			list1.add(elt2);
		}
		return list1;
	}
}
