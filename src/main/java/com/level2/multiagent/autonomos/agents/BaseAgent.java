package com.level2.multiagent.autonomos.agents;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.ojalgo.array.Array2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.fitness.IFitnessFunction;


public abstract class BaseAgent implements IAgent {
	public static final Logger logger = LoggerFactory.getLogger(BaseAgent.class);
	private static int nextAgentNumber = 0;
	
	private int agentNumber;
	private Random random;
	private IFitnessFunction fitnessFunction;
	private int symbolNumber, meaningNumber;

	public BaseAgent(int symbols, int meanings)
	{
		setupAgent();
		this.symbolNumber=symbols;
		this.meaningNumber=meanings;
		this.fitnessFunction = null;
	}
	
	
	private void setupAgent()
	{
		setAgentNumber(nextAgentNumber++);
		SecureRandom srandom = new SecureRandom();
//		byte seed[] = srandom.generateSeed(numBytes);

		random = new Random(new Date().getTime());
//		random.s();
		logger.debug("New agent spawned!");
	}
	
	
	@Override 
	public Double generateRandom()
	{
		return random.nextDouble();
	}

	
	@Override
	public void setAgentNumber(int agentNumber) {
		this.agentNumber = agentNumber;
	}

	@Override
	public int getAgentNumber() {
		return agentNumber;
	}
	
	
	
	
	
	
	public int getSymbolNumber() {
		return symbolNumber;
	}


	public void setSymbolNumber(int symbolNumber) {
		this.symbolNumber = symbolNumber;
	}


	public int getMeaningNumber() {
		return meaningNumber;
	}


	public void setMeaningNumber(int meaningNumber) {
		this.meaningNumber = meaningNumber;
	}


	/*
	 * Communication stuff
	 */
	public boolean meaningSended(BigDecimal[] symbols)
	{
		return true;
	}
	
	public boolean symbolSended(BigDecimal[] symbols)
	{
		return true;
	}
	
	


	public BigDecimal[] getSymbols(int meaningIndex) {
		BigDecimal[] symbols = new BigDecimal[getSymbolNumber()];
		Array2D<BigDecimal> sendMatrix = getSendMatrix();
		
		if(logger.isDebugEnabled())
			logMatrix();


		for(int i=0;i<getSymbolNumber(); i++)
		{
			symbols[i] = sendMatrix.get(meaningIndex, i);
		}
		
		if(!meaningSended(symbols))
		{
			logger.warn("Error sending symbols, see logs for details");
		}
		return symbols;
	}


	public void setSymbols(int meaningIndex, BigDecimal[] newSymbols) {
		Array2D<BigDecimal> sendMatrix = getSendMatrix();
		
		for(int i=0;i<getSymbolNumber(); i++)
		{
			// Hack
			if(newSymbols[i].compareTo(new BigDecimal(0.0001)) < 0)
			{
				sendMatrix.set(meaningIndex, i, BigDecimal.ZERO);
			}
			else
			{
				sendMatrix.set(meaningIndex, i, newSymbols[i]);
			}
			
			//(meaningIndex, i);
		}

		BigDecimal fitness = BigDecimal.ZERO;

		if(this.fitnessFunction!=null)
			fitness = this.fitnessFunction.computeFitness(sendMatrix);
//		if(logger.isDebugEnabled())
//		{
//			logger.debug("Result Matrix:");
//			logMatrix();
//			logger.debug(String.format("With fitness %f", fitness));
//		}
	}
	
	/**
	 * Returns stored meanings for a selected symbol 
	 */
	public BigDecimal[] getMeanings(int symbolIndex) {
		BigDecimal[] meanings = new BigDecimal[getMeaningNumber()];
		Array2D<BigDecimal> receptMatrix = getReceptionMatrix();
		
		if(logger.isDebugEnabled())
			logMatrix();


		for(int i=0;i<getMeaningNumber(); i++)
		{
			meanings[i] = receptMatrix.get(i,symbolIndex);
		}
		return meanings;
	}

	
	public void setMeanings(int symbolIndex, BigDecimal[] newMeanings) {
		Array2D<BigDecimal> receptMatrix = getReceptionMatrix();
		for(int i=0;i<getMeaningNumber(); i++)
		{
			// Hack
			if(newMeanings[i].compareTo(new BigDecimal(0.0009)) < 0)
			{
				receptMatrix.set(i, symbolIndex, BigDecimal.ZERO);
			}
			else
			{
				receptMatrix.set(i, symbolIndex, newMeanings[i]);
			}
			//(meaningIndex, i);
		}

		BigDecimal fitness = BigDecimal.ZERO;

		if(this.fitnessFunction!=null)
			fitness = this.fitnessFunction.computeFitness(receptMatrix);

//		if(logger.isDebugEnabled())
//		{
//			logger.debug("Result Matrix:");
//			logMatrix();
//			logger.debug(String.format("with fitness %s", fitness.toPlainString()));
//		}

	}
	
	
	
	
	@Override
	public IFitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	@Override
	public void setFitnessFunction(IFitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	/*
	 * Mathematic fuctions
	 */
	

	/**
	 * Generate a set of values that conforms a probability
	 * 
	 * 
	 */
	protected BigDecimal[] generateProbability(int columns)
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
	
	/**
	 * Generates a serie of random values between 0 and 1
	 * @param elements
	 * @return
	 */
	protected BigDecimal[] generateRandom(int elements)
	{
		BigDecimal[] retVal = new BigDecimal[elements];

		for(int i=0;i<elements; i++)
		{

			// Generate a new value between range
			retVal[i] = generateRandomValue();

		}

		return retVal;
	}

	/**
	 * Generate a random value from 0 to 1.
	 * @return
	 */
	protected BigDecimal generateRandomValue()
	{
		return  generateProbabiltyValue(BigDecimal.ZERO);
	}
	
	/**
	 * Generate a probability value given the rest as summatory
	 * @param min Summatory of the values
	 * @return
	 */
	protected BigDecimal generateProbabiltyValue(BigDecimal min)
	{
		//get the range, casting to long to avoid overflow problems
		BigDecimal range = BigDecimal.ONE.subtract(min);
		// compute a fraction of the range, 0 <= frac < range
		BigDecimal fraction = range.multiply(BigDecimal.valueOf(this.generateRandom()));
		//int randomNumber =  (int)(fraction + aStart);    
		logger.debug("Generated {}.", fraction);
		return fraction;
	}
}
