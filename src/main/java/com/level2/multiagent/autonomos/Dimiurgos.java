package com.level2.multiagent.autonomos;

import java.util.ArrayList;

import joptsimple.OptionSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.DeterministicAgent;
import com.level2.multiagent.autonomos.agents.IAgent;
import com.level2.multiagent.autonomos.agents.fitness.NearOneFitnessFunction;
import com.level2.multiagent.autonomos.decisor.ACOCommDecisor;
import com.level2.multiagent.autonomos.decisor.ICommDecisor;
import com.level2.multiagent.autonomos.scheduler.IBasicPopulationScheduler;
import com.level2.multiagent.autonomos.scheduler.IBasicPopulationStatistics;
import com.level2.multiagent.autonomos.scheduler.PlainPopulationScheduler;

public class Dimiurgos {
	public static final Logger logger = LoggerFactory.getLogger(Dimiurgos.class);
	
	private Integer totalAgentNumber = new Integer(200);
	private ArrayList<IAgent> agents;
	private Integer iterationsSucceful= new Integer(50);
	private Integer symbolNumber= new Integer(3);

	public Dimiurgos(OptionSet optionSet)
	{
		
		setupOptions(optionSet);
		
		agents = new ArrayList<IAgent>(totalAgentNumber);
		
		for(Integer i = 0; i<totalAgentNumber; i++)
		{
			
			IAgent newAgent = new DeterministicAgent(symbolNumber,symbolNumber);
			agents.add(newAgent);
			logger.debug("Created {} agent of a total of {}", newAgent.getAgentNumber(), totalAgentNumber);
		}
	}
	
	public void run()
	{
		ICommDecisor ACODecisor = new ACOCommDecisor(new NearOneFitnessFunction());
		IBasicPopulationScheduler sched = new PlainPopulationScheduler(agents, ACODecisor);
		boolean stopCondition=false;
		
		while(!stopCondition && sched.doIteration()<iterationsSucceful)
		{
			//logger.debug("Iteration {} done!", sched.getCurrentIteration());
			sched.logStats();
			IBasicPopulationStatistics stats = sched.getStats();
			logger.info(String.format("Current succes ratio %f", stats.getSucceedRatio()));
			stopCondition = checkStopCondition(stats);
			stats.resetIterationStats();
		}
			
	}
	
	public boolean checkStopCondition(IBasicPopulationStatistics stats)
	{
		boolean stopCondition=false;
		
		// Full success maintained for two iterations
		if(stats.getSucceedRatio()>= 0.98)
		{
			iterationsSucceful++;
			if(iterationsSucceful>=2)
			{
				stopCondition=true;
			}
		}
		
		// Chatted at least twice ok all people
		
		
		return stopCondition;
	}
	
	public void setupOptions(OptionSet options)
	{
		if( options.has( "numberAgents" ))
		{
			logger.debug("New number of objects provided: " + options.valueOf("numberAgents"));
			totalAgentNumber = Integer.valueOf(options.valueOf("numberAgents").toString());
			
		}
	}
	
	public void setTotalAgentNumber(int totalAgentNumber) {
		this.totalAgentNumber = totalAgentNumber;
	}

	public int getTotalAgentNumber() {
		return totalAgentNumber;
	}
	
	
	
}
