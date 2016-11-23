package ru.croc.sbkz.adapters.billing.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

public class RequestBillingPrep extends MbJavaComputeNode{

	@Override
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		// TODO Auto-generated method stub
		MbMessage inMessage = assembly.getMessage();
		MbMessage outMessage = new MbMessage();
		String data_message="";
		byte[] blobData = getBlobData(inMessage);
		
		Socket clientSocket = null;
		
		try {
			String sentences = new String(blobData, "UTF-8");
			//String sentences = "<Billing UID=\"0c074559-442d-4a3f-8224-e4b90112312\" SystemId=\"BP_ERIB\"><GroupId>5b46377d-f91f-43e1-9f6b-d435fac52da1</GroupId><Recipient name=\"kisc\"><Requisites><objects><object name=\"TaxId\" value=\"000000000001\"/><object name=\"CorrAccount\" value=\"KZ000000000000000001\"/><object name=\"BIC\" value=\"ALSCKZKA\"/><object name=\"AcctId\" value=\"KZ100000000000000000\"/></objects></Requisites></Recipient><Source>65756876</Source><Clazz>operation</Clazz><Id>payment</Id><Target>prep</Target><Code>KISC0137</Code><Result><Status Id=\"0\">Successfully completed</Status></Result><Body><objects><object name=\"Target\" title=\"Номер абонента\" type=\"string\" value=\"0123456789\"><objects/></object><object name=\"Terminal\" value=\"65756876\"><objects/></object><object name=\"BillCode\" value=\"KISC0137\"><objects/></object><object name=\"Invoice\" title=\"Номер инвойса\" type=\"string\" value=\"141010133570\"><objects/></object><object name=\"Items\" type=\"list\"><objects><object name=\"Item\" type=\"list\" key=\"5\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"5\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"тг/м3\"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"60.230000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"100 \"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"3635.71\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"3635.71\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"Сумен жабдықтау/Водоснабжение\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"129\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"129\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"тг/м3\"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"27.850000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"                        \"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"1681.13\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"1681.13\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"Суды бұру/Водоотведение\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"11\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"11\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"тг/чел\"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"259.200000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"                        \"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"1818.03\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"1818.03\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"ҚТҚ шығару/Вывоз мусора\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"1\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"1\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"тг/тг/м2\"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"25.000000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"-4165.00\"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"Экспл.шығындар/Экспл.расходы КСК\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"124\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"124\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"                        \"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"0.000000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"                        \"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"5287.37\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"5287.37\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"ҚТО.Абон. Ақысы/ГЦТ.Абон. Плата\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"173\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"173\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"                        \"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"0.000000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"                        \"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"ҚТО Интернет/ГЦТ.Интернет\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"176\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"176\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"                        \"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"0.000000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"                        \"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"ҚТО Басқа қызметтер/ГЦТ.Прочие услуги\"><objects/></object></objects></object><object name=\"Item\" type=\"list\" key=\"209\"><objects><object name=\"Id\" title=\"Код услуги\" type=\"string\" value=\"209\"><objects/></object><object name=\"Measure\" title=\"Ед. изм.\" type=\"string\" value=\"тг/м3\"><objects/></object><object name=\"MinTariffValue\" title=\"Тариф менее лимита\" type=\"string\" value=\"378.110000\"><objects/></object><object name=\"DebtInfo\" title=\"Информация о долге/переплате\" type=\"string\" value=\"-0.12\"><objects/></object><object name=\"FixSum\" title=\"Сумма к оплате\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"PaySum\" title=\"Сумма оплаты\" type=\"money\" value=\"0.00\"><objects/></object><object name=\"groupName\" title=\"Электр қуаты / Электр. энергия\" type=\"string\" value=\"Газ (есептегiш)/Газ (счетчик)\"><objects/></object></objects></object></objects></object></objects></Body></Billing> ";
			clientSocket = new Socket();//("10.20.2.12", 20000);
			clientSocket.connect(new InetSocketAddress("10.20.2.12", 20000), 50000);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			byte[] bytes = sentences.getBytes("UTF-8");
			int l = bytes.length;
			
			outToServer.writeInt(l+4);
			outToServer.writeInt(l);
			outToServer.write(bytes);		
			
			
			/*byte buf[] = new byte[l];
			int r = clientSocket.getInputStream().read(buf, 0 , 4);*/
			//int r1 = clientSocket.getInputStream().read(buf,4,l);
			//Integer sizeResponse = new Integer(buf);
			//data_byte = new String(buf, 0, r);
			
			/*ByteBuffer bufferWrapper = ByteBuffer.wrap(buf);
			int sizeResponse = bufferWrapper.getInt();
			
			byte[] res = new byte[sizeResponse];
			
			String rs = "";
			long ln = 0;
			
			String data_message = "";
			while (ln <= sizeResponse ) {
				r = clientSocket.getInputStream().read(res);		
				data_message = data_message + (new String(res, 0, r));
				//rs = rs + data_message;
				ln = ln + r;
			}*/
			
			//DataInputStream r = new DataInputStream(clientSocket.getInputStream());

	       /* int l1 = r.readInt();
	        byte[] b = new byte[l1];
	        r.read(b, 0, l1);*/
	        
			InputStream is = clientSocket.getInputStream();
            DataInputStream r = new DataInputStream(is);
            int l1 = r.readInt();
            
            byte[] b = new byte[l1];
            
            try {
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
            
            ByteBuffer bf = ByteBuffer.allocate(l1);      

            
            
            is.read(b);
            bf.put(b);
            
            /*int rs =0;
            
            while (rs < l1)  {
                int u = is.read(b);
                rs = rs + u;
                bf.put(b);
            }*/

            
            //System.out.println(new String(bf.array(), "UTF-8"));			
			//data_message = b.toString();
	        data_message = new String(bf.array(), "UTF-8");
	       // System.out.println(new String(b, "UTF-8"));

	        bf.clear();
	        is.close();
	        r.close();
	        clientSocket.close();
	        
	        
//	----------------------------
	        //r.read(b, 0, l1);
	        
	        //data_message = new String(b, "UTF-8");
			
			/*File file_date = new File("/home/esbkzusr/SBOLRSID/billing.txt");	
			//File file = new File("/home/esbkzusr/SBOLRSID/id_"+service+".txt");			
			// if file doesnt exists, then create it
			if (!file_date.exists()) {
				file_date.createNewFile();
			}
			
			FileWriter fw_date = new FileWriter(file_date.getAbsoluteFile());
			BufferedWriter bw_date = new BufferedWriter(fw_date);
			
			bw_date.write(data_message);
			bw_date.close();*/
			
			
			
			MbElement outRoot = outMessage.getRootElement();
			MbElement outBody = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
			
			outBody.createElementAsLastChild(MbElement.TYPE_NAME, MbBLOB.ROOT_ELEMENT_NAME, data_message.getBytes("UTF-8"));
			
			MbMessageAssembly outAssembly = new MbMessageAssembly(
					assembly,
					assembly.getLocalEnvironment(),
					assembly.getExceptionList(), 
					outMessage);
			getOutputTerminal("out").propagate(outAssembly);
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
	}
	
	private byte[] getBlobData(MbMessage inMessage) throws MbException {

		MbElement root = inMessage.getRootElement();
		MbElement node = root.getFirstElementByPath("BLOB/BLOB");
		
		return (byte[]) node.getValue();
	}
	
	
}
