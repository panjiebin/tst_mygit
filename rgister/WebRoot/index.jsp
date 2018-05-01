<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>报名系统</title>

	<link rel="stylesheet" type="text/css" href="css/index.css"/>
	<script src="${pageContext.request.contextPath}/js/index.js" type="text/javascript" charset="utf-8"></script>

	<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
        $(function () {
            var $butSub = $("button");
            var $inEmail = $(":input[name=student.email]");
            var $inNumber = $(":s[name='student.number']");

            // 密码等信息是否可用的标识
            var flags = true;
           
            $butSub.prop("disabled", true);

            $inEmail.change(function () {
                var email = $(this).val();
                email = $.trim(email);

                var $this = $(this);
                $this.nextAll("span").remove();
                $butSub.prop("disabled", true);

                var $span = $("<span style='color: red'></span>");

                if (email != "") {
                    var url = "emailCheck.action";
                    var args = {"email": email};
                    $.post(url, args, function (data) {
                        if (data == "0") { //  不可用
                            $this.after($span.text("邮箱不可用"));
                            flags = false;
                        } else if (data == "1") { //  可用
                            $this.after($span.css("color", "green").text("可用"));
                            $(":password").each(function () {
                                if ($(this).val() == "") {
                                } else {
                                    flagsNameAble = true;
                                    $butSub.prop('disabled', false);
                                }
                            })
                        } else {
                            alert("服务器错误");
                        }
                    })
                } else {
                    $this.after($span.text("名字不可为空"));
                    $butSub.prop("disabled", true);
                    this.focus();
                }
                return false;
            })

            
        })
    </script>
  </head>
  
  <body>
    <center style="margin-top: 2em"><h1>个人注册页面</h1></center>
 <s:form action = "register.action" method = "post" namespace="/"  theme = "simple"  >
	 <div class="register-box">
		 <label class="username_label">姓名
			 <s:textfield name = "student.name" maxlength="20" type="text" placeholder="您的用户名和登录名"/>
		 </label>
		 <div class="tips">

		 </div>
	 </div>
	 <div class="register-box">
		 <label class="username_label">学号
			 <s:textfield name = "student.number" id="number" maxlength="20" type="text" placeholder="您的学号"/>
		 </label>
		 <div class="tips">

		 </div>
	 </div>
	 <div class="register-box">
		 <label  class="other_label">设 置 密 码
			 <s:textfield name = "student.password" maxlength="20" type="password" placeholder="建议至少使用两种字符组合"/>

		 </label>
		 <div class="tips">

		 </div>
	 </div>
	 <div class="register-box">
		 <label class="username_label">学院
			 <s:textfield name = "student.department" maxlength="20" type="text" placeholder="您的学院"/>
		 </label>
		 <div class="tips">

		 </div>
	 </div>
	 <div class="register-box">
		 <label class="username_label">email
			 <s:textfield name = "student.email" id="email" maxlength="20" type="text" placeholder="您的邮件"/>
		 </label>
		 <div class="tips">

		 </div>
	 </div>
	 <div class="submit_btn" align="right">
		 <button type="submit" id="submit_btn">立 即 注 册</button>
		 <a href="login.jsp">已有账号去登录</a>
	 </div>

 </s:form>
  </body>
</html>
