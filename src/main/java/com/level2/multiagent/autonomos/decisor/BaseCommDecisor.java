package com.level2.multiagent.autonomos.decisor;

import java.math.BigDecimal;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.IAgent;
import com.level2.multiagent.autonomos.agents.fitness.IFitnessFunction;
import com.level2.ojalgo.Probabilities;

public abstract class BaseCommDecisor implements ICommDecisor {
	public static final Logger logger = LoggerFactory.getLogger(BaseCommDecisor.class);

	protected BigDecimal kSpeaker;
	protected BigDecimal kListener;
	protected IFitnessFunction fitnessFunction;

	public BaseCommDecisor(IFitnessFunction func)
	{
		kSpeaker = BigDecimal.valueOf(0.2);
		kListener = BigDecimal.ONE.divide(BigDecimal.valueOf(2.0));
		this.fitnessFunction = func;
	}


	public abstract BigDecimal[] compute(BigDecimal[] values, int selectedIndex, Boolean succeded);

	public void updateSpeaker(IAgent speaker, int meaningIndex, int symbolIndex, Boolean succeded)
	{
		BigDecimal[] symbolRow = speaker.getSymbols(meaningIndex);
		BigDecimal[] resultRow = compute(symbolRow,symbolIndex, succeded);
		speaker.setSymbols(meaningIndex, resultRow);
		if(logger.isDebugEnabled())
		{
			logger.debug("New matrix:");
			speaker.logMatrix();
		}
		if(this.fitnessFunction!=null)
			this.fitnessFunction.computeFitness(speaker);
		//
		//symbolRow = speaker.getSymbols(meaningIndex);
	}
	
	public void updateListener(IAgent listener, int meaningIndex, int symbolIndex, Boolean succeded)
	{
		BigDecimal[] meaningsCol = listener.getMeanings(symbolIndex);
		BigDecimal[] resultCol = compute(meaningsCol,meaningIndex, succeded);
		listener.setMeanings(symbolIndex, resultCol);

		if(logger.isDebugEnabled())
		{
			logger.debug("New matrix:");
			listener.logMatrix();
		}
		if(this.fitnessFunction!=null)
			this.fitnessFunction.computeFitness(listener);
	}

	/**
	 * Will make them chat using selected algorithm
	 */
	@Override
	public Boolean chat(IAgent speaker, IAgent listener)
	{
		Boolean commSuccess=false;
		int meanings=speaker.getMeaningNumber();
		int timesSuccess=0;
		
		if(logger.isDebugEnabled())
		{
			speaker.logMatrix();
			listener.logMatrix();
		}
		
		for(int i=0; i<meanings; i++)
		{
			int symbolIndex = getSymbolIndexForMeaning(speaker, i);
			int receptMeaningIndex = getMeaningIndexForSymbol(listener, symbolIndex);

			/*
			 * If meanings are equivalent
			 */
			if(i==receptMeaningIndex)
			{
				timesSuccess++;
				logger.debug("Meaning understood!");
				updateSpeaker(speaker, i, symbolIndex, true);
				updateListener(listener,receptMeaningIndex,symbolIndex, true);
			}
			else
			{
				logger.debug("I don't understand!");
				updateSpeaker(speaker, i, symbolIndex, false);
				updateListener(listener,i,receptMeaningIndex, false);
			}
		}
		logger.debug(String.format("Finish chatting agent %d and agent %d", speaker.getAgentNumber(), listener.getAgentNumber()));
		// Everything must be understood to be a success
		if(meanings==timesSuccess)
			commSuccess=true;

		
		return commSuccess;
	}

	protected void logSelectedOption(BigDecimal[] array)
	{
		try
		{
			logger.debug(String.format("| %1.3f %1.3f %1.3f |", 
					array[0], array[1], array[2]));
		}catch(IllegalArgumentException ex)
		{
			Log.error(ex.getMessage());
		}
	}

	
	/**
	 * Returns the symbol to be transmitted based on related probabilities.
	 * 
	 * Current matrix is defined as follows:
	 * 
	 * MS s1 s2 s3
	 * m1
	 * m2
	 * m3
	 * 
	 * So one of the values of the row represented by the meaning will be returned
	 * 
	 * @param speaker
	 * @param meaningIndex
	 * @return
	 */
	protected int getSymbolIndexForMeaning(IAgent speaker, int meaningIndex)
	{
		int symbols=speaker.getSymbolNumber(), indexSymbol = -1;
		BigDecimal[] symbolRow = speaker.getSymbols(meaningIndex);



		if(logger.isDebugEnabled())
		{
			logger.debug("Symbols for meaning {}.", meaningIndex);
			logSelectedOption(symbolRow);
		}

		for(int i=0; i<symbols; i++)
		{
			if(indexSymbol>=0)
			{
				if(symbolRow[indexSymbol].compareTo(symbolRow[i])<0)
				{
					indexSymbol=i;
				}
			}
			else
			{
				indexSymbol = i;
			}

		}
		return indexSymbol;
	}

	/**
	 * 
	 * 
	 * Current matrix is defined as follows:
	 * 
	 * MS s1 s2 s3
	 * m1
	 * m2
	 * m3
	 * 
	 * 
	 * @param listener
	 * @param symbolIndex
	 * @return
	 */
	protected int getMeaningIndexForSymbol(IAgent listener, int symbolIndex)
	{
		int symbols=listener.getSymbolNumber(), meaningIndex = -1;
		BigDecimal[] meaningCol = listener.getMeanings(symbolIndex);
		if(logger.isDebugEnabled())
		{
			logger.debug("Meaning for symbol {}.", symbolIndex);
			logSelectedOption(meaningCol);
		}

		for(int i=0; i<symbols; i++)
		{
			if(meaningIndex>=0)
			{
				if(meaningCol[meaningIndex].compareTo(meaningCol[i])<0)
				{
					meaningIndex=i;
				}
			}
			else
			{
				meaningIndex = i;
			}

		}
		return meaningIndex;
	}

}
