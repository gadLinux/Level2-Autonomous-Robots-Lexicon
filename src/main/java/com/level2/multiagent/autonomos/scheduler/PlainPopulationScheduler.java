package com.level2.multiagent.autonomos.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.IAgent;
import com.level2.multiagent.autonomos.decisor.BaseCommDecisor;
import com.level2.multiagent.autonomos.decisor.ICommDecisor;

public class PlainPopulationScheduler implements IBasicPopulationScheduler {
	public static final Logger logger = LoggerFactory.getLogger(PlainPopulationScheduler.class);

	private List<IAgent> population;
	private BasicPopulationStatistics stats;
	private ICommDecisor decisor;
	
	 
	public PlainPopulationScheduler(List<IAgent> population, ICommDecisor decisor)
	{
		this.population = population;
		this.decisor = decisor;
		int popSize = population.size();
		this.stats=new BasicPopulationStatistics((popSize*popSize)-popSize);
	}
	
	@Override
	public int doIteration()
	{
//		int speakerIndex, listenerIndex;
		int populationSize = this.population.size();
		for(int speakerIndex=0;speakerIndex<populationSize;speakerIndex++)
		{
			for(int listenerIndex=0;listenerIndex<populationSize;listenerIndex++)
			{
				if(speakerIndex!=listenerIndex)
				{
					logger.debug(String.format("Talking agent %d -> %d", speakerIndex, listenerIndex));
					if(decisor.chat(population.get(speakerIndex), population.get(listenerIndex)))
					{
						stats.chatSucceded();
					}
					else
						stats.chatError();
				}
				
			}
			stats.calculateFitness(population);
			stats.logStepStatistics();
		}
		return stats.iterationDone();
	}
	
	@Override
	public int getCurrentIteration()
	{
		return stats.getIteration();
	}
	
	@Override
	public void logStats()
	{
		stats.logStatistics();
	}
	
	@Override
	public IBasicPopulationStatistics getStats()
	{
		return this.stats;
	}
}
