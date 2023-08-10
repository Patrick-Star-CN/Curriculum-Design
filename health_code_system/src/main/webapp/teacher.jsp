<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="team.star.healthcodesystem.model.Teacher" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>教师管理页面</title>
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
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/teacher/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function loadPreviousPage() {
            if (currentPage === 1) {
                alert("已经是第一页了");
                return;
            }
            currentPage--;
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/teacher/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function editTeacher(teacherId, teacherName, teacherIid, teacherCollegeName, teacherType) {
            fillForm({
                id: teacherId,
                name: teacherName,
                iid: teacherIid,
                collegeName: teacherCollegeName,
                type: teacherType
            });

            // 显示遮罩和弹出窗口
            var overlay = document.getElementById("overlay");
            var popup = document.getElementById("popup");
            overlay.style.display = "block";
            popup.style.display = "block";
        }

        function changePwd(teacherId) {
            var request = new XMLHttpRequest();
            var url = '/teacher/change_pwd';
            var data = new FormData();
            data.append('teacherId', teacherId);
            data.append('newPwd', '123456');
            data.append('isAdmin', 'admin');
            data.append('type', window.location.href.split('?')[1].split('&page')[0]);
            request.open('POST', url, true);

            request.onreadystatechange = function () {
                if (request.readyState === 4 && request.status === 200) {
                    alert("密码已重置为123456");
                }
            };
            request.send(data);
        }

        function closePopup() {
            // 隐藏遮罩和弹出窗口
            var overlay = document.getElementById("overlay");
            var popup = document.getElementById("popup");
            overlay.style.display = "none";
            popup.style.display = "none";
        }

        function fillForm(teacher) {
            // 填充表单中的输入框
            document.getElementById("updateType").value = window.location.href.split('?')[1].split('&page')[0];
            document.getElementById("teacherId").value = teacher.id;
            document.getElementById("teacherName").value = teacher.name;
            document.getElementById("teacherIid").value = teacher.iid;
            document.getElementById("teacherCollegeName").value = teacher.collegeName;
            document.getElementById("teacherType").value = teacher.type;
        }

        function deleteTeacher(teacherId) {
            var request = new XMLHttpRequest();
            var url = '/teacher/delete';
            var data = new FormData();
            data.append('id', teacherId);
            request.open('POST', url, true);

            request.onreadystatechange = function () {
            };
            request.send(data);
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/teacher/query?" + type + "&page=" + currentPage + "&size=10";
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
    </script>
</head>
<body>
<table id="teacherTable" class="table">
    <thead>
    <th>工号</th>
    <th>姓名</th>
    <th>身份证号</th>
    <th>学院名称</th>
    <th>类型</th>
    <c:if test="${type eq 'SUPER_ADMIN'}">
        <th>操作</th>
    </c:if>
    </thead>
    <tbody>
    <c:forEach var="teacher" items="${teachers}">
        <tr>
            <td>${teacher.id}</td>
            <td>${teacher.name}</td>
            <td>${teacher.iid}</td>
            <td>${teacher.collegeName}</td>
            <td>${teacher.type}</td>
            <c:if test="${type eq 'SUPER_ADMIN'}">
                <td>
                    <button class="button"
                            onclick="editTeacher('${teacher.id}', '${teacher.name}', '${teacher.iid}', '${teacher.collegeName}', '${teacher.type}')">
                        修改
                    </button>
                    <button class="button" onclick="deleteTeacher('${teacher.id}')">删除</button>
                    <button class="button" onclick="changePwd('${teacher.id}')">重置密码</button>
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
        <a class="button" onclick="showUploadPopup()">导入数据</a>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/teacher_query.jsp">返回</a>
</div>
<div id="overlay" class="overlay"></div>

<div id="popup" class="popup">
    <div class="item">
        <h2>修改教师信息</h2>
    </div>
    <form action="${pageContext.request.contextPath}/teacher/update" method="post" class="add_main">
        <input type="hidden" id="updateType" name="type">
        <input type="hidden" id="teacherId" name="teacherId"/>
        <div class="item">
            <label for="teacherName">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
            <input type="text" id="teacherName" name="teacherName"/>
        </div>
        <div class="item">
            <label for="teacherIid">身份证号：</label>
            <input type="number" id="teacherIid" name="teacherIid"/>
        </div>
        <div class="item">
            <label for="teacherCollegeName">学院名称：</label>
            <input type="text" id="teacherCollegeName" name="teacherCollegeName"/>
        </div>
        <div class="item">
            <label for="teacherType">教师类别：</label>
            <select id="teacherType" name="teacherType">
                <option value="SUPER_ADMIN">超级管理员</option>
                <option value="SCHOOL_ADMIN">校级管理员</option>
                <option value="COLLEGE_ADMIN">院级管理员</option>
                <option value="TEACHER">教师</option>
            </select>
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
        <h2>上传Excel文件</h2>
    </div>
    <form enctype="multipart/form-data" action="${pageContext.request.contextPath}/teacher/add" method="post" class="add_main">
        <input type="hidden" id="addType" name="type">
        <div class="item">
            <input type="file" accept=".xlsx, .xls" name="file" required/>
        </div>
        <div class="item">
            <button type="submit">上传</button>
            <button type="button" onclick="closeUploadPopup()">取消</button>
        </div>
    </form>
</div>
</body>
</html>
