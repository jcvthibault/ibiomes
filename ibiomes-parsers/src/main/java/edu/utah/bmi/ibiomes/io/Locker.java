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

package edu.utah.bmi.ibiomes.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Date;

import edu.utah.bmi.Utils;

/**
 * Directory locker. Use to avoid parallel writes from different users.
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class Locker {
	
	private final static String PATH_FOLDER_SEPARATOR  = (Utils.isWindows() ? "\\" : "/");
	public final static String LOCK_FILE_NAME = ".ibiomes-lock";
	private String directoryPath;
	private String key;
	private FileChannel channel = null;
	private FileLock javaLock = null;
	
	/**
	 * Create new locker
	 * @param dirPath Path to directory to (un)lock
	 * @throws IOException 
	 */
	public Locker(String dirPath) throws IOException{
		File directory = new File(dirPath);
		if (directory.exists() && directory.isDirectory()){
			directoryPath = directory.getCanonicalPath();
			key = System.getProperty("user.name") + " [" +(new Date()).toString() + "]";
		}
		else throw new IOException("Directory " + dirPath + " does not exist!");
	}
	
	/**
	 * Lock directory
	 * @return True if it was successfully locked
	 * @throws IOException
	 */
	public boolean lock() throws IOException {
		File file = new File(directoryPath + PATH_FOLDER_SEPARATOR + LOCK_FILE_NAME);
		boolean success = false;
		try{
			success = file.createNewFile();
			if (success){
				channel = new RandomAccessFile(file, "rw").getChannel();
				try {
					javaLock = channel.tryLock();
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write(key);
					bw.close();
					javaLock.release();
			        channel.close();
		        } catch (OverlappingFileLockException e) {
			        channel.close();
		            return false;
		        }
			}
		}
		catch (IOException ioe){
			// release the lock on file
			if (javaLock!=null) javaLock.release();
	        if (channel!=null) channel.close();
		}
		return success;
	}
	
	/**
	 * Lock directory, and wait until the directory can be locked if not available yet
	 * @return True if it was successfully locked
	 * @throws IOException
	 */
	public boolean waitAndLock() throws IOException {
		File file = new File(directoryPath + PATH_FOLDER_SEPARATOR + LOCK_FILE_NAME);
		boolean success = false;
		try{
			while (!success){
				success = file.createNewFile();
				if (success){
					channel = new RandomAccessFile(file, "rw").getChannel();
					javaLock = channel.lock();
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write(String.valueOf(key));
					bw.close();
					javaLock.release();
			        channel.close();
				}
			}
		}
		catch (IOException ioe){
			// release the lock on file
			if (javaLock!=null) javaLock.release();
	        if (channel!=null) channel.close();
		}
		return success;
	}
	
	/**
	 * Unlock directory
	 * @return True if it was successfully unlocked
	 * @throws IOException
	 */
	public boolean unlock() throws IOException{
		File file = new File(directoryPath + PATH_FOLDER_SEPARATOR + LOCK_FILE_NAME);
		//delete file
		boolean success = file.delete();
        return success;
	}

	/**
	 * Check if the directory is locked
	 * @return True if locked
	 */
	public boolean isLocked(){
		File file = new File(directoryPath + PATH_FOLDER_SEPARATOR + LOCK_FILE_NAME);
		return (file.exists());
	}
}
