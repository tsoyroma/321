package ru.croc.sbkz.adapters.dynfiletransfer;  

import java.util.List;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;

import kz.primesource.nio.download.entity.FileGetInfo;
import kz.primesource.nio.download.entity.FileInfo;
import kz.primesource.nio.factory.DownloadFactory;

public class DynFileIn_JavaFileGetterFactory extends MbJavaComputeNode {
	
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		
		
		MbMessageAssembly outAssembly = null;
		boolean noFilesDownloaded = true;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			/*** ENVIRONMENT VARIABLES GETTER ***/
			FileInfo fi = getFileInfo(inMessage);
			/*** ENVIRONMENT VARIABLES GETTER - END ***/
			
			List<FileGetInfo> fgis = DownloadFactory.download(fi);
			
			MbElement filesRoot = outMessage.getRootElement().getFirstElementByPath("XMLNSC")
        			.getFirstElementByPath("FileTransferRequest").createElementAsLastChild(MbXMLNSC.FOLDER, "Files", null);
			
	        if(fgis != null){
	        	noFilesDownloaded = false;
	        	int filesNotDounloadedCount = 0;
	            for (FileGetInfo fgi : fgis) {
	            	if(fgi != null){
	            		
		            	MbElement fileRoot = filesRoot.createElementAsLastChild(MbXMLNSC.FOLDER, "File", null);
		            	fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "FileName", fgi.fileName);
		            	
		            	fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Base64FileBody", fgi.fileContentBase64);
		            	if (fgi.errorCode == -1){
		            		fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ErrorCode", fgi.errorCode);
		            		fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ErrorText", fgi.errorText);
		            		filesNotDounloadedCount++;
		    			}else{
		    				fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ErrorCode", fgi.errorCode);
		    			}
	            			            		
	            	}
	            }
	            if(filesNotDounloadedCount == fgis.size()){
	            	noFilesDownloaded = true;
	            }
	        }else{
	        	
	        	MbElement fileRoot = filesRoot.createElementAsLastChild(MbXMLNSC.FOLDER, "File", null);
            	
        		fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ErrorCode", "500");
        		fileRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ErrorText", "No files downloaded.");
    			
	        }
	        
			
			
			
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below

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
		if(noFilesDownloaded){
			alt.propagate(outAssembly);
		}else{
			out.propagate(outAssembly);
		}

	}
	
	
	public FileInfo getFileInfo(MbMessage inMessage)  throws MbException {
		
		MbElement dest = inMessage.getRootElement().getFirstElementByPath("XMLNSC").getFirstElementByPath("FileTransferRequest").getFirstElementByPath("Source");
		
		if(dest == null){
			return null;
		}	
		
		FileInfo fi = new FileInfo();
		
		fi.Domain = getElementValue(dest.getFirstElementByPath("Domain"));
		fi.Port = getElementValuePort(dest.getFirstElementByPath("Port"), dest.getFirstElementByPath("Protocol").getValueAsString());  
		fi.Host = getElementValue(dest.getFirstElementByPath("Host"));
		fi.Protocol =	getElementValue(dest.getFirstElementByPath("Protocol"));
		fi.Login =	getElementValue(dest.getFirstElementByPath("Login")); 
		fi.Key = getElementValue(dest.getFirstElementByPath("Key")); 
		fi.Phrase = getElementValue(dest.getFirstElementByPath("Phrase")); 
		fi.Password =	getElementValue(dest.getFirstElementByPath("Password")); 
		fi.Directory = getElementValue(dest.getFirstElementByPath("Directory"));
		fi.Archivation = getElementValue(dest.getFirstElementByPath("Archivation"));
		
		fi.FileGetPattern = getElementValue(dest.getFirstElementByPath("FileGetPattern"));
		fi.File = getElementValue(dest.getFirstElementByPath("File"));
		fi.FileAfterReadAction = getElementValue(dest.getFirstElementByPath("FileAfterReadAction"));
		return fi;
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
}
