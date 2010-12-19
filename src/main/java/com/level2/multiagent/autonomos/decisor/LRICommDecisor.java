package com.level2.multiagent.autonomos.decisor;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the LRI algorithm as seen in class
 * 
 * @author gad
 *
 */
public class LRICommDecisor extends BaseCommDecisor implements ICommDecisor {

	public static final Logger logger = LoggerFactory.getLogger(LRICommDecisor.class);



	/**
	 * The algorithm computes as follows
	 * 
	 *  Selected choice
	 *  P(k+1) = P(k) + Lambda * (1-P(k))Bk
	 *
	 *  Not selected choice
	 *  P(k+1) = P(k) + Lambda * P(k) * Bk
	 * 
	 */
	@Override
	public BigDecimal[] compute(BigDecimal[] values, int selectedIndex,	Boolean succeded) {
		{


			BigDecimal Bk;
			
			if(succeded)
			{
				Bk = BigDecimal.ONE;
			}
			else
			{
				Bk = BigDecimal.ZERO;
			}

			for(int i=0; i<values.length;i++)
			{
				BigDecimal newValue;
				BigDecimal PKAnterior = values[i];
				if(i==selectedIndex)
				{
					// Selected
	
					BigDecimal LabdaCoef = kSpeaker.multiply(BigDecimal.ONE.subtract(PKAnterior));
					BigDecimal PremioCastigo = LabdaCoef.multiply(Bk);

					newValue = PKAnterior.add(PremioCastigo);

				}
				else
				{
					//BigDecimal Bk = succeded ? BigDecimal.ZERO : BigDecimal.ONE;

					// Not selected
					BigDecimal LabdaCoef = kSpeaker.multiply(PKAnterior);
					BigDecimal PremioCastigo = LabdaCoef.multiply(Bk);

					newValue = PKAnterior.subtract(PremioCastigo);

				}
				values[i] = newValue;
			}
			return values;


		}
	}
}
