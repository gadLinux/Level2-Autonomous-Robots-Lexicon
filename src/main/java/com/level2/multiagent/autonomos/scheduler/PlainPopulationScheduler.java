package com.level2.multiagent.autonomos.scheduler;

import java.util.List;

import com.level2.multiagent.autonomos.IAgent;
import com.level2.multiagent.autonomos.decisor.ICommDecisor;

public class PlainPopulationScheduler implements IBasicPopulationScheduler {

	private List<IAgent> population;
	private int iteration;
	private ICommDecisor decisor;
	
	 
	public PlainPopulationScheduler(List<IAgent> population, ICommDecisor decisor)
	{
		this.population = population;
		this.decisor = decisor;
		iteration=0;
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
					decisor.chat(population.get(speakerIndex), population.get(listenerIndex));
				}
			}
		}
		return iteration++;
	}
	
}
