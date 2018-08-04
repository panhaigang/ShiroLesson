package cn.et.lesson02.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.et.lesson02.dao.UserMapper;

@Controller
public class SpringLoginController {
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/loginShiro")
	public String login(String userName,String password,Model model){
		
		
		
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		//获取当前用户的会话
		Session session = currentUser.getSession();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			//登陆失败会抛出异常
	    	 currentUser.login(token);//自动调用cn.et.lesson02.conf.MyDbRealm中的doGetAuthenticationInfo==认证
	    	 //登陆成功查询出菜单
	    	 model.addAttribute("menuList",userMapper.queryMenuByUser(userName));
	    	 
	    	 
	    	 return "/layout.jsp";
	    } catch ( UnknownAccountException uae ) {
	       System.out.println("账号错误");
	    } catch ( IncorrectCredentialsException ice ) {
	       System.out.println("密码不匹配");
	    } catch ( LockedAccountException lae ) {
	    	System.out.println("账号锁定");
	    }catch ( AuthenticationException ae ) {
	       System.out.println("未知异常");
	    }
		return "erroy.jsp";
	}
}
