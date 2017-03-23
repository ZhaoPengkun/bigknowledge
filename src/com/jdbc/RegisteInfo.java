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
	 * ���û���ע����Ϣ�������ݿ�
	 * @param userName  �û�ע����д���û���
	 * @param password  �û�ע����д������
	 * @param email 
	 * @param college 
	 * @return
	 */
	public String registe_info(String userName,String password, String college,String attention, String email){
		//--->>�������System.out.println("ע������");
		JdbcUtil jdbcUtil = new JdbcUtil();
		//�������ݿ�
		jdbcUtil.getConnection();
		//����һ��boolean�����ж��Ƿ������ݿ��гɹ��������
		boolean add_flag = false;
		//����һ��boolean���������ж��û����Ƿ����
		boolean flag = false;
		//����ע��ķ��ؽ��
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
		    	regist_reault = "ע��ɹ�";
				
			}else {
				regist_reault = "ע������";
			}
		}else {
			
			regist_reault = "�Բ�������ע����û����Ѵ���";
		}
	    
		return regist_reault;
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
        	return true; 
		}else {
			return false;
		}
		
	}
}

