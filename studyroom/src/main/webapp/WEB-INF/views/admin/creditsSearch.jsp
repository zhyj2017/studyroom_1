<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生信誉信息</title>
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

    </script>
    <script>




        function searching(){
            var userid = $("#userid").val();
            var reserve1 = $("#reserve1").val();
            var reserve2 = $("#reserve2").val();
            var cancel1 = $("#cancel1").val();
            var cancel2 = $("#cancel2").val();
            var sign1 = $("#sign1").val();
            var sign2 = $("#sign2").val();
            var late1 = $("#late1").val();
            var late2 = $("#late2").val();
            var violate1 = $("#violate1").val();
            var violate2 = $("#violate2").val();
            var time1 = $("#time1").val();
            var time2 = $("#time2").val();

            var ok = 1;

            if (userid == "" && reserve1 == "" && reserve2 == "" && cancel1 == "" && cancel2 == "" && sign1 == "" &&
                sign2 == "" && late1 == "" && late2 == "" && violate1 == "" && violate2 == "" && time1 == "" && time2 == ""){
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
                if (cancel1 != ""){
                    if (!/^\d+$/.test(cancel1)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        cancel1 = parseInt(cancel1);
                    }
                }
                if (cancel2 != ""){
                    if (!/^\d+$/.test(cancel2)){
                        alert("不能包含整数以外的查询信息");
                        ok=0;
                    }else {
                        cancel2 = parseInt(cancel2);
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
                if (time1 != ""){
                    if (isNaN(time1)){
                        alert("不能包含数字（包括小数）以外的查询信息");
                        ok=0;
                    }else {
                        time1 = parseDouble(time1);
                    }
                }
                if (time2 != ""){
                    if (isNaN(time2)){
                        alert("不能包含数字（包括小数）以外的查询信息");
                        ok=0;
                    }else {
                        time2 = parseDouble(time2);
                    }
                }
                if (reserve1 !="" && reserve2 != ""){
                    if (reserve1 > reserve2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (cancel1 !="" && cancel2 != ""){
                    if (cancel1 > cancel2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (sign1 !="" && sign2 != ""){
                    if (sign1 > sign2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (late1 !="" && late2 != ""){
                    if (late1 > late2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (violate1 !="" && violate2 != ""){
                    if (violate1 > violate2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (time1 !="" && time2 != ""){
                    if (time1 > time2){
                        alert("最小值和最大值错误");
                        ok=0;
                    }
                }
                if (ok == 1){
                    var url = "/credits/search?uid="+userid+"&&reserve1="+reserve1+"&&reserve2="+reserve2+"&&cancel1="+cancel1
                        +"&&cancel2="+cancel2+"&&sign1="+sign1+"&&sign2="+sign2+"&&late1="+late1+"&&late2="+late2+"&&violate1="
                        +violate1+"&&violate2="+violate2+"&&time1="+time1+"&&time2="+time2;
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
                        <li class="active"><a href="/credits">学生信誉信息<span class="sr-only">(current)</span></a></li>
                        <li><a href="/rooms">自习室使用情况</a></li>
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
            </div>
            <div class="row" style="margin-top: 20px">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr class="success" style="font-size: 14px">
                        <th>学号</th>
                        <th>预约次数</th>
                        <th>取消预约次数</th>
                        <th>正常签到次数</th>
                        <th>迟到次数</th>
                        <th>违约次数</th>
                        <th>自习时长</th>
                        <th>状态</th>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${credits}" var="c" >
                        <tr>
                            <td id="id_${c.id}">${c.uid}</td>
                            <td id="reserve_${c.id}">${c.reserve}</td>
                            <td id="cancel_${c.id}">${c.cancel}</td>
                            <td id="sign_${c.id}">${c.sign}</td>
                            <td id="late_${c.id}">${c.late}</td>
                            <td id="violate_${c.id}">${c.violate}</td>
                            <td id="time_${c.id}">${c.time} h</td>
                            <td id="status_${c.id}">
                                <c:if test="${c.warning == 0}">正常</c:if>
                                <c:if test="${c.warning == 1}">被警告</c:if>
                            </td>
                            <td>
                                <c:if test="${(c.late >= 5 || c.violate >= 3) && c.warning == 0 }">
                                    <a href="/credits/warning?uid=${c.uid}" onclick="return isWarning()">发出警告</a>
                                </c:if>
                                <c:if test="${ c.warning == 1 }">
                                    <a href="/credits/cancel?uid=${c.uid}" onclick="return isCanceling()">取消警告</a>
                                </c:if>
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
                            <a href="/credits/search?start=0${page.param}&&uid=${condition1.uid}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&cancel1=${condition1.cancel}&&cancel2=${condition2.cancel}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}&&time1=${condition1.time}&&time2=${condition2.time}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/credits/search?start=${page.start-page.count}${page.param}&&uid=${condition1.uid}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&cancel1=${condition1.cancel}&&cancel2=${condition2.cancel}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate} &&violate2=${condition2.violate}&&time1=${condition1.time}&&time2=${condition2.time}" aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
                            <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                                    <a href="/credits/search?start=${status.index*page.count}${page.param}&&uid=${condition1.uid}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&cancel1=${condition1.cancel}&&cancel2=${condition2.cancel}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}&&time1=${condition1.time}&&time2=${condition2.time}"
                                       <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                                    >${status.count}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/credits/search?start=${page.start+page.count}${page.param}&&uid=${condition1.uid}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&cancel1=${condition1.cancel}&&cancel2=${condition2.cancel}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}&&time1=${condition1.time}&&time2=${condition2.time}" aria-label="Next">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/credits/search?start=${page.last}${page.param}&&uid=${condition1.uid}&&reserve1=${condition1.reserve}&&reserve2=${condition2.reserve}&&cancel1=${condition1.cancel}&&cancel2=${condition2.cancel}&&sign1=${condition1.sign}&&sign2=${condition2.sign}&&late1=${condition1.late}&&late2=${condition2.late}&&violate1=${condition1.violate}&&violate2=${condition2.violate}&&time1=${condition1.time}&&time2=${condition2.time}" aria-label="Next">
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
                    <h4 class="modal-title" id="myModalLabel2">查询学生信誉信息</h4>
                </div>
                <div class="modal-body">
                    <label  style="font-size: 16px">请填写以下一个或多个查询条件：</label>
                    <div class="form-group" >
                        <label for="userid" style="font-size: 16px">学号：</label>
                        <input type="text" class="form-control" id="userid" name="userid" placeholder="请输入学号"
                               style=""  value="">
                    </div>
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%">预约次数：</label>
                        <input type="text" class="form-control" id="reserve1" name="reserve1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="reserve2" name="reserve2" placeholder="请输入最大值"
                               style="margin-left: 110px; float: left;width:40%"  value="">
                    </div>
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">取消次数：</label>
                        <input type="text" class="form-control" id="cancel1" name="cancel1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="cancel2" name="cancel2" placeholder="请输入最大值"
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
                    <div class="form-group" >
                        <label style="font-size: 16px;width:100%;margin-top: 15px">自习时长：</label>
                        <input type="text" class="form-control" id="time1" name="time1" placeholder="请输入最小值"
                               style="float: left;width:40%"  value="">
                        <input type="text" class="form-control" id="time2" name="time2" placeholder="请输入最大值"
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
</div>
</body>
</html>