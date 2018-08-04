package cn.et.lesson01.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Servlet implementation class LogonController
 */
public class LogonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LogonController() {
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取前台的账号密码
		String username=request.getParameter("userNamo");
		String pwd=request.getParameter("possword");
		
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		//获取当前用户的会话
		Session session = currentUser.getSession();
		UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
		try {
	    	 currentUser.login(token);
	    	 
	    	 //跳转到成功页面
	    	 request.getRequestDispatcher("/suc.jsp").forward(request, response);
	    	 
	    	 
	    } catch ( UnknownAccountException uae ) {
	       System.out.println("账号错误");
	    } catch ( IncorrectCredentialsException ice ) {
	       System.out.println("密码不匹配");
	    } catch ( LockedAccountException lae ) {
	    	System.out.println("账号锁定");
	    }catch ( AuthenticationException ae ) {
	       System.out.println("未知异常");
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
