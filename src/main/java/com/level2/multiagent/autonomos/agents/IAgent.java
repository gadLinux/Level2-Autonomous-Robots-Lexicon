package com.level2.multiagent.autonomos.agents;

import java.math.BigDecimal;

import org.ojalgo.array.Array2D;

public interface IAgent {

	public void setAgentNumber(int agentNumber);
	public int getAgentNumber();

	public Double generateRandom();
	public void initialize();
	public int getSymbols();
	public void setSymbols(int symbols);
	public BigDecimal[] getSymbols(int meaningIndex);
	public BigDecimal[] getMeanings(int symbolIndex);
	public void setSymbols(int meaningIndex, BigDecimal[] newSymbols);
	public void setMeanings(int symbolIndex, BigDecimal[] newMeanings);
	
	public Array2D<BigDecimal> getReceptionMatrix();
	public Array2D<BigDecimal> getSendMatrix();
	
	public void logMatrix();
	
	
	public boolean meaningSended(BigDecimal[] symbols);
	public boolean symbolSended(BigDecimal[] meanings);
	
}
