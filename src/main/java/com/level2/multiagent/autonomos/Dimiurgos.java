package com.level2.multiagent.autonomos;

import java.util.ArrayList;

import joptsimple.OptionSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.decisor.BaseCommDecisor;
import com.level2.multiagent.autonomos.decisor.ICommDecisor;
import com.level2.multiagent.autonomos.probabilistic.UnoAgent;
import com.level2.multiagent.autonomos.scheduler.IBasicPopulationScheduler;
import com.level2.multiagent.autonomos.scheduler.PlainPopulationScheduler;

public class Dimiurgos {
	public static final Logger logger = LoggerFactory.getLogger(Dimiurgos.class);
	
	private Integer totalAgentNumber = new Integer(100);
	private ArrayList<IAgent> agents;

	public Dimiurgos(OptionSet optionSet)
	{
		
		setupOptions(optionSet);
		
		agents = new ArrayList<IAgent>(totalAgentNumber);
		
		for(Integer i = 0; i<totalAgentNumber; i++)
		{
			IAgent newAgent = new UnoAgent();
			
			newAgent.initialize();
			
			agents.add(newAgent);
			logger.debug("Creating {} agent of a total of {}", newAgent.getAgentNumber(), totalAgentNumber);
		}
	}
	
	public void run()
	{
		ICommDecisor LRIDecisor = new BaseCommDecisor();
		IBasicPopulationScheduler sched = new PlainPopulationScheduler(agents, LRIDecisor);
		
		for(int i = 100; i<sched.doIteration();)
		{
			logger.debug("Iteration {} done!", i);
		}
			
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
