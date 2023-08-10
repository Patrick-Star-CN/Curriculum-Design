<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>打卡统计查询页</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
    <style>
        .hind {
            display: none;
        }
    </style>
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
            $('#firstSelect').on('change', function () {
                var selectedValue = $(this).val();
                if (selectedValue === '全部') {
                    $('#secondSelect').html('');
                    return;
                }
                $.ajax({
                    url: '/major/query/json',
                    type: 'GET',
                    data: {
                        'name': selectedValue
                    },
                    success: function (response) {
                        var options = '<option value="全部">全部</option>';
                        for (var i = 0; i < response.length; i++) {
                            options += '<option value="' + response[i].name + '">' + response[i].name + '</option>';
                        }
                        $('#secondSelect').html(options);
                    },
                    error: function (xhr, status, error) {
                        console.error(error);
                    }
                });
            });

            $('#secondSelect').on('change', function () {
                var selectedValue = $(this).val();
                if (selectedValue === '全部') {
                    $('#thirdSelect').html('');
                    return;
                }
                $.ajax({
                    url: '/class/query/json',
                    type: 'GET',
                    data: {
                        'name': selectedValue
                    },
                    success: function (response) {
                        var options = '<option value="全部">全部</option>';
                        for (var i = 0; i < response.length; i++) {
                            options += '<option value="' + response[i].name + '">' + response[i].name + '</option>';
                        }
                        $('#thirdSelect').html(options);
                    },
                    error: function (xhr, status, error) {
                        console.error(error);
                    }
                });
            });
        });

        function submit() {
            var type = $('#typeSelect').val();
            var date = $('input[type="date"]').val();
            var firstSelect = $('#firstSelect');
            var secondSelect = $('#secondSelect');
            var thirdSelect = $('#thirdSelect');
            if (firstSelect.val() === '全部') {
                window.location.href = '/record/query?userType=' + type + '&type=all&date=' + date;
                return;
            }
            if (thirdSelect.val() === '全部' && type === 'student') {
                window.location.href = '/record/query?userType=' + type + '&type=major&name=' + secondSelect.val() + '&date=' + date;
                return;
            } else if (thirdSelect.val() != null && type === 'student') {
                window.location.href = '/record/query?userType=' + type + '&type=class&name=' + thirdSelect.val() + '&date=' + date;
                return;
            }
            if (secondSelect.val() === '全部') {
                window.location.href = '/record/query?userType=' + type + '&type=college&name=' + firstSelect.val() + '&date=' + date;
            } else if (type === 'student') {
                window.location.href = '/record/query?userType=' + type + '&type=major&name=' + secondSelect.val() + '&date=' + date;
            }
        }
    </script>
</head>
<body>
<div class="main">
    <input type="hidden" value="${type}" id="type">
    <div class="item">
        <label for="typeSelect">用户类型：</label>
        <select id="typeSelect">
            <option value="student">学生</option>
            <option value="teacher">教师</option>
        </select>
    </div>
    <div class="item">
        <label for="date">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：</label>
        <input type="date" value="date" id="date">
    </div>
    <div class="item">
        <label for="firstSelect">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院：</label>
        <select id="firstSelect">
        </select>
    </div>
    <div class="item">
        <label for="secondSelect" id="secondSelectLabel">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</label>
        <select id="secondSelect">
        </select>
    </div>
    <div class="item">
        <label for="thirdSelect" id="thirdSelectLabel">班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
        <select id="thirdSelect">
        </select>
    </div>
    <div class="item">
        <a class="button"  onclick="submit()" >查询 </a>
        <a class="button" href="${pageContext.request.contextPath}/admin.jsp">返回</a>
    </div>
</div>
<script>
    $('#typeSelect').on('change', function () {
        var selectedValue = $(this).val();
        if (selectedValue === 'teacher') {
            document.getElementById('secondSelectLabel').classList.add('hind');
            document.getElementById('secondSelect').classList.add('hind');
            document.getElementById('thirdSelectLabel').classList.add('hind');
            document.getElementById('thirdSelect').classList.add('hind');
        } else {
            document.getElementById('secondSelectLabel').classList.remove('hind');
            document.getElementById('secondSelect').classList.remove('hind');
            document.getElementById('thirdSelectLabel').classList.remove('hind');
            document.getElementById('thirdSelect').classList.remove('hind');
        }
    });
    $(document).ready(function () {
        var time = new Date();
        var day = ("0" + time.getDate()).slice(-2);
        var month = ("0" + (time.getMonth() + 1)).slice(-2);
        var today = time.getFullYear() + "-" + (month) + "-" + (day);
        $('#date').val(today);
    })
</script>
</body>
</html>
