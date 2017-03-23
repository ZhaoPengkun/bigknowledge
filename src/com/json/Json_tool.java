package com.json;

import com.alibaba.fastjson.JSONObject;

public class Json_tool {
	 
	public static String creatJsonString(String key,Object value){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);
		
		return jsonObject.toString();
	}

}
