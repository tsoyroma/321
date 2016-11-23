package ru.croc.sbkz.adapters.way.java;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class FileResponseFromWAY_GetIdentifiers extends MbJavaComputeNode {

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
			
			
			MbElement envLocalRoot = inAssembly.getLocalEnvironment().getRootElement();
			MbElement fileElm = envLocalRoot.getFirstElementByPath("File/Name");
			String receivedFile = fileElm.getValueAsString();
			int start = receivedFile.lastIndexOf("_");
			String fileNum = receivedFile.substring(start+1, start+6);
			String dayNum = receivedFile.substring(start+7);
			
			DataContainer.MessageHolder data = DataContainer.getRecord(dayNum, fileNum);
			if (data==null) {
				throw new RuntimeException("No request data found");
			}
			
			MbElement envRoot = outAssembly.getGlobalEnvironment().getRootElement();
			MbElement uPropElm = envRoot.createElementAsFirstChild(MbElement.TYPE_NAME,"UserProperties", null);
			MbElement fileExElm = uPropElm.createElementAsFirstChild(MbElement.TYPE_NAME, "FileExchange", null);
			fileExElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "MsgId", data.msgId);
			fileExElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "RqUID", data.rqUid);
			
			//msgIdElm.setValue(data.msgId);
			//rqUidElm.setValue(data.rqUid);
			
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
