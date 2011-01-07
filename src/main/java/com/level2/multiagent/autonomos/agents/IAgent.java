package com.level2.multiagent.autonomos.agents;

import java.math.BigDecimal;

import org.ojalgo.array.Array2D;

import com.level2.multiagent.autonomos.agents.fitness.IFitnessFunction;

public interface IAgent {

	public void setAgentNumber(int agentNumber);
	public int getAgentNumber();

	public Double generateRandom();
	public void initializeMatrices();
	public BigDecimal[] getSymbols(int meaningIndex);
	public BigDecimal[] getMeanings(int symbolIndex);
	public void setSymbols(int meaningIndex, BigDecimal[] newSymbols);
	public void setMeanings(int symbolIndex, BigDecimal[] newMeanings);
	
	public Array2D<BigDecimal> getReceptionMatrix();
	public Array2D<BigDecimal> getSendMatrix();
	
	public void logMatrix();
	

	public int getSymbolNumber();
	public void setSymbolNumber(int symbolNumber);
	public int getMeaningNumber();
	public void setMeaningNumber(int meaningNumber);
	public boolean meaningSended(BigDecimal[] symbols);
	public boolean symbolSended(BigDecimal[] meanings);
	BigDecimal getFitness();
	void setFitness(BigDecimal fitness);
	BigDecimal generateProbabiltyValue(BigDecimal min);
	
}
