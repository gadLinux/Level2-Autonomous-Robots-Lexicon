package com.level2.multiagent.autonomos.scheduler;

import java.math.BigDecimal;
import java.util.List;

import com.level2.multiagent.autonomos.agents.IAgent;

public interface IBasicPopulationStatistics {

	void logStatistics();

	int iterationDone();

	Double getSucceedRatio();

	void resetIterationStats();

	void logStepStatistics();

	void calculateFitness(List<IAgent> population);

	Double getErrorRatio();

	Double getReferenceRatio();

	BigDecimal getFirstIterationFullFitness();

	void setFirstIterationFullFitness(BigDecimal firstIterationFullFitness);

	BigDecimal getLastIterationFullFitness();

	void setLastIterationFullFitness(BigDecimal lastIterationFullFitness);

}
