<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="team.star.healthcodesystem.model.Major" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>专业管理页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        const url = window.location.href;
        const queryString = url.split('?')[1];
        var currentPage;
        if (queryString) {
            const queryParams = {};
            queryString.split('&').forEach(param => {
                const [key, value] = param.split('=');
                queryParams[key] = decodeURIComponent(value);
            });
            if (queryParams.error) {
                alert(queryParams.error);
            }
            currentPage = queryParams.page;
        }
    </script>
</head>
<body>
<table id="recordCountTable" class="table">
    <thead>
    <th>名称</th>
    <th>已打卡数量/总数</th>
    </thead>
    <tbody>
    <c:forEach var="recordCount" items="${recordCounts}">
        <tr>
            <td>${recordCount.name}</td>
            <td>${recordCount.count} / ${recordCount.totalCount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${records.size() > 0}">
    <table id="recordTable" class="table">
        <thead>
        <th>姓名</th>
        <th>已打卡数量/总数</th>
        </thead>
        <tbody>
        <c:forEach var="record" items="${records}">
            <tr>
                <td>${record.name}</td>
                <td>${record.codeColor}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<div class="back">
    <a class="button" href="${pageContext.request.contextPath}/record_query.jsp">返回</a>
</div>

</body>
</html>
