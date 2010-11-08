package com.level2.multiagent.autonomos.decisor;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.IAgent;
import com.level2.ojalgo.Probabilities;

public class BaseCommDecisor implements ICommDecisor {
	public static final Logger logger = LoggerFactory.getLogger(BaseCommDecisor.class);
	
	private BigDecimal kSpeaker;
	private BigDecimal kListener;
	
	public BaseCommDecisor()
	{
		kSpeaker = BigDecimal.ONE.divide(BigDecimal.valueOf(2.0));
		kListener = BigDecimal.ONE.divide(BigDecimal.valueOf(2.0));
	}

	public BigDecimal[] compute(BigDecimal[] values, int selectedIndex, Boolean succeded)
	{
		BigDecimal Bk = succeded ? BigDecimal.ONE : BigDecimal.ZERO;
		for(int i=0; i<values.length;i++)
		{
			BigDecimal newValue;
			if(i==selectedIndex)
			{
				// Selected
				newValue = values[i].add(kSpeaker.multiply((BigDecimal.ONE.subtract(values[i]))).multiply(Bk));
				values[i] = newValue;
			}
			else
			{
				// Not selected
				newValue = values[i].subtract(kSpeaker.multiply(values[i]).multiply(Bk));
				values[i] = newValue;
			}
		}
		return values;
	}
	
	public void updateSpeaker(IAgent speaker, int meaningIndex, int symbolIndex, Boolean succeded)
	{
		BigDecimal[] symbolRow = speaker.getSymbols(meaningIndex);
		BigDecimal[] resultRow = compute(symbolRow,symbolIndex, succeded);
		if(logger.isDebugEnabled())
		{
			// Do sanity checks
			if(!Probabilities.isProbability(resultRow))
			{
				logger.error("We are not using probabilities for symbols!");
			}
		}		
	}
	
	@Override
	public Boolean chat(IAgent speaker, IAgent listener)
	{
		Boolean commSuccess=false;
		int meanings=speaker.getSymbols();
		for(int i=0; i<meanings; i++)
		{
			int symbol = getTransmittedSymbolIndex(speaker, i);
			int receptMeaning = getMeaningIndexForSymbol(listener, symbol);
			
			if(i==receptMeaning)
			{
				commSuccess=true;
				logger.debug("Communication succeded!");
				updateSpeaker(speaker, i, symbol, true);
			}
			else
			{
				logger.debug("Communication error!");
				updateSpeaker(speaker, i, symbol, false);
			}
		}
		return commSuccess;
	}
	
	protected int getTransmittedSymbolIndex(IAgent speaker, int meaningIndex)
	{
		int symbols=speaker.getSymbols(), indexSymbol = -1;
		BigDecimal[] symbolRow = speaker.getSymbols(meaningIndex);
		if(logger.isDebugEnabled())
		{
			// Do sanity checks
			if(!Probabilities.isProbability(symbolRow))
			{
				logger.error("We are not using probabilities for symbols!");
			}
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

	protected int getMeaningIndexForSymbol(IAgent listener, int symbolIndex)
	{
		int symbols=listener.getSymbols(), meaningIndex = -1;
		BigDecimal[] meaningCol = listener.getMeanings(symbolIndex);
		if(logger.isDebugEnabled())
		{
			// Do sanity checks
			if(!Probabilities.isProbability(meaningCol))
			{
				logger.error("We are not using probabilities for meanings!");
			}
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
