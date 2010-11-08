package com.level2.ojalgo;

import java.math.BigDecimal;

public class Probabilities {

	public static Boolean isProbability(BigDecimal[] values)
	{
		Boolean retVal = false;
		BigDecimal summatory = BigDecimal.ZERO;
		for(int i=0; i<values.length;i++)
		{
			summatory=summatory.add(values[i]);
		}
		if(summatory.compareTo(BigDecimal.ONE)==0)
		{
			retVal = true;
		}
		return retVal;
	}
}
