package com.information;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.jdbc.JdbcUtil;
import com.json.Json_tool;

public class GetInfoUrl {
	public GetInfoUrl() {
		// TODO Auto-generated constructor stub
	}
	public static String info_url(String collegeName) {
		// TODO Auto-generated method stub
		//--->>测试语句System.out.println("注册驱动");
		JdbcUtil jdbcUtil = new JdbcUtil();
		//连接数据库
		jdbcUtil.getConnection();
		
		String sql_select = "select * from "+collegeName;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = JdbcUtil.findMoreResult(sql_select, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url_list = Json_tool.creatJsonString("url_list", list);
		System.out.println(url_list);
		return url_list;
	}

}
