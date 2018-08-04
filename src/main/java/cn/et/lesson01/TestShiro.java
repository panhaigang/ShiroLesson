package cn.et.lesson01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestShiro {

	public static void main(String[] args) {
		
		//从配置文件中读取用户的权限信息    用来测试不用管是否过期
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");  
        org.apache.shiro.mgt.SecurityManager securityManager = (org.apache.shiro.mgt.SecurityManager)factory.getInstance();  
        SecurityUtils.setSecurityManager(securityManager);  
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		//获取当前用户的会话
		//Session session = currentUser.getSession();
		/**
		 * 用户包括两个部分
		 * 	principals and credentials
		 * 		principals（本人）表示用户的标识信息 比如用户名  用户地址
		 * 		credentials（凭证） 表示用户用于登陆的凭证 比如密码  证书等
		 * 
		 */
		//判断是否登陆
		if ( !currentUser.isAuthenticated() ) {
			//使用用户的登录信息创建令牌
		    UsernamePasswordToken token = new UsernamePasswordToken("jiaozi", "123456");
		    //登录时抓取错误  账号密码正取会登录成功
		    try {
		    	 currentUser.login(token);
		    	 System.out.println("登陆成功");
		    	 //检查登录后的用户名是否拥有某个角色
				    if(currentUser.hasRole("role1")){
				    	System.out.println("拥有role1角色");
				    }
				    //检查登陆是否的用户是否有某个权限
				    if(currentUser.isPermitted("user:query:1")){
				    	System.out.println("有查询1号用户的权限");
				    }
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
	}
}