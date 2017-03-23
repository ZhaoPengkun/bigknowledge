package com.jdbc;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



public class JdbcUtil {
	
	//�������ݿ���û���
	private final String USERNAME = "34jz0x5yo5";
	//�������ݿ������
	private final String PASSWORD = "hxw31h0ikkwk2ylih05l00100x122x4jkk224j4h";
	//�������ݿ��������Ϣ  
	private final String DRIVER = "com.mysql.jdbc.Driver";
	//����������ݿ�������ַ
    private final String URL = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_dailynewspaper";
    //�������ݿ������																			
    private static Connection connection;
    //����sql����ִ�ж���
    private static PreparedStatement pstmt;
    //�����ѯ���صĽ������
    private static ResultSet resultSet;
    
	public JdbcUtil() {
	    //���� JDBC ��������
		try {
			Class.forName(DRIVER);
			//System.out.println("--->>����ע��ɹ�");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

    }
		
			

	public Connection getConnection(){
		try {
			connection =DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
	}
	/**
	 * �����ݿ�������û���Ϣ
	 * @param sql
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public boolean updataByPreparedStatement(String sql,List<Object> params)throws SQLException{
		//�ж��Ƿ�����ݿ�����ɹ�
		boolean flag = false;
		//��ʾ��ǰ���������ݿ������
		int result =-1;
		//----->>>>>�������
		System.out.println(sql);
		pstmt = connection.prepareStatement(sql);
		//���ó�ʼ�±�
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		//ִ�� SQL ���
		result = pstmt.executeUpdate();
		flag = result > 0 ? true:false;
		return flag;
	}
	/**
	 * ��ѯ���ݿⷵ�ص�����¼ 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	
	public static Map<String, Object> findSimpleResult(String sql,List<Object> params)
			throws SQLException{
		Map<String, Object> result = new HashMap<String,Object>();
		pstmt = (PreparedStatement) connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		//�õ�sql���ķ��ؽ��
		resultSet = pstmt.executeQuery();
		//ʵ�ֶ�resultSet���в���
		ResultSetMetaData metaData = resultSet.getMetaData();
		//��ȡresultSet������
		int col_len = metaData.getColumnCount();
		while(resultSet.next()){
			
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i+1);
			    Object col_value = resultSet.getObject(col_name);
			    if (col_name == null) {
					col_name ="";
				}
			    result.put(col_name, col_value);
			}
		}
			
		return result;
	}
	/**
	 * ��ѯ���ݿⷵ�صĶ�����¼
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> findMoreResult(String sql,List<Object> params)throws SQLException{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		pstmt = (PreparedStatement) connection.prepareStatement(sql);
	    int index = 1;
	    if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				//ʹ�ø�����������ָ��������ֵ
				pstmt.setObject(index++, params.get(i));
			}
		}
	    
	    resultSet = pstmt.executeQuery();
	    ResultSetMetaData metaData = resultSet.getMetaData();
	    int col_len = metaData.getColumnCount();
	    while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String,Object>();
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i+1);
				Object col_value = resultSet.getObject(col_name);
				if (col_value == null) {
					col_value = "";
				}
				map.put(col_name, col_value);
			}
			result.add(map);
			map =null;
		}
		return result;
		
	}
	/**
	 * �ر����ݿ������
	 */
	public static void releaseConn()
	{
		
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * ��ӹ�ע���޸Ĺ�ע��Ϣ
	 * @param userName �û���
	 * @param attention ��ע
	 * 
	 */
	public static String modify(String userName,String attention){
        String sql = "update userinfo set attention ='"+attention
                +"' where userName ='"+userName+"'";
       
        try {
        	
        	pstmt = connection.prepareStatement(sql);
        	pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return sql;
    }
	public static void attentionModify(String userName,String attention){
		String sql2="select * from userinfo where userName = ?";
		List<Object> params = new ArrayList<Object>();
    	params.add(userName);
		try {
			attention=findSimpleResult(sql2,params).get("attention")+attention;
			System.out.println(attention);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String sql = "update userinfo set attention ='"+attention
                +"' where userName ='"+userName+"'";
     
        try {
        	pstmt = connection.prepareStatement(sql);
        	pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
	public static void canAttentionModify(String userName,String attention){
		String sql2="select * from userinfo where userName = ?";
		System.out.println(attention);
		List<Object> params = new ArrayList<Object>();
    	params.add(userName);
		try {
			List<String> attentionList= new ArrayList<String>();
			String attentionTemp =(String) findSimpleResult(sql2,params).get("attention");
			StringTokenizer tokenizer = new StringTokenizer(attentionTemp, "@");
			while(tokenizer.hasMoreTokens()){
				attentionList.add(tokenizer.nextToken());
			}
			System.out.println(attentionList);
		    for (int i = 0; i < attentionList.size(); i++) {
				if (attentionList.get(i).equals(attention)) {
					attentionList.remove(i);
					break;
				}
			}
		    attention = "";
			for (int i = 0; i < attentionList.size(); i++) {
				attention = attention+"@"+attentionList.get(i);
			}
			System.out.println(attention);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String sql = "update userinfo set attention ='"+attention
                +"' where userName ='"+userName+"'";
     
        try {
        	pstmt = connection.prepareStatement(sql);
        	pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
	
}
