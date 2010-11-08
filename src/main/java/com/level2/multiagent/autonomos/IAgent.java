package com.level2.multiagent.autonomos;

import java.math.BigDecimal;

public interface IAgent {

	public void setAgentNumber(int agentNumber);
	public int getAgentNumber();

	public Double generateRandom();
	public void initialize();
	public int getSymbols();
	public void setSymbols(int symbols);
	public BigDecimal[] getSymbols(int meaningIndex);
	public BigDecimal[] getMeanings(int symbolIndex);
}
