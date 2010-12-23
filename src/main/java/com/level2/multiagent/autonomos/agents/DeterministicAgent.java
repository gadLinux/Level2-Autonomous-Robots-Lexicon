package com.level2.multiagent.autonomos.agents;

import java.math.BigDecimal;

import org.jfree.util.Log;
import org.ojalgo.array.Array2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This agent is an example of a deterministic agent. 
 * This was not finished because I requiered to switch to deterministic agent.
 * @author gad
 *
 */
public class DeterministicAgent extends BaseAgent {
	public static final Logger logger = LoggerFactory.getLogger(DeterministicAgent.class);

	/**
	 * Characteristics
	 * 	Each agent has only one association Matrix
	 */
	
	/**
	 * 			Symbols
	 *  Meanings	s1	s2	s3
	 *  		______________
	 * 		m1	|   0	1	0
	 * 		m2	|	1	0	1
	 * 		m3	|	0	0	0
	 * 
	 */
	private Array2D<BigDecimal> commMatrix;


	private int symbols, meanings;

	public DeterministicAgent()
	{
		super();
		symbols=3;
		meanings=3;

		commMatrix = Array2D.makeBig(meanings, symbols);

	}

	@Override
	public void initialize() {
		randomizeMatrix(commMatrix);
	}

	
	private void randomizeMatrix(Array2D<BigDecimal> matrix)
	{
		for(int i=0; i<symbols; i++)
		{
			BigDecimal[] row = generateRandom(symbols);
			for(int j=0; j<symbols; j++)
			{
				matrix.set(i, j, row[j]);
			}
		}
	}


	public Array2D<BigDecimal> getCommMatrix() {
		return commMatrix;
	}

	public void setCommMatrix(Array2D<BigDecimal> commMatrix) {
		this.commMatrix = commMatrix;
	}
	
	@Override
	public int getSymbols() {
		return symbols;
	}

	@Override
	public void setSymbols(int symbols) {
		this.symbols = symbols;
	}



	public void logMatrix()
	{
		logMatrix(commMatrix);
	}

	public void logMatrix(Array2D<BigDecimal> commMtx)
	{
		logger.debug("Agent {} matrix:", this.getAgentNumber());
		logger.debug("|-------CMTX--------|");
		for(int i=0; i<symbols; i++)
		{
			try
			{
				logger.debug(String.format("| %.3f %.3f %.3f |", 
						commMtx.get(i,0), commMtx.get(i,1), commMtx.get(i,2)));
			}catch(IllegalArgumentException ex)
			{
				Log.error(ex.getMessage());
			}
		}
		logger.debug("|-------------------|");

	}
	@Override
	public Array2D<BigDecimal> getReceptionMatrix() {
		return this.commMatrix;
	}

	@Override
	public Array2D<BigDecimal> getSendMatrix() {
		return this.commMatrix;
	}



}
