package com.level2.multiagent.autonomos.scheduler;


public interface IBasicPopulationScheduler {

	public int doIteration();

	public int getCurrentIteration();

	public void logStats();

	public IBasicPopulationStatistics getStats();

}
