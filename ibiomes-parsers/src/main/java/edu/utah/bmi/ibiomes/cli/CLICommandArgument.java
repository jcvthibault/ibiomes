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

package edu.utah.bmi.ibiomes.cli;

/**
 * CLI command argument definition
 * @author Julien Thibault, University of Utah
 *
 */
public class CLICommandArgument
{
	private String keyword;
	private String shortValueDescription;
	private String definition;
	private boolean requiresValue;
	private boolean optional;

	/**
	 * New command argument
	 * @param keyword
	 * @param shortValueDescription
	 * @param definition
	 * @param requiresValue
	 * @param optional
	 */
	public CLICommandArgument(
			String keyword,
			String shortValueDescription,
			String definition,
			boolean requiresValue,
			boolean optional){
		
		this.keyword = keyword;
		this.shortValueDescription = shortValueDescription;
		this.definition = definition;
		this.requiresValue = requiresValue;
		this.optional = optional;
	}
	
	/**
	 * Get argument keyword
	 * @return Argument keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * Set argument keyword
	 * @param keyword Argument keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * Get short value description for the argument
	 * @return Short value description for the argument
	 */ 
	public String getShortValueDescription() {
		return shortValueDescription;
	}

	/**
	 * Set short value description for the argument
	 * @param shortValueDescription Short value description for the argument
	 */
	public void setShortValueDescription(String shortValueDescription) {
		this.shortValueDescription = shortValueDescription;
	}

	/**
	 * Get argument definition
	 * @return Argument definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * Set argument definition
	 * @param definition Argument definition
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * Check if argument requires a value 
	 * @return True if value is required
	 */
	public boolean isRequiresValue() {
		return requiresValue;
	}

	/**
	 * Set whether argument requires a value or not 
	 * @param requiresValue True if required
	 */
	public void setRequiresValue(boolean requiresValue) {
		this.requiresValue = requiresValue;
	}

	/**
	 * Check if argument is optional 
	 * @return True if it is optional
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * Set whether the argument is optional or not
	 * @param optional True if it is optional
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	/**
	 * Get string to use for full description of the option
	 * @return Full description of the option
	 */
	public String getUsageDescription(){
		String argumentDescStr = "[ " + this.keyword + " ";
		if (this.isRequiresValue()){
			argumentDescStr += this.shortValueDescription + " ";
		}
		argumentDescStr += "]  " + this.definition;
		return argumentDescStr;
	}
	
	/**
	 * Get string to use for short description of the option
	 * @return Short description of the option
	 */
	public String getUsageArgument(){
		String left = "[";
		String right = "]";
		if (!optional){
			left = "<";
			right = ">";
		}
		if (requiresValue)
			return left + keyword + " " + shortValueDescription + right;
		else return left + keyword + right;
	}
}
