<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>预约选座</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/bootstrap/js/jquery.validate.min.js"></script>
    <script src="/bootstrap/js/messages_zh.js"></script>


    <script>
        function logout(){
            var message = confirm("你确定要退出吗？");
            if (message == true){
                return true;
            }else if (message == false){
                return false;
            }
        }
    </script>
    <script>
        function search() {
            var year = parseInt($('#year').find("option:selected").val());
            var month = parseInt($('#month').find("option:selected").val());
            var day = parseInt($('#day').find("option:selected").val());
            var starttime = parseInt($('#starttime').find("option:selected").val());
            var endtime = parseInt($('#endtime').find("option:selected").val());
            var yeartext = $('#year').find("option:selected").text();
            var monthtext = $('#month').find("option:selected").text();
            var daytext = $('#day').find("option:selected").text();
            var starttimetext = $('#starttime').find("option:selected").text();
            var endtimetext = $('#endtime').find("option:selected").text();
            var now = new Date();
            var now_year = now.getFullYear();
            var now_month = now.getMonth()+1;
            var now_day = now.getDate();
            var now_hour = now.getHours();
            var now_minute = now.getMinutes();
            if (yeartext==''||monthtext==''||daytext==''){
                alert("请选择预约日期！")
            } else {
                if (year < now_year){
                    alert("该时间段不可预约，请重新选择！");
                }else if (year == now_year && month < now_month){
                    alert("该时间段不可预约，请重新选择！");
                }else if (year == now_year && month == now_month && day < now_day){
                    alert("该时间段不可预约，请重新选择！");
                }else if (year == now_year && month == now_month && day == now_day && starttime <= now_hour){
                    alert("该时间段不可预约，请重新选择！");
                }else {
                    window.location.href='/reserve/seats?year='+yeartext+'&&month='+monthtext+'&&day='+daytext
                        +'&&starttime='+starttimetext+':00'+'&&endtime='+endtimetext+':00';
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
            <a class="navbar-brand" href="#">图书馆自习室预约管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" style="font-size: 15px">
                <li><a href="/user/personality">个人信息</a></li>
                <li class="active"><a href="/reserve">预约选座<span class="sr-only">(current)</span></a></li>
                <li><a href="/reservation">预约记录</a></li>
                <li><a href="/notice/list">通知查看</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon glyphicon-user" aria-hidden="true"></span>
                    <span class="text-default">${student.name}</span>&nbsp;&nbsp;同学</a></li>
                <li>
                    <a href="/logout" onclick="return logout()"><span class="glyphicon glyphicon glyphicon-off" aria-hidden="true"></span>
                        <span>退出</span></a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--页面主体内容-->
<div class="container-fiuld">
    <div class="row" style="margin-top:40px">
        <div class="col-sm-6 col-sm-offset-3" style="border: 1px solid black;height: 50px">
            <select id="year" onchange="addDay()" style="margin-top:10px;height:30px;width: 100px">
                    <option></option>
            </select>
            <label>年</label>
            <select id="month" onchange="addDay()" style="margin-top:10px;margin-left:10px;height:30px;width: 100px">
                <option></option>
            </select>
            <label>月</label>
            <select id="day" style="margin-top:10px;margin-left:10px;height:30px;width: 100px">
                <option></option>
            </select>
            <label>日</label>
            <select id="starttime" onchange="addTime()" style="margin-top:10px;margin-left:30px;height:30px;width: 100px">
            </select>
            <label>--</label>
            <select id="endtime" style="margin-top:10px;height:30px;width: 100px">
            </select>
            <button id="search" type="button" class="btn btn-primary" style="font-size: 14px;font-weight: bold;float: right;
                margin-top:7px;margin-right: 20px" onclick="search()">查询</button>
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
    var start_slt = document.getElementById('starttime');
    var end_slt = document.getElementById('endtime');
    var start = ${opening.open};
    var end = ${opening.close};
    for (i=start;i<=end;i++){
        var opt_start = document.createElement('option');
        opt_start.value=i;
        if (i<10){
            opt_start.innerText = '0'+i+':00';
        }else {
            opt_start.innerText = i+':00';
        }
        start_slt.appendChild(opt_start);
    }
    for (i=start+1;i<=end;i++){
        var opt_end = document.createElement('option');
        opt_end.value=i;
        if (i<10){
            opt_end.innerText = '0'+i+':00';
        }else {
            opt_end.innerText = i+':00';
        }
        end_slt.appendChild(opt_end);
    }
    function addTime() {
        end_slt.innerText='';
        for (i=parseInt(start_slt.value)+1;i<=end;i++){
            var opt_end = document.createElement('option');
            opt_end.value=i;
            if (i<10){
                opt_end.innerText = '0'+i+':00';
            }else {
                opt_end.innerText = i+':00';
            }
            end_slt.appendChild(opt_end);
        }
    }
</script>