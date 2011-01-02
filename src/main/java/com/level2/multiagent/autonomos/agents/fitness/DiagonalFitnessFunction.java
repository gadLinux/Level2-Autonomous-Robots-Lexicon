package com.level2.multiagent.autonomos.agents.fitness;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.IAgent;

public class DiagonalFitnessFunction implements IFitnessFunction {
	public static final Logger logger = LoggerFactory.getLogger(DiagonalFitnessFunction.class);

	private BigDecimal fitness;

	public DiagonalFitnessFunction(IAgent agent)
	{
		fitness=BigDecimal.ZERO;
	}

	/**
	 * Computes fitness based on diagonal values, the result value will 
	 * be between 0 and 1.
	 * @param commMatrix
	 * @return
	 */
	@Override
	public BigDecimal computeFitness(IAgent agent)
	{
		fitness = BigDecimal.ZERO;
		if(agent.getSymbolNumber()>0)
		{
			for(int i=0; i<agent.getSymbolNumber(); i++)
			{
				fitness.add(agent.getSendMatrix().get(i, i));
			}

			fitness.divide(BigDecimal.valueOf(agent.getSymbolNumber()));
		}
		return fitness;
	}

	public BigDecimal getFitness() {
		return fitness;
	}

	public void setFitness(BigDecimal fitness) {
		this.fitness = fitness;
	}


}
