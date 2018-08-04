package cn.et.lesson02.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;

import cn.et.lesson02.entity.Menu;
import cn.et.lesson02.entity.UserInfo;

public interface UserMapper {
	
	
	//查询用户的账号和密码
	@Select("select user_name as userName,pass_word as password from user_info where user_name=#{0}")
	public UserInfo queryUser(String username);
	
	//查询用户的拥有的角色
	@Select("SELECT r.role_name FROM user_info u INNER JOIN user_role_relation urr ON u.user_id=urr.user_id "+
			"INNER JOIN role r ON urr.role_id=r.role_id INNER JOIN role_perms_relation rpr ON rpr.perm_id=r.role_id "+	
			"WHERE u.user_name=#{0}")
	public Set<String> queryRoleByName(String userName);
	
	//查询用户拥有的权限
	@Select("SELECT p.perm_tag FROM user_info u INNER JOIN user_role_relation urr ON u.user_id=urr.user_id "+ 
			"INNER JOIN role r ON urr.role_id=r.role_id INNER JOIN role_perms_relation rpr ON rpr.perm_id=r.role_id "+
			"INNER JOIN perms p ON rpr.role_id=r.role_id "+
			"WHERE u.user_name=#{0}")
	public Set<String> queryPermsByName(String userName);
	
	//查询菜单
	@Select("SELECT menu_id AS menuId,menu_name AS menuName,menu_url AS menuUrl,menu_filter AS menuFilter,is_menu AS isMemnu FROM menu")
	public List<Menu> queryMenu();
	
	
	//查询url对应的菜单
	@Select("SELECT menu_filter AS menuFilter,menu_url AS menuUrl FROM menu where menu_url=#{0}")
	public List<Menu> queryMenuByUrl(String url);
	
	
	//查询用户对应的菜单
	@Select("SELECT menu_name AS menuName,menu_url AS menuUrl FROM user_info ui INNER JOIN user_menu_relation umr ON ui.user_id=umr.user_id INNER JOIN menu m ON umr.menu_id=m.menu_id where user_name=#{0}")
	public List<Menu> queryMenuByUser(String userName);
	
	
}