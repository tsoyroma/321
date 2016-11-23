package ru.croc.sbrf.adapters.mdm.java;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class RequestPoolProcessor_Wait extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		Object obj = getUserDefinedAttribute("Timeout");
		Integer timeout;
		if(obj instanceof Integer) {
			timeout = (Integer)obj;
		} else {
			timeout = new Integer(15000);
		}
		try{
			Thread.sleep(timeout.longValue());
		}
		catch(InterruptedException ie)
		{
		}

		out.propagate(assembly);
	}

}
