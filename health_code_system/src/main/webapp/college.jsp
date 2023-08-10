<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="team.star.healthcodesystem.model.College" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>学院管理页面</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
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

        function loadNextPage() {
            if (currentPage === <%= request.getAttribute("total_page")%>) {
                alert("已经是最后一页了");
                return;
            }
            currentPage++;
            window.location.href = "/college/query?page=" + currentPage + "&size=10";
        }

        function loadPreviousPage() {
            if (currentPage === 1) {
                alert("已经是第一页了");
                return;
            }
            currentPage--;
            window.location.href = "/college/query?page=" + currentPage + "&size=10";
        }

        function editCollege(collegeId, collegeName) {
            fillForm({
                id: collegeId,
                name: collegeName
            });

            // 显示遮罩和弹出窗口
            var overlay = document.getElementById("overlay");
            var popup = document.getElementById("popup");
            overlay.style.display = "block";
            popup.style.display = "block";
        }

        function closePopup() {
            // 隐藏遮罩和弹出窗口
            var overlay = document.getElementById("overlay");
            var popup = document.getElementById("popup");
            overlay.style.display = "none";
            popup.style.display = "none";
        }

        function fillForm(college) {
            // 填充表单中的输入框
            document.getElementById("collegeId").value = college.id;
            document.getElementById("collegeName").value = college.name;
        }

        function deleteCollege(collegeId) {
            $.ajax({
                url: '/college/delete',
                type: 'POST',
                data: {
                    'id': collegeId
                },
                success: function (response) {
                    window.location.href = "/college/query?page=" + currentPage + "&size=10";
                },
                error: function (xhr, status, error) {
                    console.error(error);
                }
            });
        }

        function showUploadPopup() {
            var overlay = document.getElementById('overlay');
            var popup = document.getElementById('popup_add');
            overlay.style.display = 'block';
            popup.style.display = 'block';
        }

        function closeUploadPopup() {
            var overlay = document.getElementById('overlay');
            var popup = document.getElementById('popup_add');
            overlay.style.display = 'none';
            popup.style.display = 'none';
        }
    </script>
</head>
<body>
<table id="studentTable" class="table">
    <thead>
    <th>序号</th>
    <th>学院名称</th>
    <c:if test="${type eq 'SUPER_ADMIN'}">
        <th>操作</th>
    </c:if>
    </thead>
    <tbody>
    <c:forEach var="college" items="${colleges}">
        <tr>
            <td>${college.id}</td>
            <td>${college.name}</td>
            <c:if test="${type eq 'SUPER_ADMIN'}">
                <td>

                    <button class="button" onclick="editCollege('${college.id}', '${college.name}')">修改</button>
                    <button class="button" onclick="deleteCollege('${college.id}')">删除</button>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="back">
    <c:choose>
        <c:when test="${total_page eq 0}">
            0 / <%= request.getAttribute("total_page")%>
        </c:when>
        <c:otherwise>
            <%= request.getAttribute("page") %> / <%= request.getAttribute("total_page")%>
        </c:otherwise>
    </c:choose>
</div>
<div class="back">
    <c:if test="${page ne 1}">
        <a class="button" onclick="loadPreviousPage()">上一页</a>
    </c:if>
    <c:if test="${page ne total_page}">
        <a class="button" onclick="loadNextPage()">下一页</a>
    </c:if>
    <c:if test="${type eq 'SUPER_ADMIN'}">
        <a class="button" onclick="showUploadPopup()">添加学院</a>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/admin.jsp">返回</a>
</div>

<div id="overlay" class="overlay"></div>

<div id="popup" class="popup">
    <div class="item">
        <h2>修改学院信息</h2>
    </div>
    <form action="${pageContext.request.contextPath}/college/update" method="post" class="add_main">
        <input type="hidden" id="updateType" name="type">
        <input type="hidden" id="collegeId" name="collegeId"/>
        <div class="item">
            <label for="collegeName">学院名称:</label>
            <input type="text" id="collegeName" name="collegeName"/>
        </div>
        <div class="item">
            <div class="button-container">
                <button class="button" type="submit">保存</button>
                <button class="button" type="button" onclick="closePopup()">取消</button>
            </div>
        </div>
    </form>
</div>

<div id="popup_add" class="popup">
    <div class="item">
        <h2>添加学院</h2>
    </div>
    <form action="${pageContext.request.contextPath}/college/add" method="post" class="add_main">
        <div class="item">
            <label for="collegeAddName">学院名称:</label>
            <input type="text" id="collegeAddName" name="collegeName"/>
        </div>
        <div class="item">
            <button type="submit">提交</button>
            <button type="button" onclick="closeUploadPopup()">取消</button>
        </div>
    </form>
</div>
</body>
</html>
