package com.level2.multiagent.autonomos.decisor;

import java.math.BigDecimal;
import java.math.MathContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level2.multiagent.autonomos.agents.fitness.IFitnessFunction;
import com.level2.ojalgo.Probabilities;

/**
 * This class implements the incremental algorithm as seen in class
 * 
 * @author gad
 *
 */
public class IncrementalCommDecisor extends BaseCommDecisor implements ICommDecisor {

	public IncrementalCommDecisor(IFitnessFunction func) {
		super(func);
	}



	public static final Logger logger = LoggerFactory.getLogger(IncrementalCommDecisor.class);



	/**
	 * The algorithm computes as follows
	 * 
	 *  Selected choice
	 *  A(k+1) = A(k) + B(k) * delta
	 * 
	 */
	@Override
	public BigDecimal[] compute(BigDecimal[] values, int selectedIndex,	Boolean succeded) {

		MathContext mc = new MathContext(3);
		MathContext zeroRound = new MathContext(0);

		BigDecimal Bk, delta;

		//BigDecimal value = Probabilities.generateProbabiltyValue(new BigDecimal(0.010));
		BigDecimal value = BigDecimal.ZERO;
		delta = value.add(new BigDecimal(0.023)); // Param to be set

		for(int i=0; i<values.length;i++)
		{
			BigDecimal newValue;
			BigDecimal PKAnterior = values[i];
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
				BigDecimal right = Bk.multiply(delta);
				newValue = PKAnterior.add(right);
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
				BigDecimal right = Bk.multiply(delta);
				newValue = PKAnterior.add(right);

			}
			//Math.r
			values[i] = newValue;//.round(mc);
		}
		
		return values;



	}
	
		
}
