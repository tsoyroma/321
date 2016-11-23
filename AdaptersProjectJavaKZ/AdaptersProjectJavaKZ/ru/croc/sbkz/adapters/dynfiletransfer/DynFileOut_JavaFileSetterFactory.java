package ru.croc.sbkz.adapters.dynfiletransfer; 

import java.util.ArrayList;
import java.util.List;

import kz.primesource.nio.factory.UploadFactory;
import kz.primesource.nio.upload.entity.FilePutInfo;
import kz.primesource.nio.upload.entity.FileInfo;
import kz.primesource.nio.upload.entity.FilePutLog;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;

public class DynFileOut_JavaFileSetterFactory extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			
			
			
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			/*** PREP VARIABLES ***/
			MbElement envRoot = inMessage.getRootElement().getFirstElementByPath("XMLNSC").getFirstElementByPath("FileTransferRequest");
			FilePutInfo fpi = getFilePutInfo(envRoot);
			/*** PREP VARIABLES - END ***/
			
			List<FilePutLog> logs = UploadFactory.upload(fpi);
			outMessage.getRootElement().getFirstElementByPath("XMLNSC").getFirstElementByPath("FileTransferRequest").getFirstElementByPath("Files").detach();
			if(logs != null){
	        	
	        	MbElement filesRoot = outMessage.getRootElement().getFirstElementByPath("XMLNSC").getFirstElementByPath("FileTransferRequest").createElementAsLastChild(MbXMLNSC.FOLDER, "FileReports", null);
	        	
	            for (FilePutLog log : logs) {
	            	if(log != null){
	            		
		            	MbElement fileRoot = filesRoot.createElementAsLastChild(MbXMLNSC.FOLDER, "FileReport", null);
		            	fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "File", log.fileName);
		            	
		            	fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Result", log.errorText);	            		
	            	}
	            }
	            
	        }
			
			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}
	
	private String getElementValue(MbElement item){
		
		String value = null;
		try {
			if(item != null && item.getValueAsString() != null){
				value = item.getValueAsString();
			}
		} catch (MbException e) {
			return null;
		}
		return value;
	}
	
	private int getElementValuePort(MbElement item, String protocol){
		
		int value = 0;
		try {
			if(item != null && item.getValueAsString() != null){
				value = Integer.parseInt(item.getValueAsString()); 
			}
		} catch (MbException e) {
			return defaultByProtocol(protocol);
		}
		return value;
	}
	
	private FilePutInfo getFilePutInfo(MbElement envRoot) throws MbException {
		
		MbElement dest = envRoot.getFirstElementByPath("Destination");
		FilePutInfo fpi = null;
		if(dest == null){
			return null;
		}		
		
		
		fpi = new FilePutInfo();
		
		fpi.Domain = getElementValue(dest.getFirstElementByPath("Domain"));
		fpi.Port = getElementValuePort(dest.getFirstElementByPath("Port"), dest.getFirstElementByPath("Protocol").getValueAsString());  
		fpi.Host = getElementValue(dest.getFirstElementByPath("Host"));
		fpi.Protocol =	getElementValue(dest.getFirstElementByPath("Protocol"));
		fpi.Login =	getElementValue(dest.getFirstElementByPath("Login")); 
		fpi.Key = getElementValue(dest.getFirstElementByPath("Key")); 
		fpi.Phrase = getElementValue(dest.getFirstElementByPath("Phrase")); 
		fpi.Password =	getElementValue(dest.getFirstElementByPath("Password")); 
		fpi.Directory = getElementValue(dest.getFirstElementByPath("Directory")); 
		fpi.FileExistAction = getElementValue(dest.getFirstElementByPath("FileExistAction"));
				
		
		if(
				envRoot.getFirstElementByPath("Files") != null 
				//&& envRoot.getFirstElementByPath("Files").getFirstElementByPath("File") != null
			){
			
			MbElement env = envRoot.getFirstElementByPath("Files").getFirstElementByPath("File");
			//FileInfo(String File, String Base64FileBody)
			//public FilePutInfo(String Domain, int Port, String Host, String Protocol, String Login, String Password, String Directory, String FileExistAction) {
			List<FileInfo> fileInfos = new ArrayList<FileInfo>();   	
	    	while (env != null)
			{
	    		if(env.getFirstElementByPath("ErrorCode") != null && 
    				"1".equals(env.getFirstElementByPath("ErrorCode").getValueAsString()) ){
	    			
	    				fileInfos.add(new FileInfo(env.getFirstElementByPath("FileName").getValueAsString(), env.getFirstElementByPath("Base64FileBody").getValueAsString()));
	    		}
	    		env = env.getNextSibling();
	    		
			}
	    	fpi.files = fileInfos;
		}
		
		return fpi;
	}
	
	public int defaultByProtocol(String protocol){
		int port = 0;
		
		if("ftp".equalsIgnoreCase(protocol)){
			port = 21;
		}else if("sftp".equalsIgnoreCase(protocol)){
			port = 22;
		}
		
		return port;
	}

}
