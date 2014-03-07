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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.util.ArrayList;
import java.util.List;

public class AmberFileSection 
{
	private String _title;
	private String[] _format;
	private String _content;
	private List<String> _values;
	private String _type;
	
	/**
	 * Constructor
	 * @param title
	 * @param format
	 * @param content
	 * @param values
	 * @param type
	 */
	public AmberFileSection(String title, String[] format, String content, ArrayList<String> values, String type)
	{
		_title = title;
		_format = format;
		_content = content;
		_values = values;
		_type = type;
	}
	
	public AmberFileSection() {
		
	}

	public String getTitle() {
		return _title;
	}
	
	public String[] getFormat() {
		return _format;
	}
	
	public String getContent() {
		return _content;
	}
	public List<String> getValues() {
		return _values;
	}
	public String getValue(int i) {
		return _values.get(i);
	}
	public String getType() {
		return _type;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public void setFormat(String[] format) {
		this._format = format;
	}

	public void setContent(String content) {
		this._content = content;
	}

	public void setValues(List<String> values) {
		this._values = values;
	}

	public void setType(String type) {
		this._type = type;
	}
}
