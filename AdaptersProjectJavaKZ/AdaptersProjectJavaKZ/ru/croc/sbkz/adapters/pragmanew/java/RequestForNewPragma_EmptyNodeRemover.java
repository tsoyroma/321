package ru.croc.sbkz.adapters.pragmanew.java;

import java.util.ArrayList;
import java.util.List;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class RequestForNewPragma_EmptyNodeRemover extends MbJavaComputeNode {
	
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbMessage inMessage = assembly.getMessage();
		MbMessage outMessage = new MbMessage();
		copyMessage(inMessage, outMessage);
		removeEmptyNodes(outMessage);
		
		MbMessageAssembly outAssembly = new MbMessageAssembly(
				assembly,
				assembly.getLocalEnvironment(),
				assembly.getExceptionList(), 
				outMessage);
		getOutputTerminal("out").propagate(outAssembly);
	}
	
	/**
	 * Копирует заголовки входного сообщения в
	 * выходное.
	 * 
	 * @param inMessage входное сообщение
	 * @param outMessage выходное сообщение
	 * @throws MbException ошибка операций с сообщениями MB
	 */
	private void copyMessage(MbMessage inMessage, MbMessage outMessage) 
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();
		MbElement element = inMessage.getRootElement().getFirstChild();
		
		while (element != null) {
			outRoot.addAsLastChild(element.copy());
			element = element.getNextSibling();
		}
	}
	
	/**
	 * Удаление пустых нод
	 * @param outMessage
	 * @throws MbException
	 */
	private void removeEmptyNodes(MbMessage outMessage) throws MbException {
		List<MbElement> emptyNodes = new ArrayList<MbElement>();
		MbElement element = outMessage.getRootElement().getLastChild();
		collectEmptyNodes(element, emptyNodes);
		if (emptyNodes.size()>0) {
			for (MbElement emptyNode: emptyNodes) {
				emptyNode.detach();
			}
			removeEmptyNodes(outMessage);
		}
	}
	
	/**
	 * Сбор всех пустых нод в одну коллекцию
	 * @param element
	 * @param emptyNodes
	 * @throws MbException
	 */
	private void collectEmptyNodes(MbElement element, List<MbElement> emptyNodes) throws MbException {
		MbElement child = element.getFirstChild();
		while (child!=null) {
			if (child.getFirstChild()!=null) {
				collectEmptyNodes(child, emptyNodes);
			} else {
				String value = child.getValueAsString();
				if (value==null || "".equals(value)) {
					emptyNodes.add(child);
				}
			}
			child = child.getNextSibling();
		}
	}
	
}
