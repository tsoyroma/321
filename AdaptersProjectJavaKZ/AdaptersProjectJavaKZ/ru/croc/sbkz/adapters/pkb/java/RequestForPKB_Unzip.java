package ru.croc.sbkz.adapters.pkb.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * Класс для реализации логики обработки JavaCompute-ноды.
 * Предназначен для использование в ноде, которая принимает
 * на вход двоичные данные (домен BLOB), которые представляют
 * собой zip-архив, а на выходе возвращает двоичные данные уже
 * разархивированного файла. Предполагается, что внутри архива
 * только один файл.
 * 
 * @author AFomenko
 *
 */
public class RequestForPKB_Unzip extends MbJavaComputeNode {

	/**
	 * Размер блока данных для чтения байт.
	 */
	private final static int BUFFER_SIZE = 4096;
	
	/**
	 * Разделитель имен файлов и каталогов архива
	 */
	final static String ZIP_FOLDER_SLASH = "/";
	
	/**
	 * Реализует логику обработки JavaCompute-ноды.
	 */
	@Override
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		
		MbMessage inMessage = assembly.getMessage();
		MbMessage outMessage = new MbMessage();
		copyMessageHeaders(inMessage, outMessage);
		
		byte[] blobData = getBlobData(inMessage);
		Map<String, byte[]> files = extractFiles(blobData);
		
		MbElement outRoot = outMessage.getRootElement();
		MbElement outBody = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
		if (files.size() != 0) {
			outBody.createElementAsLastChild(MbElement.TYPE_NAME, MbBLOB.ROOT_ELEMENT_NAME, files.values().toArray()[0]);
		}
		
		MbMessageAssembly outAssembly = new MbMessageAssembly(
				assembly,
				assembly.getLocalEnvironment(),
				assembly.getExceptionList(), 
				outMessage);
		getOutputTerminal("out").propagate(outAssembly);
	}
	
	/**
	 * Распаковывает переданный в виде массива байт
	 * zip-архив, и возвращает карту соответствий имени файла
	 * и данных по этому файлу в виде массива байт. 
	 * В случае какой-либо ошибки при разборе архива,
	 * возвращает пустую карту.
	 * 
	 * @param zipData - массив байт архива
	 * @return карта соответствий имени файла и данных 
	 * по этому файлу в виде массива байт.
	 */
	private Map<String, byte[]> extractFiles(final byte[] zipData) {
		try {
			
			Map<String, byte[]> result = new HashMap<String, byte[]>();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(zipData);
			BufferedOutputStream dest = null;
			
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(bais));
			ZipEntry entry;

			// Распаковка архива
			while ((entry = zis.getNextEntry()) != null) {
				
				if (entry.isDirectory() ||
						entry.getName().lastIndexOf(ZIP_FOLDER_SLASH) != -1) {
					continue;
				}
				
				// Получение имени файла (предполагается, что никаких директорий внутри
				// архива нету).
				String fileName = entry.getName();

				int count;
				byte data[] = new byte[BUFFER_SIZE];

				// Запись файла в буфер
				ByteArrayOutputStream bous = new ByteArrayOutputStream();
				dest = new BufferedOutputStream(bous, BUFFER_SIZE);
				while ((count = zis.read(data)) != -1) {
					dest.write(data, 0, count);
				}
				zis.closeEntry();
				dest.close();

				result.put(fileName, bous.toByteArray());
			}
			zis.close();

			return Collections.unmodifiableMap(result);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}
	
	/**
	 * Возвращает массив байт для данных в BLOB домене.
	 * 
	 * @param inMessage входное сообщение
	 * @return массив данных поля BLOB
	 * @throws MbException ошибка операций с сообщениями MB
	 */
	private byte[] getBlobData(MbMessage inMessage) throws MbException {
		// Обработка ZIP-архива
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
