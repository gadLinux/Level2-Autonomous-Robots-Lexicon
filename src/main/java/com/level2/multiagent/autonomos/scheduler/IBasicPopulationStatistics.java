package com.level2.multiagent.autonomos.scheduler;

import java.util.List;

import com.level2.multiagent.autonomos.agents.IAgent;

public interface IBasicPopulationStatistics {

	void logStatistics();

	int iterationDone();

	double getSucceedRatio();

	void resetIterationStats();

	void logStepStatistics();

	void calculateFitness(List<IAgent> population);

}
