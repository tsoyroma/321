package ru.croc.sbkz.adapters.way.java;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class RequestForWAY_StoreIdentifiers extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();

		// create new message
		MbMessage outMessage = new MbMessage(inMessage);
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,
				outMessage);
		
		

		try {
			// ----------------------------------------------------------
			// Add user code below
			
			
			MbElement envRoot = inAssembly.getGlobalEnvironment().getRootElement();
			MbElement dayElm = envRoot.getFirstElementByPath("UserProperties/FileExchange/DayNum"); 
			String day = dayElm.getValueAsString();
			MbElement fileElm = envRoot.getFirstElementByPath("UserProperties/FileExchange/FileNum");
			String file = fileElm.getValueAsString();
			MbElement rqUidElm = envRoot.getFirstElementByPath("UserProperties/FileExchange/RqUID"); 
			
			MbElement envLocalRoot = inAssembly.getLocalEnvironment().getRootElement();
			MbElement msgIdElm = envLocalRoot.getFirstElementByPath("WrittenDestination/MQ/DestinationData/msgId");
			
			DataContainer.MessageHolder data = new DataContainer.MessageHolder();
			byte[] msgId = (byte[]) msgIdElm.getValue();
			data.msgId = msgId; // msgIdElm.getValueAsString();
			data.rqUid = rqUidElm.getValueAsString();
			
			DataContainer.addRecord(day, file, data);
			
			// End of user code
			// ----------------------------------------------------------

			// The following should only be changed
			// if not propagating message to the 'out' terminal
			out.propagate(outAssembly);

		} finally {
			// clear the outMessage
			outMessage.clearMessage();
		}
	}

}
