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

        function loadNextPage() {
            if (currentPage === <%= request.getAttribute("total_page")%>) {
                alert("已经是最后一页了");
                return;
            }
            currentPage++;
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/major/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function loadPreviousPage() {
            if (currentPage === 1) {
                alert("已经是第一页了");
                return;
            }
            currentPage--;
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/major/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function editMajor(majorId, majorName, majorCollegeName) {
            fillForm({
                id: majorId,
                name: majorName,
                collegeName: majorCollegeName
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

        function fillForm(major) {
            // 填充表单中的输入框
            document.getElementById("updateType").value = window.location.href.split('?')[1].split('&page')[0];
            document.getElementById("majorId").value = major.id;
            document.getElementById("majorName").value = major.name;
            document.getElementById("majorCollegeName").value = major.collegeName;
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

        function deleteMajor(id) {
            $.ajax({
                url: '/major/delete',
                type: 'POST',
                data: {
                    'id': id
                },
                success: function (response) {
                    var type = window.location.href.split('?')[1].split('&page')[0];
                    window.location.href = "/major/query?" + type + "&page=" + currentPage + "&size=10";
                },
                error: function (xhr, status, error) {
                    console.error(error);
                }
            });
        }
    </script>
</head>
<body>
<table id="majorTable" class="table">
    <thead>
    <th>编号</th>
    <th>专业名称</th>
    <th>学院名称</th>
    <c:if test="${type eq 'SUPER_ADMIN'}">
        <th>操作</th>
    </c:if>
    </thead>
    <tbody>
    <c:forEach var="major" items="${majors}">
        <tr>
            <td>${major.id}</td>
            <td>${major.name}</td>
            <td>${major.collegeName}</td>
            <c:if test="${type eq 'SUPER_ADMIN'}">
                <td>
                    <button class="button"
                            onclick="editMajor('${major.id}', '${major.name}', '${major.collegeName}')">
                        修改
                    </button>
                    <button class="button" onclick="deleteMajor('${major.id}')">删除</button>
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
        <a class="button" onclick="showUploadPopup()">添加专业</a>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/major_query.jsp">返回</a>
</div>
<div id="overlay" class="overlay"></div>

<div id="popup" class="popup">
    <div class="item">
        <h2>修改专业信息</h2>
    </div>
    <form action="${pageContext.request.contextPath}/major/update" method="post">
        <input type="hidden" id="updateType" name="type">
        <input type="hidden" id="majorId" name="majorId"/>
        <div class="item">
            <label for="majorName">专业名称：</label>
        <input type="text" id="majorName" name="majorName"/>
        </div>
        <div class="item">
            <label for="majorCollegeName">学院名称：</label>
        <input type="text" id="majorCollegeName" name="majorCollegeName"/>
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
        <h2>添加专业</h2>
    </div>
    <form action="${pageContext.request.contextPath}/major/add" method="post" class="add_main">
        <input type="hidden" id="addType" name="type">
        <div class="item">
            <label for="majorAddName">学院名称：</label>
            <input type="text" id="majorAddName" name="majorName"/>
        </div>
        <div class="item">
            <label for="majorAddCollegeName">学院名称：</label>
            <select id="majorAddCollegeName" name="collegeName">
                <c:forEach var="college" items="${colleges}">
                    <option value="${college.name}">${college.name}</option>
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
