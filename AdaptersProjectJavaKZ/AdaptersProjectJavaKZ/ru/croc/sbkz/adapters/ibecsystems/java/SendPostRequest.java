package ru.croc.sbkz.adapters.ibecsystems.java;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class SendPostRequest extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		//MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = assembly.getMessage();
		MbMessage outMessage = new MbMessage();
		
		String data_message="";
		byte[] blobData = getBlobData(inMessage);
		
		try {
			data_message = new String(blobData,"UTF-8");
			
			//String httpsURL = "https://www.sberbank.kz/ru/course/upload";
			String httpsURL = "https://new-sber.ibec.systems/ru/course/upload";
			URL myurl = new URL(httpsURL);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.2.4", 3128));
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection(proxy);
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-length", String.valueOf(data_message.length()));
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			con.setDoOutput(true);
			con.setDoInput(true);
			
			// PROXY
			/*String proxy = "192.168.2.4";
			String proxyPort = "3128";
	        System.setProperty("http.proxyHost", proxy);
	        System.setProperty("http.proxyPort", proxyPort);
	        System.setProperty("https.proxyHost", proxy);
	        System.setProperty("https.proxyPort", proxyPort);*/
			
			DataOutputStream output = new DataOutputStream(con.getOutputStream());
			
			output.writeBytes(data_message);
			output.close();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			
			StringBuffer inputLine = new StringBuffer();
			String tmp;
			while((tmp = input.readLine()) != null)
			{
				inputLine.append(tmp);
			}
			
			input.close();
			
			MbElement outRoot = outMessage.getRootElement();
			
			MbElement xmlnsc_root = outRoot.createElementAsLastChild("XMLNSC");			
			MbElement xmlnsc = xmlnsc_root.createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);
			
			xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseCode",con.getResponseCode());
			xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseMessage",con.getResponseMessage());
			xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseText",tmp);
			
			MbMessageAssembly outAssembly = new MbMessageAssembly(
					assembly,
					assembly.getLocalEnvironment(),
					assembly.getExceptionList(), 
					outMessage);
			getOutputTerminal("out").propagate(outAssembly);
			
			
			
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			MbElement outRoot = outMessage.getRootElement();
			
			MbElement xmlnsc_root = outRoot.createElementAsLastChild("XMLNSC");			
			MbElement xmlnsc = xmlnsc_root.createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);
			
			xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseCode",e.getMessage());
			xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseMessage",e.getMessage());
						
			MbMessageAssembly outAssembly = new MbMessageAssembly(
					assembly,
					assembly.getLocalEnvironment(),
					assembly.getExceptionList(), 
					outMessage);
			getOutputTerminal("out").propagate(outAssembly);
		}
		
		// ----------------------------------------------------------
		// Add user code below

		// End of user code
		// ----------------------------------------------------------

		// The following should only be changed
		// if not propagating message to the 'out' terminal

		//out.propagate(assembly);
	}
	
	private byte[] getBlobData(MbMessage inMessage) throws MbException {

		MbElement root = inMessage.getRootElement();
		MbElement node = root.getFirstElementByPath("BLOB/BLOB");
		
		return (byte[]) node.getValue();
	}
	

}
