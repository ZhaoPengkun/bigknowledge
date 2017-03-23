package com.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginInfo {
	
	public LoginInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public String login_info(String userName,String password){
		//--->>测试语句System.out.println("注册驱动");
		JdbcUtil jdbcUtil = new JdbcUtil();
		//连接数据库
		jdbcUtil.getConnection();
		//定义一个boolean变量用来判断用户名是否存在
		boolean flag = false;
		//定义注册的返回结果
		String login_reault;
		flag=Judge(userName);
		//----》》》测试语句
		System.out.println(flag);
		if (flag) {
			String sql_login ="select * from userinfo where userName = ? and password = ?";
			System.out.println(sql_login);
			Map<String, Object> map = new HashMap<String, Object>();
		    try {
		    	List<Object> params = new ArrayList<Object>();
		    	params.add(userName);
		    	params.add(password);
				map = JdbcUtil.findSimpleResult(sql_login, params);
			} catch (Exception e) {
				
		        e.printStackTrace();
		    }
		    if (!map.toString().equals("{}")) {
		    	login_reault = "login_success";
				
			}else {
				login_reault = "password_Fals";
				
			}
		}else {
			
			login_reault = "password_null";
			//---->>>调试语句
			System.out.println(login_reault);
		}
		//--->>>调试语句
	    //System.out.println(login_reault);
		return login_reault;
	}
	/**
	 * 判断用户名是否已经存在，如果存在返回false否则返回true
	 * @param userName
	 * @return
	 */
	private static boolean Judge(String userName) {
		// TODO Auto-generated method stub 
	    //SQL语句用来查询用户名是否存在
	    String sql_select = "select * from userinfo where userName = ?";
	    
        List<Object> params = new ArrayList<Object>();
        params.add(userName);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
			map = JdbcUtil.findSimpleResult(sql_select, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
	
        System.out.println(map.toString());
        if (map.toString().equals("{}")) {
        	return false; 
		}else {
			return true;
		}
		
	}
}

