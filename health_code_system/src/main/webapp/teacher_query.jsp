<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>教师信息查询页</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
    <script>
        $(document).ready(function () {
            $.ajax({
                url: '/college/query/json',
                type: 'GET',
                success: function (response) {
                    var options = '';
                    if ($('#type').val() === 'SUPER_ADMIN') {
                        options += '<option value="全部">全部</option>';
                    }
                    for (var i = 0; i < response.length; i++) {
                        options += '<option value="' + response[i].name + '">' + response[i].name + '</option>';
                    }
                    $('#firstSelect').html(options);
                },
                error: function (xhr, status, error) {
                    console.error(error);
                }
            });
        });

        function submit() {
            var firstSelect = $('#firstSelect');
            if (firstSelect.val() === '全部') {
                window.location.href = '/teacher/query?type=all&page=1&size=10';
            } else {
                window.location.href = '/teacher/query?type=college&name=' + firstSelect.val() + '&page=1&size=10';
            }
        }
    </script>
</head>
<body>
<div class="main">
    <input type="hidden" value="${type}" id="type">
    <div class="item">
        <label for="firstSelect">学院：</label>
        <select id="firstSelect">
        </select>
    </div>
    <div class="item">
        <a class="button" type="button" onclick="submit()" >查询</a>
        <a class="button" href="${pageContext.request.contextPath}/admin.jsp">返回</a>
    </div>
</div>
</body>
</html>
