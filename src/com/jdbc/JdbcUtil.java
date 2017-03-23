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
	
	//定义数据库的用户名
	private final String USERNAME = "34jz0x5yo5";
	//定义数据库的密码
	private final String PASSWORD = "hxw31h0ikkwk2ylih05l00100x122x4jkk224j4h";
	//定义数据库的驱动信息  
	private final String DRIVER = "com.mysql.jdbc.Driver";
	//定义访问数据库的物理地址
    private final String URL = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_dailynewspaper";
    //定义数据库的链接																			
    private static Connection connection;
    //定义sql语句的执行对象
    private static PreparedStatement pstmt;
    //定义查询返回的结果集合
    private static ResultSet resultSet;
    
	public JdbcUtil() {
	    //加载 JDBC 驱动程序
		try {
			Class.forName(DRIVER);
			//System.out.println("--->>驱动注册成功");
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
	 * 往数据库中添加用户信息
	 * @param sql
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public boolean updataByPreparedStatement(String sql,List<Object> params)throws SQLException{
		//判断是否对数据库操作成功
		boolean flag = false;
		//表示当前操作的数据库的行数
		int result =-1;
		//----->>>>>测试语句
		System.out.println(sql);
		pstmt = connection.prepareStatement(sql);
		//设置初始下标
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		//执行 SQL 语句
		result = pstmt.executeUpdate();
		flag = result > 0 ? true:false;
		return flag;
	}
	/**
	 * 查询数据库返回单条记录 
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
		//得到sql语句的返回结果
		resultSet = pstmt.executeQuery();
		//实现对resultSet的列操作
		ResultSetMetaData metaData = resultSet.getMetaData();
		//获取resultSet的列数
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
	 * 查询数据库返回的多条记录
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
				//使用给定对象设置指定参数的值
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
	 * 关闭数据库的连接
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
	 * 添加关注，修改关注信息
	 * @param userName 用户名
	 * @param attention 关注
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
