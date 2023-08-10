<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>管理后台</title>
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

        function showPopup() {
            var overlay = document.getElementById('overlay');
            var popup = document.getElementById('popup');
            overlay.style.display = 'block';
            popup.style.display = 'block';
        }

        function closePopup() {
            var overlay = document.getElementById('overlay');
            var popup = document.getElementById('popup');
            overlay.style.display = 'none';
            popup.style.display = 'none';
        }
    </script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
<div class="admin">
    <a class="button" href="${pageContext.request.contextPath}/student_query.jsp">学生管理</a>
    <c:if test="${type eq 'SUPER_ADMIN' or type eq 'SCHOOL_ADMIN'}">
        <a class="button" href="${pageContext.request.contextPath}/teacher_query.jsp">教师管理</a><br>
    </c:if>
    <c:if test="${type eq 'SUPER_ADMIN' or type eq 'SCHOOL_ADMIN'}">
        <a class="button" href="${pageContext.request.contextPath}/college/query?page=1&size=10">学院管理</a>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/major_query.jsp">专业管理</a><br>
    <a class="button" href="${pageContext.request.contextPath}/class_query.jsp">班级管理</a>
    <a class="button" href="${pageContext.request.contextPath}/record_query.jsp">打卡统计</a><br>
    <a class="button" onclick="showPopup()">修改密码</a>
</div>

<div id="overlay" class="overlay"></div>

<div id="popup" class="popup">
    <div class="item">
        <h2>修改密码</h2>
    </div>
    <form action="${pageContext.request.contextPath}/teacher/change_pwd" method="post" class="add_main">
        <div class="item">
            <label for="oldPwd">旧密码：</label>
            <input type="password" id="oldPwd" name="oldPwd" required><br>
        </div>
        <div class="item">
            <label for="newPwd">新密码：</label>
            <input type="password" id="newPwd" name="newPwd" required><br>
            <input type="hidden" name="isAdmin" value="no">
        </div>
        <div class="item">
            <div class="button-container">
                <button class="button" type="submit">确认修改</button>
                <button class="button" type="button" onclick="closePopup()">取消</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
