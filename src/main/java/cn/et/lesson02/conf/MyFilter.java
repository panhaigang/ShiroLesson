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
 * �˷�װ����Ϊͨ�÷���  ֻ��Ҫ�޸�   List<Menu> queryMenu=userMapper.queryMenu();  �����ݿ��ȡ�ķ�������
 * @author Administrator
 *
 */

@Component
public class MyFilter extends AuthorizationFilter {  
      
    @Autowired  
    UserMapper userMapper; 
    /**  
     * ƥ��ָ�������������url  
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
     * ����  
     * @param args  
     */  
    public static void main(String[] args) {  
        System.out.println(matchUrl("/**/s*.html","/t/g/login.html"));  
    }   
    /**  
     * isAccessAllowed�����жϵ�ǰurl�������Ƿ�����֤ͨ��  �����֤ʧ�� ���ø����onAccessDenied���Ƕ���ת��¼ʧ��ҳ������Ȩʧ��ҳ��  
     */  
    @Override  
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  
            throws Exception {  
        HttpServletRequest req=(HttpServletRequest)request;
      
     
        
        //��ȡ�û����ʵ���Դ·��
        String url=req.getRequestURI();  
        //��ȡ·��  �������Ľ�ȡ��
        int i=url.lastIndexOf("/");
        String newurl=url.substring(i);
        
        
        
        
        //ͨ��url��ȡ��Ȩ����  
        //List<Menu> queryMenu = userMapper.queryMenuByUrl(newurl);
        
        //��ȡ�˵�����Ϣ
        List<Menu> queryMenu=userMapper.queryMenu();
      //�ж����ݿ�����û�����õ�ǰurl����Ȩ
        if(queryMenu.size()==0){
        	return false;
        }
        String urlAuth=null;
        for(Menu menu:queryMenu){
        	//���ݿ��е�·��������·��ƥ��
        	if(matchUrl(menu.getMenuUrl(), newurl)){
        		//��ȡ·������Ҫ��Ȩ��
        		urlAuth=menu.getMenuFilter();
        	}
        }
        
                
        /*//��ȡȨ��
        String urlAuth=queryMenu.get(0).getMenuFilter();*/
        //�ж��Ƿ���Ȩ��
        if(urlAuth==null){  
            return false;  
        }  
        //���õĹ�������anon ֱ�ӷŹ�  
        if(urlAuth.startsWith("anon")){  
            return true;  
        }  
        //���õ���authc �жϵ�ǰ�û��Ƿ���֤ͨ��(��¼)  
        Subject subject = getSubject(request, response);  
        if(urlAuth.startsWith("authc")){  
            return subject.isAuthenticated();  
        }  
        //��Ȩ��֤ Ҳ��Ҫ�ж��Ƿ��¼ û�е�¼���� ��¼�����������֤  
        boolean ifAuthc=subject.isAuthenticated();  
        if(!ifAuthc){
            return ifAuthc;
        }
        //����Ƕ����roles������  ��ȡ���е�roles һ����roles[a,b]  
        if(urlAuth.startsWith("roles")){  
            String[] rolesArray=urlAuth.split("roles\\[")[1].split("\\]")[0].split(",");
            if (rolesArray == null || rolesArray.length == 0) {  
                return true;  
            }  
            Set<String> roles = CollectionUtils.asSet(rolesArray);  
          /*  for (int i1 = 0; i1 < rolesArray.length; i1++) {  
                if (subject.hasRole(rolesArray[i1])) { //����ǰ�û���rolesArray�е��κ�һ��������Ȩ�޷���  
                    return true;
                }  
            }  */
            return subject.hasAllRoles(roles);//��Ҫȫ���������н�ɫ
           

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