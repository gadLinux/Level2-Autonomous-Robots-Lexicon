package com.level2.multiagent.autonomos.decisor;

import java.math.BigDecimal;

import com.level2.multiagent.autonomos.agents.IAgent;

public interface ICommDecisor {

	public Boolean chat(IAgent speaker, IAgent listener);
	public BigDecimal[] compute(BigDecimal[] values, int selectedIndex, Boolean succeded);
		
}
