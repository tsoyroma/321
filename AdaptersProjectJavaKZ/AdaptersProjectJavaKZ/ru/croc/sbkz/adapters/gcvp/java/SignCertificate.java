package ru.croc.sbkz.adapters.gcvp.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import kz.gcvp.billing.ws.Deduction;
import kz.gcvp.billing.ws.DeductionDetailed;
import kz.gcvp.billing.ws.DeductionDetailedResponse;
import kz.gcvp.billing.ws.DeductionRequest;
import kz.gcvp.billing.ws.DeductionRequestType;
import kz.gcvp.billing.ws.DeductionShortResponse;
import kz.gcvp.billing.ws.Person;
import kz.gcvp.billing.ws.impl.BillingWS;
import kz.gcvp.billing.ws.impl.BillingWSImplService;
import kz.iola.jce.provider.IolaProvider;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ru.croc.sbkz.adapters.gcvp.java.handler.SaveHandler;
import ru.croc.sbkz.adapters.gcvp.java.handler.SignHandler;

import com.google.common.io.Files;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class SignCertificate extends MbJavaComputeNode{
	
	private static DateFormat df = new SimpleDateFormat("yyyyMMdd");
	private static DateFormat dfT = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
	
	private static XMLGregorianCalendar a(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        XMLGregorianCalendar x = null;
        try {
            x = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            x.setDay(c.get(Calendar.DAY_OF_MONTH));
            x.setMonth(c.get(Calendar.MONTH) + 1);
            x.setYear(c.get(Calendar.YEAR));
        } catch (DatatypeConfigurationException e) {
        }
        return x;
    }	
	
	@Override
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		// TODO Auto-generated method stub		
		
		MbMessage inMessage  = assembly.getMessage();
		MbMessage outMessage = new MbMessage(); 	
		copyMessageHeaders(inMessage, outMessage);	
		
		byte[] blobData = getBlobData(inMessage);
		
		String message = null;
		try {
			message = new String(blobData,"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		Document doc = convertStringToDocument(message);
		
		String keyDir = "/var/mqsi/ssl";
		//String xmlPath = "/var/mqsi/ssl/1.xml";
		String outDir = "/var/mqsi/ssl/out";
		
		String requestNumber = null;
		
		try {
			
			Request r = readRequest(doc);
			
			String type = r.getType();
			requestNumber = r.getRequestNumber();
			String iin = r.getIin();
			String ln = r.getSurname();
			String fn = r.getName();
			String mn = r.getFatherName();
			String bd = r.getBirthDate();
			String docNumber = r.getDocumentNumber();
			String docIssueDate = r.getDocumentDate();
			
			// PROXY
			String proxy = "192.168.2.4";
			String proxyPort = "3128";
	        System.setProperty("http.proxyHost", proxy);
	        System.setProperty("http.proxyPort", proxyPort);
	        System.setProperty("https.proxyHost", proxy);
	        System.setProperty("https.proxyPort", proxyPort);

			System.setProperty("javax.net.ssl.keyStore", keyDir+"/AUTH_RSA.p12");
	    	System.setProperty("javax.net.ssl.keyStorePassword", "123456");
	    	System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	
	    	System.setProperty("javax.net.ssl.trustStore",keyDir+"/TRUSTSTORE.JKS");
	    	System.setProperty("javax.net.ssl.trustStorePassword","123456");
	    	System.setProperty("javax.net.ssl.trustStoreType", "JKS");
	
	    	System.setProperty("jsse.enableSNIExtension", "false");
			
	    	Person person = new Person();
			person.setBirthDate(a(df.parse(bd)));
			person.setFatherName(mn);
			person.setIin(iin);
			person.setName(fn);
			person.setSurname(ln);
	    	
			
			BillingWSImplService service = new BillingWSImplService();
	    	BillingWS port = service.getBillingWSImplPort();
	
	    	BindingProvider bp = (BindingProvider)port;
	
	    	Security.addProvider(new IolaProvider());
	    	@SuppressWarnings("rawtypes")
			List<Handler> handlers = new LinkedList<Handler>();
	    	SaveHandler handler1 = new SaveHandler(requestNumber,outDir);
	    	SignHandler handler2 = new SignHandler(keyDir);
			handlers.add(handler2);
			handlers.add(handler1);
			bp.getBinding().setHandlerChain(handlers);
						
			if (type.startsWith("DD") || type.startsWith("DS")) {
				DeductionRequest request = new DeductionRequest();
				
				//PensionRequest request_req = new PensionRequest();
				request.setDocumentIssueDate(a(df.parse(docIssueDate)));
				request.setDocumentNumber(docNumber);
				request.setPerson(person);
				request.setRequestNumber(requestNumber);
				
				
				if (type.endsWith("36")) {
					request.setRequestType(DeductionRequestType.DEDUCTION_36);
				} else if (type.endsWith("3")) {
					request.setRequestType(DeductionRequestType.DEDUCTION_3);
				} else if (type.endsWith("12")) {
					request.setRequestType(DeductionRequestType.DEDUCTION_12);
				} else if (type.endsWith("6")) {
					request.setRequestType(DeductionRequestType.DEDUCTION_6);
				}
	
				if (type.startsWith("DD")) {
					DeductionDetailedResponse response = port.getDeductionDetailedInfo(request);
					request.setRequestNumber(UUID.randomUUID().toString());
					//DeductionShortResponse response_simple = port.getDeductionShortInfo(request);
					
					MbElement outRoot = outMessage.getRootElement();
					//MbElement outBody = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
					MbElement xmlnsc_root = outRoot.createElementAsLastChild("XMLNSC");			
					MbElement xmlnsc = xmlnsc_root.createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);
					
					if(response.getResponseCode() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseCode",response.getResponseCode().toString());
					}					
					
					if(response.getResponseDate() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseDate",response.getResponseDate().toString());
					}
						
					if(response.getResponseNumber() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseNumber",response.getResponseNumber().toString());
					}
					
					/*if(response_simple.getAverageAmount() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"averageSum",response_simple.getAverageAmount().toString());
						outBody.createElementAsLastChild(MbElement.TYPE_NAME, MbBLOB.ROOT_ELEMENT_NAME, argsd.getBytes("UTF-8"));
					}*/
					
					if(response.getDeductions() != null){
						List<DeductionDetailed> deductions = response.getDeductions(); 
						
						for (int i=0;i<deductions.size();i++) {
							DeductionDetailed d = deductions.get(i);
							MbElement deduct = xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME,"deductions",null);
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "bin", d.getBin().toString());
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "date", d.getDate().toString());
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "name", d.getName().toString());
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "amount", d.getAmount().toString());
						}
						
						String enumCount = Integer.toString(deductions.size());
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"enumCount",enumCount);
						
					}
					
					
					MbMessageAssembly outAssembly = new MbMessageAssembly(
							assembly,
							assembly.getLocalEnvironment(),
							assembly.getExceptionList(), 
							outMessage);
					getOutputTerminal("out").propagate(outAssembly);
					
					//SOAPMessageContext message_ctx = handler1.soapMessage();
					//writeDD(response,outDir);
					
					
					
				} else if (type.startsWith("DS")) {
					DeductionShortResponse response = port.getDeductionShortInfo(request);
					request.setRequestNumber(UUID.randomUUID().toString());
					//DeductionDetailedResponse response_detailed = port.getDeductionDetailedInfo(request);
												
					MbElement outRoot = outMessage.getRootElement();
					//MbElement outBody = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
					MbElement xmlnsc_root = outRoot.createElementAsLastChild("XMLNSC");			
					MbElement xmlnsc = xmlnsc_root.createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);
					
					if(response.getResponseCode() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseCode",response.getResponseCode().toString());
					}
					
					if(response.getResponseDate() != null){					
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseDate",response.getResponseDate().toString());
					}
					
					if(response.getResponseNumber() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseNumber",response.getResponseNumber().toString());
					}
					
					if(response.getAverageAmount() != null){
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"averageSum",response.getAverageAmount().toString());
					}
					
					//outBody.createElementAsLastChild(MbElement.TYPE_NAME, MbBLOB.ROOT_ELEMENT_NAME, argsd.getBytes("UTF-8"));
					
					if(response.getDeductions() != null){
						List<Deduction> deductions = response.getDeductions(); 
						//List<DeductionDetailed> deductions_detailed = response_detailed.getDeductions();
						
						for (int i=0;i<deductions.size();i++) {
							Deduction d = deductions.get(i);
							MbElement deduct = xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME,"deductions",null);
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "bin", d.getBin().toString());
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "date", d.getDate().toString());
							deduct.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "name", d.getName().toString());
	
						}
						String enumCount = Integer.toString(deductions.size());
						
						xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"enumCount",enumCount);
					}
					
					
					
					
					
					MbMessageAssembly outAssembly = new MbMessageAssembly(
							assembly,
							assembly.getLocalEnvironment(),
							assembly.getExceptionList(), 
							outMessage);
					getOutputTerminal("out").propagate(outAssembly);
					
					
					
					//writeDS(response,outDir);
				}
			}
			
		} catch (Throwable e) {
			// TODO: handle exception
				
			if (requestNumber!=null) {
				PrintStream stream = null;
				try {
					stream = new PrintStream(outDir+"/"+requestNumber+".err");
					e.printStackTrace(stream);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String messageText = e.toString();
				
				MbElement outException = outMessage.getRootElement();
				MbElement xmlnsc_root = outException.getLastChild();		
				MbElement xmlnsc = xmlnsc_root.getLastChild();
				
				if (xmlnsc_root.getValueAsString() == null){
					xmlnsc_root = outException.createElementAsLastChild("XMLNSC");			
					xmlnsc = xmlnsc_root.createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);
					xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseCode","ERROR "+messageText);
					xmlnsc.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"responseText",messageText);
				}
				
				MbMessageAssembly outAssembly = new MbMessageAssembly(
						assembly,
						assembly.getLocalEnvironment(),
						assembly.getExceptionList(), 
						outMessage);
				getOutputTerminal("out").propagate(outAssembly);
							
				
				//e.printStackTrace(stream);
				
			} else {
				e.printStackTrace(System.out);
				
			}
		}
		
		
		
	}
	
	
	private byte[] getBlobData(MbMessage inMessage) throws MbException {
		MbElement root = inMessage.getRootElement();
		MbElement node = root.getFirstElementByPath("BLOB/BLOB");
		
		return (byte[]) node.getValue();
	}
	
	/**
	 * Копирует заголовки входного сообщения в
	 * выходное.
	 * 
	 * @param inMessage входное сообщение
	 * @param outMessage выходное сообщение
	 * @throws MbException ошибка операций с сообщениями MB
	 */
	private void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage) 
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();
		MbElement header = inMessage.getRootElement().getFirstChild();
		
		while (header != null && header.getNextSibling() != null) {
			outRoot.addAsLastChild(header.copy());
			header = header.getNextSibling();
		}
	}	
	// В параметре было значение String filename
	
	private static Request readRequest(Document doc) throws ParserConfigurationException, SAXException, IOException {
		/*File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);*/

		doc.getDocumentElement().normalize();
	 
		NodeList nList = doc.getElementsByTagName("request");

		Node nNode = nList.item(0);
	 
		Element eElement = (Element) nNode;

		Request request = new Request();
		String type = eElement.getElementsByTagName("type").item(0).getTextContent();
		request.setType(type);
		String requestNumber = eElement.getElementsByTagName("requestNumber").item(0).getTextContent();
		request.setRequestNumber(requestNumber);
		String iin = eElement.getElementsByTagName("iin").item(0).getTextContent();
		request.setIin(iin);
		String surname = eElement.getElementsByTagName("surname").item(0).getTextContent();
		request.setSurname(surname);
		String name = eElement.getElementsByTagName("name").item(0).getTextContent();
		request.setName(name);
		String fatherName = null;
		if (eElement.getElementsByTagName("fatherName").getLength()!=0)
			fatherName = eElement.getElementsByTagName("fatherName").item(0).getTextContent();
		request.setFatherName(fatherName);
		String birthDate = eElement.getElementsByTagName("birthDate").item(0).getTextContent();
		request.setBirthDate(birthDate);
		String documentNumber = eElement.getElementsByTagName("documentNumber").item(0).getTextContent();
		request.setDocumentNumber(documentNumber);
		String documentDate = eElement.getElementsByTagName("documentDate").item(0).getTextContent();
		request.setDocumentDate(documentDate);

		return request;
	}

	private static void writeDD(DeductionDetailedResponse response, String outDir) throws IOException {
		DeductionRequest request = response.getRequest();
		String requestNumber = request.getRequestNumber();
		String iin = request.getPerson().getIin();
		String ln = request.getPerson().getSurname();
		String fn = request.getPerson().getName();
		String mn = request.getPerson().getFatherName();
		String bd = df.format(request.getPerson().getBirthDate().toGregorianCalendar().getTime());
		String docNumber = request.getDocumentNumber();
		String docIssueDate = df.format(request.getDocumentIssueDate().toGregorianCalendar().getTime());
		
		String dateStr = dfT.format(response.getResponseDate().toGregorianCalendar().getTime());
		Writer fw = Files.newWriter(new File(outDir+"/"+requestNumber+".out"), Charset.forName("UTF-8"));
		fw.write(response.getResponseNumber()+";"+dateStr+";"+response.getResponseCode()+";");
		fw.write("\n");
		fw.write(requestNumber+";"+iin+";"+ln+";"+fn+";"+mn+";"+bd+";"+docNumber+";"+docIssueDate+";");
		fw.write("\n");

		fw.write("\n");

		List<DeductionDetailed> deductions = response.getDeductions(); 
		fw.write(Integer.toString(deductions.size()));
		fw.write("\n");
		for (int i=0;i<deductions.size();i++) {
			DeductionDetailed d = deductions.get(i);
			fw.write(d.getBin()+";"+d.getName()+";"+df.format(d.getDate().toGregorianCalendar().getTime())+";"+d.getAmount()+";");
			fw.write("\n");
		}
		fw.flush();
		fw.close();
	}

	private static void writeDS(DeductionShortResponse response, String outDir) throws IOException {
		DeductionRequest request = response.getRequest();
		String requestNumber = request.getRequestNumber();
		String iin = request.getPerson().getIin();
		String ln = request.getPerson().getSurname();
		String fn = request.getPerson().getName();
		String mn = request.getPerson().getFatherName();
		String bd = df.format(request.getPerson().getBirthDate().toGregorianCalendar().getTime());
		String docNumber = request.getDocumentNumber();
		String docIssueDate = df.format(request.getDocumentIssueDate().toGregorianCalendar().getTime());
		
		String dateStr = dfT.format(response.getResponseDate().toGregorianCalendar().getTime());
		Writer fw = Files.newWriter(new File(outDir+"/"+requestNumber+".out"), Charset.forName("UTF-8"));
		fw.write(response.getResponseNumber()+";"+dateStr+";"+response.getResponseCode()+";");
		fw.write("\n");
		fw.write(requestNumber+";"+iin+";"+ln+";"+fn+";"+mn+";"+bd+";"+docNumber+";"+docIssueDate+";");
		fw.write("\n");

		if (response.getAverageAmount()!=null)
			fw.write(response.getAverageAmount().toString());
		fw.write("\n");

		List<Deduction> deductions = response.getDeductions(); 
		fw.write(Integer.toString(deductions.size()));
		fw.write("\n");
		for (int i=0;i<deductions.size();i++) {
			Deduction d = deductions.get(i);
			fw.write(d.getBin()+";"+d.getName()+";"+df.format(d.getDate().toGregorianCalendar().getTime())+";");
			fw.write("\n");
		}
		fw.flush();
		fw.close();
	}
	
	
	private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
         
        return null;
    }
 
    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try 
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }

	
	
	
	

}
