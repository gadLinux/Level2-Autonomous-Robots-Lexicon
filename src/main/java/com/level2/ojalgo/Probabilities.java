package com.level2.ojalgo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

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
	
	/**
	 * Generate a probability value given the rest as summatory
	 * @param min Summatory of the values
	 * @return
	 */
	public static BigDecimal generateProbabiltyValue(BigDecimal min)
	{
		//get the range, casting to long to avoid overflow problems
		BigDecimal range = BigDecimal.ONE.subtract(min);
		// compute a fraction of the range, 0 <= frac < range
		BigDecimal fraction = range.multiply(BigDecimal.valueOf(generateRandom()));
		//int randomNumber =  (int)(fraction + aStart);    
		return fraction;
	}
	 
	public static Double generateRandom()
	{
		Random random = new Random(new Date().getTime());

		return random.nextDouble();
	}

}
