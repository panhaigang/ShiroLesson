package cn.et.lesson02.conf;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.et.lesson02.dao.UserMapper;
import cn.et.lesson02.entity.UserInfo;


/*
 * AuthorizingRealm��һ��������   �ڲ�����֤����Ȩ����
 * 
 */

@Component
public class MyDbRealm extends AuthorizingRealm{
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * Ȩ�޵Ŀ��� ��ͨ��URLӳ����Ƶ�  ʲô����·����Ҫʲô����Ȩ�޲��ܷ���
	 * ���û���һ�ε�½ʱ ҳ�������û�����������ҪRealm����֤��AuthenticationInfo��   Realm��Ҫȥ���ݿ��ж�ȡ��ƥ����û���������
	 *    ��½�ɹ����û������������session��
	 * �´η��� �Ͳ���ҪRealm�ĵ���֤   ����ҪRealm����Ȩ��֤��AuthorizationInfo��  ��Ȩ��ͨ��������ʧ��ҳ��
	 * 
	 * 
	 */
	
	
	
	
	/*
	 * �@ȡ��ǰ�Ñ����ڙ�����
	 * ����ǰ�Ñ��ڔ�����Ľ�ɫ�͙���  ���d��AuthorizationInfo
	 *  doGetAuthenticationInfo��֤ͨ�����˺���������session ��ȡ�û�����Ϣ����ɫ��Ȩ�޵ȣ�
	 * Ĭ���ڽ�����Ȩ��֤ʱ����
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection Principals) {
		String userName=Principals.getPrimaryPrincipal().toString();
		//��ȡ��ɫ
		Set<String> roleSet=userMapper.queryRoleByName(userName);
		//��ȡȨ��
		Set<String> permsSet=userMapper.queryPermsByName(userName);
		//��ɫ��Ȩ�޼��ϵĶ���
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//��ɫ���
		simpleAuthorizationInfo.setRoles(roleSet);
		//Ȩ�����
		simpleAuthorizationInfo.setStringPermissions(permsSet);
		return simpleAuthorizationInfo;
	}

	
	/*
	 * ��֤
	 * ����½������û��������������ݿ��е��û���������Ա� �Ƿ�һ��
	 * ����ֵnull��ʾ��֤ʧ��  ��null��֤ͨ��
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//�������token
		UsernamePasswordToken upt=(UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(token.getPrincipal().toString());
		//new String(upt.getPassword()))  ��һ������ת��  upt.getPassword()��ȡ����һ��char[]
		if(queryUser!=null && queryUser.getPassword().equals(new String(upt.getPassword()))){
			SimpleAccount simpleAccount = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			return simpleAccount;
		}
		return null;
	}

}
