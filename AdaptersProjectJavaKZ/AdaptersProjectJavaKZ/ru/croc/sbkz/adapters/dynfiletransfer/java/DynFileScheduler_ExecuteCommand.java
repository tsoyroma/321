package ru.croc.sbkz.adapters.dynfiletransfer.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class DynFileScheduler_ExecuteCommand extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException{
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		//MbMessage message = assembly.getMessage();
		
		File file = new File("/home/esbkzusr/filePattern/local.xml");
		try {
			InputStream inputStream = new FileInputStream(file);
			int fileSize =(int) file.length();
			byte[] fileBody = new byte[fileSize];
			inputStream.read(fileBody);
			MbMessage outMessage = new MbMessage();
			MbElement rootElement = outMessage.getRootElement();
			MbElement bodyElement = rootElement.createElementAsLastChild(MbBLOB.PARSER_NAME);
			bodyElement.createElementAsLastChild(MbElement.TYPE_NAME, MbBLOB.ROOT_ELEMENT_NAME, fileBody);
			
			MbMessageAssembly outAssembly = new MbMessageAssembly(
					assembly,
					assembly.getLocalEnvironment(),
					assembly.getExceptionList(), 
					outMessage);
			getOutputTerminal("out").propagate(outAssembly);
			inputStream.close();
			//outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			//MbMessage outAssembly = new MbMessage();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// ----------------------------------------------------------
		// Add user code below

		// End of user code
		// ----------------------------------------------------------

		// The following should only be changed
		// if not propagating message to the 'out' terminal

		out.propagate(assembly);
	}

}
