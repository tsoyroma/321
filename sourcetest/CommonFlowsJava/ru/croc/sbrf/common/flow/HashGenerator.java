package ru.croc.sbrf.common.flow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

@SuppressWarnings("deprecation")
public class HashGenerator {
	/**
	 * Возвращает хэш длиной 24 байта переданной строки 
	 * @param in 	исходная строка для хэширования
	 * @param out	выходной буффер байт хэша
	 * @param error	текст ошибки возникшей во время выполнения функции
	 */
	
	static String qMngrStr = "MKZ.ESB.ADP1";
	static String queueName = "ESB.TESTT.IN";
	static String hostName = "ws-test";
	static int port = 1414;
	static String channel = "ESB.GW.SVRCONN";
	static String msg = "Hello World, Welcome to MQ.";
	
	static MQQueue defaultLocaleQueue;
	static MQQueueManager qManager;
	
	@SuppressWarnings("unchecked")
	public static void putQueue()
	{
		MQEnvironment.hostname = hostName;
		MQEnvironment.channel = channel;
		MQEnvironment.port = port;
		
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
		
		try {
			qManager = new MQQueueManager(qMngrStr);
		} catch (MQException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF|MQC.MQOO_OUTPUT;
		  openOptions = openOptions + MQC.MQOO_INQUIRE; 
		
		  try {
				
				defaultLocaleQueue = qManager.accessQueue(queueName, openOptions);
				
				MQMessage putMessage = new MQMessage();
				//putMessage.expiry = 3000;
				//putMessage.expiry = 600;
				

				//putMessage.setStringProperty(expiry, descriptor, value)
				putMessage.writeUTF(msg);
				//putMessage.expiry = 600;
				
				
				//int count = defaultLocaleQueue.getOpenInputCount();
							
				MQPutMessageOptions pmo = new MQPutMessageOptions();
				//System.out.println(pmo);
				
				defaultLocaleQueue.put(putMessage,pmo);

				//defaultLocaleQueue.getCurrentDepth();			
				//defaultLocaleQueue.getDescription();
				//int count = defaultLocaleQueue.getCurrentDepth();
				//defaultLocaleQueue.

				defaultLocaleQueue.close();
				qManager.disconnect();
				/*
				MQMessage getMessage = new MQMessage();
				
				getMessage.messageId = putMessage.messageId;
				
				MQGetMessageOptions gmo = new MQGetMessageOptions();
				defaultLocaleQueue.get(getMessage,gmo);
				
				String retrieveMsg = getMessage.readUTF();
				System.out.println("Message got from MQ: "+retrieveMsg);
				*/
				
			} catch (MQException e) {
				// TODO: handle exception
				e.printStackTrace();
			}catch (IOException e)
			{
				e.printStackTrace();
			}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void getID(String[] output)
	{
		MQEnvironment.hostname = hostName;
		MQEnvironment.channel = channel;
		MQEnvironment.port = port;
		
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
		
		try {
			qManager = new MQQueueManager(qMngrStr);
		} catch (MQException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF|MQC.MQOO_OUTPUT;
		  openOptions = openOptions + MQC.MQOO_INQUIRE; 
		
		  try {
				
				defaultLocaleQueue = qManager.accessQueue(queueName, openOptions);
				
				//MQMessage putMessage = new MQMessage();
				//putMessage.expiry = 3000;
				//putMessage.writeUTF(msg);
				
				//int count = defaultLocaleQueue.getOpenInputCount();
							
				//MQPutMessageOptions pmo = new MQPutMessageOptions();
				//System.out.println(pmo);
				//defaultLocaleQueue.put(putMessage,pmo);

				//defaultLocaleQueue.getCurrentDepth();			
				//defaultLocaleQueue.getDescription();
				int count = defaultLocaleQueue.getCurrentDepth();
				//defaultLocaleQueue.
				
				String result = String.valueOf(count);
				
				int lngt = result.length();
				
				if(lngt == 1)
				{
					output[0] = "00000"+result;
				}
				else if(lngt == 2)
				{
					output[0] = "0000"+result;
				}
				else if(lngt == 3)
				{
					output[0] = "000"+result;
				}
				else if(lngt == 4)
				{
					output[0] = "00"+result;
				}
				else if(lngt == 5)
				{
					output[0] = "0"+result;
				}
				else if(lngt == 6)
				{
					output[0] = result;
				}
				
				defaultLocaleQueue.close();
				qManager.disconnect();
				
				/*
				MQMessage getMessage = new MQMessage();
				
				getMessage.messageId = putMessage.messageId;
				
				MQGetMessageOptions gmo = new MQGetMessageOptions();
				defaultLocaleQueue.get(getMessage,gmo);
				
				String retrieveMsg = getMessage.readUTF();
				System.out.println("Message got from MQ: "+retrieveMsg);
				*/
				
			} catch (MQException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	
	public static void hash(String in, byte[][] out, String[] error) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.reset();
			md.update(("" + in).getBytes("utf-8"));

			byte[] output = new byte[24];
			md.digest(output, 0, 24);

			out[0] = output;
			error[0] = null;
		} catch (Exception e) {
			StringBuilder stackTrace = new StringBuilder();

			for (StackTraceElement traceLine : e.getStackTrace()) {
				stackTrace.append(traceLine.toString());
			}

			error[0] = ExceptionProcessor.getExcText("" + e.toString()) +
				"\n" + stackTrace;
		}
	}
	
	/*public static void intervalDate(int oneYear,int oneMonth,int oneDay,int twoYear,int twoMonth,int twoDay, String[] result)
	{ 
		try {
			
			DateTime start = new DateTime(oneYear, 12, oneDay, 0, 0, 0, 0);
			DateTime end = new DateTime(twoYear, 12, twoDay, 0, 0, 0, 0);
			Interval interval = new Interval(start, end);
			Period period = interval.toPeriod();
			
			result[0] = String.valueOf(period.getDays());
			
		} catch (Exception e) {
			StringBuilder stackTrace = new StringBuilder();

			for (StackTraceElement traceLine : e.getStackTrace()) {
				stackTrace.append(traceLine.toString());
			}
			
		}
	}*/

	
	public static void generateId(String in, String service,String[] output)
	{
		try {
			
			File file_date = new File("/dev/vg_data1/lv_data1/SBOLRSID/date_"+service+".txt");	
			File file = new File("/dev/vg_data1/lv_data1/SBOLRSID/id_"+service+".txt");			
			// if file doesnt exists, then create it
			if (!file_date.exists()) {
				file_date.createNewFile();
			}
			
			BufferedReader br_date = new BufferedReader(new FileReader("/dev/vg_data1/lv_data1/SBOLRSID/date_"+service+".txt"));
			
			String sCurrentLine_date;
			
			sCurrentLine_date = br_date.readLine();
			
			if(sCurrentLine_date == null)
			{
				FileWriter fw_date = new FileWriter(file_date.getAbsoluteFile());
				BufferedWriter bw_date = new BufferedWriter(fw_date);
				
				bw_date.write(in);
				bw_date.close();
			}
			else
			{
				if(!in.equals(sCurrentLine_date))
				{
					FileWriter fw_date = new FileWriter(file_date.getAbsoluteFile());
					BufferedWriter bw_date = new BufferedWriter(fw_date);
					
					bw_date.write(in);
					bw_date.close();
					
					file.delete();
				}
			}
				
				
			
			int count = 0;
					
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader br = new BufferedReader(new FileReader("/dev/vg_data1/lv_data1/SBOLRSID/id_"+service+".txt"));
								
			String sCurrentLine;
			
			sCurrentLine = br.readLine();
			
			if(sCurrentLine == null)
			{
				count = count + 1;
				
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				String parse = String.valueOf(count);
				bw.write(parse);
				bw.close();	
			}
			else
			{
				count = Integer.parseInt(sCurrentLine);
				count = count + 1;
				
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				String parse = String.valueOf(count);
				bw.write(parse);
				bw.close();
			
			}
			
					
			String result = String.valueOf(count);
			
			int lngt = result.length();
			
			if(lngt == 1)
			{
				output[0] = "00000"+result;
			}
			else if(lngt == 2)
			{
				output[0] = "0000"+result;
			}
			else if(lngt == 3)
			{
				output[0] = "000"+result;
			}
			else if(lngt == 4)
			{
				output[0] = "00"+result;
			}
			else if(lngt == 5)
			{
				output[0] = "0"+result;
			}
			else if(lngt == 6)
			{
				output[0] = result;
			}
			
			

		} catch (Exception e) {
			StringBuilder stackTrace = new StringBuilder();

			for (StackTraceElement traceLine : e.getStackTrace()) {
				stackTrace.append(traceLine.toString());
			}

			
		}
	}
	
	
	public static void getdate(String endate, String startdate, String[] args)
	{
		try {
			
			//int diffInDays = (int)((enddate - startdate)/(1000*60*60*24));
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			
			Date endate_date = format.parse(endate);
			Date start_date = format.parse(startdate);
			
			int diffInDays = (int)((endate_date.getTime() - start_date.getTime())/(1000*60*60*24));
			
			args[0] = String.valueOf(diffInDays);
			
			} catch (Exception e) {
			StringBuilder stackTrace = new StringBuilder();

			for (StackTraceElement traceLine : e.getStackTrace()) {
				stackTrace.append(traceLine.toString());
			}

			
		}
	}	
	
	public static void removeDigits(String number, String[] result)
	{
		result[0] = number.replaceAll("[0-9]", "");
	}
	
	public static void removeString(String character, String[] result)
	{
		result[0] = stripNonDigits(character);	
	}
	
	public static String stripNonDigits(
            final CharSequence input /* inspired by seh's comment */){
    final StringBuilder sb = new StringBuilder(
            input.length() /* also inspired by seh's comment */);
    for(int i = 0; i < input.length(); i++){
        final char c = input.charAt(i);
        if(c > 47 && c < 58){
            sb.append(c);
        }
    }
    return sb.toString();
	}
	
	public static void parseNameBs(String name, String[] output)
	{
		try {
			
			String[] sp = name.split("_");
			output[0] = sp[1];
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void getNameBs(String name, String[] output)
	{
		try {
			
			String[] sp = name.split("_");
			output[0] = sp[2];
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void crc8(String data,String[] output) {
        int crc = 0xFF;
        for (byte el : data.getBytes()) {
            crc ^= el;

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (crc << 1) ^ 0x31;
                } else {
                    crc = crc << 1;
                }
            }
        }
        
        output[0] = StringTo(crc);
        
    }
	
	public static String StringTo(int src)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(src);
		
		return sb.toString();
		
	}

	
	
}
