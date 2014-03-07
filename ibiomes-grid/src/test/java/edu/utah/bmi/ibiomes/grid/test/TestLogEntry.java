package edu.utah.bmi.ibiomes.grid.test;

import java.util.Date;

import edu.utah.bmi.ibiomes.history.LogEntry;

public class TestLogEntry {
	public static void main(String[] args) throws Exception
	{
		LogEntry log = new LogEntry(new Date(), "This is a test");
		System.out.println(log.toString());
		
		log = new LogEntry("02/02/12 18:43:38# This is a test");
		System.out.println(log.toString());
	}
}
