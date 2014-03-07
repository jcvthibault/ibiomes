/* iBIOMES - Integrated Biomolecular Simulations
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

package edu.utah.bmi.ibiomes.local.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import edu.utah.bmi.ibiomes.io.Locker;

/**
 * Test to check locker system
 * @author Julien Thibault, University of Utah
 *
 */
public class TestLocker{

	private final Logger logger = Logger.getLogger(TestLocker.class);

	@Test
	public void testLock() throws Exception{
		
		Locker locker = new Locker(TestCommon.TEST_DATA_DIR);
		
		Assert.assertEquals(locker.lock(), true);
		Assert.assertEquals(locker.isLocked(), true);
		locker.unlock();
		Assert.assertEquals(locker.isLocked(), false);
	}
	
	@Test
	public void testLockParallel() throws Exception{
		
		Locker locker1 = new Locker(TestCommon.TEST_DATA_DIR);
		Locker locker2 = new Locker(TestCommon.TEST_DATA_DIR);
		//lock
		locker1.lock();
		Assert.assertEquals(locker2.lock(), false);
		Assert.assertEquals(locker1.unlock(), true);
		Assert.assertEquals(locker2.waitAndLock(), true);
		Assert.assertEquals(locker2.unlock(), true);
	}
}
