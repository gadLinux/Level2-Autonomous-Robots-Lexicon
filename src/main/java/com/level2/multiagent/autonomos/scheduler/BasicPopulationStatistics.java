package com.level2.multiagent.autonomos.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.decisor.LRICommDecisor;

public class BasicPopulationStatistics implements IBasicPopulationStatistics {
	public static final Logger logger = LoggerFactory.getLogger(BasicPopulationStatistics.class);


	protected int iteration;
	protected int chats;
	protected int chatsSucceded;

	public BasicPopulationStatistics()
	{

	}
	
	@Override
	public float getSucceedRatio()
	{
		return chatsSucceded/chats;
	}

	@Override
	public void logStatistics()
	{
		logger.info(String.format("Iteration %d, chats %d -> Succeded %d vs Error %d", 
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
