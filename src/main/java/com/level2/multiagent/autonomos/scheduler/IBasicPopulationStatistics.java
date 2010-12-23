package com.level2.multiagent.autonomos.scheduler;

public interface IBasicPopulationStatistics {

	void logStatistics();

	int iterationDone();

	double getSucceedRatio();

	void resetIterationStats();

}
