package com.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteInfo {

	public RegisteInfo() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 将用户的注册信息加入数据库
	 * @param userName  用户注册填写的用户名
	 * @param password  用户注册填写的密码
	 * @param email 
	 * @param college 
	 * @return
	 */
	public String registe_info(String userName,String password, String college,String attention, String email){
		//--->>测试语句System.out.println("注册驱动");
		JdbcUtil jdbcUtil = new JdbcUtil();
		//连接数据库
		jdbcUtil.getConnection();
		//定义一个boolean变量判断是否往数据库中成功添加数据
		boolean add_flag = false;
		//定义一个boolean变量用来判断用户名是否存在
		boolean flag = false;
		//定义注册的返回结果
		String regist_reault;
		flag=Judge(userName);
		if (flag) {
			String sql_add ="insert into userinfo(userName,password,college,email,attention) value(?,?,?,?,?)";
		    List<Object> params = new ArrayList<Object>();
		    params.add(userName);
		    params.add(password);
		    params.add(college);
		    params.add(email);
		    params.add(attention);
		    try {
		    	
				add_flag = jdbcUtil.updataByPreparedStatement(sql_add, params);
			} catch (Exception e) {
				
		        e.printStackTrace();
		    }
		    if (add_flag) {
		    	regist_reault = "注册成功";
				
			}else {
				regist_reault = "注册有误";
			}
		}else {
			
			regist_reault = "对不起，您所注册的用户名已存在";
		}
	    
		return regist_reault;
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
        	return true; 
		}else {
			return false;
		}
		
	}
}

