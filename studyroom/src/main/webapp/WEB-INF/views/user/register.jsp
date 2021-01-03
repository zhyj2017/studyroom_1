<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/bootstrap/js/jquery.validate.min.js"></script>
    <script src="/bootstrap/js/messages_zh.js"></script>
    <script>
        $().ready(function() {
            jQuery.validator.addMethod("isId",function (value,element) {
                var id = value;
                if (!/^[0-9]*$/.test(id)){
                    return false;
                }
                return this.optional(element) || /^[0-9]*$/.test(id);
            },"学号必须为数字");
            jQuery.validator.addMethod("isCellphone",function (value,element) {
                var id = value;
                if (!/^[0-9]*$/.test(id)){
                    return false;
                }
                return this.optional(element) || /^[0-9]*$/.test(id);
            },"手机号必须为数字");
            jQuery.validator.addMethod("isName",function (value,element) {
                var name = value;
                if (/\d+/.test(name)){
                    return false;
                }
                return true;
            },"姓名中不能包含数字");
            $("#registForm").validate({
                rules:{
                    userid:{
                        required: true,
                        minlength: 9,
                        maxlength: 9,
                        isId: true
                    },
                    name:{
                        required: true,
                        isName: true
                    },
                    password:{
                        required: true,
                        minlength: 6
                    },
                    cellphone:{
                        required: true,
                        minlength: 11,
                        maxlength: 11,
                        isCellphone: true
                    },
                    confirmPassword:{
                        required:true,
                        minlength: 6,
                        equalTo: "#password"
                    },
                    dormitory:{
                        required:true
                    }
                },
                messages:{
                    userid:{
                        required: "学号不能为空",
                        minlength: "学号长度为9位",
                        maxlength: "学号长度为9位"
                    },
                    name:{
                        required: "姓名不能为空",
                    },
                    password:{
                        required: "密码不能为空",
                        minlength: "密码长度至少为6位"
                    },
                    cellphone:{
                        required: "手机号不能为空",
                        minlength: "手机号长度为11位",
                        maxlength: "手机号长度为11位"
                    },
                    confirmPassword:{
                        required: "确认密码不能为空",
                        minlength: "确认密码长度至少为6位",
                        equalTo: "两次输入的密码不一致"
                    },
                    dormitory:{
                        required: "宿舍地址不能为空"
                    }
                },
            });
        });
    </script>
    <script>
        function isRegist(){
            var id = $("#userid").val();
            var phone = $("#cellphone").val();
            var temp = false;
            $.ajax({
                url:"/isregist",
                async:false,
                datatype:"json",
                type:"get",
                data: {
                    "id": id,
                    "cellphone": phone
                },
                success: function (result) {
                    var msg = result.message;
                    if (msg == "OK") {
                        temp = true;
                        alert("注册成功！");
                    } else if (msg == "id"){
                        alert("该账号已存在！");
                        document.getElementById("registForm").reset()
                        temp = false;
                    } else if (msg == "cellphone"){
                        alert("该手机号已注册过！");
                        document.getElementById("registForm").reset()
                        temp = false;
                    }
                }
            });
            return temp;
        }
    </script>
    <style>
        .error{
            color:red;
            font-size: 16px
        }
    </style>
</head>
<body>
<div class="container-fiuld">
    <div class="row" style="margin-top: 50px">
        <div class="col-sm-6 col-sm-offset-3" style="border: 1px solid black">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <h1 class="text-center" style="font-family: 楷体;font-weight:bold;font-size: 30px;color:purple" >
                        注&nbsp&nbsp册</h1>
                </div>
            </div>
            <div class="row">
                <div>
                    <form id="registForm" action="/regist" method="post" onsubmit="return isRegist()">
                        <div class="form-group" style="margin-top: 30px;margin-left: 50px; width:35%; float:left;height:15%">
                            <label for="userid" style="font-size: 16px">学号：</label>
                            <input type="text" class="form-control" id="userid" name="userid" placeholder="请输入学号"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-right: 50px; width:35%; float:right;height:15%">
                            <label for="name" style="font-size: 16px">姓名：</label>
                            <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-left: 50px; width:35%; float:left;height:15%">
                            <label for="password" style="font-size: 16px">密码：</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-right: 50px; width:35%; float:right;height:15%">
                            <label for="cellphone" style="font-size: 16px">手机号：</label>
                            <input type="text" class="form-control" id="cellphone" name="cellphone" placeholder="请输入手机号"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-left: 50px; width:35%; float:left;height:15%">
                            <label for="confirmPassword" style="font-size: 16px">确认密码：</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="请再次输入密码"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-right: 50px; width:35%; float:right;height:15%">
                            <label for="dormitory" style="font-size: 16px">宿舍地址：</label>
                            <input type="text" class="form-control" id="dormitory" name="dormitory" placeholder="请输入宿舍地址"
                                   style="">
                        </div>
                        <div class="form-group text-center" style="margin-top: 30px; float:left; width:100%">
                            <button type = "submit" class="btn btn-primary" style="width:25%; font-weight:bold;font-size:16px">注&nbsp&nbsp册</button>
                            <button type = "reset" class="btn btn-success" style="width:25%; margin-left:45px; font-weight:bold;font-size:16px">重&nbsp&nbsp置</button>
                            <button type = "button" class="btn btn-default" style="width:25%; margin-left:45px; font-weight:bold;font-size:16px" onclick="window.location.href='/index'">返&nbsp&nbsp回</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>