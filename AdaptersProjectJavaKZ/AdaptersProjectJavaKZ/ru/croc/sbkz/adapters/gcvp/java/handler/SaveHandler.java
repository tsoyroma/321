package ru.croc.sbkz.adapters.gcvp.java.handler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SaveHandler implements SOAPHandler<SOAPMessageContext> {

	private String requestNumber = null;
	private String outDir = null;
	
	public SaveHandler(String requestNumber, String outDir) {
		this.requestNumber = requestNumber;
		this.outDir = outDir;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		boolean out = (Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);

		String filename = outDir+"/"+requestNumber+"_in.xml";
		if (out) {
			filename = outDir+"/"+requestNumber+"_out.xml";
		}
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			context.getMessage().writeTo(fout);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		return true;
	}
		
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
}
