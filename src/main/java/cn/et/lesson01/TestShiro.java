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
		
		//�������ļ��ж�ȡ�û���Ȩ����Ϣ    �������Բ��ù��Ƿ����
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");  
        org.apache.shiro.mgt.SecurityManager securityManager = (org.apache.shiro.mgt.SecurityManager)factory.getInstance();  
        SecurityUtils.setSecurityManager(securityManager);  
		//��ȡ��ǰ�û�
		Subject currentUser = SecurityUtils.getSubject();
		//��ȡ��ǰ�û��ĻỰ
		//Session session = currentUser.getSession();
		/**
		 * �û�������������
		 * 	principals and credentials
		 * 		principals�����ˣ���ʾ�û��ı�ʶ��Ϣ �����û���  �û���ַ
		 * 		credentials��ƾ֤�� ��ʾ�û����ڵ�½��ƾ֤ ��������  ֤���
		 * 
		 */
		//�ж��Ƿ��½
		if ( !currentUser.isAuthenticated() ) {
			//ʹ���û��ĵ�¼��Ϣ��������
		    UsernamePasswordToken token = new UsernamePasswordToken("jiaozi", "123456");
		    //��¼ʱץȡ����  �˺�������ȡ���¼�ɹ�
		    try {
		    	 currentUser.login(token);
		    	 System.out.println("��½�ɹ�");
		    	 //����¼����û����Ƿ�ӵ��ĳ����ɫ
				    if(currentUser.hasRole("role1")){
				    	System.out.println("ӵ��role1��ɫ");
				    }
				    //����½�Ƿ���û��Ƿ���ĳ��Ȩ��
				    if(currentUser.isPermitted("user:query:1")){
				    	System.out.println("�в�ѯ1���û���Ȩ��");
				    }
		    } catch ( UnknownAccountException uae ) {
		       System.out.println("�˺Ŵ���");
		    } catch ( IncorrectCredentialsException ice ) {
		       System.out.println("���벻ƥ��");
		    } catch ( LockedAccountException lae ) {
		    	System.out.println("�˺�����");
		    }catch ( AuthenticationException ae ) {
		       System.out.println("δ֪�쳣");
		    }
		}
	}
}