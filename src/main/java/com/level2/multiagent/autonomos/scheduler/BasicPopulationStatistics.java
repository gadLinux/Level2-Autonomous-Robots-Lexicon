package com.level2.multiagent.autonomos.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.decisor.LRICommDecisor;

public class BasicPopulationStatistics implements IBasicPopulationStatistics {
	public static final Logger logger = LoggerFactory.getLogger(BasicPopulationStatistics.class);
	public static final Logger loggerResult = LoggerFactory.getLogger("resultFile");


	protected int iteration;
	protected int chats;
	protected int chatsSucceded;

	public BasicPopulationStatistics()
	{
		loggerResult.info("# This file contains information from one execution");
		loggerResult.info("# Author: Gonzalo Aguilar Delgado <gaguilar@aguilardelgado.com>");
		loggerResult.info("# Iteration\tChats\tSucceded\tError");
	}
	
	@Override
	public double getSucceedRatio()
	{
		Double result = 0.0;
		if(this.chats>0)
			result = new Double(this.chatsSucceded)/new Double(this.chats);
		return result;
	}

	@Override
	public void logStatistics()
	{
		logger.info(String.format("Iteration %d, chats %d -> Succeded %d vs Error %d", 
					iteration, chats, this.chatsSucceded, (this.chats-this.chatsSucceded)));
		
		loggerResult.info(String.format("\t%d\t%d\t%d\t\t%d", 
				iteration, chats, this.chatsSucceded, (this.chats-this.chatsSucceded)));
	
	}


	public void chatSucceded()
	{
		chats++;
		chatsSucceded++;
	}

	public void chatError()
	{
		chats++;
	}

	@Override
	public int iterationDone()
	{

		return iteration++;
	}

	@Override
	public void resetIterationStats()
	{
		this.chatsSucceded=0;
		this.chats=0;
	}
	
	public int getIteration() {
		return iteration;
	}
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	public int getChats() {
		return chats;
	}
	public void setChats(int chats) {
		this.chats = chats;
	}
	public int getChatsSucceded() {
		return chatsSucceded;
	}
	public void setChatsSucceded(int chatsSucceded) {
		this.chatsSucceded = chatsSucceded;
	}

}
