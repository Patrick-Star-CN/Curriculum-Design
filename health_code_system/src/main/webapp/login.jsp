<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>登录页</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
    <script>
        const url = window.location.href;
        const queryString = url.split('?')[1];
        if (queryString) {
            const queryParams = {};
            queryString.split('&').forEach(param => {
                const [key, value] = param.split('=');
                queryParams[key] = decodeURIComponent(value);
            });
            if (queryParams.error) {
                alert(queryParams.error);
            }
        }
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/auth/login" method="post" >
    <div class="login-form" id="form" >
        <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" placeholder="请输入你的学号或工号" required>
        </div>
        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" name="password"
                   placeholder="非管理员用户输入身份证后八位，管理员用户输入密码" required>
        </div>
        <div>
            <label>用户类型</label>
            <input type="radio" name="type" value="student">学生
            <input type="radio" name="type" value="teacher">教师
            <input type="radio" name="type" value="admin">管理员
            <br>
        </div>
        <div class="form-group">
            <button type="submit">登录</button>
        </div>
    </div>
</form>
</body>
</html>
