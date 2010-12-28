package com.level2.multiagent.autonomos.agents.fitness;

import java.math.BigDecimal;

import org.ojalgo.array.Array2D;

public interface IFitnessFunction {

	public BigDecimal computeFitness(Array2D<BigDecimal> commMatrix);

}
