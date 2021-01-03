<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>预约记录</title>
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

    </script>
    <script>
        function searching(){
            var sid = $("#sid").val();
            var year = $('#year').find("option:selected").text();
            var month = $('#month').find("option:selected").text();
            var day = $('#day').find("option:selected").text();
            var status = $('#status').find("option:selected").val();
            if (sid == "" && year == "" && month == "" && day == "" && status == -1){
                alert("查询信息不可全部为空！");
            }else if (year != "" && month != "" && day != ""){
                var date = year+"-"+month+"-"+day;
                url = "/reservation/search?sid="+sid+"&&date="+date+"&&status="+status;
                window.location.href=url;
            }else if ((sid != "" || status != -1)&&(year == "" && month == "" && day == "")){
                var date = "";
                url = "/reservation/search?sid="+sid+"&&date="+date+"&&status="+status;
                window.location.href=url;
            }else {
                alert("日期条件不可有空！");
            }
        }
    </script>
    <script>
        function cancel(date,starttime,id,uid){

            var now = new Date();
            var nowyear = now.getFullYear();
            var nowmonth = now.getMonth() + 1;
            var nowday = now.getDate();
            var nowhour = now.getHours();
            var nowminute = now.getMinutes();
            var nowsecond = now.getSeconds();
            if (parseInt(nowmonth)/10 == 0){
                nowmonth = "0"+nowmonth;
            }
            if (parseInt(nowday)/10 == 0){
                nowday = "0"+nowday;
            }
            if (parseInt(nowhour)/10 == 0){
                nowhour = "0"+nowhour;
            }
            if (parseInt(nowminute)/10 == 0){
                nowminute = "0"+nowminute;
            }
            if (parseInt(nowsecond)/10 == 0){
                nowsecond = "0"+nowsecond;
            }
            var nowdate = nowyear+"-"+nowmonth+"-"+nowday;
            var nowtime = nowhour+":"+nowminute+":"+nowsecond;
            if (nowdate > date){
                alert("已超过预约日期，无法取消");
            }else if (nowdate == date ){
                if (nowtime >= starttime){
                    alert("已超过预约时间，无法取消");
                }else{
                    alert("取消成功");
                }
            }else if (nowdate < date){
                alert("取消成功");
                window.location.href='/reservation/cancel?id='+id+"&&uid="+uid;
            }

        }

        function signin(date,starttime,endtime,id,uid){
            var now = new Date();
            var nowyear = now.getFullYear();
            var nowmonth = now.getMonth() + 1;
            var nowday = now.getDate();
            var nowhour = now.getHours();
            var nowminute = now.getMinutes();
            var nowsecond = now.getSeconds();
            if (parseInt(nowmonth)/10 == 0){
                nowmonth = "0"+nowmonth;
            }
            if (parseInt(nowday)/10 == 0){
                nowday = "0"+nowday;
            }
            if (parseInt(nowhour)/10 == 0){
                nowhour = "0"+nowhour;
            }
            if (parseInt(nowminute)/10 == 0){
                nowminute = "0"+nowminute;
            }
            if (parseInt(nowsecond)/10 == 0){
                nowsecond = "0"+nowsecond;
            }
            var nowdate = nowyear+"-"+nowmonth+"-"+nowday;
            var nowtime = nowhour+":"+nowminute+":"+nowsecond;
            var status = 1;
            if (nowdate != date){
                alert("不在预约时间内，无法签到");
            }else if (nowdate == date ) {

                if (nowtime < starttime) {
                    alert("不在预约时间内，无法签到");
                } else if (nowtime>= starttime && nowtime < endtime){
                    var s = starttime.split(":");
                    var shour = parseInt(s[0]);
                    var sminute = parseInt(s[1]);
                    var nhour = parseInt(nowhour);
                    var nminute = parseInt(nowminute);
                    if (shour<nhour){
                        alert("超过预约开始时间30分钟，你已迟到");
                        status = 5;
                        window.location.href='/reservation/signin?id='+id+"&&uid="+uid+"&&signin="+nowtime+"&&status="+status;
                    } else if (shour == nhour){
                        if (nminute-sminute>=30){
                            alert("超过预约开始时间30分钟，你已迟到");
                            status = 5;
                            window.location.href='/reservation/signin?id='+id+"&&uid="+uid+"&&signin="+nowtime+"&&status="+status;
                        }else {
                            alert("签到成功");
                            status = 3;
                            window.location.href='/reservation/signin?id='+id+"&&uid="+uid+"&&signin="+nowtime+"&&status="+status;
                        }
                    }


                } else if (nowtime >= endtime){
                    alert("超过预约时间，你已违约");
                    status = 6;
                    window.location.href='/reservation/signin?id='+id+"&&uid="+uid+"&&signin="+nowtime+"&&status="+status;
                }
            }
        }

        function signout(date,starttime,endtime,id,uid){
            var now = new Date();
            var nowyear = now.getFullYear();
            var nowmonth = now.getMonth() + 1;
            var nowday = now.getDate();
            var nowhour = now.getHours();
            var nowminute = now.getMinutes();
            var nowsecond = now.getSeconds();
            if (parseInt(nowmonth)/10 == 0){
                nowmonth = "0"+nowmonth;
            }
            if (parseInt(nowday)/10 == 0){
                nowday = "0"+nowday;
            }
            if (parseInt(nowhour)/10 == 0){
                nowhour = "0"+nowhour;
            }
            if (parseInt(nowminute)/10 == 0){
                nowminute = "0"+nowminute;
            }
            if (parseInt(nowsecond)/10 == 0){
                nowsecond = "0"+nowsecond;
            }
            var nowdate = nowyear+"-"+nowmonth+"-"+nowday;
            var nowtime = nowhour+":"+nowminute+":"+nowsecond;

            alert("签退成功");
            if (nowdate > date){
                window.location.href='/reservation/signout?id='+id+"&&uid="+uid+"&&signout="+endtime;
            }else if (nowdate == date ) {
                if (nowtime < endtime) {
                    window.location.href='/reservation/signout?id='+id+"&&uid="+uid+"&&signout="+nowtime;
                } else if (nowtime >= endtime) {
                    window.location.href='/reservation/signout?id='+id+"&&uid="+uid+"&&signout="+endtime;
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
                <li ><a href="/user/personality">个人信息</a></li>
                <li><a href="/reserve">预约选座</a></li>
                <li class="active"><a href="/reservation">预约记录<span class="sr-only">(current)</span></a></li>
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
    <div class="row" style="margin-top: 80px">
        <div class="col-sm-10 col-sm-offset-1">
            <div class="row" style="margin-top: 20px">
                <button type ="button" id="search" data-toggle="modal" data-target="#searchModal" class="btn btn-primary" style="font-size: 14px;font-weight: bold;
                " >查询信息</button>
            </div>
            <div class="row" style="margin-top: 20px">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr class="success" style="font-size: 14px">
                        <th>座位号</th>
                        <th>预约日期</th>
                        <th>预约开始时间</th>
                        <th>预约结束时间</th>
                        <th>签到时间</th>
                        <th>签退时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${reservations}" var="r" >
                        <tr>
                            <td id="sid_${r.id}">${r.sid}</td>
                            <td id="date_${r.id}">${r.date}</td>
                            <td id="starttime_${r.id}">${r.starttime}</td>
                            <td id="endtime_${r.id}">${r.endtime}</td>
                            <td id="signin_${r.id}">
                                <c:if test="${r.signin == null}"></c:if>
                                <c:if test="${r.signin != null}">${r.signin}</c:if>
                            </td>
                            <td id="signout_${r.id}">
                                <c:if test="${r.signout == null}"></c:if>
                                <c:if test="${r.signout != null}">${r.signout}</c:if>
                            </td>
                            <td id="status_${r.id}">
                                <c:if test="${r.status == 1}">已预约</c:if>
                                <c:if test="${r.status == 2}">已取消</c:if>
                                <c:if test="${r.status == 3}">已签到</c:if>
                                <c:if test="${r.status == 4}">已完成</c:if>
                                <c:if test="${r.status == 5}">迟到</c:if>
                                <c:if test="${r.status == 6}">违约</c:if>
                                <c:if test="${r.status == 7}">服务中止</c:if>
                                <c:if test="${r.status == 8}">已完成（迟到）</c:if>
                            </td>
                            <td>
                                <c:if test="${r.status == 1}"><a  onclick="cancel('${r.date}','${r.starttime}','${r.id}','${r.uid}')">取消预约</a>
                                    &nbsp;&nbsp;<a  onclick="signin('${r.date}','${r.starttime}','${r.endtime}','${r.id}','${r.uid}')">签到</a></c:if>
                                <c:if test="${r.status == 3 || r.status == 5}"><a onclick="signout('${r.date}','${r.starttime}','${r.endtime}','${r.id}','${r.uid}')">签退</a></c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>
            <div class="row" style="text-align: center">
                <nav>
                    <ul class="pagination">
                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/reservation?start=0${page.param}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/reservation?start=${page.start-page.count}${page.param}" aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
                            <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                                    <a href="reservation?start=${status.index*page.count}${page.param}"
                                       <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                                    >${status.count}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/reservation?start=${page.start+page.count}${page.param}" aria-label="Next">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/reservation?start=${page.last}${page.param}" aria-label="Next">
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
                    <h4 class="modal-title" id="myModalLabel2">查询预约信息</h4>
                </div>
                <div class="modal-body">
                    <label  style="font-size: 16px">请填写以下一个或多个查询条件：</label>
                    <div class="form-group" >
                        <label for="sid" style="font-size: 16px">座位号：</label>
                        <input type="text" class="form-control" id="sid" name="sid" placeholder="请输入座位号"
                               style=""  value="">
                    </div>
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
                        <label  style="font-size: 16px">状态：</label><br/>
                        <select id="status" style="height:30px;width: 100%">
                            <option value="-1">请选择状态</option>
                            <option value="1">已预约</option>
                            <option value="2">已取消</option>
                            <option value="3">已签到</option>
                            <option value="4">已完成</option>
                            <option value="5">迟到</option>
                            <option value="6">违约</option>
                            <option value="7">服务中止</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn-search" class="btn btn-primary" onclick="searching()"><span class="glyphicon glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
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