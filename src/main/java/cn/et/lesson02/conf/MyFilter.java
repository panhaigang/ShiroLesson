package cn.et.lesson02.conf;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;

import cn.et.lesson02.dao.UserMapper;
import cn.et.lesson02.entity.Menu;  

/**
 * 
 * 此封装方法为通用方法  只需要修改   List<Menu> queryMenu=userMapper.queryMenu();  从数据库读取的方法即可
 * @author Administrator
 *
 */

@Component
public class MyFilter extends AuthorizationFilter {  
      
    @Autowired  
    UserMapper userMapper; 
    /**  
     * 匹配指定过滤器规则的url  
     * @param regex  
     * @param url  
     * @return  
     */  
    public static boolean matchUrl(String regex,String url){  
        regex=regex.replaceAll("/+", "/");  
        if(regex.equals(url)){  
            return true;  
        }  
        regex=regex.replaceAll("\\.", "\\\\.");  
        // /login.html  /l*.html  
        regex=regex.replaceAll("\\*", ".*");  
        // /**/login.html  /a/b/login.html  
        if(regex.indexOf("/.*.*/")>=0){  
            regex=regex.replaceAll("/\\.\\*\\.\\*/", "((/.*/)+|/)");  
        }  
        System.out.println(regex+"----"+url);  
        return Pattern.matches(regex, url);  
    }  
    /**  
     * 测试  
     * @param args  
     */  
    public static void main(String[] args) {  
        System.out.println(matchUrl("/**/s*.html","/t/g/login.html"));  
    }   
    /**  
     * isAccessAllowed用于判断当前url的请求是否能验证通过  如果验证失败 调用父类的onAccessDenied决登定跳转到录失败页还是授权失败页面  
     */  
    @Override  
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  
            throws Exception {  
        HttpServletRequest req=(HttpServletRequest)request;
      
     
        
        //获取用户访问的资源路径
        String url=req.getRequestURI();  
        //截取路径  把上下文截取掉
        int i=url.lastIndexOf("/");
        String newurl=url.substring(i);
        
        
        
        
        //通过url获取授权类型  
        //List<Menu> queryMenu = userMapper.queryMenuByUrl(newurl);
        
        //获取菜单的信息
        List<Menu> queryMenu=userMapper.queryMenu();
      //判断数据库中有没有配置当前url的授权
        if(queryMenu.size()==0){
        	return false;
        }
        String urlAuth=null;
        for(Menu menu:queryMenu){
        	//数据库中的路劲与请求路径匹配
        	if(matchUrl(menu.getMenuUrl(), newurl)){
        		//获取路径所需要的权限
        		urlAuth=menu.getMenuFilter();
        	}
        }
        
                
        /*//获取权限
        String urlAuth=queryMenu.get(0).getMenuFilter();*/
        //判断是否有权限
        if(urlAuth==null){  
            return false;  
        }  
        //配置的过滤器是anon 直接放过  
        if(urlAuth.startsWith("anon")){  
            return true;  
        }  
        //配置的是authc 判断当前用户是否认证通过(登录)  
        Subject subject = getSubject(request, response);  
        if(urlAuth.startsWith("authc")){  
            return subject.isAuthenticated();  
        }  
        //授权认证 也需要判断是否登录 没有登录返回 登录继续下面的验证  
        boolean ifAuthc=subject.isAuthenticated();  
        if(!ifAuthc){
            return ifAuthc;
        }
        //如果是定义的roles过滤器  获取所有的roles 一般是roles[a,b]  
        if(urlAuth.startsWith("roles")){  
            String[] rolesArray=urlAuth.split("roles\\[")[1].split("\\]")[0].split(",");
            if (rolesArray == null || rolesArray.length == 0) {  
                return true;  
            }  
            Set<String> roles = CollectionUtils.asSet(rolesArray);  
          /*  for (int i1 = 0; i1 < rolesArray.length; i1++) {  
                if (subject.hasRole(rolesArray[i1])) { //若当前用户是rolesArray中的任何一个，则有权限访问  
                    return true;
                }  
            }  */
            return subject.hasAllRoles(roles);//需要全部满足所有角色
           

        }  
        if(urlAuth.startsWith("perms")){
            String[] perms=urlAuth.split("perms\\[")[1].split("\\]")[0].split(",");  
            boolean isPermitted = true;  
            if (perms != null && perms.length > 0) {  
                if (perms.length == 1) {  
                    if (!subject.isPermitted(perms[0])) {  
                        isPermitted = false;  
                    }  
                } else {  
                    if (!subject.isPermittedAll(perms)) {  
                        isPermitted = false;  
                    }  
                }  
            }  
  
            return isPermitted;  
        }  
        return false;  
    }  
  
}  