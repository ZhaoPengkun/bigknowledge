package com.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.RegisteInfo;

public class Register extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Register() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//定义返回的注册结果
		String regist_result ="";
		//获取客户端传进来的值
		String userName = request.getParameter("userName");
		System.out.println(userName);
		String password = request.getParameter("password");
		System.out.println(password);
		String college = request.getParameter("college");
		System.out.println(college);
		String email = request.getParameter("email");
		String attention = request.getParameter("attention");
//		//----》》》》测试语句
//		if (userName.equals("admin")&&password.equals("123")) {
//			out.print("success!!");
//		}else {
//			out.print("false!!!");
//		}
		RegisteInfo registeInfo = new RegisteInfo();
		regist_result = registeInfo.registe_info(userName, password,college,attention,email);
		//返回注册结果
		out.print(regist_result);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

