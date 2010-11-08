package com.level2.multiagent.autonomos;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseAgent implements IAgent {
	public static final Logger logger = LoggerFactory.getLogger(BaseAgent.class);
	private static int nextAgentNumber = 0;
	
	private int agentNumber;
	private Random random;
	
	public BaseAgent()
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

	public abstract void initialize();
	
	@Override
	public void setAgentNumber(int agentNumber) {
		this.agentNumber = agentNumber;
	}

	@Override
	public int getAgentNumber() {
		return agentNumber;
	}

}
