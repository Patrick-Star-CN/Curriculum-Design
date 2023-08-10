<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="team.star.healthcodesystem.model.Major" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>班级管理页面</title>
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

        function loadNextPage() {
            if (currentPage === <%= request.getAttribute("total_page")%>) {
                alert("已经是最后一页了");
                return;
            }
            currentPage++;
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/class/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function loadPreviousPage() {
            if (currentPage === 1) {
                alert("已经是第一页了");
                return;
            }
            currentPage--;
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/class/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function editClass(classId, className, classMajorName) {
            fillForm({
                id: classId,
                name: className,
                majorName: classMajorName
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

        function fillForm(clazz) {
            // 填充表单中的输入框
            document.getElementById("updateType").value = window.location.href.split('?')[1].split('&page')[0];
            document.getElementById("classId").value = clazz.id;
            document.getElementById("className").value = clazz.name;
            document.getElementById("classMajorName").value = clazz.majorName;
        }

        function showUploadPopup() {
            document.getElementById("addType").value = window.location.href.split('?')[1].split('&page')[0];
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

        function deleteClass(id) {
            $.ajax({
                url: '/class/delete',
                type: 'POST',
                data: {
                    'id': id
                },
                success: function (response) {
                    var type = window.location.href.split('?')[1].split('&page')[0];
                    window.location.href = "/class/query?" + type + "&page=" + currentPage + "&size=10";
                },
                error: function (xhr, status, error) {
                    console.error(error);
                }
            });
        }
    </script>
</head>
<body>
<table id="classTable" class="table">
    <thead>
    <th>编号</th>
    <th>班级名称</th>
    <th>专业名称</th>
    <c:if test="${type eq 'SUPER_ADMIN'}">
        <th>操作</th>
    </c:if>
    </thead>
    <tbody>
    <c:forEach var="clazz" items="${classes}">
        <tr>
            <td>${clazz.id}</td>
            <td>${clazz.name}</td>
            <td>${clazz.majorName}</td>
            <c:if test="${type eq 'SUPER_ADMIN'}">
                <td>
                    <button class="button"
                            onclick="editClass('${clazz.id}', '${clazz.name}', '${clazz.majorName}')">
                        修改
                    </button>
                    <button class="button" onclick="deleteClass('${clazz.id}')">删除</button>
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
        <a class="button" onclick="showUploadPopup()">添加班级</a>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/class_query.jsp">返回</a>
</div>

<div id="overlay" class="overlay"></div>

<div id="popup" class="popup">
    <div class="item">
        <h2>修改班级信息</h2>
    </div>
    <form action="${pageContext.request.contextPath}/class/update" method="post" class="add_main">
        <input type="hidden" id="updateType" name="type">
        <input type="hidden" id="classId" name="classId"/>
        <div class="item">
            <label for="className">班级名称:</label>
            <input type="text" id="className" name="className"/>
        </div>
        <div class="item">
            <label for="classMajorName">专业名称:</label>
            <input type="text" id="classMajorName" name="classMajorName"/>
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
        <h2>添加班级</h2>
    </div>
    <form action="${pageContext.request.contextPath}/class/add" method="post" class="add_main">
        <input type="hidden" id="addType" name="type">
        <div class="item">
            <label for="classAddName">班级名称：</label>
            <input type="text" id="classAddName" name="className"/>
        </div>
        <div class="item">
            <label for="classAddMajorName">专业名称：</label>
            <select id="classAddMajorName" name="majorName">
                <c:forEach var="major" items="${majors}">
                    <option value="${major.name}">${major.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="item">
            <button type="submit">提交</button>
            <button type="button" onclick="closeUploadPopup()">取消</button>
        </div>
    </form>
</div>
</body>
</html>
