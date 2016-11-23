package ru.croc.sbrf.common.flow;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class HashValidator extends MbJavaComputeNode {
	/**
	 * Метод обрабоки сообщения узла JavaCompute
	 */
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
		MbMessage message = assembly.getMessage();
		MbElement rootElement = message.getRootElement();

		if (rootElement == null) {
			alt.propagate(assembly);
		} else {
			String[] error = new String[1];

			if (RouteHashValidator.isMsgCorrect(rootElement, error)) {
				out.propagate(assembly);
			}
			else {
				alt.propagate(assembly);
			}
		}		
	}
}
