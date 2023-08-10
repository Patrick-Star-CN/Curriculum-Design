<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
    <title>每日一报</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/record/add?type=${type}" class="form">
    <div class="item">
        <label for="id">学号/工号：</label>
        <input id="id" type="text" value="${user.id}" readonly="readonly" name="userId"><br>
    </div>
    <div class="item">
        <label for="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
        <input id="name" type="text" value="${user.name}" readonly="readonly"><br>
    </div>
    <div class="item">
        <label for="iid">身份&nbsp;证号：</label>
        <input id="iid" type="text" value="${user.iid}" readonly="readonly"><br>
    </div>
    <div class="item">
        <label for="phoneNum">手&nbsp;&nbsp;机&nbsp;&nbsp;号：</label>
        <input id="phoneNum" type="text" name="phoneNum"><br>
    </div>
    <div class="item">
        <input id="haveBeenWuhan" type="checkbox" name="haveBeenWuhan" value="yes"><br>
        <label for="haveBeenWuhan">本人近期（14天内）是否去过湖北省或重点疫区？</label>
    </div>
    <div class="item">
        <input id="haveBeenAbroad" type="checkbox" name="haveBeenAbroad" value="yes"><br>
        <label for="haveBeenAbroad">本人近期（14天内）是否与去过国外？</label>
    </div>
    <div class="item">
        <input id="haveBeenTouchPatient" type="checkbox" name="haveBeenTouchPatient" value="yes"><br>
        <label for="haveBeenTouchPatient">本人近期（14天内）是否与接触过新冠确诊病人或疑似病人？</label>
    </div>
    <div class="item">
        <input id="isPatient" type="checkbox" name="haveBeenTouchPatient" value="yes"><br>
        <label for="isPatient">本人是否被卫生部门确认为新冠肺炎确诊病例或疑似病例？</label>
    </div>
    <div class="item">
        <label>当前健康状况</label><br>
    </div>
    <div class="item">
        <input id="health_op1" type="checkbox" name="health" value="1"><br>
        <label for="health_op1">无异&nbsp;常&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <input id="health_op2" type="checkbox" name="unhealthy" value="2"><br>
        <label for="health_op2">发热（≥37.3°C）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
    </div>
    <div class="item">
        <input id="health_op3" type="checkbox" name="unhealthy" value="3"><br>
        <label for="health_op3">乏&nbsp;&nbsp;&nbsp;&nbsp;力&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <input id="health_op4" type="checkbox" name="unhealthy" value="4"><br>
        <label for="health_op4">干&nbsp;&nbsp;&nbsp;&nbsp;咳</label>
    </div>
    <div class="item">
        <input id="health_op5" type="checkbox" name="unhealthy" value="5"><br>
        <label for="health_op5">鼻&nbsp;&nbsp;&nbsp;&nbsp;塞&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <input id="health_op6" type="checkbox" name="unhealthy" value="6"><br>
        <label for="health_op6">流&nbsp;&nbsp;&nbsp;&nbsp;涕</label>
    </div>
    <div class="item">
        <input id="health_op7" type="checkbox" name="unhealthy" value="7"><br>
        <label for="health_op7">咽&nbsp;&nbsp;&nbsp;&nbsp;痛&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <input id="health_op8" type="checkbox" name="unhealthy" value="8"><br>
        <label for="health_op8">腹&nbsp;&nbsp;&nbsp;&nbsp;泻</label>
        <input id="healthStatus" type="hidden" name="healthStatus">
    </div>
    <div class="item">
        <input id="promise" type="checkbox" name="promise" value="yes"><br>
        <label for="promise">本人郑重承诺：填报信息真实，愿意承担相应的法律责任。</label>
    </div>
    <div class="item">
        <a class="button" id="submit" onsubmit="submit()">提交</a>
    </div>
</form>
<script>
    $('#submit').on('click', function () {
        if (!$('#promise').is(':checked')) {
            alert('请勾选承诺');
            return;
        }
        var checkboxes = document.getElementsByName("unhealthy");
        var checkedCount = 0;
        console.log(checkboxes);
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkedCount++;
            }
        }
        var healthStatus = $('#healthStatus');
        healthStatus.val(checkedCount);

        $.ajax({
            url: '/record/add?type=${type}',
            type: 'POST',
            data: {
                'userId': $('#id').val(),
                'phoneNum': $('#phoneNum').val(),
                'haveBeenWuhan': $('#haveBeenWuhan').is(':checked') ? 'yes' : 'no',
                'haveBeenAbroad': $('#haveBeenAbroad').is(':checked') ? 'yes' : 'no',
                'haveBeenTouchPatient': $('#haveBeenTouchPatient').is(':checked') ? 'yes' : 'no',
                'isPatient': $('#isPatient').is(':checked') ? 'yes' : 'no',
                'healthStatus': healthStatus.val(),
            },
            success: function () {
                window.location.href = '/health_code?type=${type}';
            },
            error: function (xhr, status, error) {
                console.error(error);
            }
        });
    });
</script>
</body>
</html>
