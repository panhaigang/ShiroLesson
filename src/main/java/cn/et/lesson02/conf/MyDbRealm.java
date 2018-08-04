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
 * AuthorizingRealm是一个抽象类   内部有验证和授权方法
 * 
 */

@Component
public class MyDbRealm extends AuthorizingRealm{
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 权限的控制 是通过URL映射控制的  什么样的路径需要什么样的权限才能访问
	 * 当用户第一次登陆时 页面输入用户名和密码需要Realm的认证（AuthenticationInfo）   Realm需要去数据库中读取所匹配的用户名和密码
	 *    登陆成功后用户名和密码存在session中
	 * 下次访问 就不需要Realm的的认证   而需要Realm的授权验证（AuthorizationInfo）  授权不通过就跳入失败页面
	 * 
	 * 
	 */
	
	
	
	
	/*
	 * 獲取當前用戶的授權數據
	 * 將當前用戶在數據庫的角色和權限  加載到AuthorizationInfo
	 *  doGetAuthenticationInfo认证通过后账号密码会存入session 拉取用户的信息（角色，权限等）
	 * 默认在进行授权认证时调用
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection Principals) {
		String userName=Principals.getPrimaryPrincipal().toString();
		//获取角色
		Set<String> roleSet=userMapper.queryRoleByName(userName);
		//获取权限
		Set<String> permsSet=userMapper.queryPermsByName(userName);
		//角色和权限集合的对象
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//角色添加
		simpleAuthorizationInfo.setRoles(roleSet);
		//权限添加
		simpleAuthorizationInfo.setStringPermissions(permsSet);
		return simpleAuthorizationInfo;
	}

	
	/*
	 * 认证
	 * 将登陆输入的用户名和密码与数据库中的用户名和密码对比 是否一致
	 * 返回值null表示认证失败  非null认证通过
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//頁面傳入的token
		UsernamePasswordToken upt=(UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(token.getPrincipal().toString());
		//new String(upt.getPassword()))  做一个类型转化  upt.getPassword()获取的是一个char[]
		if(queryUser!=null && queryUser.getPassword().equals(new String(upt.getPassword()))){
			SimpleAccount simpleAccount = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			return simpleAccount;
		}
		return null;
	}

}
