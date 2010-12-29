package com.level2.multiagent.autonomos.agents;

import java.math.BigDecimal;

import org.jfree.util.Log;
import org.ojalgo.array.Array2D;
import org.ojalgo.array.ArrayAnyD;
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

	public DeterministicAgent(int symbols, int meanings)
	{
		super(symbols,meanings);
		initializeMatrices();
	}

	
	@Override
	public void initializeMatrices() {
		commMatrix = Array2D.makeBig(this.getMeaningNumber(), this.getSymbolNumber());
//		randomizeMatrix(commMatrix);
		if(this.getAgentNumber()==0)
			determineMatrix(commMatrix, false);
		else
			determineMatrix(commMatrix, true);
	}

	
	private void randomizeMatrix(Array2D<BigDecimal> matrix)
	{
		for(int i=0; i<this.getSymbolNumber(); i++)
		{
			BigDecimal[] row = generateRandom(this.getSymbolNumber());
			for(int j=0; j<this.getSymbolNumber(); j++)
			{
				matrix.set(i, j, row[j]);
			}
		}
	}

	
	private void determineMatrix(Array2D<BigDecimal> matrix, boolean transpose)
	{

		
		if(transpose)
		{
			matrix.set(0, 0, new BigDecimal(0.3));
			matrix.set(1, 0, new BigDecimal(0.8));
			matrix.set(2, 0, new BigDecimal(0.2));
			matrix.set(0, 1, new BigDecimal(0.8));
			matrix.set(1, 1, new BigDecimal(0.3));
			matrix.set(2, 1, new BigDecimal(0.2));
			matrix.set(0, 2, new BigDecimal(0.2));
			matrix.set(1, 2, new BigDecimal(0.3));
			matrix.set(2, 2, new BigDecimal(0.9));
			
		}else
		{
			matrix.set(0, 0, new BigDecimal(0.3));
			matrix.set(0, 1, new BigDecimal(0.8));
			matrix.set(0, 2, new BigDecimal(0.2));
			matrix.set(1, 0, new BigDecimal(0.8));
			matrix.set(1, 1, new BigDecimal(0.3));
			matrix.set(1, 2, new BigDecimal(0.2));
			matrix.set(2, 0, new BigDecimal(0.2));
			matrix.set(2, 1, new BigDecimal(0.3));
			matrix.set(2, 2, new BigDecimal(0.9));			
		}
		
		
		return;
	}
	

	public Array2D<BigDecimal> getCommMatrix() {
		return commMatrix;
	}

	public void setCommMatrix(Array2D<BigDecimal> commMatrix) {
		this.commMatrix = commMatrix;
	}
	


	public void logMatrix()
	{
		logMatrix(commMatrix);
	}

	public void logMatrix(Array2D<BigDecimal> commMtx)
	{
		logger.debug("Agent {} matrix:", this.getAgentNumber());
		logger.debug("|-------CMTX--------|");
		for(int i=0; i<this.getSymbolNumber(); i++)
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
