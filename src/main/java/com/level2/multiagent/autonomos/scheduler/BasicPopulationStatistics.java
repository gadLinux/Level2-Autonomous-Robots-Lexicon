package com.level2.multiagent.autonomos.scheduler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.IAgent;
import com.level2.multiagent.autonomos.decisor.LRICommDecisor;

public class BasicPopulationStatistics implements IBasicPopulationStatistics {
	public static final Logger logger = LoggerFactory.getLogger(BasicPopulationStatistics.class);
	public static final Logger loggerResult = LoggerFactory.getLogger("resultFile");


	protected int chatsPerRound;
	protected int totalChats;
	protected int totalChatsSucceded;
	protected int iteration;
	protected int chats;
	protected int chatsSucceded;
	protected BigDecimal totalFitness = BigDecimal.ZERO;
	protected int agentsNumber=0;

	public BasicPopulationStatistics(int chats)
	{
		chatsPerRound=chats;
		loggerResult.info("# This file contains information from one execution");
		loggerResult.info("# Author: Gonzalo Aguilar Delgado <gaguilar@aguilardelgado.com>");
		loggerResult.info("# Iteration\tChats\tSucceded\tError\tMeanFitness");
//		loggerResult.info(String.format("\t%d\t\t%d\t%d\t\t%d\t\t%s", 
//				iteration, totalChats, this.chatsSucceded, (this.totalChats-this.chatsSucceded), getMeanFitness().toPlainString()));
		loggerResult.info(String.format("\t%d\t\t%f\t%f\t\t%f\t\t%s", 
				iteration, getReferenceRatio(), getSucceedRatio(), getErrorRatio(), getMeanFitness().toPlainString()));
		totalChats=0;
	}
	
	@Override
	public Double getSucceedRatio()
	{
		Double result = 0.0;
		if(this.chats>0)
			result = new Double(this.chatsSucceded)/new Double(this.chats);
		return result;
	}

	@Override
	public Double getErrorRatio()
	{
		Double result = 0.0;
		if(this.chats>0)
			result = new Double(this.chats - this.chatsSucceded)/new Double(this.chats);
		return result;
	}
	
	@Override
	public Double getReferenceRatio()
	{
		Double result = 0.0;
		if(this.chats>0)
			result = (getErrorRatio() -  getSucceedRatio()) /2.0;
		return result;
	}
	
	@Override
	public void logStatistics()
	{
		
		logger.info(String.format("Round %d, chats in this round %d -> Succeded %d vs Error %d (Fitness %s)", 
					iteration, chats, this.chatsSucceded, (this.chats-this.chatsSucceded), getMeanFitness().toPlainString()));
		
//		loggerResult.info(String.format("\t%d\t%d\t%d\t\t%d", 
//				iteration, chats, this.chatsSucceded, (this.chats-this.chatsSucceded)));
//		loggerResult.info(String.format(Locale.US, "\t%d\t%d\t%d\t\t%d\t\t%s", 
//				iteration, totalChats, this.totalChatsSucceded, (this.totalChats-this.totalChatsSucceded), getMeanFitness().toPlainString()));
		loggerResult.info(String.format(Locale.US, "\t%d\t%f\t%f\t\t%f\t\t%s", 
				iteration, getReferenceRatio(), getSucceedRatio(), getErrorRatio(), getMeanFitness().toPlainString()));
	}
	
	
	
	@Override
	public void logStepStatistics()
	{
		double  percentage= new Double(chats)/new Double(this.chatsPerRound);
		logger.info(String.format(Locale.US, "Step Iteration %d [%f], chats %d -> Succeded %d vs Error %d (Fitness %s)", 
				iteration, percentage, totalChats, this.totalChatsSucceded, (this.totalChats-this.totalChatsSucceded), getMeanFitness().toPlainString()));
		
//		loggerResult.info(String.format(Locale.US, "\t%f\t%d\t%d\t\t%d\t\t%s", 
//				iteration+percentage, totalChats, this.totalChatsSucceded, (this.totalChats-this.totalChatsSucceded), getMeanFitness().toPlainString()));

		loggerResult.info(String.format(Locale.US, "\t%f\t%f\t%f\t\t%f\t\t%s", 
				iteration+percentage, getReferenceRatio(), getSucceedRatio(), getErrorRatio(), getMeanFitness().toPlainString()));

	}

	@Override
	public void calculateFitness(List<IAgent> population)
	{
		
		agentsNumber = population.size();
		totalFitness=BigDecimal.ZERO;
		for(int agentIndex=0;agentIndex<agentsNumber;agentIndex++)
		{
			totalFitness = totalFitness.add(population.get(agentIndex).getFitness());
		}
	}
	
	public BigDecimal getMeanFitness()
	{
		BigDecimal mean = BigDecimal.ZERO;
		if(agentsNumber>0)
		{
			mean = totalFitness.divide(new BigDecimal(agentsNumber),2, RoundingMode.HALF_UP);
		}
		return mean;
	}

	public void chatSucceded()
	{
		chats++;
		totalChats++;
		chatsSucceded++;
		totalChatsSucceded++;
	}

	public void chatError()
	{
		chats++;
		totalChats++;
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
