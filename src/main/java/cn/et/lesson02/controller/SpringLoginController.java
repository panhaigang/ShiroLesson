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
		
		
		
		//��ȡ��ǰ�û�
		Subject currentUser = SecurityUtils.getSubject();
		//��ȡ��ǰ�û��ĻỰ
		Session session = currentUser.getSession();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			//��½ʧ�ܻ��׳��쳣
	    	 currentUser.login(token);//�Զ�����cn.et.lesson02.conf.MyDbRealm�е�doGetAuthenticationInfo==��֤
	    	 //��½�ɹ���ѯ���˵�
	    	 model.addAttribute("menuList",userMapper.queryMenuByUser(userName));
	    	 
	    	 
	    	 return "/layout.jsp";
	    } catch ( UnknownAccountException uae ) {
	       System.out.println("�˺Ŵ���");
	    } catch ( IncorrectCredentialsException ice ) {
	       System.out.println("���벻ƥ��");
	    } catch ( LockedAccountException lae ) {
	    	System.out.println("�˺�����");
	    }catch ( AuthenticationException ae ) {
	       System.out.println("δ֪�쳣");
	    }
		return "erroy.jsp";
	}
}
