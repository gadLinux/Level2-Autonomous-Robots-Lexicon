package com.level2.multiagent.autonomos.agents;

import java.math.BigDecimal;

import org.jfree.util.Log;
import org.ojalgo.array.Array2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.ojalgo.Probabilities;


/**
 * This agent is an example of a probabilistic agent. 
 * This was not finished because I requiered to switch to deterministic agent.
 * @author gad
 *
 */
public class ProbabilisticAgent extends BaseAgent {
	public static final Logger logger = LoggerFactory.getLogger(ProbabilisticAgent.class);

	private Array2D<BigDecimal> receptMatrix;
	private Array2D<BigDecimal> sendMatrix;

	private int symbols;

	public ProbabilisticAgent()
	{
		super();
		symbols=3;

		receptMatrix = Array2D.makeBig(symbols, symbols);
		sendMatrix = Array2D.makeBig(symbols, symbols);
	}

	@Override
	public void initialize() {
		randomizeMatrix(sendMatrix,true);
		randomizeMatrix(receptMatrix,false);
		logMatrix(receptMatrix, sendMatrix);
	}

	public void randomizeMatrix(Array2D<BigDecimal> matrix, boolean byRows)
	{
		for(int i=0; i<symbols; i++)
		{
			BigDecimal[] row = generateProbability(symbols);
			for(int j=0; j<symbols; j++)
			{
				if(byRows)
					matrix.set(i, j, row[j]);
				else
					matrix.set(j, i, row[j]);
			}
		}
	}



	@Override
	public int getSymbols() {
		return symbols;
	}

	@Override
	public void setSymbols(int symbols) {
		this.symbols = symbols;
	}

	@Override
	public boolean meaningSended(BigDecimal[] symbols)
	{
		boolean retVal = true;
		// Do sanity checks
		if(!Probabilities.isProbability(symbols))
		{
			logger.error("We are not using probabilities for symbols!");
			retVal=false;
		}
		return retVal;
	}
	
	@Override
	public boolean symbolSended(BigDecimal[] meanings)
	{
		boolean retVal = true;
		// Do sanity checks
		if(!Probabilities.isProbability(meanings))
		{
			logger.error("We are not using probabilities for meanings!");
			retVal=false;
		}
		return retVal;
	}
	
	
	

	public void logMatrix()
	{
		logMatrix(receptMatrix, sendMatrix);
	}

	public void logMatrix(Array2D<BigDecimal> recept, Array2D<BigDecimal> send)
	{
		logger.debug("Agent {} matrices:", this.getAgentNumber());
		logger.debug("|-------SEND--------| |--------REC--------|");
		for(int i=0; i<symbols; i++)
		{
			try
			{
				logger.debug(String.format("| %.3f %.3f %.3f | | %.3f %.3f %.3f |", 
						send.get(i,0), send.get(i,1), send.get(i,2), recept.get(i, 0), recept.get(i, 1), recept.get(i, 2)));
			}catch(IllegalArgumentException ex)
			{
				Log.error(ex.getMessage());
			}
		}
		logger.debug("|-------------------| |-------------------|");

	}

	@Override
	public Array2D<BigDecimal> getReceptionMatrix() {
		return this.receptMatrix;
	}

	@Override
	public Array2D<BigDecimal> getSendMatrix() {
		return this.sendMatrix;
	}



}
