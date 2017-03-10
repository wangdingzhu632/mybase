<%--
  Created by IntelliJ IDEA.
  User: chenping
  Date: 16/8/30
  Time: 上午9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>系统登陆</title>
</head>
<body>

<form name="loginForm" id="login_form" action="<spring:url value="api/auth/login" />" method="post" autocomplete="off">

    <input type='text' name='loginid'
           hidefocus="true" class="user" required
           tabindex="1" placeholder="用户名"/>
    <input type='password' class="password"
           name='password' hidefocus="true" placeholder="密码" tabindex="2" required/>
    <input type="hidden" name="rememberMe" value="true"/>
    <input type="hidden" name="type" value="SYSTEM"/>
    <button type="submit" class="confirm">登录</button>

</form>


</body>
</html>
