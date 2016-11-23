package ru.croc.sbkz.adapters.gcvp.java;

import java.io.UnsupportedEncodingException;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbRecoverableException;
import java.io.ByteArrayOutputStream;

/**
 * Класс для перекодировки символов из UTF-8 в 1174.
 * 
 * @author AFomenko
 *
 */
public class IBM1174OutputConverter extends MbJavaComputeNode {
	
	/**
	 * Входящая кодировка символов
	 */
	public static final String INPUT_CHARSET = "UTF-8";
	
	@Override
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbMessage inMessage = assembly.getMessage();
		MbMessage outMessage = new MbMessage();
		copyMessageHeaders(inMessage, outMessage);
		
		byte[] blobData = getBlobData(inMessage);
		String charData;
		try {
			charData = new String(blobData, INPUT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new MbRecoverableException(
					IBM1174OutputConverter.class.toString(),
					"evaluate", 
					"InputRoot", 
					"", 
					e.toString(), 
					null
				);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i=0; i<charData.length(); i++) {
			int charCode = charData.codePointAt(i);
			if (!IBM1174CharTable.IBM_1174_CHARMAP_FROM_UNICODE.containsKey(charCode)) {
				throw new MbRecoverableException(
						IBM1174OutputConverter.class.toString(),
						"evaluate", 
						"InputRoot", 
						"", 
						"Unspecified unicode char in 1174 charset code table; char code = " + charCode, 
						null
					);
			}
			baos.write(IBM1174CharTable.IBM_1174_CHARMAP_FROM_UNICODE.get(charData.codePointAt(i)));
		}
		
		MbElement outRoot = outMessage.getRootElement();
		MbElement outBody = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
		outBody.createElementAsLastChild(MbElement.TYPE_NAME, MbBLOB.ROOT_ELEMENT_NAME, baos.toByteArray());
	
		MbMessageAssembly outAssembly = new MbMessageAssembly(
				assembly,
				assembly.getLocalEnvironment(),
				assembly.getExceptionList(), 
				outMessage);
		getOutputTerminal("out").propagate(outAssembly);
	}
	
	/**
	 * Возвращает массив байт для данных в BLOB домене.
	 * 
	 * @param inMessage входное сообщение
	 * @return массив данных поля BLOB
	 * @throws MbException ошибка операций с сообщениями MB
	 */
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
	
}
