<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>防疫溯源</title>
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
        function searching() {
            var uid = $("#uid").val();
            var name = $("#name").val();
            if (uid == "" || name == ""){
                alert("查询条件不可为空");
            }else if (!/\d+/.test(uid)){
                alert("学号必须为数字");
            }else if (/\d+/.test(name)){
                alert("姓名中不能包含数字");
            }else {
                $.ajax({
                    url:"/isSearch",
                    async:false,
                    datatype:"json",
                    type:"get",
                    data: {
                        "id": uid,
                        "name":name
                    },
                    success: function (result) {
                        var status = result.flag;
                        if (status == true) {
                            window.location.href='/preservation/search?id='+uid+"&&name="+name;
                            // alert("ok");
                        } else {
                            alert("该学生不存在");
                        }
                    }
                });
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
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">数据统计<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/credits">学生信誉信息</a></li>
                        <li><a href="/rooms">自习室使用情况</a></li>
                    </ul>
                </li>
                <li class="active"><a href="/preservation">防疫溯源<span class="sr-only">(current)</span></a></li>
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
    <div class="row" style="margin-top:60px">
        <div class="col-sm-6 col-sm-offset-3" style="border: 1px solid black;height: 50px;font-size:16px">

            <label style="margin-top: 13px; margin-left: 10px">学号</label>
            <input type="text"  id="uid" name="uid" placeholder="请输入学号" style="margin-left: 10px;width: 30%"  value="">
            <label style="margin-left: 50px">姓名</label>
            <input type="text"  id="name" name="name" placeholder="请输入姓名" style="margin-left: 10px;width: 30%"  value="">

            <button id="search" type="button" class="btn btn-primary" style="font-size: 14px;font-weight: bold;float: right;
                margin-top:7px;margin-right: 20px" onclick="searching()">查询</button>
        </div>
    </div>
    <div class="row" style="margin-top: 20px">
        <div class="col-sm-10 col-sm-offset-1">
            <div class="row" style="margin-top: 20px">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr class="success" style="font-size: 14px">
                        <th>学号</th>
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>宿舍</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${students}" var="s" >
                        <tr>
                            <td id="id_${s.id}">${s.id}</td>
                            <td id="name_${s.id}">${s.name}</td>
                            <td id="cellphone_${s.id}">${s.cellphone}</td>
                            <td id="dormitory_${s.id}">${s.dormitory}</td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>
            <div class="row" style="text-align: center">
                <nav>
                    <ul class="pagination">
                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/preservation/search?start=0${page.param}&&id=${condition.id}&&name=${condition.name}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/preservation/search?start=${page.start-page.count}${page.param}&&id=${condition.id}&&name=${condition.name}" aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
                            <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                                    <a href="/preservation/search?start=${status.index*page.count}${page.param}&&id=${condition.id}&&name=${condition.name}"
                                       <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                                    >${status.count}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/preservation/search?start=${page.start+page.count}${page.param}&&id=${condition.id}&&name=${condition.name}" aria-label="Next">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/preservation/search?start=${page.last}${page.param}&&id=${condition.id}&&name=${condition.name}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    </div>



</div>
</body>
</html>