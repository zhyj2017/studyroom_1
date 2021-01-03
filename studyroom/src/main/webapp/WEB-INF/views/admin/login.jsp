<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员登录</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/bootstrap/js/jquery.validate.min.js"></script>
    <script src="/bootstrap/js/messages_zh.js"></script>

    <script>
        $().ready(function() {
            $("#loginForm").validate({
                rules:{
                    adminid:{
                        required: true
                    },
                    password:{
                        required: true
                    }
                },
                messages:{
                    adminid:{
                        required: "账号不能为空"
                    },
                    password:{
                        required: "密码不能为空"
                    }
                },
            });
        });

    </script>
    <script>
        function isLogin(){
            var id = $("#adminid").val();
            var pwd = $("#password").val();
            var temp = false;
            if ((id != null&&id!="") && (pwd != null&&pwd!="")){
                console.log(id);
                $.ajax({
                    url:"/admin/islogin",
                    async:false,
                    datatype:"json",
                    type:"get",
                    data: {
                        "id": id,
                        "password": pwd
                    },
                    success: function (result) {
                        var status = result.flag;
                        console.log(1);
                        if (status == true) {
                            temp = true;
                        } else if (status == false){
                            alert("账号或密码有误！");
                            document.getElementById("loginForm").reset()
                            temp = false;
                        }
                    }
                });
            }
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
    <div class="row" style="margin:150px auto; width: 50%;">
        <div class="col-sm-6 col-sm-offset-3" style="border: 1px solid black">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <h1 class="text-center" style="font-family: 楷体;font-weight:bold;font-size: 30px;color:purple" >
                        管理员登录</h1>
                </div>
            </div>
            <div class="row" >
                <div>
                    <form id="loginForm" action="/admin/login" method="post" onsubmit="return isLogin()">
                        <div class="form-group" style="margin-top: 30px;margin-left: 60px; width:70%; ">
                            <label for="adminid" style="font-size: 16px">账号：</label>
                            <input type="text" class="form-control" id="adminid" name="adminid" placeholder="请输入账号"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-left: 60px; width:70%; ">
                            <label for="password" style="font-size: 16px">密码：</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"
                                   style="">
                        </div>

                        <div class="form-group text-center" style="margin-top: 30px;  width:100%">
                            <button type = "submit" class="btn btn-primary" style="width:25%; font-weight:bold;font-size:16px">登&nbsp&nbsp录</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>