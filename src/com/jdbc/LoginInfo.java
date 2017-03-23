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
		//--->>�������System.out.println("ע������");
		JdbcUtil jdbcUtil = new JdbcUtil();
		//�������ݿ�
		jdbcUtil.getConnection();
		//����һ��boolean���������ж��û����Ƿ����
		boolean flag = false;
		//����ע��ķ��ؽ��
		String login_reault;
		flag=Judge(userName);
		//----�������������
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
			//---->>>�������
			System.out.println(login_reault);
		}
		//--->>>�������
	    //System.out.println(login_reault);
		return login_reault;
	}
	/**
	 * �ж��û����Ƿ��Ѿ����ڣ�������ڷ���false���򷵻�true
	 * @param userName
	 * @return
	 */
	private static boolean Judge(String userName) {
		// TODO Auto-generated method stub 
	    //SQL���������ѯ�û����Ƿ����
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

