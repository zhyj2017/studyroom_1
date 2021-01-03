<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>自习室使用情况</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script>
        function logout(){
            var message = confirm("你确定要退出吗？");
            if (message == true){
                return true;
            }else if (message == false){
                return false;
            }
        }
        function isWarning(){
            var message = confirm("你确定要警告该学生吗？");
            if (message == true){
                return true;
            }else if (message == false){
                return false;
            }
        }
        function isCanceling(){
            var message = confirm("你确定要取消警告吗？");
            if (message == true){
                return true;
            }else if (message == false){
                return false;
            }
        }

    </script>
    <script>
        $(function () {
            $("ul.pagination li.disabled a").click(function () {
                return false;
            });
        });
    </script>
    <script>
        $("#search").click(function(){
            $("#searchModal").modal();
        });
        $("#update").click(function(){
            $("#updateModal").modal();
        });

    </script>
    <script>




        function searching(){
            var year = $('#year').find("option:selected").text();
            var month = $('#month').find("option:selected").text();
            var day = $('#day').find("option:selected").text();

            var seat1 = $("#seat1").val();
            var seat2 = $("#seat2").val();
            var closed1 = $("#closed1").val();
            var closed2 = $("#closed2").val();
            var reserve1 = $("#reserve1").val();
            var reserve2 = $("#reserve2").val();

            var sign1 = $("#sign1").val();
            var sign2 = $("#sign2").val();
            var late1 = $("#late1").val();
            var late2 = $("#late2").val();
            var violate1 = $("#violate1").val();
            var violate2 = $("#violate2").val();


            var ok = 1;

            if (year == "" && month == "" && day == "" && reserve1 == "" && reserve2 == ""  && sign1 == "" &&
                sign2 == "" && late1 == "" && late2 == "" && violate1 == "" && violate2 == "" && seat1 == "" &&
                seat2 == "" && closed1 == "" && closed2 == ""){
                alert("查询信息不可全部为空！");
            }else {
                if (reserve1 != ""){
                    if (!/^\d+$/.test(reserve1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        reserve1 = parseInt(reserve1);
                    }
                }
                if (reserve2 != ""){
                    if (!/^\d+$/.test(reserve2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        reserve2 = parseInt(reserve2);
                    }
                }
                if (sign1 != ""){
                    if (!/^\d+$/.test(sign1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        sign1 = parseInt(sign1);
                    }
                }
                if (sign2 != ""){
                    if (!/^\d+$/.test(sign2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        sign2 = parseInt(sign2);
                    }
                }
                if (late1 != ""){
                    if (!/^\d+$/.test(late1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        late1 = parseInt(late1);
                    }
                }
                if (late2 != ""){
                    if (!/^\d+$/.test(late2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        late2 = parseInt(late2);
                    }
                }
                if (violate1 != ""){
                    if (!/^\d+$/.test(violate1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        violate1 = parseInt(violate1);
                    }
                }
                if (violate2 != ""){
                    if (!/^\d+$/.test(violate2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        violate2 = parseInt(violate2);
                    }
                }
                if (seat1 != ""){
                    if (!/^\d+$/.test(seat1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        seat1 = parseInt(seat1);
                    }
                }
                if (seat2 != ""){
                    if (!/^\d+$/.test(seat2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        seat2 = parseInt(seat2);
                    }
                }
                if (closed1 != ""){
                    if (!/^\d+$/.test(closed1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        closed1 = parseInt(closed1);
                    }
                }
                if (closed2 != ""){
                    if (!/^\d+$/.test(closed2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        closed2 = parseInt(closed2);
                    }
                }
                if ((reserve1 !="" || reserve1 == 0) && (reserve2 != "" || reserve2 == 0) ){
                    if (reserve1 > reserve2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if ((sign1 !="" || sign1 == 0) && (sign2 != "" || sign2 == "")){
                    if (sign1 > sign2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if ((late1 !="" || late1 == 0) && (late2 != "" || late2 == 0)){
                    if (late1 > late2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if ((violate1 !=""||violate1 == 0) && (violate2 != "" || violate2 == 0)){
                    if (violate1 > violate2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if ((seat1 !="" || seat1 == 0)&& (seat2 != "" || seat2 == 0)){
                    if (seat1 > seat2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if ((closed1 !=""||closed1 == "") && (closed2 != "" || closed2 == 0)){
                    if (closed1 > closed2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (ok == 1){
                    var date;
                    if (year != "" && month != "" && day != ""){
                        date = year+"-"+month+"-"+day;
                        var url = "/rooms/search?date="+date+ "&&num1="+seat1+"&&num2="+seat2+"&&closed1="+closed1+
                            "&&closed2="+closed2+"&&reserve1="+reserve1+"&&reserve2="+reserve2+"&&sign1="+sign1+
                            "&&sign2="+sign2+"&&late1="+late1+"&&late2="+late2+"&&violate1=" +violate1+"&&violate2="+violate2;
                        window.location.href=url;
                    }else if ((reserve1 != "" || reserve2 != ""  || sign1 != "" || sign2 != "" || late1 != "" ||
                        late2 != "" || violate1 != "" || violate2 != "" || seat1 != "" || seat2 != "" || closed1 != "" ||
                        closed2 != "") && (year == "" && month == "" && day == "")){
                        date = "";
                        var url = "/rooms/search?date="+date+ "&&num1="+seat1+"&&num2="+seat2+"&&closed1="+closed1+
                            "&&closed2="+closed2+"&&reserve1="+reserve1+"&&reserve2="+reserve2+"&&sign1="+sign1+
                            "&&sign2="+sign2+"&&late1="+late1+"&&late2="+late2+"&&violate1=" +violate1+"&&violate2="+violate2;
                        window.location.href=url;
                    }else {
                        alert("日期条件不可有空！");
                    }


                }
            }

        }
    </script>

    <script>
        function updating(){
            var open = $("#open").val();
            var close = $("#close").val();
            if (open == "" || close == ""){
                alert("错误，时间不能为空");
            }else if (!/^\d+$/.test(open)){
                alert("错误，开馆时间必须为整数");
            }else if (!/^\d+$/.test(close)){
                alert("错误，闭馆时间必须为整数");
            }else {
                if (parseInt(open) < 0 || parseInt(open) > 24){
                    alert("错误，开馆时间范围为0-24时");
                }else if (parseInt(close) < 0 || parseInt(close) > 24){
                    alert("错误，闭馆时间范围为0-24时");
                }else if (parseInt(open) >= parseInt(close)){
                    alert("错误，开馆时间不能晚于闭馆时间");
                }else{
                    alert("修改成功");
                    var url = "/openUpdate?open="+open+"&&close="+close;
                    window.location.href=url;
                }
            }
        }
    </script>

</head>
<body>
<!--导航条-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">图书馆自习室预约管理后台系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" style="font-size: 15px">
                <li><a href="/students">学生信息</a></li>
                <li><a href="/seats">座位管理</a></li>
                <li><a href="/chosen/reservations">选座管理</a></li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">数据统计<span class="sr-only">(current)</span><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/credits">学生信誉信息</a></li>
                        <li class="active"><a href="/rooms">自习室使用情况<span class="sr-only">(current)</span></a></li>
                    </ul>
                </li>
                <li><a href="/preservation">防疫溯源</a></li>
                <li><a href="/notices/list">通知管理</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon glyphicon-user" aria-hidden="true"></span>
                    <span class="text-default">${administrator.name}</span></a></li>
                <li>
                    <a href="/admin/logout" onclick="return logout()"><span class="glyphicon glyphicon glyphicon-off" aria-hidden="true"></span>
                        <span>退出</span></a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--页面主体内容-->
<div class="container-fiuld">
    <div class="row" style="margin-top: 80px">
        <div class="col-sm-10 col-sm-offset-1">
            <div class="row" style="margin-top: 20px">
                <button type ="button" id="search" data-toggle="modal" data-target="#searchModal" class="btn btn-primary" style="font-size: 14px;font-weight: bold;
                " >查询信息</button>
                <button type ="button" id="update" data-toggle="modal" data-target="#updateModal" class="btn btn-success" style="font-size: 14px;font-weight: bold;margin-left: 10px;
                " >设置自习室开放时间</button>
            </div>
            <div class="row" style="margin-top: 20px">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr class="success" style="font-size: 14px">
                        <th>日期</th>
                        <th>座位总数</th>
                        <th>已关闭座位数</th>
                        <th>预约次数</th>
                        <th>正常签到次数</th>
                        <th>迟到次数</th>
                        <th>违约次数</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${rooms}" var="r" >
                        <tr>
                            <td id="id_${r.id}">${r.date}</td>
                            <td id="num_${r.id}">${r.num}</td>
                            <td id="closed_${r.id}">${r.closed}</td>
                            <td id="reserve_${r.id}">${r.reserve}</td>
                            <td id="sign_${r.id}">${r.sign}</td>
                            <td id="late_${r.id}">${r.late}</td>
                            <td id="violate_${r.id}">${r.violate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>
            <div class="row" style="text-align: center">
                <nav>
                    <ul class="pagination">
                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/rooms/search?start=0${page.param}&&date=${condition1.date}&&num1=${condition1.num}&&num2=${condition2.num}&&closed1=${condition1.closed}&&closed2=${condition2.closed}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/rooms/search?start=${page.start-page.count}${page.param}&&date=${condition1.date}&&num1=${condition1.num}&&num2=${condition2.num}&&closed1=${condition1.closed}&&closed2=${condition2.closed}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}" aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
                            <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                                    <a href="/rooms/search?start=${status.index*page.count}${page.param}&&date=${condition1.date}&&num1=${condition1.num}&&num2=${condition2.num}&&closed1=${condition1.closed}&&closed2=${condition2.closed}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}"
                                       <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                                    >${status.count}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/rooms/search?start=${page.start+page.count}${page.param}&&date=${condition1.date}&&num1=${condition1.num}&&num2=${condition2.num}&&closed1=${condition1.closed}&&closed2=${condition2.closed}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}" aria-label="Next">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/rooms/search?start=${page.last}${page.param}&&date=${condition1.date}&&num1=${condition1.num}&&num2=${condition2.num}&&closed1=${condition1.closed}&&closed2=${condition2.closed}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    </div>


    <div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel2">查询自习室使用情况</h4>
                </div>
                <div class="modal-body">
                    <label  style="font-size: 16px">请填写以下一个或多个查询条件：</label>
                    <div class="form-group" >
                        <label  style="font-size: 16px">日期：</label><br/>
                        <select id="year" onchange="addDay()" style="height:30px;width: 100px">
                            <option></option>
                        </select>
                        <label>年</label>
                        <select id="month" onchange="addDay()" style="margin-left:80px;height:30px;width: 100px">
                            <option></option>
                        </select>
                        <label>月</label>
                        <select id="day" style="margin-left:80px;height:30px;width: 100px">
                            <option></option>
                        </select>
                        <label>日</label>
                    </div>
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%">座位总数：</label>
                        <input type="text" class="form-control" id="seat1" name="seat1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="seat2" name="seat2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">关闭座位数：</label>
                        <input type="text" class="form-control" id="closed1" name="closed1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="closed2" name="closed2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>

                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">预约次数：</label>
                        <input type="text" class="form-control" id="reserve1" name="reserve1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="reserve2" name="reserve2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>

                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">签到次数：</label>
                        <input type="text" class="form-control" id="sign1" name="sign1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="sign2" name="sign2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">迟到次数：</label>
                        <input type="text" class="form-control" id="late1" name="late1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="late2" name="late2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">违约次数：</label>
                        <input type="text" class="form-control" id="violate1" name="violate1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="violate2" name="violate2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn-search" class="btn btn-primary" onclick="searching()"><span class="glyphicon glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改自习室开放时间</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group" >
                        <label for="open" style="font-size: 16px;width:100%">开馆时间：</label>
                        <input type="text" class="form-control" id="open" name="open" placeholder="请输入开馆时间"
                               style="width: 80%;float:left" value="${opening.open}">
                        <label style="font-size: 16px;margin-top: 5px;margin-left: 20px">时</label>
                    </div>
                    <div class="form-group" >
                        <label for="close" style="font-size: 16px;width: 100%;margin-top: 20px">闭馆时间：</label>
                        <input type="text" class="form-control" id="close" name="close" placeholder="请输入闭馆时间"
                               style="width: 80%;float:left" value="${opening.close}">
                        <label style="font-size: 16px;margin-top: 5px;margin-left: 20px">时</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn-update" class="btn btn-primary" onclick="updating()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var year_slt = document.getElementById('year');
    var month_slt = document.getElementById('month');
    var day_slt = document.getElementById('day');        //提取日期
    var now = new Date();
    now_year = now.getFullYear();         //获取当前年份
    for(i=now_year;i<=now_year+50;i++){              //从当前年份选近70年
        var opt_year = document.createElement('option');       //造元素
        opt_year.value = i;
        opt_year.innerText = i;
        year_slt.appendChild(opt_year);               //赋给天数的下拉框
    }
    for(i=1;i<=12;i++){
        var opt_month = document.createElement('option');
        opt_month.value = i;
        if (i<10){
            opt_month.innerText = '0'+i;
        } else {
            opt_month.innerText = i;
        }
        month_slt.appendChild(opt_month);
    }
    function isRun(year){                        //判断闰年和平年，需确定二月份天数
        if((year%4==0 && year%100!==0)||year%400==0){
            return 29;
        }else{
            return 28;
        }
    }
    function addDay(){
        day_slt.innerHTML = '';
        if(month_slt.value==1||month_slt.value==3||month_slt.value==5||month_slt.value==7||month_slt.value==8||month_slt.value==10||month_slt.value   ==12){
            for(var i=1;i<=31;i++){
                var opt_day = document.createElement('option');
                opt_day.value = i;
                if (i<10){
                    opt_day.innerText = '0'+i;
                } else {
                    opt_day.innerText = i;
                }
                day_slt.appendChild(opt_day);         //选出31天的月份，赋天数1~31
            }
        }else if(month_slt.value==4||month_slt.value==6||month_slt.value==9||month_slt.value==11){
            for(var i=1;i<=30;i++){
                var opt_day = document.createElement('option');
                opt_day.value = i;
                if (i<10){
                    opt_day.innerText = '0'+i;
                } else {
                    opt_day.innerText = i;
                }
                day_slt.appendChild(opt_day);          // //选出30天的月份，赋天数1~30
            }
        }else{
            for(var i=1;i<=isRun(year_slt.value);i++){
                var opt_day = document.createElement('option');
                opt_day.value = i;
                if (i<10){
                    opt_day.innerText = '0'+i;
                } else {
                    opt_day.innerText = i;
                }
                day_slt.appendChild(opt_day);      //确定二月份的天数
            }
        }
    }

</script>
</body>
</html>