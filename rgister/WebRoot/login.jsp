<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@taglib prefix="s" uri="/struts-tags"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css" rel='stylesheet' type='text/css' />
<title>用户登陆页面</title>

 <script> 
    function reload(){ 
        document.getElementById("code").setAttribute("src","code.jsp?a="+new Date().getTime()); 
    } 
    function regist(){ 
        window.top.location = "student_regist.jsp";
    } 
    </script>
</head>
<body>
${tip }
<h1>登入页面</h1>
<div class="login-form">
  <div class="close"></div>
  <div class="head-info">
    <label class="lbl-1"> </label>
    <label class="lbl-2"> </label>
    <label class="lbl-3"> </label>
  </div>
  <div class="clear"> </div>
  <div class="avtar">
    <img src="images/avtar.png" />
  </div>
<form action="studentlogin.action" method="post" >
  <input type="text" class="text" name="student.number" value="Username" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Username';}" >
  <s:fielderror fieldName="student.number"/>
  <div class="key">
    <input type="password" name="student.password" style="margin-bottom: 0;" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
    <input class="code" type="text"  maxLength=10 size=6 name="validateCode" style="width: 50%;margin-top:0;">
    <img width="80" id="code" style="CURSOR: pointer" onclick="reload();"  src="code.jsp" alt="点击刷新验证码">
  </div>


  <div class="signin" align="right">
    <input type="submit" value="登陆" ><a href="index.jsp"><span style="font-size: 20px;color: red">注册？</span></a>
  </div>
</form>
</div>
  <div class="copy-rights">
    <p>made by muweng</p>
  </div>
</body>
</html>
