package com.level2.multiagent.autonomos.probabilistic;

import java.math.BigDecimal;

import org.ojalgo.array.Array2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.BaseAgent;

public class UnoAgent extends BaseAgent {
	public static final Logger logger = LoggerFactory.getLogger(UnoAgent.class);
	
	private Array2D<BigDecimal> receptMatrix;
	private Array2D<BigDecimal> sendMatrix;
	
	private int symbols;
	
	public UnoAgent()
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
	
	
	public BigDecimal[] generateProbability(int columns)
	{
		BigDecimal summatory = BigDecimal.ZERO;
		BigDecimal[] retVal = new BigDecimal[columns];
		
		for(int i=0;i<columns; i++)
		{
			if(i==(columns-1))
			{
				// Last value should accumulate rest of probability
				retVal[i] = BigDecimal.ONE.subtract(summatory);
				logger.debug("Last value is {} because the rest", retVal[i]);
			}
			else
			{
				// Generate a new value between range
				retVal[i] = generateProbabiltyValue(summatory);
				
			}
			summatory = summatory.add(retVal[i]);
		}
		
		if(summatory.compareTo(BigDecimal.ONE)!=0)
		{
			logger.warn("Generated values are not a probability");
		}
		
		return retVal;
	}
	
	public BigDecimal generateProbabiltyValue(BigDecimal min)
	{
		 //get the range, casting to long to avoid overflow problems
		BigDecimal range = BigDecimal.ONE.subtract(min);
	    // compute a fraction of the range, 0 <= frac < range
		BigDecimal fraction = range.multiply(BigDecimal.valueOf(this.generateRandom()));
	    //int randomNumber =  (int)(fraction + aStart);    
	    logger.debug("Generated {}.", fraction);
	    return fraction;
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
	public BigDecimal[] getSymbols(int meaningIndex) {
		BigDecimal[] symbols = new BigDecimal[getSymbols()];
		
		if(logger.isDebugEnabled())
			logMatrix();
		
		
		for(int i=0;i<getSymbols(); i++)
		{
			symbols[i] = sendMatrix.get(meaningIndex, i);
		}
		return symbols;
	}

	@Override
	public BigDecimal[] getMeanings(int symbolIndex) {
		BigDecimal[] meanings = new BigDecimal[getSymbols()];

		if(logger.isDebugEnabled())
			logMatrix();

		
		for(int i=0;i<getSymbols(); i++)
		{
			meanings[i] = receptMatrix.get(i,symbolIndex);
		}
		return meanings;
	}

	
	public void logMatrix()
	{
		logMatrix(receptMatrix, sendMatrix);
	}
	
	public void logMatrix(Array2D<BigDecimal> recept, Array2D<BigDecimal> send)
	{
		logger.info("Agent {} matrices:", this.getAgentNumber());
		for(int i=0; i<symbols; i++)
		{
			logger.info("| "+send.get(i,0)+" "+send.get(i,1)+" "+send.get(i,2)+ " | | "+ recept.get(i, 0) + " " + recept.get(i,1) + " " + recept.get(i,2) +" |");
		}
		
	}
	
	

}
