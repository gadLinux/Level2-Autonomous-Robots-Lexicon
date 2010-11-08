package com.level2.multiagent.autonomos;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	public static final Logger logger = LoggerFactory.getLogger(App.class);

	private static Dimiurgos dimiurgos;
    public static void main( String[] args )
    {
    	
    	logger.debug("Initializing...");
    	dimiurgos = setup(args);
    	if(dimiurgos!=null)
    	{
    		dimiurgos.run();
    	}
    }
    
    public static Dimiurgos setup(String[] args)
    {
    	Dimiurgos dimiurgos = null;
    	OptionParser parser = new OptionParser();
        parser.accepts( "numberAgents" ).withRequiredArg().ofType(Integer.class);

        OptionSet options = parser.parse( args );
        
        dimiurgos = new Dimiurgos(options);

        return dimiurgos;
    }
}
