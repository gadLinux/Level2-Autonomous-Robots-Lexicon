package com.level2.multiagent.autonomos.agents.fitness;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.IAgent;

public class UnboundFitnessFunction implements IFitnessFunction {
	public static final Logger logger = LoggerFactory.getLogger(UnboundFitnessFunction.class);

	private BigDecimal fitness;

	public UnboundFitnessFunction()
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
			for(int i=0; i<agent.getSymbolNumber(); i++)
			{
				BigDecimal[] symbols = agent.getSymbols(i);
				int indexSymbols = getBigger(symbols);
				// Check meanings
				BigDecimal[] meanings = agent.getMeanings(indexSymbols);
				int indexMeanings = getBigger(meanings);
				if(indexMeanings==i)
				{
					fitness = fitness.add(BigDecimal.ONE);
				}
			}
			logger.debug("Computing fitness for agent {} with matrix {}", agent.getAgentNumber(), fitness);
			BigDecimal divisor = new BigDecimal(agent.getSymbolNumber());
//			if(divisor.compareTo(BigDecimal.ZERO)!=0)
//			{
//				fitness = fitness.divide(divisor, 5, RoundingMode.HALF_UP);
//			}
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
			if(data[i].compareTo(data[indexVal])>=0)
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
