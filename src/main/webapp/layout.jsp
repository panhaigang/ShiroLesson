<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- uri="http://java.sun.com/jstl/core"===这个可能用不了   要用这个uri="http://java.sun.com/jstl/core_rt" -->
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
  <head>
    <title>layout.jsp</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<script type="text/javascript" src="jquery.min.js"></script>
	<script type="text/javascript" src="jquery.easyui.min.js"></script>
    
    
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

	<script type="text/javascript">
	
		//通用的跳转  MyTitle标签   myUrl路径
		function urlClick(MyTitle,myUrl){
			//判断标签页是否存在
			var falg=$("#tt").tabs("exists",MyTitle)
			if(!falg){
				
				$("#tt").tabs("add",{
					title:MyTitle,
					closable:true,
					//<ifram></iframe>    
					content:'<iframe frameborder=0 width="100%" height="100%" src="'+myUrl+'"></iframe>'
				
				})
			}
			
			
			//存在就直接打开
			$("#tt").tabs("select",MyTitle);
		}
	</script>
  </head>
  
  <body>
	<body style="margin: 1px">
	<!-- 布局 -->
	<div class="easyui-layout" style="width:100%;height:100%;" >
		<!-- 上面的部分 -->
		<div data-options="region:'north'" style="height:15%; background-image: url(4545.jpg);background-repeat: round;">
			<div style="height:85%"></div>
			<div style="text-align:right;width:80%;heigth:80%"><a href="" ><font color=red>安全退出</font></a></div>
		</div>
		<!-- 左边部分 -->
		<div data-options="region:'west',split:true" title="导航菜单" style="width:17%;">
			<div class="easyui-accordion" style="width:100%;height:85%;">
				<div title="权限管理" style="padding:10px;">
					<table style="font-size:14px" id="table">
						<c:forEach var="menu" items="${requestScope.menuList }">
							<tr><td id="tr2"><a href="javascript:urlClick('${menu.menuName }','${pageContext.request.contextPath}/${menu.menuUrl }')" style="text-decoration: none"><img alt="" src="themes/icons/man.png">${menu.menuName }</a></td></tr>
						</c:forEach>
					</table>
				</div>
				<div title="系统设置" style="padding:10px;">
				
				</div>
			</div>
		</div>
		<!-- 中间部分显示内容 -->
		<div data-options="region:'center'">
			<div id="tt" class="easyui-tabs" style="width:100%;height:100%">
				<div title="欢迎使用" style="padding:10px"></div>
				
			</div>
		</div>
	</div>
 
</body>
  </body>
</html>