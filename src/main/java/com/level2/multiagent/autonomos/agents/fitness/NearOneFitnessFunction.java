package com.level2.multiagent.autonomos.agents.fitness;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.IAgent;

public class NearOneFitnessFunction implements IFitnessFunction {
	public static final Logger logger = LoggerFactory.getLogger(NearOneFitnessFunction.class);

	private BigDecimal fitness;

	public NearOneFitnessFunction()
	{
		fitness=BigDecimal.ZERO;
	}

	/**
	 * Computes fitness based how near 
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
			logger.debug("Computing fitness for agent {} with matrix:", agent.getAgentNumber());
			agent.logMatrix();
			for(int i=0; i<agent.getSymbolNumber(); i++)
			{
				BigDecimal[] symbols = agent.getSymbols(i);
				int indexSymbols = getBigger(symbols);
				// Check meanings
				BigDecimal[] meanings = agent.getMeanings(indexSymbols);
				int indexMeanings = getBigger(meanings);
				if(indexMeanings==i)
				{
					fitness.add(BigDecimal.ONE);
				}
			}

			fitness.divide(BigDecimal.valueOf(agent.getSymbolNumber()));
		}
		return fitness;
	}
	
	/**
	 * 
	 * @return
	 */
	private int getBigger(BigDecimal[] data)
	{
		int indexVal = 0;
		for(int i=0;i<data.length;i++)
		{
			if(data[i].compareTo(data[indexVal])<=0)
			{
				indexVal = i;
			}
		}
		return indexVal;
	}

	public BigDecimal getFitness() {
		return fitness;
	}

	public void setFitness(BigDecimal fitness) {
		this.fitness = fitness;
	}



}
