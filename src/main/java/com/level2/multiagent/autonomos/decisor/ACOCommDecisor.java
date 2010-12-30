package com.level2.multiagent.autonomos.decisor;

import java.math.BigDecimal;
import java.math.MathContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.fitness.IFitnessFunction;

/**
 * This class implements the ACO algorithm as seen in class
 * 
 * @author gad
 *
 */
public class ACOCommDecisor extends BaseCommDecisor implements ICommDecisor {

	public ACOCommDecisor(IFitnessFunction func) {
		super(func);
	}



	public static final Logger logger = LoggerFactory.getLogger(ACOCommDecisor.class);



	/**
	 * The algorithm computes as follows
	 * 
	 *  Selected choice
	 *  A(k+1) = p*A(k) + B(k) * (1-p)
	 * 
	 */
	@Override
	public BigDecimal[] compute(BigDecimal[] values, int selectedIndex,	Boolean succeded) {

		MathContext mc = new MathContext(3);
		MathContext zeroRound = new MathContext(0);

		BigDecimal Bk, p;

		p = BigDecimal.valueOf(0.70); // Param to be set

		for(int i=0; i<values.length;i++)
		{
			BigDecimal newValue;
			BigDecimal PKAnterior = values[i];
//			if(PKAnterior.round(zeroRound).compareTo(BigDecimal.ONE)==0)
//			{
//				PKAnterior = BigDecimal.ONE;
//			}
			if(i==selectedIndex)
			{
				// Selected
				if(succeded)
				{
					Bk = BigDecimal.ONE;
				}
				else
				{
					Bk = BigDecimal.ZERO;
				}
				BigDecimal left = p.multiply(PKAnterior);
				BigDecimal right = Bk.multiply(BigDecimal.ONE.subtract(p));
				newValue = left.add(right);
			}
			else
			{
				if(!succeded)
				{
					Bk = BigDecimal.ONE;
				}
				else
				{
					Bk = BigDecimal.ZERO;
				}
				BigDecimal left = p.multiply(PKAnterior);
				BigDecimal right = Bk.multiply(BigDecimal.ONE.subtract(p));
				newValue = left.add(right);

			}
			//Math.r
			values[i] = newValue.round(mc);
		}
		
		return values;



	}
	
		
}
