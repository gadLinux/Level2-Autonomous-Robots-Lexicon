package com.level2.multiagent.autonomos.agents.fitness;

import java.math.BigDecimal;

import org.ojalgo.array.Array2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.BaseAgent;

public class DiagonalFitnessFunction implements IFitnessFunction {
	public static final Logger logger = LoggerFactory.getLogger(DiagonalFitnessFunction.class);

	private BigDecimal fitness;
	private int meanings, symbols;
	
	public DiagonalFitnessFunction(int meanings, int symbols)
	{
		this.meanings = meanings;
		this.symbols = symbols;
		fitness=BigDecimal.ZERO;
	}
	
	/**
	 * Computes fitness based on diagonal values, the result value will 
	 * be between 0 and 1.
	 * @param commMatrix
	 * @return
	 */
	@Override
	public BigDecimal computeFitness(Array2D<BigDecimal> commMatrix)
	{
		fitness = BigDecimal.ZERO;
		for(int i=0; i<symbols; i++)
		{
			fitness.add(commMatrix.get(i, i));
		}
		fitness.divide(BigDecimal.valueOf(symbols));
		return fitness;
	}

	public BigDecimal getFitness() {
		return fitness;
	}

	public void setFitness(BigDecimal fitness) {
		this.fitness = fitness;
	}

	public int getMeanings() {
		return meanings;
	}

	public void setMeanings(int meanings) {
		this.meanings = meanings;
	}

	public int getSymbols() {
		return symbols;
	}

	public void setSymbols(int symbols) {
		this.symbols = symbols;
	}
	
	
	
}
