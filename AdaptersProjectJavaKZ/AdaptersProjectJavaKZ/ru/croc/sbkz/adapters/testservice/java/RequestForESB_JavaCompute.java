package ru.croc.sbkz.adapters.testservice.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class RequestForESB_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
				
		MbOutputTerminal out = getOutputTerminal("out");
		MbMessage inMessage = assembly.getMessage();
		
		MbMessage outMessage = new MbMessage(inMessage);
		MbMessageAssembly outAssembly = new MbMessageAssembly(assembly,outMessage);
		
		Statement stmt = null;
		ResultSet src = null;
		Connection conn = null;
		
		try{
		
		conn = this.getJDBCType4Connection("Oracle", JDBC_TransactionType.MB_TRANSACTION_AUTO);
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		src = stmt.executeQuery("SELECT * FROM ESBLOG.EVENT WHERE EVENT_ID = 81944788");
		if(src.next())
		{
			System.out.println(src.getString("RQ_UID"));
		}
		}catch(SQLException e)
		{
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}finally{
			try{
			if(stmt != null) {stmt.close();}
			if(src != null) {src.close();}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
			
		
	}

}
