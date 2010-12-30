package com.level2.multiagent.autonomos.agents.fitness;

import java.math.BigDecimal;

import com.level2.multiagent.autonomos.agents.IAgent;

public interface IFitnessFunction {

	public BigDecimal computeFitness(IAgent agent);

}
