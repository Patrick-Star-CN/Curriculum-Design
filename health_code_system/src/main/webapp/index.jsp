<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>健康码管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
<c:choose>
    <c:when test="${user_id eq null}">
        <a class="button" href="login.jsp">登录</a>
    </c:when>
    <c:otherwise>
        <a class="button" href="${pageContext.request.contextPath}/record/query/info?type=${type}">查看健康码</a>
    </c:otherwise>
</c:choose>
</body>
</html>