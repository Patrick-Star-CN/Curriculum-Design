<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="team.star.healthcodesystem.model.Student" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>学生管理页面</title>
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
            window.location.href = "/student/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function loadPreviousPage() {
            if (currentPage === 1) {
                alert("已经是第一页了");
                return;
            }
            currentPage--;
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/student/query?" + type + "&page=" + currentPage + "&size=10";
        }

        function editStudent(studentId, studentName, studentIid, studentCollegeName, studentMajorName, studentClassName) {
            // 获取学生信息并填充表单
            fillForm({
                id: studentId,
                name: studentName,
                iid: studentIid,
                collegeName: studentCollegeName,
                majorName: studentMajorName,
                className: studentClassName
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

        function fillForm(student) {
            // 填充表单中的输入框
            document.getElementById("updateType").value = window.location.href.split('?')[1].split('&page')[0];
            document.getElementById("studentId").value = student.id;
            document.getElementById("studentName").value = student.name;
            document.getElementById("studentIid").value = student.iid;
            document.getElementById("studentCollegeName").value = student.collegeName;
            document.getElementById("studentMajorName").value = student.majorName;
            document.getElementById("studentClassName").value = student.className;
        }

        function deleteStudent(studentId) {
            var request = new XMLHttpRequest();
            var url = '/student/delete';
            var data = new FormData();
            data.append('id', studentId);
            request.open('POST', url, true);

            request.onreadystatechange = function () {
            };
            request.send(data);
            var type = window.location.href.split('?')[1].split('&page')[0];
            window.location.href = "/student/query?" + type + "&page=" + currentPage + "&size=10";
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
<table id="studentTable" class="table">
    <thead>
    <th>学号</th>
    <th>姓名</th>
    <th>身份证号</th>
    <th>学院名称</th>
    <th>专业名称</th>
    <th>班级名称</th>
    <c:if test="${type eq 'SUPER_ADMIN'}">
        <th>操作</th>
    </c:if>
    </thead>
    <tbody>
    <c:forEach var="student" items="${students}">
        <tr>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.iid}</td>
            <td>${student.collegeName}</td>
            <td>${student.majorName}</td>
            <td>${student.className}</td>
            <c:if test="${type eq 'SUPER_ADMIN'}">
                <td>
                    <button class="button"
                            onclick="editStudent('${student.id}', '${student.name}', '${student.iid}', '${student.collegeName}', '${student.majorName}', '${student.className}')">
                        修改
                    </button>
                    <button class="button" onclick="deleteStudent('${student.id}')">删除</button>
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
    <a class="button" href="${pageContext.request.contextPath}/student_query.jsp">返回</a>
</div>
<div id="overlay" class="overlay"></div>

<div id="popup" class="popup">
    <div class="item">
        <h2>修改学生信息</h2>
    </div>
    <form action="${pageContext.request.contextPath}/student/update" method="post" class="add_main">
        <input type="hidden" id="updateType" name="type">
        <input type="hidden" id="studentId" name="studentId"/>
        <div class="item">
            <label for="studentName">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
            <input type="text" id="studentName" name="studentName"/>
        </div>
        <div class="item">
            <label for="studentIid">身份证号：</label>
            <input type="number" id="studentIid" name="studentIid"/>
        </div>
        <div class="item">
            <label for="studentCollegeName">学院名称：</label>
            <input type="text" id="studentCollegeName" name="studentCollegeName"/>
        </div>
        <div class="item">
            <label for="studentMajorName">专业名称：</label>
            <input type="text" id="studentMajorName" name="studentMajorName"/>
        </div>
        <div class="item">
            <label for="studentClassName">班级名称：</label>
            <input type="text" id="studentClassName" name="studentClassName"/>
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
    <form enctype="multipart/form-data" action="${pageContext.request.contextPath}/student/add" method="post"
          class="add_main">
        <input type="hidden" id="addType" name="type">
        <div class="item">
            <input type="file" accept=".xlsx, .xls" name="file"/>
        </div>
        <div class="item">
            <button type="submit">上传</button>
            <button type="button" onclick="closeUploadPopup()">取消</button>
        </div>
    </form>
</div>
</body>
</html>
