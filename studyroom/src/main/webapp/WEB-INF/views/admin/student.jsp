<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>学生管理</title>
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
        function isDelete(){
            var message = confirm("你确定要删除吗？");
            if (message == true){
                alert("删除成功");
                return true;
            }else if (message == false){
                return false;
            }
        }
        function isReset(){
            var message = confirm("你确定要重置密码为666666吗？");
            if (message == true){
                alert("重置成功");
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
        function getDetails(param){
            var id = param;
            $.ajax({
                url:"/students/details",
                async:false,
                datatype:"json",
                type:"get",
                data: {
                    "id":id
                },
                success:function(result){
                    $("#userid").val(result.data.student.id);
                    $("#name").val(result.data.student.name);
                    $("#cellphone").val(result.data.student.cellphone);
                    $("#dormitory").val(result.data.student.dormitory);
                }
            })

        }

        function updating(){
            var id = $("#userid").val();
            var name = $("#name").val();
            var cellphone = $("#cellphone").val();
            var dormitory = $("#dormitory").val();
            if (name == ""||cellphone == ""||dormitory == ""){
                alert("修改信息不可为空！");
            } else if (/\d+/.test(name)){
                alert("姓名中不能包含数字！");
            } else if (cellphone.length != 11){
                alert("手机号必须要为11位！");
            } else if (!/^[0-9]*$/.test(cellphone)){
                alert("手机号必须为数字");
            } else {
                $.ajax({
                    url:"/students/update",
                    async:false,
                    datatype:"json",
                    type:"post",
                    data: {
                        "userid":id,
                        "name":name,
                        "cellphone": cellphone,
                        "dormitory":dormitory
                    },
                    success:function(result){
                        $("#id_"+id).html(result.data.id);
                        $("#name_"+id).html(result.data.name);
                        $("#cellphone_"+id).html(result.data.cellphone);
                        $("#dormitory_"+id).html(result.data.dormitory);
                        alert("修改成功");
                    }
                })
            }
        }

        function searching(){
            var id = $("#suserid").val();
            var name = $("#sname").val();
            var cellphone = $("#scellphone").val();
            var dormitory = $("#sdormitory").val();


            if (id =="" && name == "" && cellphone == "" && dormitory == ""){
                alert("查询信息不可全部为空！");
            }
            else {
                var url = "/students/search?id="+id+"&&name="+name+"&&cellphone="+cellphone+"&&dormitory="+dormitory
                window.location.href=url;
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
                <li class="active"><a href="/students">学生信息<span class="sr-only">(current)</span></a></li>
                <li><a href="/seats">座位管理</a></li>
                <li><a href="/chosen/reservations">选座管理</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">数据统计<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/credits">学生信誉信息</a></li>
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
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>宿舍</th>
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${students}" var="s" >
                        <tr>
                            <td id="id_${s.id}">${s.id}</td>
                            <td id="name_${s.id}">${s.name}</td>
                            <td id="cellphone_${s.id}">${s.cellphone}</td>
                            <td id="dormitory_${s.id}">${s.dormitory}</td>
                            <td><a id="update" data-toggle="modal" data-target="#updateModal" onclick="getDetails('${s.id}')">修改信息</a>
                                &nbsp;&nbsp;<a href="/students/reset?id=${s.id}" onclick="return isReset()">重置密码</a>&nbsp;&nbsp;
                                <a href="/students/delete?id=${s.id}" onclick="return isDelete()">删除学生</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>
            <div class="row" style="text-align: center">
                <nav>
                    <ul class="pagination">
                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/students?start=0${page.param}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
                            <a href="/students?start=${page.start-page.count}${page.param}" aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
                            <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                                    <a href="/students?start=${status.index*page.count}${page.param}"
                                       <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                                    >${status.count}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/students?start=${page.start+page.count}${page.param}" aria-label="Next">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
                            <a href="/students?start=${page.last}${page.param}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    </div>
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel1">修改学生信息</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group" >
                        <label for="userid" style="font-size: 16px">学号：</label>
                        <input type="text" class="form-control" id="userid" name="userid" placeholder="请输入学号"
                               style="" disabled="disabled" value="">
                    </div>
                    <div class="form-group" >
                        <label for="name" style="font-size: 16px">姓名：</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名"
                               style="" value="">
                    </div>
                    <div class="form-group">
                        <label for="cellphone" style="font-size: 16px">手机号：</label>
                        <input type="text" class="form-control" id="cellphone" name="cellphone" placeholder="请输入手机号"
                               style="" value="">
                    </div>
                    <div class="form-group">
                        <label for="dormitory" style="font-size: 16px">宿舍地址：</label>
                        <input type="text" class="form-control" id="dormitory" name="dormitory" placeholder="请输入宿舍地址"
                               style="" value="">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn-update" class="btn btn-primary" onclick="updating()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel2">查询学生信息</h4>
                </div>
                <div class="modal-body">
                    <label  style="font-size: 16px">请填写以下一个或多个查询条件：</label>
                    <div class="form-group" >
                        <label for="suserid" style="font-size: 16px">学号：</label>
                        <input type="text" class="form-control" id="suserid" name="suserid" placeholder="请输入学号"
                               style=""  value="">
                    </div>
                    <div class="form-group" >
                        <label for="sname" style="font-size: 16px">姓名：</label>
                        <input type="text" class="form-control" id="sname" name="sname" placeholder="请输入姓名"
                               style="" value="">
                    </div>
                    <div class="form-group">
                        <label for="scellphone" style="font-size: 16px">手机号：</label>
                        <input type="text" class="form-control" id="scellphone" name="scellphone" placeholder="请输入手机号"
                               style="" value="">
                    </div>
                    <div class="form-group">
                        <label for="sdormitory" style="font-size: 16px">宿舍地址：</label>
                        <input type="text" class="form-control" id="sdormitory" name="sdormitory" placeholder="请输入宿舍地址"
                               style="" value="">
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