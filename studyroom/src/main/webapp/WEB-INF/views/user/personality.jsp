<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/bootstrap/js/jquery.validate.min.js"></script>
    <script src="/bootstrap/js/messages_zh.js"></script>

    <script>
        $("#update").click(function(){
            $("#updateModal").modal();
        });
    </script>
    <script>
        function updating() {
            var id = $("#userid").val();
            var name = $("#name").val();
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            var cellphone = $("#cellphone").val();
            var dormitory = $("#dormitory").val();

            if (name == ""||password == ""||confirmPassword == ""||cellphone == ""||dormitory == ""){
                alert("修改信息不可为空！");
            } else if (/\d+/.test(name)){
                alert("姓名中不能包含数字！")
            } else if (password.length < 6){
                alert("密码要大于等于6位！");
            } else if (password != confirmPassword){
                alert("两次输入的密码不一致！");
            } else if (cellphone.length != 11){
                alert("手机号必须要为11位！");
            } else if (!/^[0-9]*$/.test(cellphone)){
                alert("手机号必须为数字");
            } else {
                $.ajax({
                    url:"/user/update",
                    async:false,
                    datatype:"json",
                    type:"post",
                    data: {
                        "userid":id,
                        "name":name,
                        "password":password,
                        "confirmPassword":confirmPassword,
                        "cellphone": cellphone,
                        "dormitory":dormitory
                    },
                    success:function(result){

                        $("#sid").html(result.data.id);
                        $("#sname").html(result.data.name);
                        $("#scellphone").html(result.data.cellphone);
                        $("#sdormitory").html(result.data.dormitory);
                        alert("修改成功");
                    }
                })
            }


        }
    </script>
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
                <li class="active"><a href="/user/personality">个人信息<span class="sr-only">(current)</span></a></li>
                <li><a href="/reserve">预约选座</a></li>
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
    <div class="row" style="margin-top: 100px">
        <div class="col-sm-6 col-sm-offset-3" style="border: 1px solid black">
            <div class="row" style="margin-top: 20px;margin-left: 20px">
                <label style="color:dodgerblue; font-size: 16px;font-weight: bold">基本信息</label>
                <button type ="button" id="update" data-toggle="modal" data-target="#updateModal" class="btn btn-primary" style="font-size: 14px;font-weight: bold;float: right;
                margin-top:-5px;margin-right: 50px" >修改信息</button>
                <div style="margin-top: 30px; font-size: 15px">
                    <label style="">学号：</label><label style="margin-left:100px ;width: 25%" id="sid">${student.id}</label>
                    <label>姓名：</label><label style="margin-left:100px ;width: 25%" id="sname">${student.name}</label>
                </div>
                <div style="margin-top: 30px; font-size: 15px">
                    <label>手机号：</label><label style="margin-left:85px ;width: 25%" id="scellphone">${student.cellphone}</label>
                    <label>宿舍：</label><label style="margin-left:100px ;width: 25%" id="sdormitory">${student.dormitory}</label>
                </div>

            </div>
            <div class="row" style="margin-top: 20px;margin-left: 20px">
                <label style="color:dodgerblue;font-size: 16px;font-weight: bold ">信誉信息</label>
                <div style="margin-top: 30px; font-size: 15px">
                    <label>预约次数：</label><label style="margin-left:60px;width: 10%">${credit.reserve}</label>
                    <label>取消预约次数：</label><label style="margin-left:60px;width: 10%">${credit.cancel}</label>
                    <label>正常签到次数：</label><label style="margin-left:60px;width: 10%">${credit.sign}</label>
                </div>
                <div style="margin-top: 30px; font-size: 15px">
                    <label>迟到次数：</label><label style="margin-left:60px;width: 10%">${credit.late}</label>
                    <label>违约次数：</label><label style="margin-left:90px;width: 10%">${credit.violate}</label>
                    <label>自习时长：</label><label style="margin-left:90px;width: 10%">${credit.time}h</label>
                </div>
                <div style="margin-top: 20px"></div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改个人信息</h4>
            </div>
            <div class="modal-body">
                <div class="form-group" >
                    <label for="userid" style="font-size: 16px">学号：</label>
                    <input type="text" class="form-control" id="userid" name="userid" placeholder="请输入学号"
                           style="" disabled="disabled" value="${student.id}">
                </div>
                <div class="form-group" >
                    <label for="name" style="font-size: 16px">姓名：</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名"
                           style="" value="${student.name}">
                </div>
                <div class="form-group" >
                    <label for="password" style="font-size: 16px">密码：</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"
                           style="" value="${student.password}">
                </div>
                <div class="form-group" >
                    <label for="confirmPassword" style="font-size: 16px">确认密码：</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="请再次输入密码"
                           style="" value="${student.password}">
                </div>
                <div class="form-group">
                    <label for="cellphone" style="font-size: 16px">手机号：</label>
                    <input type="text" class="form-control" id="cellphone" name="cellphone" placeholder="请输入手机号"
                           style="" value="${student.cellphone}">
                </div>
                <div class="form-group">
                    <label for="dormitory" style="font-size: 16px">宿舍地址：</label>
                    <input type="text" class="form-control" id="dormitory" name="dormitory" placeholder="请输入宿舍地址"
                           style="" value="${student.dormitory}">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                <button type="button" id="btn-update" class="btn btn-primary" onclick="updating()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>